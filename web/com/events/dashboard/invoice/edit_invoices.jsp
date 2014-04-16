<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sInvoiceId = ParseUtil.checkNull(request.getParameter("invoice_id"));
    String breadCrumbPageTitle = "Invoice - New";
    boolean loadInvoice = false;
    if(!Utility.isNullOrEmpty(sInvoiceId)) {
        loadInvoice = true;
        breadCrumbPageTitle = "Invoice - Edit";
    }
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="dashboard_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title"><%=breadCrumbPageTitle%></div>
        </div>
    </div>
    <form class="form-horizontal" id="frm_save_invoice">
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="invoices_active" value="active"/>
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
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-4">
                            <label for="client_selector" class="form_label">Client</label><span class="required"> *</span>
                            <select class="form-control" id="client_selector" name="invoiceClient">
                                <option value="">Select A Client</option>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label for="invoiceNumber" class="form_label">Invoice Number</label><span class="required"> *</span>
                            <input type="text" class="form-control" id="invoiceNumber" name="invoiceNumber"/>
                        </div>
                        <div class="col-md-4">
                            <label for="invoicePONumber" class="form_label">Contract Number/Purchase Order Number</label>
                            <input type="text" class="form-control" id="invoicePONumber" name="invoicePONumber"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <label for="invoiceDate" class="form_label">Invoice Date</label><span class="required"> *</span>
                            <input type="text" class="form-control" id="invoiceDate" name="invoiceDate"/>
                        </div>
                        <div class="col-md-6">
                            <label for="invoiceDueDate" class="form_label">Due Date</label>
                            <input type="text" class="form-control" id="invoiceDueDate" name="invoiceDueDate"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <label for="invoiceDiscount" class="form_label">Discount</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="invoiceDiscount" name="invoiceDiscount">
                                <span class="input-group-addon">%</span>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <label for="invoiceTax" class="form_label">Tax</label>
                            <div class="input-group">
                            <input type="text" class="form-control" id="invoiceTax" name="invoiceTax"/>
                            <span class="input-group-addon">%</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container  container-fixed">
        <div class="content_format">
            <div class="row ">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="invoice_item" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-1 col-sm-1" role="columnheader">Item</th>
                            <th class="sorting  col-md-3 col-sm-3" role="columnheader">Description</th>
                            <th class="sorting col-md-2 col-sm-2" role="columnheader">Unit Cost </th>
                            <th class="sorting col-md-1 col-sm-1" role="columnheader">Quantity </th>
                            <th class="sorting col-md-1 col-sm-1" role="columnheader">Total </th>
                            <th class="center  col-md-1 col-sm-1" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="invoice_item_row">
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
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="row"  >
                        <div class="col-md-12">
                            <label for="invoiceTerms" class="form_label">Invoice Terms And Conditions</label>
                            <input type="text" class="form-control" id="invoiceTerms" name="invoiceTerms"/>
                        </div>
                    </div>
                    <div class="row"  >
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row"  >
                        <div class="col-md-12">
                            <label for="invoiceNote" class="form_label">Note:</label>
                            <input type="text" class="form-control" id="invoiceNote" name="invoiceNote"/>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="row"  >
                        <div class="col-md-4"  style="text-align:right;" >
                            <h5>Sub Total</h5>
                        </div>
                        <div class="col-md-5"  style="text-align:right;">
                            <h6><span id="sub_total">$0.00</span></h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4" style="text-align:right;">
                            <h5>Discount</h5>
                        </div>
                        <div class="col-md-5"  style="text-align:right;">
                            <h6><span id="discount_amount">$0.00</span></h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4" style="text-align:right;">
                            <h5>Tax</h5>
                        </div>
                        <div class="col-md-5"  style="text-align:right;">
                            <h6><span id="tax_amount">$0.00</span></h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4" style="text-align:right;">
                            <h5>Balance Due</h5>
                        </div>
                        <div class="col-md-5"  style="text-align:right;">
                            <h4><span id="balance_due">$0.00</span></h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <input type="hidden" id="invoice_id" name ="invoice_id" value="<%=sInvoiceId%>"/>
        <div id="hidden_item_ids">

        </div>
    </form>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-4" >
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-1 col-sm-1">
                    <button class="btn btn-filled" id="btn_save_invoice">Save</button>
                </div>
                <div class="col-md-2 col-sm-2">
                    <button class="btn btn-filled" id="btn_email_invoice">Email Invoice</button>
                </div>
                <div class="col-md-2 col-sm-2">
                    <a class="btn btn-filled" id="download_invoice" target="_blank">Download Invoice</a>
                </div>
            </div>
        </div>
    </div>
</div>
<form id="frm_load_invoice">
    <input type="hidden"  id="load_invoice_id"  name="invoice_id" value="<%=sInvoiceId%>">
</form>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/bignumber.min.js"></script>
<script src="/js/datepicker/picker.js"></script>
<script src="/js/datepicker/picker.date.js"></script>
<script src="/js/datepicker/legacy.js"></script>
<script type="text/javascript">
    var mainSubTotal = new BigNumber(0.00);
    var mainDiscount = new BigNumber(0.00);
    var mainTax = new BigNumber(0.00);
    $(window).load(function() {

        initializeTable();
        $('#add_item').click(function(){
            var itemId = getTimeStamp();
            fnClickAddRow(itemId);
        })

        $('#invoiceTax').bind("keyup paste input", function() {
            updateTax(true);
        });
        $('#invoiceDiscount').bind("keyup paste input", function() {
            updateDiscount(true);
        });

        $('#btn_save_invoice').click(function(){
            generateItemList();
            saveInvoice(getResult);
        })

        $('#btn_download_invoice').click(function(){
            generateDownloadableInvoice(getDownloadableInvoice);
        })


        loadClients(populateClientList);
        $('#invoiceDate').pickadate()
        $('#invoiceDueDate').pickadate()
    });
    function loadInvoice(callbackmethod) {
        var actionUrl = "/proc_load_invoice.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_invoice").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function saveInvoice( callbackmethod ) {
        var actionUrl = "/proc_save_invoice.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_invoice").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function generateDownloadableInvoice(callbackmethod){
        var actionUrl = "/proc_generate_downloadable_invoice.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_invoice").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function getDownloadableInvoice(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                }
            }
        }
    }

    function download() {

        iframe.src = "file.doc";
    }

    function populateInvoice(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varInvoiceBean = jsonResponseObj.invoice_bean;
                    var varNumOfInvoiceItems = jsonResponseObj.num_of_invoice_items;
                    var varAllInvoiceItemsList = jsonResponseObj.invoice_items;

                    $('#client_selector').val(varInvoiceBean.client_id);
                    $('#invoiceNumber').val(varInvoiceBean.invoice_number);
                    $('#invoicePONumber').val(varInvoiceBean.contract_po_number);
                    $('#invoiceDate').val(varInvoiceBean.human_invoice_date);
                    $('#invoiceDueDate').val(varInvoiceBean.human_due_date);
                    $('#invoiceDiscount').val(varInvoiceBean.discount_percentage);
                    $('#invoiceTax').val(varInvoiceBean.tax_percentage);
                    $('#invoiceTerms').val(varInvoiceBean.terms_and_conditions);
                    $('#invoiceNote').val(varInvoiceBean.note);

                    if(varNumOfInvoiceItems>0){
                        for(i = 0; i<varNumOfInvoiceItems;i++ ){
                            var varInvoiceItem = varAllInvoiceItemsList[i];

                            var varInvoiceItemId = varInvoiceItem.invoice_item_id;
                            fnClickAddRow(varInvoiceItemId);
                            $('#item_'+varInvoiceItemId).val(varInvoiceItem.item_name);
                            $('#item_description_'+varInvoiceItemId).val(varInvoiceItem.item_description);
                            $('#unit_cost_'+varInvoiceItemId).val(varInvoiceItem.unit_cost);
                            $('#quantity_'+varInvoiceItemId).val(varInvoiceItem.quantity);
                            updateTotal(varInvoiceItemId,true);
                        }
                    }

                    var varInvoiceId = jsonResponseObj.invoice_id;
                    var varFileHost = jsonResponseObj.file_host;
                    var varBucket = jsonResponseObj.bucket;
                    var varPathName = jsonResponseObj.folder_path_name;
                    $('#download_invoice').attr("href",varFileHost+'/'+varBucket+'/'+varPathName+'/'+varInvoiceId+'.pdf');
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        $('#invoice_id').val(jsonResponseObj.invoice_id);


                        var varInvoiceId = jsonResponseObj.invoice_id;
                        var varFileHost = jsonResponseObj.file_host;
                        var varBucket = jsonResponseObj.bucket;
                        var varPathName = jsonResponseObj.folder_path_name;
                        $('#download_invoice').attr("href",varFileHost+'/'+varBucket+'/'+varPathName+'/'+varInvoiceId+'.pdf');
                    }
                }
                displayAjaxOk(varResponseObj);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    function initializeTable(){

        objInvoiceItemsTable =  $('#invoice_item').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,

            "aoColumns": [
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false },
                { "bSortable": false }
            ]
        });
    }
    function fnClickAddRow(varItemId) {
        var oTable = objInvoiceItemsTable;
        var ai = oTable.fnAddData( [
            '<input type="text" class="form-control" id="item_'+varItemId+'" name="item_'+varItemId+'"/>',
            '<input type="text" class="form-control" id="item_description_'+varItemId+'" name="item_description_'+varItemId+'"/>',
            '<div class="input-group"> <span class="input-group-addon">$</span><input type="text" class="form-control" id="unit_cost_'+varItemId+'" name="unit_cost_'+varItemId+'"/></div>',
            '<input type="text" class="form-control" id="quantity_'+varItemId+'" name="quantity_'+varItemId+'"/>',
            '<h6><span id="total_'+varItemId+'" style="text-align:right"></span></h6>',
            '<button class="btn btn-xs btn-default" id="delete_'+varItemId+'" name="delete_'+varItemId+'"><i class="fa fa-trash-o"></i>&nbsp; Remove</button>'] );

        var oSettings = oTable.fnSettings();
        var rows=$('#invoice_item tr').length-2;
        var position=oSettings.aoData[rows].nTr.rowIndex+1;
        $($('#invoice_item tr')[position-1]).attr('id', 'row_'+varItemId);

        $('#unit_cost_'+varItemId).bind("keyup paste input", function() {
                updateTotal(varItemId,true);
            });

            $('#quantity_'+varItemId).bind("keyup paste input", function() {
                updateTotal(varItemId,true);
            });

            $('#delete_'+varItemId).bind("click", function() {
                deleteItemRow(varItemId,true);
            });

        arrayItemId.push( varItemId );

    }

    var arrayItemId =  [];

    function updateTax(varISCalledByEvent) {
        var varTaxPercentage = $('#invoiceTax').val();
        if( isNaN( varTaxPercentage  ) ==false  && varTaxPercentage.trim()!=''  ) {
            varTaxPercentage = new BigNumber( varTaxPercentage.trim() );

            var varSubTotal = updateSubTotal();
            varSubTotal =  new BigNumber( varSubTotal )
            varTaxPercentage = (varTaxPercentage.dividedBy(100.00).times(varSubTotal.minus(mainDiscount)) ).toFixed(2)


            $('#tax_amount').text('$'+varTaxPercentage);
        } else {
            varTaxPercentage  = new BigNumber( 0.00 ).toFixed(2) ;
            $('#tax_amount').text('$0.00');
        }
        mainTax = new BigNumber( varTaxPercentage );
        if(varISCalledByEvent){
            updateBalanceDue(false);
        }
        return varTaxPercentage;
    }
    function deleteItemRow(varItemId, varISCalledByEvent){
        objInvoiceItemsTable.fnDeleteRow((objInvoiceItemsTable.$('#row_'+varItemId))[0] );
        if(varISCalledByEvent){
            var index = arrayItemId.indexOf(varItemId);
            if (index > -1) {
                arrayItemId.splice(index, 1);
            }
            updateSubTotal(false)
            updateDiscount(false)
            updateTax(false);
            updateBalanceDue(false);
        }
    }
    function updateTotal(varItemId, varISCalledByEvent){
        var varUnitCost = $('#unit_cost_'+varItemId).val();
        var varQuantity = $('#quantity_'+varItemId).val();

        var varTotal = '';
        if(isNaN( varUnitCost  ) ==false && isNaN( varQuantity )  ==false && varUnitCost.trim()!='' && varQuantity.trim()!=''  ) {
            varUnitCost = new BigNumber( varUnitCost.trim() );
            varQuantity = new BigNumber( varQuantity.trim() );

            varTotal = '$'+varUnitCost.times(varQuantity).toFixed(2) ;
        }
        $('#total_'+varItemId).text(varTotal);

        if(varISCalledByEvent){
            updateSubTotal(false)
            updateDiscount(false)
            updateTax(false);
            updateBalanceDue(false);
        }
    }

    function updateSubTotal(varISCalledByEvent){
        var varSubTotal = new BigNumber(0.00);
        for ( var i = 0; i < arrayItemId.length; i = i + 1 ) {

            var varUnitCost = $('#unit_cost_'+arrayItemId[i]).val();
            var varQuantity = $('#quantity_'+arrayItemId[i]).val();


            if(isNaN( varUnitCost ) ==false && isNaN( varQuantity )  ==false  && varUnitCost.trim()!='' && varQuantity.trim()!='' ) {
                varUnitCost = new BigNumber( varUnitCost.trim() );
                varQuantity = new BigNumber( varQuantity.trim() );

                varSubTotal = new BigNumber(varSubTotal.plus(varUnitCost.times(varQuantity)).toFixed(2)) ;
            }
        }
        varSubTotal = new BigNumber( varSubTotal );
        varSubTotal = varSubTotal.dividedBy(1.00).toFixed(2) ;
        mainSubTotal  = new BigNumber( varSubTotal );
        $('#sub_total').text('$'+varSubTotal);
        return varSubTotal;
    }

    function updateDiscount(varISCalledByEvent){
        var varDiscountTotal = $('#invoiceDiscount').val();
        if( isNaN( varDiscountTotal  ) ==false  && varDiscountTotal.trim()!=''  ) {
            varDiscountTotal = new BigNumber( varDiscountTotal.trim() );

            var varSubTotal = updateSubTotal();
            varDiscountTotal = (varDiscountTotal.dividedBy(100.00).times(varSubTotal) ).toFixed(2) ;


            $('#discount_amount').text('- $'+varDiscountTotal);


        } else {
            varDiscountTotal  = new BigNumber( 0.00 ).toFixed(2) ;
            $('#discount_amount').text('$'+varDiscountTotal);
        }

        mainDiscount = new BigNumber( varDiscountTotal );
        if(varISCalledByEvent == true){
            updateTax(false);
            updateBalanceDue(false);
        }
        return varDiscountTotal;
    }

    function updateBalanceDue(varISCalledByEvent){
        var varSubTotal = new BigNumber(mainSubTotal);
        var varDiscount = new BigNumber(mainDiscount);
        var varTax = new BigNumber(mainTax);

        var varBalanceDue = varSubTotal.minus(varDiscount).plus(varTax).toFixed(2);
        $('#balance_due').text('$'+varBalanceDue);

    }

    function getTimeStamp() {
        return Math.round(new Date().getTime() / 1000) ;
    }

    function generateItemList(){
        $('#hidden_item_ids').empty();
        if(arrayItemId!=undefined){
            for ( var i = 0; i < arrayItemId.length; i = i + 1 ) {
                var varItemId = arrayItemId[i];
                $('<input>').attr({
                    type: 'hidden',
                    id: 'item_id_list',
                    name: 'item_id[]',
                    value:varItemId
                }).appendTo('#hidden_item_ids');
            }
        }

    }

    function loadClients(callbackmethod) {
        var actionUrl = "/proc_load_clients.aeve";
        var methodType = "POST";
        var dataString = '';
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateClientList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfClients = jsonResponseObj.num_of_clients;
                    if(varNumOfClients>0){
                        processClientListSummary(varNumOfClients,jsonResponseObj.all_client_summary);
                    }
                    else {
                        //displayMssgBoxAlert("Create a new client here.", true);
                    }

                }
            } else {
                displayMssgBoxAlert("Please try again later (populateClientList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientList - 2)", true);
        }
    }
    function processClientListSummary(varNumOfClients,clientSummaryList) {
        var varDropDownClientList = $('#client_selector');
        for(i=0;i<varNumOfClients;i++){
            var varClientBean = clientSummaryList[i];
            varDropDownClientList.append('<option value="'+varClientBean.client_id+'">'+varClientBean.client_name+'</option>');
        }

        var varLoadInvoice = <%=loadInvoice%>
        if(varLoadInvoice){
            loadInvoice(populateInvoice);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>