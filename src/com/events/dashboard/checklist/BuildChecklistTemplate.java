package com.events.dashboard.checklist;

import com.events.bean.dashboard.checklist.*;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.events.data.dashboard.checklist.AccessChecklistTemplateData;
import com.events.data.dashboard.checklist.BuildChecklistTemplateData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 6/13/14
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildChecklistTemplate {
    public ChecklistTemplateResponseBean saveChecklistTemplate( ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateResponseBean checklistTemplateResponseBean = new ChecklistTemplateResponseBean();
        if(checklistTemplateRequestBean!=null ){

            ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
            if(Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())) {
                checklistTemplateBean = createChecklistTemplate(checklistTemplateRequestBean);
            } else {
                checklistTemplateBean = updateChecklistTemplate(checklistTemplateRequestBean);
            }

            if(checklistTemplateBean!=null && !Utility.isNullOrEmpty(checklistTemplateBean.getChecklistTemplateId())) {
                checklistTemplateResponseBean.setChecklistTemplateBean( checklistTemplateBean );
            }
        }
        return checklistTemplateResponseBean;
    }

    public Integer deleteChecklistTemplate(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        Integer iNumOfChecklistTemplateDeleted = 0;
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())){
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            iNumOfChecklistTemplateDeleted = buildChecklistTemplateData.deleteChecklistTemplate(checklistTemplateRequestBean);
        }
        return iNumOfChecklistTemplateDeleted;
    }

    public ChecklistTemplateResponseBean saveChecklistTemplateItem( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ChecklistTemplateResponseBean checklistTemplateResponseBean = new ChecklistTemplateResponseBean();
        if(checklistTemplateRequestBean!=null ){
            ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean();
            if(Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId())) {
                checklistTemplateItemBean = createChecklistTemplateItem(checklistTemplateRequestBean);
            } else {
                checklistTemplateItemBean = updateChecklistTemplateItem(checklistTemplateRequestBean);
            }

            if(checklistTemplateItemBean!=null && !Utility.isNullOrEmpty(checklistTemplateItemBean.getChecklistTemplateItemId())) {
                ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean = checklistTemplateRequestBean.getArrChecklistTemplateTodoBean();
                deleteAllChecklistTemplateTodoBean( checklistTemplateRequestBean );
                Integer iNumberOfTodos = createChecklistTemplateTodoBean( arrChecklistTemplateTodoBean );
                checklistTemplateResponseBean.setChecklistTemplateItemBean( checklistTemplateItemBean );
            }
        }
        return checklistTemplateResponseBean;
    }

    public void deleteAllChecklistTemplateTodoBean(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId()) ){
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            buildChecklistTemplateData.deleteAllChecklistTemplateTodo( checklistTemplateRequestBean );
        }
    }

    public Integer createChecklistTemplateTodoBean(ArrayList<ChecklistTemplateTodoBean> arrChecklistTemplateTodoBean){
        Integer iNumberOfTodos = 0;
        if(arrChecklistTemplateTodoBean!=null && !arrChecklistTemplateTodoBean.isEmpty() ){
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            iNumberOfTodos = buildChecklistTemplateData.insertChecklistTemplateTodo(arrChecklistTemplateTodoBean);
        }
        return iNumberOfTodos;
    }

    public Integer deleteChecklistTemplateItem( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        Integer iNumOfChecklistTemplateItemDeleted = 0;
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateId())
                && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateItemId())  ){
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            iNumOfChecklistTemplateItemDeleted = buildChecklistTemplateData.deleteChecklistTemplateItem(checklistTemplateRequestBean);
        }
        return iNumOfChecklistTemplateItemDeleted;
    }

    public Integer deleteChecklistTemplateTodo( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        Integer iNumOfChecklistTemplateTodoDeleted = 0;
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getChecklistTemplateTodoId())   ){
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            iNumOfChecklistTemplateTodoDeleted = buildChecklistTemplateData.deleteChecklistTemplateTodo(checklistTemplateRequestBean);
        }
        return iNumOfChecklistTemplateTodoDeleted;
    }

    public void sortChecklistTemplateItem(  ChecklistTemplateRequestBean checklistTemplateRequestBean  ){
        if(checklistTemplateRequestBean!=null){
            HashMap<String,Long> hmSortChecklistTemplateId = checklistTemplateRequestBean.getHmSortChecklistTemplateId();

            if(hmSortChecklistTemplateId!=null && !hmSortChecklistTemplateId.isEmpty()) {
                BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
                for( Map.Entry<String,Long> mapSortChecklistItem : hmSortChecklistTemplateId.entrySet() ){
                    ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean();
                    checklistTemplateItemBean.setSortOrder( mapSortChecklistItem.getValue() );
                    checklistTemplateItemBean.setChecklistTemplateItemId( mapSortChecklistItem.getKey() );

                    buildChecklistTemplateData.updateChecklistTemplateItemSort( checklistTemplateItemBean );
                }
            }
        }
    }

    public ChecklistTemplateResponseBean createDefaultChecklistTemplate(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateResponseBean checklistTemplateResponseBean = new ChecklistTemplateResponseBean();
        if(checklistTemplateRequestBean!=null && !Utility.isNullOrEmpty(checklistTemplateRequestBean.getVendorId() ))  {
            checklistTemplateResponseBean = generateDefaultWeddingChecklistTemplate( checklistTemplateRequestBean  );
            Integer numOfRowsTemplateCreated = 0;
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            if(checklistTemplateResponseBean!=null && checklistTemplateResponseBean.getChecklistTemplateBean()!=null){
                ChecklistTemplateBean checklistTemplateBean = checklistTemplateResponseBean.getChecklistTemplateBean();
                numOfRowsTemplateCreated = buildChecklistTemplateData.insertChecklistTemplate(checklistTemplateBean);
                if(numOfRowsTemplateCreated<=0){
                    checklistTemplateResponseBean.setChecklistTemplateBean( new ChecklistTemplateBean());
                }
            }

            if(checklistTemplateResponseBean!=null && checklistTemplateResponseBean.getArrChecklistTemplateItemBean()!=null && numOfRowsTemplateCreated>0){
                ArrayList<ChecklistTemplateItemBean> arrChecklistTemplateItemBean = checklistTemplateResponseBean.getArrChecklistTemplateItemBean();

                buildChecklistTemplateData.insertChecklistTemplateItems(arrChecklistTemplateItemBean);
            }
        }
        return checklistTemplateResponseBean;
    }

    private ChecklistTemplateItemBean createChecklistTemplateItem(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean();
        if(checklistTemplateRequestBean!=null){

            AccessChecklistTemplate accessChecklistTemplate = new AccessChecklistTemplate();
            Long lNumberOfItems = accessChecklistTemplate.getNumOfItems(checklistTemplateRequestBean);
            checklistTemplateRequestBean.setChecklistTemplateItemId( Utility.getNewGuid() );
            checklistTemplateRequestBean.setSortOrder( lNumberOfItems++ );
            checklistTemplateItemBean = generateChecklistTemplateItemBean(checklistTemplateRequestBean);
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            Integer iNumOfRowsInserted = buildChecklistTemplateData.insertChecklistTemplateItem(checklistTemplateItemBean);
            if(iNumOfRowsInserted<=0){
                checklistTemplateItemBean = new ChecklistTemplateItemBean();
            }
        }
        return checklistTemplateItemBean;
    }

    private ChecklistTemplateItemBean updateChecklistTemplateItem(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean();
        if(checklistTemplateRequestBean!=null){
            checklistTemplateItemBean = generateChecklistTemplateItemBean(checklistTemplateRequestBean);
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            Integer iNumOfRowsUpdated = buildChecklistTemplateData.updateChecklistTemplateItem(checklistTemplateItemBean);
            if(iNumOfRowsUpdated<=0){
                checklistTemplateItemBean = new ChecklistTemplateItemBean();
            }
        }
        return checklistTemplateItemBean;
    }

    private ChecklistTemplateBean createChecklistTemplate(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null){
            checklistTemplateRequestBean.setChecklistTemplateId( Utility.getNewGuid() );
            checklistTemplateBean = generateChecklistTemplateBean(checklistTemplateRequestBean);
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            Integer iNumOfRowsInserted = buildChecklistTemplateData.insertChecklistTemplate( checklistTemplateBean );
            if(iNumOfRowsInserted<=0){
                checklistTemplateBean = new ChecklistTemplateBean();
            }
        }
        return checklistTemplateBean;
    }
    private ChecklistTemplateResponseBean generateDefaultWeddingChecklistTemplate(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateResponseBean checklistTemplateResponseBean = new ChecklistTemplateResponseBean();


        ChecklistTemplateRequestBean defaultChecklistTemplateRequestBean = new ChecklistTemplateRequestBean();
        defaultChecklistTemplateRequestBean.setChecklistTemplateBean(Utility.getNewGuid(), checklistTemplateRequestBean.getVendorId(), true, "Wedding Checklist");

        ChecklistTemplateBean checklistTemplateBean = generateChecklistTemplateBean( defaultChecklistTemplateRequestBean );
        String sChecklistTemplateId = checklistTemplateBean.getChecklistTemplateId();

        checklistTemplateResponseBean.setChecklistTemplateBean( checklistTemplateBean );

        ArrayList<ChecklistTemplateItemBean>   arrChecklistTemplateItemBean = new ArrayList<ChecklistTemplateItemBean>();

        ChecklistTemplateRequestBean defaultChecklistTemplateItemRequestBean = new ChecklistTemplateRequestBean();

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Determine your budget",100L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Select your guests",200L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a reception venue",300L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a ceremony venue",400L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find an officiant",500L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a photographer and videographer",600L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a florist",700L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a band",800L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a DJ",900L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a baker and select a cake",1000L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Create your registry.",1100L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Create your website.",1200L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Send out invitations with RSVP deadlines",1300L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Dress Shopping for bridal party",1400L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a gown.",1500L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Find a make up and hair stylist.",1600L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Finalize guest list after all RSVP.",1700L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Finalize all booking and rentals.",1800L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Finalize all music selection.",1900L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Get your marriage licence.",2000L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Finalize and book your honeymoon.",2100L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Send all details to all your vendors.",2200L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        defaultChecklistTemplateItemRequestBean.setChecklistTemplateItemBean(Utility.getNewGuid(),sChecklistTemplateId,"Ensure all payments are complete",2300L );
        arrChecklistTemplateItemBean.add( generateChecklistTemplateItemBean(defaultChecklistTemplateItemRequestBean) );

        checklistTemplateResponseBean.setArrChecklistTemplateItemBean( arrChecklistTemplateItemBean );

        return checklistTemplateResponseBean;
    }
    private ChecklistTemplateBean updateChecklistTemplate(ChecklistTemplateRequestBean checklistTemplateRequestBean){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null){
            checklistTemplateBean = generateChecklistTemplateBean(checklistTemplateRequestBean);
            BuildChecklistTemplateData buildChecklistTemplateData = new BuildChecklistTemplateData();
            Integer iNumOfRowsUpdated = buildChecklistTemplateData.updateChecklistTemplate( checklistTemplateBean );
            if(iNumOfRowsUpdated<=0){
                checklistTemplateBean = new ChecklistTemplateBean();
            }
        }
        return checklistTemplateBean;
    }

    private ChecklistTemplateBean generateChecklistTemplateBean( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ChecklistTemplateBean checklistTemplateBean = new ChecklistTemplateBean();
        if(checklistTemplateRequestBean!=null){
            checklistTemplateBean.setChecklistTemplateId( checklistTemplateRequestBean.getChecklistTemplateId() );
            checklistTemplateBean.setName( checklistTemplateRequestBean.getChecklistTemplateName() );
            checklistTemplateBean.setVendorId(checklistTemplateRequestBean.getVendorId());
            checklistTemplateBean.setDefault( checklistTemplateRequestBean.isDefault() );
        }
        return checklistTemplateBean;
    }

    private ChecklistTemplateItemBean generateChecklistTemplateItemBean( ChecklistTemplateRequestBean checklistTemplateRequestBean ){
        ChecklistTemplateItemBean checklistTemplateItemBean = new ChecklistTemplateItemBean();
        if(checklistTemplateRequestBean!=null){
            checklistTemplateItemBean.setChecklistTemplateItemId(checklistTemplateRequestBean.getChecklistTemplateItemId());
            checklistTemplateItemBean.setChecklistTemplateId(checklistTemplateRequestBean.getChecklistTemplateId());
            checklistTemplateItemBean.setName(checklistTemplateRequestBean.getChecklistTemplateItemName());
            checklistTemplateItemBean.setSortOrder( checklistTemplateRequestBean.getSortOrder() );
        }
        return checklistTemplateItemBean;
    }
}
