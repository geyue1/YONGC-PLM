/**
 * ext.yongc.plm.util.PromotionUtil.java
 * @Author yge
 * 2017年7月5日上午10:51:36
 * Comment 
 */
package ext.yongc.plm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.doc.WTDocument;
import wt.enterprise.Master;
import wt.epm.EPMDocument;
import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.collections.WTArrayList;
import wt.fc.collections.WTHashSet;
import wt.fc.collections.WTSet;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainer;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.State;
import wt.maturity.MaturityBaseline;
import wt.maturity.MaturityHelper;
import wt.maturity.PromotionNotice;
import wt.part.WTPart;
import wt.session.SessionServerHelper;
import wt.type.TypeDefinitionReference;
import wt.type.TypedUtility;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import wt.vc.Mastered;
import wt.vc.VersionControlHelper;
import wt.vc.baseline.BaselineHelper;


public class PromotionUtil {

    /**
     * create PromotionRequest
     * @param number : String number
     * @param name : String name
     * @param objType : String objectType
     * @param container : WTContainer
     * @param folder : Folder
     * @param targetState : String targetState
     * @param ibaMap : Map<String, String> iba = ibaValue
     * @return PromotionNotice
     * @throws WTException : windchill exception
     */
     @SuppressWarnings("static-access")
    public static PromotionNotice createPromotionRequest(String number, String name, String objType, String desciption,
             WTContainer container, Folder folder, String targetState, Map<String, Object> mbaMap) throws WTException{
         PromotionNotice promotion = null;
         try { 
             promotion = PromotionNotice.newPromotionNotice(name);           
             
             //set number
             if(StringUtils.isNotBlank(number)){
                 promotion.setNumber(number);
             }
             
             //set name
             promotion.setName(name);
             
             //set type
             if (StringUtils.isNotBlank(objType)) {
                 TypeDefinitionReference typeDefinitionRef = TypedUtility.getTypeDefinitionReference(objType);
                 promotion.setTypeDefinitionReference(typeDefinitionRef);
             }
             
             //set container
             promotion.setContainer(container);
            
             //set Description
             if (StringUtils.isNotBlank(desciption)) {
                promotion.setDescription(desciption);
             }
             
            // folder
            String folderpath = "";
            if (folder == null) {
                folderpath = "/Default";
                folder = getFolder(WTContainerRef.newWTContainerRef(container), folderpath);
            }
            if (folder != null) {
                FolderHelper.assignLocation((FolderEntry) promotion, folder);
            }
             
             //set target state
             if(StringUtils.isNotBlank(targetState)){
                 promotion.setMaturityState(State.toState(targetState));             
             }
             
             MaturityBaseline maturityBaseline = MaturityBaseline.newMaturityBaseline();
             maturityBaseline.setContainer(container);
             maturityBaseline = (MaturityBaseline) PersistenceHelper.manager.save(maturityBaseline);
             promotion.setConfiguration(maturityBaseline);
             promotion = MaturityHelper.service.savePromotionNotice(promotion);
             promotion = (PromotionNotice) PersistenceHelper.manager.refresh(promotion);
             if(null != mbaMap){
                 MBAUtil mbaUtil = new MBAUtil();
                 mbaUtil.setObjectValue(promotion, mbaMap);
             }
            } catch (Exception e) {
                throw new WTException(e,e.getLocalizedMessage());
            }
            return promotion;
     }
     
     /**
      * Add object to PromotionNotice
      * @param promotion : PromotionNotice
      * @param promotableList : WTArrayList
      * @return PromotionNotice
      * @throws WTException : windchill exception
      */
     public static PromotionNotice addPromotable(PromotionNotice promotion, WTArrayList promotableList) throws WTException {
         boolean enforce = wt.session.SessionServerHelper.manager.setAccessEnforced(false);
         try {
             MaturityBaseline maturityBaseline = null;
             maturityBaseline = promotion.getConfiguration();
             maturityBaseline = (MaturityBaseline) BaselineHelper.service.addToBaseline(promotableList, maturityBaseline);
             promotion.setConfiguration(maturityBaseline);
             PersistenceHelper.manager.save(promotion);

             WTSet promotableSet = new WTHashSet(); 
             promotableSet.addAll(promotableList);
             MaturityHelper.service.savePromotionTargets(promotion, promotableSet);
            
             promotion = (PromotionNotice) PersistenceHelper.manager.refresh(promotion);
         } catch (WTPropertyVetoException e) {
             throw new WTException(e, e.getLocalizedMessage());
         } catch (WTException e) {
             throw new WTException(e, e.getLocalizedMessage());
         } finally {
             SessionServerHelper.manager.setAccessEnforced(enforce);
         }
         return promotion;
     }
     
     /**
      *  get all objects in the promotion notice
      * @description TODO
      * @param promotion
      * @return
      * @throws WTException
      * @author yge  2017年7月7日下午1:21:28
      */
     public static QueryResult getPromotionTargets(PromotionNotice promotion) throws WTException{
    	 QueryResult qr = new QueryResult();
    	 if(promotion==null){
    		 return qr;
    	 }
    	  qr = BaselineHelper.service.getBaselineItems(promotion.getConfiguration());
    	  
    	  return qr;
     }
     public static List getLatestPromotionTargets(PromotionNotice promotion) throws WTException{
    	 List list = new ArrayList();
    	 QueryResult qr= getPromotionTargets(promotion);
    	 while(qr.hasMoreElements()){
    		 Object obj = qr.nextElement();
//    		  QueryResult qr2 = VersionControlHelper.service.allIterationsOf(partMaster);
    		 Mastered master = null;
    		 if(obj instanceof WTDocument){
    			 master = ((WTDocument)obj).getMaster();
    		 }else if(obj instanceof WTPart){
    			 master =((WTPart)obj).getMaster();
    		 }else if(obj instanceof EPMDocument){
    			 master =((EPMDocument)obj).getMaster();
    		 }
    		 if(master!=null){
    			 QueryResult qr2 = VersionControlHelper.service.allIterationsOf(master);
    			 while(qr2.hasMoreElements()){
    				 list.add(qr2.nextElement());
    				 break;
    			 }
    		 }
    	 }
    	 return list;
     }
     
     /**
      * getFolder (get the special Folder object in a WTContainerRef)
      * 
      * @param WTContainerRef
      * @param String
      *            folder
      * @return Folder
      */
     public static Folder getFolder(WTContainerRef containerRef, String folder1)
             throws WTException {
         String folder = folder1;
         if (!(folder.startsWith("/"))) {
             folder = "/" + folder;
         }
         if (folder.indexOf("Default") == -1) {
             folder = "/Default" + folder;
         }
         return FolderHelper.service.getFolder(folder, containerRef);
     }
}
