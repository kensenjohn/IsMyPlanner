<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="event_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Event Budget</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_budget_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-11">

                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <div class="row">
                                    <form class="form-horizontal" id="frm_save_budget_estimate">
                                        <div class="col-xs-2 col-md-2">
                                            <label for="budget_estimate" class="form_label">My Budget:</label>
                                            <div class="input-group"> <span class="input-group-addon">$</span>
                                                <input type="text" class="form-control" id="budget_estimate" name="budget_estimate" param="My Budget"/>
                                            </div>
                                        </div>
                                        <div class="col-xs-1">
                                            &nbsp;
                                        </div>
                                        <div class="col-xs-4 col-md-3">
                                            <label for="remaining_estimate_cash" class="form_label" style="white-space: nowrap">My Budget - Total Estimated Cost:</label><br>
                                            <span id="remaining_estimate_cash" style="font-size: 14px;">$0.00</span>
                                        </div>
                                        <div class="col-xs-4 col-md-3">
                                            <label for="remaining_actual_cash" class="form_label" style="white-space: nowrap">My Budget - Total Actual Cost:</label> <br>
                                            <span id="remaining_actual_cash" style="font-size: 14px;">$0.00</span>
                                        </div>
                                        <div class="col-xs-1">
                                            &nbsp;
                                        </div>
                                        <div class="col-xs-2">
                                            &nbsp;
                                        </div>
                                        <input type="hidden" id="event_id" name="event_id" value="<%=sEventId%>"/>
                                        <input type="hidden" id="eventbudget_id" name="eventbudget_id" value=""/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="row" id="div_add_category">
                                <div class="col-xs-3">
                                    <button class="btn btn-xs btn-default" id="add_category" name="add_category"><i class="fa fa-plus"></i>&nbsp; Add A Category</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-11">
                            <div class="row">
                                <div class="col-xs-3 col-md-5">
                                </div>
                                <div class="col-xs-3 col-md-2 right">
                                    <label class="form_label">Total Estimated Cost</label><br>
                                    <span style="font-size: 17px;" id="total_estimated_cost"></span>
                                </div>
                                <div class="col-xs-3 col-md-2 right">
                                    <label class="form_label">Total Actual Cost</label><br>
                                    <span style="font-size: 17px;" id="total_actual_cost"></span>
                                </div>
                                <div class="col-xs-3">
                                    &nbsp;
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12" id="budget_categories">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script id="template_item_row" type="text/x-handlebars-template">
    <div class="row budget" id="row_{{item_id}}">
        <div class="col-xs-3 col-md-5">
            <div class="visible-md visible-lg items">
                <span class="item_name">{{full_item_name}}</span>
            </div>
            <div class="visible-xs visible-sm items">
                <span class="item_name">{{truncated_item_name}}</span>
            </div>

        </div>
        <div class="col-xs-3 col-md-2 items price">
            <span>{{item_estimate_cost}}</span>
        </div>
        <div class="col-xs-3 col-md-2 items price">
            <span>{{item_actual_cost}}</span>
        </div>
        <div class="col-xs-1">
            <span  style="white-space: nowrap;">{{item_paid_status}}</span>
        </div>
        <div class="col-xs-2">
            <div class="visible-md visible-lg ">
                &nbsp;&nbsp; &nbsp;
                &nbsp;&nbsp; &nbsp;
            </div>
            <div class="visible-xs visible-sm" style="white-space: nowrap;">
                &nbsp;&nbsp; &nbsp;
                &nbsp;&nbsp; &nbsp;
            </div>
        </div>
    </div>
</script>
<script id="template_category_row" type="text/x-handlebars-template">
    <div class="row" id="row_blank_{{category_id}}">
        <div class="col-md-12">
            &nbsp;
        </div>
    </div>
    <div class="row budget" id="row_{{category_id}}">
        <div class="col-xs-3 col-md-5">
            <div class="visible-md visible-lg category">
                <span>{{full_category_name}}</span>
            </div>
            <div class="visible-xs visible-sm category">
                <span>{{truncated_category_name}}</span>
            </div>

        </div>
        <div class="col-xs-3 col-md-2 category price">
            <span>{{category_estimate_cost}}</span>
        </div>
        <div class="col-xs-3 col-md-2 category price">
            <span>{{category_actual_cost}}</span>
        </div>
        <div class="col-xs-1">
            <span style="white-space: nowrap;">{{category_paid_status}}</span>
        </div>
        <div class="col-xs-2">
            <div class="visible-md visible-lg ">
                <button class="btn btn-xs btn-default" id="{{category_id}}"   name="edit_category"><i class="fa fa-pencil"></i>&nbsp; Edit</button>
                <button class="btn btn-xs btn-default" id="{{category_id}}"  name="delete_category"><i class="fa fa-trash-o"></i>&nbsp; Delete</button>
            </div>
            <div class="visible-xs visible-sm" style="white-space: nowrap;">
                <button class="btn btn-xs btn-default" id="{{category_id}}"   name="edit_category"><i class="fa fa-pencil"></i>&nbsp;</button>&nbsp;
                <button class="btn btn-xs btn-default" id="{{category_id}}"  name="delete_category"><i class="fa fa-trash-o"></i>&nbsp;</button>
            </div>
        </div>
    </div>
</script>
<form id="frm_load_event_budget">
    <input type="hidden" id="load_event_id" name="event_id"/>
    <input type="hidden" id="load_eventbudget_id" name="eventbudget_id" />
</form>
<form id="frm_load_event_budget_categories_items">
    <input type="hidden" id="load_categories_item_event_id" name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_delete_event_budget_category">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
    <input type="hidden" id="delete_category_id" name="category_id" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/bignumber.min.js"></script>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="/js/jquery.colorbox-min.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    var varAmountRegex  = /^\d+(\.\d{1,2})?$/;
    $(window).load(function() {
        BigNumber.config({ ERRORS: false });
        $('#budget_estimate').focusout(function() {
            saveEstimateBudget(  $('#budget_estimate').val() , $('#budget_estimate').attr('param'), getResultEstimateBudget );
        })
        loadEstimateBudget( populateEstimateBudget );

        $('#add_category').click(function(){
            displayMssgBoxAlert('Please set a valid Estimate before trying to add a category.', true);
        });
    });
    function isValidAmount( varNumbericAmount ){
        return varAmountRegex.test(varNumbericAmount)
    }

    function saveEstimateBudget( varEstimateBudget , varName, callbackmethod ) {
        if( varEstimateBudget!='' ) {
            if( isValidAmount( varEstimateBudget )  ) {
                var actionUrl = "/proc_save_event_budget.aeve";
                var methodType = "POST";
                var dataString = $("#frm_save_budget_estimate").serialize();
                makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
            } else {
                displayMssgBoxAlert('Please enter a valid ' + varName + ' amount.', true);
                $('#budget_estimate').val('');
            }
        }
    }
    function loadEstimateBudget(callbackmethod){
        var actionUrl = "/proc_load_event_budget.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_budget_estimate").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadBudgetCategoryAndItems(callbackmethod){
        var actionUrl = "/proc_load_event_budget_category_items.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_event_budget_categories_items").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResultEstimateBudget(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varEventBudgetBean = jsonResponseObj.event_budget_bean;
                        if(varEventBudgetBean!=undefined){
                            $('#eventbudget_id').val( varEventBudgetBean.event_budget_id );

                            if(varEventBudgetBean.event_budget_id != '' ) {
                                createAddCategoryEvent( varEventBudgetBean.event_budget_id );
                            }
                        }
                        updateRemaingCash( TOTAL_ESTIMATED_COST , 'remaining_estimate_cash');
                        updateRemaingCash( TOTAL_ACTUAL_COST , 'remaining_actual_cash');
                        loadBudgetCategoryAndItems( populateBudgetCategoryAndItems);
                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    function populateEstimateBudget( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varEventBudgetBean = jsonResponseObj.event_budget_bean;
                        if(varEventBudgetBean!=undefined){
                            $('#eventbudget_id').val( varEventBudgetBean.event_budget_id );
                            if(varEventBudgetBean.estimate_budget>0){
                                var varBudgetEstimate  = new BigNumber(  varEventBudgetBean.estimate_budget ).toFixed(2) ;
                                $('#budget_estimate').val( varBudgetEstimate );
                            } else {
                                $('#budget_estimate').val('');
                            }
                            if(varEventBudgetBean.event_budget_id != '' ) {
                                createAddCategoryEvent( varEventBudgetBean.event_budget_id );
                            }
                            loadBudgetCategoryAndItems( populateBudgetCategoryAndItems);
                        }
                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    var TOTAL_ESTIMATED_COST = 0;
    var TOTAL_ACTUAL_COST = 0;
    function updateRemaingCash( varTotalCost , varSpanName){
        var varFormattedRemainingCash = '0.00' ;
        var isNegativeNumber = false;
        var varRemainingCash = $('#budget_estimate').val() - varTotalCost;
        if(isNaN(  varRemainingCash  ) ==false ) {
            if(varRemainingCash<0){
                isNegativeNumber = true;
                varRemainingCash = varRemainingCash * -1;
            }
            varFormattedRemainingCash =  new BigNumber(   varRemainingCash ).toFixed(2);
        }
        varFormattedRemainingCash = '$' + varFormattedRemainingCash;
        if(isNegativeNumber){
            varFormattedRemainingCash = '- ' + varFormattedRemainingCash;
        }
        $('#'+varSpanName).text( varFormattedRemainingCash );
    }
    function populateBudgetCategoryAndItems( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        $("#budget_categories").empty();
                        var varNumOfBudgetCategories = jsonResponseObj.num_of_budget_categories;
                        if(varNumOfBudgetCategories>0){
                            var varCategoriesAndItems = jsonResponseObj.budget_categories_and_items;

                            var varFormattedTotalEstimatedCost = '0.00' ;
                            var varTotalEstimatedCost = varCategoriesAndItems.total_estimate_cost;
                            if(isNaN(  varTotalEstimatedCost  ) ==false ) {
                                TOTAL_ESTIMATED_COST = varTotalEstimatedCost;
                                varFormattedTotalEstimatedCost =  new BigNumber(   varTotalEstimatedCost ).toFixed(2);

                                updateRemaingCash( varTotalEstimatedCost , 'remaining_estimate_cash');
                            }
                            $('#total_estimated_cost').text( '$'+varFormattedTotalEstimatedCost );

                            var varFormattedTotalActualCost = '0.00' ;
                            var varTotalActualCost = varCategoriesAndItems.total_actual_cost;
                            if(isNaN(  varTotalActualCost  ) ==false ) {
                                TOTAL_ACTUAL_COST = varTotalActualCost;
                                varFormattedTotalActualCost =  new BigNumber(   varTotalActualCost ).toFixed(2);

                                updateRemaingCash( varTotalActualCost , 'remaining_actual_cash');
                            }
                            $('#total_actual_cost').text( '$'+varFormattedTotalActualCost );


                            for( var varCategoryCount = 0; varCategoryCount<varNumOfBudgetCategories;varCategoryCount++ ){

                                var varCategory = varCategoriesAndItems[varCategoryCount];
                                var varCategoryId = varCategory.budget_category_id;
                                this.categoriesListModel = new CategoriesListModel({
                                    'bb_category' : varCategory ,
                                    'bb_num_of_category_items': varCategoriesAndItems[varCategoryId+'_num_of_items'],
                                    'bb_row_num' : varCategoryCount
                                });
                                var categoriesListView = new CategoriesListView({model:this.categoriesListModel});
                                categoriesListView.render();
                                $("#budget_categories").append(categoriesListView.el);


                                var varNumOfItems = varCategoriesAndItems[varCategoryId+'_num_of_items'];
                                var varItemList = varCategoriesAndItems[varCategoryId+'_items'];
                                for( var varItemCount = 0; varItemCount<varNumOfItems; varItemCount++ ){
                                    var varItem = varItemList[varItemCount];

                                    this.itemListModel = new ItemListModel({
                                        'bb_item' : varItem,
                                        'bb_item_row_num' : varItemCount
                                    });
                                    var itemListView = new ItemListView({model:this.itemListModel});
                                    itemListView.render();
                                    $("#budget_categories").append(itemListView.el);
                                }
                            }

                        }
                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    function createAddCategoryEvent(varEventBudgetId){
        if(varEventBudgetId!='' && varEventBudgetId!=undefined){
            $('#add_category').unbind('click');
            $('#add_category').click(function(){
                $.colorbox({
                    width:"60%",
                    height:"80%",
                    iframe:true,
                    onClosed:function(){
                        loadBudgetCategoryAndItems( populateBudgetCategoryAndItems);
                    },
                    href:"/com/events/event/budget/edit_category.jsp?event_id="+varEventId+"&eventbudget_id="+varEventBudgetId

                });
            });
        }
    }
    var ItemListModel = Backbone.Model.extend({
        defaults: {
            bb_item: undefined,
            bb_item_row_num: undefined
        }
    });
    var ItemListView = Backbone.View.extend({
        initialize: function(){
            this.varBBItem = this.model.get('bb_item');
            this.varBBItemRowNum = this.model.get('bb_item_row_num');
        },
        template : Handlebars.compile( $('#template_item_row').html() ),
        render : function() {
            var varPaidText = '';
            if( this.varBBItem.is_paid == true ){
                varPaidText = 'Paid';
            }

            var varFormattedActualCost = '0.00' ;
            var varActualAmount = this.varBBItem.actual_amount;
            if(isNaN(  varActualAmount  ) ==false ) {
                varFormattedActualCost =  new BigNumber(   varActualAmount ).toFixed(2)
            }

            var varFormattedEstimatedCost = '0.00' ;
            var varEstimatedAmount = this.varBBItem.estimated_amount;
            if(isNaN(  varEstimatedAmount  ) ==false ) {
                varFormattedEstimatedCost =  new BigNumber(   varEstimatedAmount ).toFixed(2)
            }

            var varTmpItemBean = {
                "item_id"  : this.varBBItem.budget_category_item_id,
                "full_item_name" :this.varBBItem.item_name,
                "truncated_item_name" :this.varBBItem.item_name,
                "item_estimate_cost" : '$'+varFormattedEstimatedCost,
                "item_actual_cost" : '$'+varFormattedActualCost,
                "item_paid_status" :varPaidText,
                "row_num" :this.varBBItemRowNum
            }
            var itemRow = this.template(  eval(varTmpItemBean)  );
            $(this.el).append( itemRow );
        }
    });

    var CategoriesListModel = Backbone.Model.extend({
        defaults: {
            bb_category: undefined,
            bb_num_of_category_items: 0,
            bb_row_num: 0
        }
    });
    var CategoriesListView = Backbone.View.extend({
        initialize: function(){
            this.varBBCategory = this.model.get('bb_category');
            this.varBBNumOfCategoryItems = this.model.get('bb_num_of_category_items');
            this.varBBCategoryRowNum = this.model.get('bb_row_num');
        },
        template : Handlebars.compile( $('#template_category_row').html() ),
        events : {
            "click button[name='delete_category']" : 'assignDeleteEventHandling',
            "click button[name='edit_category']" : 'assignEditEventHandling'
        },
        render : function() {

            var varFormattedActualCost = '0.00' ;
            var varActualCost = this.varBBCategory.actual_cost;
            if(isNaN(  varActualCost  ) ==false ) {
                varFormattedActualCost =  new BigNumber(   varActualCost ).toFixed(2)
            }

            var varFormattedEstimatedCost = '0.00' ;
            var varEstimatedCost = this.varBBCategory.estimated_cost;
            if(isNaN(  varEstimatedCost  ) ==false ) {
                varFormattedEstimatedCost =  new BigNumber(   varEstimatedCost ).toFixed(2)
            }

            var varTmpCategoryBean = {
                "category_id"  : this.varBBCategory.budget_category_id,
                "full_category_name" :this.varBBCategory.category_name,
                "truncated_category_name" :this.varBBCategory.category_name,
                "category_estimate_cost" : '$'+varFormattedEstimatedCost,
                "category_actual_cost" : '$'+varFormattedActualCost,
                "row_num" :this.varBBCategoryRowNum
            }
            var categoryRow = this.template(  eval(varTmpCategoryBean)  );
            $(this.el).append( categoryRow );
        },
        assignDeleteEventHandling : function(event) {
            var varTargetDeleteButton = $(event.target);
            var varButtonId =  varTargetDeleteButton.attr('id');
            if(varButtonId!=undefined && varButtonId!=''){
                var delete_category_obj = {
                    category_id:  varButtonId,
                    printObj: function () {
                        return this.category_id ;
                    }
                }
                displayConfirmBox(
                        "Are you sure you want to delete this category? ",
                        "Delete Category",
                        "Yes", "No", deleteCategory,delete_category_obj);
            } else {
                displayMssgBoxAlert("Oops!! We were unable to process your request. Please try again. (eventBudget-001)",true);
            }


        },
        assignEditEventHandling : function(event) {
            var varTargetEditButton = $(event.target);

            var varButtonId =  varTargetEditButton.attr('id');

            if(varButtonId!=undefined && varButtonId!=''){
                $.colorbox({
                    width:"60%",
                    height:"80%",
                    onClosed:function(){
                        loadBudgetCategoryAndItems( populateBudgetCategoryAndItems);
                    },
                    iframe:true,
                    href:"/com/events/event/budget/edit_category.jsp?event_id="+varEventId+"&eventbudget_category_id="+varTargetEditButton.attr('id')
                });
            } else {
                displayMssgBoxAlert("Oops!! We were unable to process your request. Please try again. (eventBudget-001)",true);
            }

        }
    });

    function deleteCategory( varCategoryObj ){
        $('#delete_category_id').val(varCategoryObj.category_id);

        var actionUrl = "/proc_delete_event_budget_category.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_event_budget_category").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processCategoryDeletion);
    }
    function processCategoryDeletion (jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    var varIsCategoryDeleted = jsonResponseObj.is_deleted;

                    if(varIsCategoryDeleted){

                        $('#delete_category_id').val('');

                        var varCategoryId = jsonResponseObj.deleted_category_id;
                        $('#row_'+varCategoryId).remove();
                        $('#row_blank_'+varCategoryId).remove();

                        var varNumOfItems = jsonResponseObj.num_of_items;
                        if( varNumOfItems>0 ){
                            var varDeletedItemList = jsonResponseObj.deleted_items
                            for(var varItemCount = 0; varItemCount< varNumOfItems; varItemCount++ ){
                                var varItemBean = varDeletedItemList[varItemCount];
                                $('#row_'+varItemBean.budget_category_item_id).remove();
                            }
                        }

                    } else {
                        displayMssgBoxAlert("The budget category was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteCategory - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteCategory - 2)", true);
        }
    }

</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
