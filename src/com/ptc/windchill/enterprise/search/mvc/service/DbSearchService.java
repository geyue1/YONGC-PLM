/* bcwti
 *
 * Copyright (c) 2010 Parametric Technology Corporation (PTC). All Rights Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */
package com.ptc.windchill.enterprise.search.mvc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import wt.fc.FilterResultProcessor;
import wt.fc.ReferenceFactory;
import wt.fc.ResultProcessorLimitException;
import wt.fc.WTReference;
import wt.log4j.LogR;
import wt.pds.AccessController;
import wt.pds.PartialResultException;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTMessage;
import wt.util.WTPropertyVetoException;
import wt.vc.Iterated;

import com.infoengine.object.factory.Element;
import com.ptc.core.command.common.bean.repository.PageMode;
import com.ptc.core.command.common.bean.repository.ResultContainer;
import com.ptc.core.command.common.delegate.CommandDelegateUtility;
import com.ptc.core.components.export.table.ExportListWriter;
import com.ptc.core.meta.common.AttributeTypeIdentifier;
import com.ptc.core.meta.common.BasicSortSpec;
import com.ptc.core.meta.common.TypeIdentifier;
import com.ptc.core.meta.common.TypeInstanceIdentifier;
import com.ptc.core.meta.type.common.TypeInstance;
import com.ptc.core.query.command.common.BasicQueryCommand;
import com.ptc.core.query.common.CriteriaAugmentor;
import com.ptc.core.query.common.CriterionSpec;
import com.ptc.core.query.common.QueryException;
import com.ptc.core.query.common.ResultSpec;
import com.ptc.core.query.common.impl.BasicTypePagingSession;
import com.ptc.core.query.common.impl.LatestCriteriaAugmentor;
import com.ptc.core.query.common.impl.NoOpCriteriaAugmentor;
import com.ptc.core.query.server.impl.BasicQueryService;
import com.ptc.mvc.client.feedback.ClientFeedback;
import com.ptc.mvc.client.feedback.ClientFeedbackType;
import com.ptc.mvc.client.feedback.DefaultClientFeedback;
import com.ptc.mvc.components.ComponentResultProcessor;
import com.ptc.netmarkets.search.SearchWebConstants;
import com.ptc.netmarkets.search.utils.SearchUtils;
import com.ptc.windchill.enterprise.search.client.searchClientResource;
import com.ptc.windchill.enterprise.search.common.Track;
import com.ptc.windchill.enterprise.search.server.ExcludedTypesHelper;
import com.ptc.windchill.enterprise.search.server.PrintUtility;
import com.ptc.windchill.enterprise.search.server.SearchConstants;
import com.ptc.windchill.enterprise.search.server.SearchCriteriaHelper;
import com.ptc.windchill.enterprise.search.server.SearchCriteriaUtil;
import com.ptc.windchill.enterprise.search.server.SearchPagingHelper;
import com.ptc.windchill.enterprise.search.server.SearchPagingInfo;
import com.ptc.windchill.enterprise.search.server.SearchQueryHelper;
import com.ptc.windchill.enterprise.search.server.SearchSortHelper;

public class DbSearchService extends AbstractQueryService {

    private static final String CLASSNAME = DbSearchService.class.getName();
    private static final Logger logger = LogR.getLogger(CLASSNAME);

    PacketManager pm = new DefaultPacketManager();
    int recRequested = 0;
    int recToQuery = 0;
    boolean usePaging = true;
    boolean streamResult = false;
    boolean streamMaxResults = false;
    private static final ReferenceFactory referenceFactory = new ReferenceFactory();


    static {
        if ( SearchConstants.VERBOSE_SEARCH ) {
            logger.setLevel( Level.ALL );
        }
    }

    /**
     * @return Iterator<SearchResultItem>
     * @throws WTException
     */
    @Override
    public Iterator<SearchResultItem> query() throws WTException {
        Iterator<SearchResultItem> retIt = null;

        final ComponentResultProcessor processor = (ComponentResultProcessor) searchInfo.getHmGenSearchAttr().get(SearchWebConstants.COMPONENT_RESULT_PROCESSOR);
        if(processor!=null) {
            streamResult = !searchInfo.isIndexSearch() && searchInfo.isResultItemUfidType();
        }
        final long getPage = Track.getTime();

        final int maxResults = searchInfo.getMaxResults();

        if(streamResult)  {
            // data source
            final ComponentResultProcessorIFC componentResultProcessor = new DataSourceComponentResultProcessor(processor);
            streamResult(componentResultProcessor);
        } else if(maxResults>-1) {
            // auto suggest
            // SPR 2171158 GE
            // use streaming for auto suggest. This allows to enforce searchLimit and also get partial result set.
            streamMaxResults = true;
            // set attributes for suggestible
            Map<String, AttributeTypeIdentifier[]> mapAttToATI = SearchQueryHelper.setFilterAttributes(searchInfo);
            final MaxResultsComponentResultProcessor searchComponentResultProcessor = new MaxResultsComponentResultProcessor(resultProcessor, maxResults, searchInfo, mapAttToATI);
            searchComponentResultProcessor.setNeedTypeInstance(true);
            streamResult(searchComponentResultProcessor);
        }
        else {
            usePaging = SearchConstants.useDbPaging && !searchInfo.isIndexSearch();
            retIt = getPage(false);
        }

        Track.printTime("DB firstPage", getPage);

        return retIt;
    }

    protected void streamResult(final ComponentResultProcessorIFC componentResultProcessor) throws WTException {
        final BasicQueryService QUERY_SERVICE = BasicQueryService.getInstance();

        final BasicSortSpec sortSpec = SearchSortHelper.getSortSpec(searchInfo);

        final CriterionSpec criteria = SearchCriteriaHelper.getSearchCriteria(searchInfo);

        if (criteria == null) {
            return;
        }

        ResultSpec resultSpec = null;

        try {
            resultSpec = getResultSpec();
        } catch (final WTPropertyVetoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final boolean latestIterationOnly = searchInfo.getCriteriaInfo().getIteration().equalsIgnoreCase(SearchConstants.LATEST);
        final boolean latest  = latestIterationOnly && !searchInfo.isOrInObjRef();
        //We need to explicitly augment latest iteration criteria
        if(latest && SearchCriteriaHelper.isAnyTargetTypeAsClass(Iterated.class, searchInfo)) {
            final CriteriaAugmentor latestIterationCriteriaAugmentor = new LatestCriteriaAugmentor();
            criteria.augmentCriteria(latestIterationCriteriaAugmentor,resultSpec);
        }

        if(logger.isDebugEnabled()) {
            logBeforeSearch(sortSpec, criteria);
        }
        // We will send from offset (will ignore rows till offset)
        // Query Layer stream takes care of access control
        // search within project can filter out the objects, so cannot be sure about the offset
        int offset = 0;
        if(!searchInfo.getCriteriaInfo().isSearchWithinProject()) {
            componentResultProcessor.setOffSetData(true);
            if(searchInfo.getHmGenSearchAttr()!=null) {
                final Object obj = searchInfo.getHmGenSearchAttr().get(SearchWebConstants.PAGE_OFFSET);
                if(obj!=null) {
                    offset = (Integer)obj;
                }
            }
        }

        // by default, no limit (-1)
        int searchLimit = -1;

        final AccessController ac = resultSpec.getAccessController();
        FilterResultProcessor frp = null;
        // We have to apply limit to how many records access controller should process
        // we don't need limit for query without access controller
        if(ac!=null) {
            searchLimit = SearchPagingHelper.getPagingQueryLimit(searchInfo);
            frp = ac.getFilter();
        }

        final long qsTime = Track.getTime();
        // We need catch ResultProcessorLimitException as well as PartialResultException and do nothing
        // as feedback is already sent to MVC by StreamingResultCollector
        try {
            final StreamingResultCollector resultCollector = new StreamingResultCollector(componentResultProcessor, offset, frp, searchLimit, searchInfo);
            resultCollector.setQueryStartTime();
            // latest param passed here just determines the output TypeInstance type, whether OR or VR
            QUERY_SERVICE.query(resultSpec, criteria, sortSpec, searchInfo.getLocale(), latest, searchLimit, resultCollector);
            // search within project requires post processing
            resultCollector.filterObjectsWithinNonProject();
            // perf logging
            resultCollector.printLastChunkTime();
        } catch (final ResultProcessorLimitException e) {
            handleLimitException(searchLimit, e, null, componentResultProcessor);
        } catch (final PartialResultException e) {
            handleLimitException(searchLimit, e, null, componentResultProcessor);
        } catch (final WTException wte){
            final Throwable cause = wte.getNestedThrowable();
            if(cause instanceof PartialResultException || cause instanceof ResultProcessorLimitException) {
                handleLimitException(searchLimit, wte, cause, componentResultProcessor);
            } else if(wte instanceof QueryException){
                logger.debug(wte.getLocalizedMessage(),wte);
                throw new WTException(cause,WTMessage.getLocalizedMessage(SearchWebConstants.SEARCH_CLIENT_RESOURCE,
                        "INVALID_CRITERIA_ERROR", null, searchInfo.getLocale()));
            }
            else {
                throw wte;
            }
        }

        // end of results
        componentResultProcessor.endOfResults();

        Track.printTime("QUERY_SERVICE", qsTime);
    }

    private void handleLimitException(final int searchLimit, final Throwable t, final Throwable cause, final ComponentResultProcessorIFC processor) {
        if(logger.isDebugEnabled()) {
            logger.debug("Exception from QUERY_SERVICE due to result size excceded the limit : "+searchLimit);
            logger.debug("Original exception : "+ t);
            logger.debug("Cause : "+ cause);
        }
        if (processor != null) {
            try {
                final List<ClientFeedback> feedbacks = new ArrayList<ClientFeedback>();
                String feedback = SearchConstants.RESULT_INCOMPLETE_KEY + SearchUtils.getClientResourceMsg(searchClientResource.INCOMPLETE_DB_RESULT_SET_WARNING, SessionHelper.getLocale());
                feedback = SearchUtils.getClientResourceMsg(searchClientResource.INCOMPLETE_DB_RESULT_SET_WARNING, SessionHelper.getLocale());
                final ClientFeedback clientFeedback = new DefaultClientFeedback(ClientFeedbackType.PARTIAL_RESULTS_DISPLAYED, feedback, null);
                feedbacks.add(clientFeedback);
                processor.addFeedback(feedbacks);
                processor.sendFeedback(SearchConstants.RESULT_INCOMPLETE_KEY, searchClientResource.INCOMPLETE_DB_RESULT_SET_WARNING);
            } catch (final WTException e) {
                logger.error(e.getLocalizedMessage(), e);
            }
        }
    }

    private void logBeforeSearch(final BasicSortSpec sortSpec, final CriterionSpec criteria) {
        logger.debug("@@@ SortSpec = \n"+sortSpec);
        logger.debug("@@@ SearchInfo before search = " + searchInfo);
        logger.debug("@@@ Search Criteria before search = " + criteria);
    }

    /**
     * The method queries DB using BasicQueryCommand
     * @return Iterator<SearchResultItem>
     * @throws WTException
     */
    @Override
    public Iterator<SearchResultItem> getPage(final boolean requery) throws WTException {

        // streaming pushes the result to component processor and hence no paging concept
        if(streamResult | streamMaxResults) {
            return null;
        }

        Iterator<SearchResultItem> retIt =  null;

        if(!resultProcessor.isEndReached() || !usePaging) {
            resultProcessor.clear();

            try {

                ResultContainer rc = null;
                final SearchPagingInfo pagingInfo = searchInfo.getPagingInfo();

                if(usePaging && pagingInfo!=null && pagingInfo.getPagingSessionId() > 0 ) {
                    rc = getNextPageFromSession();
                }
                else {

                    final CriterionSpec criteria = SearchCriteriaHelper.getSearchCriteria(searchInfo);

                    if (criteria == null) {
                        return null;
                    }

                    BasicQueryCommand cmd = new BasicQueryCommand();

                    SearchQueryHelper.setFilterAttributes(searchInfo);

                    initPagingParameters(cmd);

                    final BasicSortSpec sortSpec = SearchSortHelper.getSortSpec(searchInfo);
                    if(logger.isDebugEnabled()) {
                        logger.debug("@@@ SortSpec = \n"+sortSpec);
                    }
                    cmd.setSort(sortSpec);

                    final boolean latestIterationOnly = searchInfo.getCriteriaInfo().getIteration().equalsIgnoreCase(SearchConstants.LATEST);
                    if (!latestIterationOnly || searchInfo.isOrInObjRef()) {
                        cmd.setCriteriaAugmentor(new NoOpCriteriaAugmentor());
                    }

                    cmd.setCriteriaSpec(criteria);
                    CommandDelegateUtility.setReadOnlyOptimizationsEnabled(true);

                    final ResultSpec resultSpec = getResultSpec();

                    cmd.setResult(resultSpec);

                    if(logger.isDebugEnabled()) {
                        logBeforeSearch(sortSpec, criteria);
                    }

                    final long cmdTime = Track.getTime();

                    if(searchInfo.isLatestVersionControllerNeeded()) {
                        setPageModeNone(cmd, resultProcessor);
                    }

                    cmd = (BasicQueryCommand)cmd.execute();
                    Track.printTime("BasicQueryCommand", cmdTime);

                    rc = cmd.getResultContainer();

                    setPagingSession(cmd);

                } //if !get page from pageSession

                boolean resultFound = true;
                if (rc == null || rc.getSize()==0) {
                    logger.debug("invoke() -> RETURNING NO RESULTS");
                    resultFound = false;
                }
                // Db search service shares same result processor with index service
                if(!searchInfo.isIndexSearch() && (!usePaging || !resultFound || rc.getSize() < recToQuery) ) {
                    resultProcessor.setEndReached();
                }


                if (rc != null) {
                    if(logger.isDebugEnabled()) {
                        PrintUtility.print(rc, "\n@@@ PRINTING RESULTCONTAINER CONTAINING " +rc.getSize() + " TYPEINSTANCES:", logger);
                    }

                    Collection<WTReference> postProcessingPacket = null;
                    Map<WTReference, TypeInstanceIdentifier> refToContainerMap = null;
                    // IndexService already filters for project but not for Project + Product
                    final boolean postProcessForProject = searchInfo.getCriteriaInfo().isSearchWithinProject()
                            && (!searchInfo.isIndexSearch() ||  searchInfo.getCriteriaInfo().isSearchWithinNonProjectContainer());

                    if(postProcessForProject) {
                        postProcessingPacket = new ArrayList<WTReference>();
                        if(searchInfo.getCriteriaInfo().isSearchWithinNonProjectContainer()) {
                            refToContainerMap = new HashMap<WTReference, TypeInstanceIdentifier>();
                        }
                    }

                    for (int i = 0; i < rc.getSize(); i++) {
                        final TypeInstance ti = rc.clearTypeInstanceAt(i);
                        ti.purgeDefaultContent();

                        if(searchInfo.isResultItemUfidType()){
                            final String ufid = ti.getPersistenceIdentifier();

                            if(postProcessForProject) {
                                final WTReference ufidRef = referenceFactory.getReference(ufid);
                                postProcessingPacket.add(ufidRef);
                                SearchCriteriaUtil.populateObjToContainerMap(searchInfo, ti, ufidRef, refToContainerMap);
                            }
                            else {
                                resultProcessor.addItem(ufid);
                            }
                        }
                        else{
                            //TODO:We can support post processing for Project for element item in future if specific need arises
                            final Element ele = new Element(ti);
                            resultProcessor.addItem(ele);
                        }
                    }
                    if(postProcessForProject) {
                        filterObjectsWithinNonProject(postProcessingPacket, refToContainerMap);
                    }
                }

                retIt = resultProcessor.iterator();

            } catch (final WTPropertyVetoException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return retIt;

    }

    /**
     * Sets the page mode none.
     * 
     * @param cmd
     *            the cmd
     * @param resultProcessor
     *            the result processor
     * @throws UnsupportedOperationException
     *             the unsupported operation exception
     * @throws WTPropertyVetoException
     *             the wT property veto exception
     */
    protected void setPageModeNone(final BasicQueryCommand cmd, final AbstractSearchResultProcessor resultProcessor)
            throws UnsupportedOperationException, WTPropertyVetoException {
        cmd.setPageMode(PageMode.NONE);
        cmd.setPagingSnapshotQueryLimit(SearchPagingHelper.getPagingQueryLimit(searchInfo));
        // Since page mode is set to none, paging session is not created. So marking end of results.
        // So next page request will return null.
        resultProcessor.setEndReached();
    }

    /** Project container filtering
     * @param postProcessingPacket
     * @param refToContainerMap
     * @throws WTException
     */
    public void filterObjectsWithinNonProject(final Collection<WTReference> postProcessingPacket, final Map<WTReference, TypeInstanceIdentifier> refToContainerMap) throws WTException {
        if(postProcessingPacket!=null && postProcessingPacket.size()>0) {
            final Collection filteredList = SearchCriteriaUtil.filterObjectsWithinNonProject(searchInfo, postProcessingPacket, refToContainerMap);
            for(final Object r:filteredList) {
                final WTReference ref = (WTReference) r;
                resultProcessor.addItem(ref);
            }
        }
    }

    private ResultSpec getResultSpec() throws WTPropertyVetoException, WTException {
        final ResultSpec resultSpec = SearchQueryHelper.getResultSpec(searchInfo);

        // IndexService does consider exclude subtypes, so no need in case of index search
        if(!searchInfo.isIndexSearch()) {
            TypeIdentifier[] excludeSubtypes = searchInfo.getExcludeSubtypes();

            excludeSubtypes = ExcludedTypesHelper.addExcludedSubTypes(SearchConstants.PART_TYPE_ID,
                    searchInfo.getTargetDataTypes(), "", excludeSubtypes);

            if (excludeSubtypes!=null && excludeSubtypes.length > 0) {
                resultSpec.setExcludedTypes(excludeSubtypes);
            }
        }
        return resultSpec;
    }

    private void setPagingSession(final BasicQueryCommand cmd) throws WTPropertyVetoException {
        if(usePaging) {
            final SearchPagingInfo pagingInfo = searchInfo.getPagingInfo();
            final BasicTypePagingSession pagingSession = (BasicTypePagingSession)cmd.getResultSession();
            if (pagingSession != null) {
                pagingInfo.setPagingSessionId( pagingSession.getSessionId() );
                pagingInfo.setOffset(pagingSession.getOffset());
            }
            pagingInfo.setPagingSession(pagingSession);
        }
    }

    private ResultContainer getNextPageFromSession() throws WTException, WTPropertyVetoException {
        final SearchPagingInfo pagingInfo = searchInfo.getPagingInfo();
        pagingInfo.setOffset(recRequested);
        recToQuery = pm.getSize();
        recRequested += recToQuery;
        pagingInfo.setPageCount(recToQuery);
        final ResultContainer rc = SearchPagingHelper.getResultFromPagingSession(searchInfo);
        if (rc != null) {
            if(logger.isDebugEnabled()) {
                logger.debug("search -> returned "+rc.getSize()+" TypeInstances from pagingSession");
            }
        }
        return rc;
    }

    private void initPagingParameters(final BasicQueryCommand cmd) throws WTPropertyVetoException {
        // paging mode and limit cannot be checked at query layer
        // limit is checked at MVC
        if(!usePaging) {
            cmd.setPageMode(PageMode.NONE);
        }
        else {
            try {
                recToQuery = pm.getSize();
                recRequested = recToQuery;
                searchInfo.setPagingInfo(SearchPagingHelper.initializePagingParameters(recRequested));
            } catch (final WTException e) {
            }
            final SearchPagingInfo pagingInfo = searchInfo.getPagingInfo();
            cmd.setPageMode(pagingInfo.getPageMode());
            cmd.setOffset(pagingInfo.getOffset());
            cmd.setCount(pagingInfo.getPageCount());

            final Object exportQueryLimit = searchInfo.getHmGenSearchAttr().get(SearchWebConstants.KEY_EXPORT_QUERY_LIMIT);
            if (exportQueryLimit != null) {
                // Export would impose a limit size so that the query data number would not exceed it.
                // TODO: exportQueryLimit is always -1, so the block below would never be called.
                // But we need to enable the LIMIT mode and setCount in the future if they works.
                if (Integer.parseInt(exportQueryLimit.toString()) > 0) {
                    cmd.setPageMode(PageMode.LIMIT);
                    cmd.setCount(ExportListWriter.getExportFullListLimit());
                }

                // SPR 2042308. setPagingSnapshotQueryLimit to -1 so that PartialResultException would not be thrown.
                cmd.setPagingSnapshotQueryLimit(-1);
            }

            // set search specific query limit. This will override wt.pom.paging.snapshotQueryLimit.
            // setting the limit to command leads the command to throw exception when result size exceeds the limit, not useful
            //cmd.setPagingSanpshotQueryLimit(SearchPagingHelper.getPagingQueryLimit());
        }
    }

}
