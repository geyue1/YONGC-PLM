package wt.indexsearch;

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import wt.util.WTException;

public class SolrQueryHelperTest {
    public static final String OR = " OR ";

    public static final String AND = " AND ";

    String searchOperator = OR;

    public TranslationSolrQueryHelper buildTranslationSolrQueryHelper(Locale clientLocale, List<Locale> sourceLocales)
            throws WTException {
        TranslationSolrQueryHelper helper = createMockBuilder(TranslationSolrQueryHelper.class).addMockedMethods(
                "initClientLocale", "getClientLocale",
                "getTranslationQueryBuilder").createMock();
        helper.initClientLocale();
        expectLastCall();
        expect(helper.getClientLocale()).andReturn(clientLocale).anyTimes();

        // build leadingWildcardQueryBuilder
        AbstractTranslationKeywordQueryBuilder leadingWildcardQueryBuilder = createMockBuilder(
                LeadingWildcardTranslationKeywordQueryBuilder.class).addMockedMethods("getSourceLocaleList")
                .createMock();
        expect(leadingWildcardQueryBuilder.getSourceLocaleList()).andReturn(sourceLocales).anyTimes();
        replay(leadingWildcardQueryBuilder);
        leadingWildcardQueryBuilder.setClientLocale(clientLocale);

        // build queryBuilder
        AbstractTranslationKeywordQueryBuilder queryBuilder = createMockBuilder(
                TranslationKeywordQueryBuilder.class).addMockedMethods("getSourceLocaleList").createMock();
        expect(queryBuilder.getSourceLocaleList()).andReturn(sourceLocales).anyTimes();
        replay(queryBuilder);
        queryBuilder.setClientLocale(clientLocale);

        // set query builder
        expect(helper.getTranslationQueryBuilder(true)).andReturn(leadingWildcardQueryBuilder).anyTimes();
        expect(helper.getTranslationQueryBuilder(false)).andReturn(queryBuilder).anyTimes();
        replay(helper);

        helper.setClientLocale(clientLocale);
        return helper;
    }

    @Test
    public void test_getLangugeQuery_clientLangEnglish() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf", langs);
        assertEquals("(keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf))", query);
    }

    @Test
    public void test_getLangugeQuery_clientLangEnglish_multiword() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf cart", langs);
        assertEquals(
                "((keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf)) AND (keywords_en:(cart) OR keywords_ja:(cart) OR translated_en:(cart)))",
                query);
    }

    @Test
    public void test_getLangugeQuery_clientLangEnglish_leadingWildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("*golf", langs);
        assertEquals(
                "(keywordsLeadingWildcard:(*golf) OR en_translatedLeadingWildcard:(*golf) OR keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf))",
                query);
    }

    @Test
    public void test_getLangugeQuery_clientLangEnglish_tralingWildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf*", langs);
        assertEquals("(keywords_en:(golf* OR golf) OR keywords_ja:(golf* OR golf) OR translated_en:(golf* OR golf))",
                query);
    }

    @Test
    public void test_getLangugeQuery_clientLangEnglish_leadingWildcard_multiword() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("*golf cart", langs);
        assertEquals(
                "((keywordsLeadingWildcard:(*golf) OR en_translatedLeadingWildcard:(*golf) OR keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf)) AND (keywords_en:(cart) OR keywords_ja:(cart) OR translated_en:(cart)))",
                query);
    }

    @Test
    public void test_getLangugeQuery_clientLangEnglish_trailingWildcard_multiword() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf cart*", langs);
        assertEquals(
                "((keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf)) AND (keywords_en:(cart* OR cart) OR keywords_ja:(cart* OR cart) OR translated_en:(cart* OR cart)))",
                query);
    }

    @Test
    public void test_getLangugeQuery_clientLangRoot() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        Locale clientLocale = Locale.ROOT;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRANCE);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf", langs);
        assertEquals(
                "(keywords_en:(golf) OR keywords_ja:(golf) OR translated_master_en:(golf) OR translated_master_fr-FR:(golf))",
                query);
    }

    @Test
    public void test_getLangugeQuery_clientLangRoot_leadingWildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        Locale clientLocale = Locale.ROOT;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("*golf", langs);
        assertEquals(
                "(keywordsLeadingWildcard:(*golf) OR master_translatedLeadingWildcard:(*golf) OR keywords_en:(golf) OR keywords_ja:(golf) OR translated_master_en:(golf) OR translated_master_fr:(golf))",
                query);
    }

    @Test
    public void test_getLangugeQuery_clientLangRoot_multiword() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ROOT;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf cart", langs);
        assertEquals(
                "((keywords_en:(golf) OR keywords_ja:(golf) OR translated_master_en:(golf) OR translated_master_fr:(golf)) AND (keywords_en:(cart) OR keywords_ja:(cart) OR translated_master_en:(cart) OR translated_master_fr:(cart)))",
                query);
    }

    @Test
    public void test_getLangugeQuery_trailingWildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");

        String query = queryHelper.getLangugeQuery("golf*", langs);
        assertEquals("(keywords_en:(golf* OR golf) OR keywords_ja:(golf* OR golf) OR translated_en:(golf* OR golf))",
                query);
    }

    @Test
    public void test_getLangugeQuery_leadingWildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");

        String query = queryHelper.getLangugeQuery("*golf", langs);
        assertEquals(
                "(keywordsLeadingWildcard:(*golf) OR en_translatedLeadingWildcard:(*golf) OR keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf))",
                query);
    }

    @Test
    public void test_getLangugeQuery_leadingTrailingWildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");

        String query = queryHelper.getLangugeQuery("*golf*", langs);
        assertEquals(
                "(keywordsLeadingWildcard:(*golf*) OR en_translatedLeadingWildcard:(*golf*) OR keywords_en:(golf* OR golf) OR keywords_ja:(golf* OR golf) OR translated_en:(golf* OR golf))",
                query);
    }

    @Test
    public void test_getLangugeQuery_multiWord_LT_Wildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");

        String query = queryHelper.getLangugeQuery("*golf cart*", langs);
        assertEquals(
                "((keywordsLeadingWildcard:(*golf) OR en_translatedLeadingWildcard:(*golf) OR keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf)) AND (keywords_en:(cart* OR cart) OR keywords_ja:(cart* OR cart) OR translated_en:(cart* OR cart)))",
                // "((keywordsLeadingWildcard:((*golf OR golf))) OR ((keywords_en:(golf)) OR (keywords_ja:(golf)))) OR ((keywords_en:((cart* OR cart))) OR (keywords_ja:((cart* OR cart))))",
                query);
    }

    @Test
    public void test_getLangugeQuery_multiWord_L_Wildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");

        String query = queryHelper.getLangugeQuery("*golf cart", langs);
        assertEquals(
                "((keywordsLeadingWildcard:(*golf) OR en_translatedLeadingWildcard:(*golf) OR keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf)) AND (keywords_en:(cart) OR keywords_ja:(cart) OR translated_en:(cart)))",
                // "((keywordsLeadingWildcard:((*golf OR golf))) OR ((keywords_en:(golf)) OR (keywords_ja:(golf)))) OR ((keywords_en:(cart)) OR (keywords_ja:(cart)))",
                query);
    }

    @Test
    public void test_getLangugeQuery_multiWord_LL_Wildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");

        String query = queryHelper.getLangugeQuery("*golf *cart", langs);
        assertEquals(
                "((keywordsLeadingWildcard:(*golf) OR en_translatedLeadingWildcard:(*golf) OR keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf)) AND (keywordsLeadingWildcard:(*cart) OR en_translatedLeadingWildcard:(*cart) OR keywords_en:(cart) OR keywords_ja:(cart) OR translated_en:(cart)))",
                // "((keywordsLeadingWildcard:((*golf OR golf) OR (*cart OR cart))) OR ((keywords_en:(golf OR cart)) OR (keywords_ja:(golf OR cart))))",
                query);
    }

    @Test
    public void test_getLangugeQuery_multiWord_LTT_Wildcard() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");

        String query = queryHelper.getLangugeQuery("*golf* cart*", langs);
        assertEquals(
                "((keywordsLeadingWildcard:(*golf*) OR en_translatedLeadingWildcard:(*golf*) OR keywords_en:(golf* OR golf) OR keywords_ja:(golf* OR golf) OR translated_en:(golf* OR golf)) AND (keywords_en:(cart* OR cart) OR keywords_ja:(cart* OR cart) OR translated_en:(cart* OR cart)))",
                // "((keywordsLeadingWildcard:((*golf* OR golf* OR golf))) OR ((keywords_en:(golf* OR golf)) OR (keywords_ja:(golf* OR golf))))"
                // +
                // " OR ((keywords_en:((cart* OR cart))) OR (keywords_ja:((cart* OR cart))))",
                query);
    }

    @Test
    public void test_getLangugeQuery_singleCharWildCard_1() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf *", langs);
        assertEquals("(keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf))",
                // "(keywordsLeadingWildcard:(*)) OR ((keywords_en:(golf)) OR (keywords_ja:(golf)))",
                query);
    }

    @Test
    public void test_getLangugeQuery_singleCharWildCard_2() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf ?", langs);
        // assertEquals("(keywordsLeadingWildcard:(?)) OR ((keywords_en:(golf)) OR (keywords_ja:(golf)))",
        assertEquals("(keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf))",
                query);
    }

    @Test
    public void test_getLangugeQuery_singleCharWildCard_3() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("golf* *", langs);
        assertEquals("(keywords_en:(golf* OR golf) OR keywords_ja:(golf* OR golf) OR translated_en:(golf* OR golf))",
                // "(keywordsLeadingWildcard:(*)) OR ((keywords_en:((golf* OR golf))) OR (keywords_ja:((golf* OR golf))))",
                query);
    }

    @Test
    public void test_getLangugeQuery_singleCharWildCard_4() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("*golf *", langs);
        assertEquals(
                "(keywordsLeadingWildcard:(*golf) OR en_translatedLeadingWildcard:(*golf) OR keywords_en:(golf) OR keywords_ja:(golf) OR translated_en:(golf))",
                // "((keywordsLeadingWildcard:((*golf OR golf) OR *)) OR ((keywords_en:(golf)) OR (keywords_ja:(golf))))",
                query);
    }

    @Test
    public void test_getLangugeQuery_singleCharWildCard_5() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(searchOperator);
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("*golf* *", langs);
        assertEquals(
                "(keywordsLeadingWildcard:(*golf*) OR en_translatedLeadingWildcard:(*golf*) OR keywords_en:(golf* OR golf) OR keywords_ja:(golf* OR golf) OR translated_en:(golf* OR golf))",
                // "((keywordsLeadingWildcard:((*golf* OR golf* OR golf) OR *)) OR ((keywords_en:(golf* OR golf)) OR (keywords_ja:(golf* OR golf))))",
                query);
    }

    @Test
    public void test_SPR_2169217_AND() throws WTException {
        // ((car* OR car) AND design12) Added brackets around [car* OR car]
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(" AND ");
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        String query = queryHelper.getLangugeQuery("*car* *design12", langs);
        assertEquals(
                "((keywordsLeadingWildcard:(*car*) OR en_translatedLeadingWildcard:(*car*) OR keywords_en:(car* OR car) OR translated_en:(car* OR car))"
                        + " AND (keywordsLeadingWildcard:(*design12) OR en_translatedLeadingWildcard:(*design12) OR keywords_en:(design12) OR translated_en:(design12)))",
                // "((keywordsLeadingWildcard:((*car* OR car* OR car) AND (*design12 OR design12))) OR ((keywords_en:((car* OR car) AND design12))))",
                query);
    }

    @Test
    public void test_SPR_2169217_OR() throws WTException {
        // ((car* OR car) AND design12) Added brackets around [car* OR car]
        SolrQueryHelper queryHelper = new SolrQueryHelper(OR);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.FRENCH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        // SolrQueryHelper queryHelper = new SolrQueryHelper(" OR ");
        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        String query = queryHelper.getLangugeQuery("*car* *design12", langs);
        assertEquals(
                "((keywordsLeadingWildcard:(*car*) OR en_translatedLeadingWildcard:(*car*) OR keywords_en:(car* OR car) OR translated_en:(car* OR car))"
                        + " OR (keywordsLeadingWildcard:(*design12) OR en_translatedLeadingWildcard:(*design12) OR keywords_en:(design12) OR translated_en:(design12)))",
                // "((keywordsLeadingWildcard:((*car* OR car* OR car) OR (*design12 OR design12))) OR ((keywords_en:(car* OR car OR design12))))",
                query);
    }
    
    /**
     * This test case is for testing phase query with single language
     * 
     * @throws WTException
     */
    @Test
    public void test_getLangugeQuery_phaseQuery1() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        String query = queryHelper.getLangugeQuery("\\\"is not listed\\\"",langs);
        assertEquals(
                "(keywords_en:(\"is not listed\") OR translated_en:(\"is not listed\"))",
                query);
    }

    /**
     * This test case is for testing phase query with multiple language
     * 
     * @throws WTException
     */
    @Test
    public void test_getLangugeQuery_phaseQuery2() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);
        sourceLocales.add(Locale.JAPANESE);
        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");
        langs.add("ja");
        String query = queryHelper.getLangugeQuery("\\\"is not listed\\\"",
                langs);
        assertEquals(
                "(keywords_en:(\"is not listed\") OR keywords_ja:(\"is not listed\") OR translated_en:(\"is not listed\"))",
                query);
    }

    /**
     * This test case is for testing phase query with single language and having extra double quote in middle
     * 
     * @throws WTException
     */
    @Test
    public void test_getLangugeQuery_phaseQuery3() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);

        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");

        String query = queryHelper.getLangugeQuery("\\\"is not \\\"listed\\\"", langs);
        assertEquals(
                "(keywords_en:(\"is not \\\"listed\") OR translated_en:(\"is not \\\"listed\"))",
                query);
    }

    /**
     * This test case is for testing keyword having double quote only at the start but not at the end
     * 
     * @throws WTException
     */
    @Test
    public void test_getLangugeQuery_phaseQuery4() throws WTException {
        SolrQueryHelper queryHelper = new SolrQueryHelper(AND);
        Locale clientLocale = Locale.ENGLISH;
        List<Locale> sourceLocales = new ArrayList<Locale>();
        sourceLocales.add(Locale.ENGLISH);

        TranslationSolrQueryHelper translationHelper = buildTranslationSolrQueryHelper(clientLocale, sourceLocales);
        queryHelper.setTranslationHelper(translationHelper);

        ArrayList<String> langs = new ArrayList<String>();
        langs.add("en");

        String query = queryHelper.getLangugeQuery("\\\"is not listed", langs);
        assertEquals(
                "((keywords_en:(\\\"is) OR translated_en:(\\\"is)) AND (keywords_en:(not) OR translated_en:(not))"
                        +" AND (keywords_en:(listed) OR translated_en:(listed)))",
                query);
    }

}
