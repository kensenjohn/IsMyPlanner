<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<%
    String sEventId = ParseUtil.checkNull( request.getParameter("event_id") ) ;
    String sEventBudgetId = ParseUtil.checkNull( request.getParameter("eventbudget_id") ) ;
    String sEventBudgetCategoryId = ParseUtil.checkNull( request.getParameter("eventbudget_category_id") ) ;

    boolean loadEventBudgetCategory = false;
    if( !Utility.isNullOrEmpty(sEventBudgetCategoryId)) {
        loadEventBudgetCategory = true;
    }

%>

<div class="page_wrap">
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-xs-12">
                    <h4>Budget Category</h4>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <form id="frm_save_budget_category">
                        <div class="row">
                            <div class="col-xs-4">
                                <label for="budget_category_name" class="form_label">Category Name</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="budget_category_name" name="budget_category_name" placeholder="e.g. Photographer" maxlength="60"/>
                            </div>
                            <div class="col-xs-2" style="text-align: right;">
                                <label class="form_label">Estimated Cost:</label><br>
                                <span  id="total_category_estimated_cost"  style="font-size: 17px;white-space: nowrap;"></span>
                            </div>
                            <div class="col-xs-2" style="text-align: right;">
                                <label class="form_label">Actual Cost:</label><br>
                                <span  id="total_category_actual_cost"  style="font-size: 17px;white-space: nowrap;"></span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row ">
                            <div class="col-md-12">
                                <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="budget_category_item" >
                                    <thead>
                                    <tr role="row">
                                        <th class="sorting col-xs-4" role="columnheader">Item Name</th>
                                        <th class="sorting  col-xs-2" role="columnheader">Estimated Cost</th>
                                        <th class="sorting col-xs-2" role="columnheader">Actual Cost </th>
                                        <th class="sorting col-xs-1" role="columnheader">Paid </th>
                                        <th class="center  col-xs-3" role="columnheader"></th>
                                    </tr>
                                    </thead>
                                    <tbody role="alert" id="budget_category_item_row">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <button type="button" class="btn btn-xs btn-default" id="add_item" name="add_item"><i class="fa fa-plus"></i>&nbsp; Add Item</button>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <button type="button" class="btn btn-sm btn-filled" id="btn_save_budget_category" name="btn_save_budget_category">Save</button>
                            </div>
                        </div>
                        <input type="hidden" name="event_id" id="event_id" value="<%=sEventId%>" />
                        <input type="hidden" name="eventbudget_id" id="eventbudget_id" value="<%=sEventBudgetId%>" />
                        <input type="hidden" name="eventbudget_category_id" id="eventbudget_category_id" value="<%=sEventBudgetCategoryId%>" />
                        <div id="hidden_item_ids">

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/bignumber.min.js"></script>
<script type="text/javascript">
    var varLoadEventBudgetCategory = <%=loadEventBudgetCategory%>
    $(window).load(function() {
        initializeTable();

        if(varLoadEventBudgetCategory){
            loadBudgetCategory( populateBudgetCategory );
        }

        $('#btn_save_budget_category').click(function(){
            generateItemList();
            saveBudgetCategory( getResultBudgetCategory );
        });

        $('#add_item').click(function(){
            var itemId = getTimeStamp();
            fnClickAddRow(itemId);
        });
    });
    function saveBudgetCategory(callbackmethod){
        var actionUrl = "/proc_save_budget_category.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_budget_category").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadBudgetCategory(callbackmethod) {
        var actionUrl = "/proc_load_budget_category.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_budget_category").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateBudgetCategory( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varEventBudgetCategoryBean = jsonResponseObj.event_budget_category_bean;
                        if(varEventBudgetCategoryBean!=undefined){
                            $('#budget_category_name').val( varEventBudgetCategoryBean.category_name );
                            $('#eventbudget_id').val( varEventBudgetCategoryBean.event_budget_id );

                            var varNumOfItems = jsonResponseObj.num_of_items;
                            if(varNumOfItems>0){
                                var varCategoryItems = jsonResponseObj.budget_items;
                                for(var varItemCount = 0; varItemCount<varNumOfItems; varItemCount++){
                                    var varItemBean = varCategoryItems[varItemCount];
                                    var varFormattedEstimatedAmount =  new BigNumber(  varItemBean.estimated_amount ).toFixed(2) ;
                                    var varFormattedActualAmount =  new BigNumber(  varItemBean.actual_amount ).toFixed(2) ;
                                    var varIsPaid = varItemBean.is_paid;

                                    var varItemId = varItemBean.budget_category_item_id;
                                    fnClickAddRow( varItemId );

                                    $('#budget_item_name_'+varItemId).val( varItemBean.item_name );
                                    $('#budget_item_estimate_'+varItemId).val( varFormattedEstimatedAmount );
                                    $('#budget_item_actual_'+varItemId).val( varFormattedActualAmount );

                                    if(varIsPaid==true){
                                        $('#budget_item_ispaid_'+varItemId).prop('checked', true);
                                    }

                                }
                                updateCategoryTotal();
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

    function getResultBudgetCategory(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varEventBudgetCategoryBean = jsonResponseObj.event_budget_category_bean;
                        if(varEventBudgetCategoryBean!=undefined){
                            $('#eventbudget_id').val( varEventBudgetCategoryBean.event_budget_id );
                            $('#eventbudget_category_id').val( varEventBudgetCategoryBean.budget_category_id );
                            $('#event_id').val( varEventBudgetCategoryBean.event_id );

                            displayAjaxOk(varResponseObj);
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
    var arrayItemId =  [];
    function getTimeStamp() {
        return Math.round(new Date().getTime() / 1000) ;
    }
    function fnClickAddRow(varItemId) {
        var oTable = objBudgetCategoryItemsTable;
        var ai = oTable.fnAddData( [
            '<input type="text" class="form-control" id="budget_item_name_'+varItemId+'" name="budget_item_name_'+varItemId+'"/>',
            '<div class="input-group"> <span class="input-group-addon">$</span><input type="text" class="form-control" id="budget_item_estimate_'+varItemId+'" name="budget_item_estimate_'+varItemId+'"/></div>',
            '<div class="input-group"> <span class="input-group-addon">$</span><input type="text" class="form-control" id="budget_item_actual_'+varItemId+'" name="budget_item_actual_'+varItemId+'"/></div>',
            '<input type="checkbox" id="budget_item_ispaid_'+varItemId+'" name="budget_item_ispaid_'+varItemId+'"/>',
            '<button class="btn btn-xs btn-default" id="delete_'+varItemId+'" name="delete_'+varItemId+'"><i class="fa fa-trash-o"></i>&nbsp; Delete</button>'] );

        var oSettings = oTable.fnSettings();
        var rows=$('#budget_category_item tr').length-2;
        var position=oSettings.aoData[rows].nTr.rowIndex+1;
        $($('#budget_category_item tr')[position-1]).attr('id', 'row_'+varItemId);

        $('#budget_item_estimate_'+varItemId).bind("keyup paste input", function() {
            updateCategoryTotal();
        });

        $('#budget_item_actual_'+varItemId).bind("keyup paste input", function() {
            updateCategoryTotal();
        });

        $('#delete_'+varItemId).bind("click", function() {
            updateCategoryTotal();
        });

        arrayItemId.push( varItemId );

    }
    function generateItemList(){
        $('#hidden_item_ids').empty();
        if(arrayItemId!=undefined){
            for ( var i = 0; i < arrayItemId.length; i = i + 1 ) {
                var varItemId = arrayItemId[i];
                $('<input>').attr({
                    type: 'hidden',
                    id: 'budget_item_id_list',
                    name: 'budget_item_id[]',
                    value:varItemId
                }).appendTo('#hidden_item_ids');
            }
        }

    }

    function initializeTable(){

        objBudgetCategoryItemsTable =  $('#budget_category_item').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,

            "aoColumns": [
                null,
                null,
                null,
                { "bSortable": false },
                { "bSortable": false }
            ]
        });
    }


    function updateCategoryTotal(){
        var varTotalEstimateCost = new BigNumber(0.00);
        var varTotalActualCost = new BigNumber(0.00);
        for ( var i = 0; i < arrayItemId.length; i = i + 1 ) {

            var varEstimateCost = $('#budget_item_estimate_'+arrayItemId[i]).val();
            var varActualCost = $('#budget_item_actual_'+arrayItemId[i]).val();


            if(isNaN( varEstimateCost  ) ==false && varEstimateCost.trim()!='' ) {
                varEstimateCost = new BigNumber( varEstimateCost.trim() );
                varTotalEstimateCost  = new BigNumber(varTotalEstimateCost.plus(varEstimateCost).toFixed(2)) ;
            }
            if(isNaN( varActualCost )  ==false && varActualCost.trim()!=''  ) {
                varActualCost = new BigNumber( varActualCost.trim() );
                varTotalActualCost  = new BigNumber(varTotalActualCost.plus(varActualCost).toFixed(2)) ;
            }
        }
        $('#total_category_estimated_cost').text( '$'+varTotalEstimateCost.toFixed(2));
        $('#total_category_actual_cost').text('$'+varTotalActualCost.toFixed(2));

    }
</script>
</html>