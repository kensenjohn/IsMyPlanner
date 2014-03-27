<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">

<jsp:include page="/com/events/common/header_bottom.jsp"/>
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
            <div class="page-title">Dashboard - Invoices</div>
        </div>
    </div>
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
                <div class="col-md-2">
                    <a href="/com/events/dashboard/invoice/edit_invoices.jsp" class="btn btn-filled">
                        <span><i class="fa fa-plus"></i> New Invoice</span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_invoice" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-2" role="columnheader">Invoice Num</th>
                            <th class="sorting col-md-4" role="columnheader">Client</th>
                            <th class="sorting col-md-2" role="columnheader">Date </th>
                            <th class="sorting col-md-2" role="columnheader">Due Date </th>
                            <th class="center col-md-2" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_invoice_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_delete_invoice">
    <input type="hidden" id="delete_invoice_id" name="invoice_id" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        loadInvoices(populateInvoiceList);
        initializeTable();
    });
    function loadInvoices(callbackmethod) {
        var actionUrl = "/proc_load_all_invoices.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_theme_color_font").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateInvoiceList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varAllInvoices = jsonResponseObj.all_invoices;
                    var numOfInvoices = jsonResponseObj.num_of_invoices;


                    this.invoiceListModel = new InvoiceListModel({

                        'bb_num_of_invoices' : numOfInvoices,
                        'bb_all_invoices' : varAllInvoices
                    });
                    var invoiceListView = new InvoiceListView({model:this.invoiceListModel});
                    invoiceListView.render();


                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    var InvoiceListModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_invoices: 0 ,
            bb_all_invoices: undefined
        }
    });
    var InvoiceListView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfInvoices = this.model.get('bb_num_of_invoices');
            this.varAllInvoicesBean = this.model.get('bb_all_invoices');
        },
        render:function(){
            if(this.varNumOfInvoices>0){
                var oTable = objInvoiceTable;
                if(oTable!='' && oTable!=undefined){
                    oTable.fnClearTable();
                    for (var i = 0;i < this.varNumOfInvoices;i++) {
                        var varInvoices = this.varAllInvoicesBean[i];
                        var varInvoiceId = varInvoices.invoice_id;
                        var varInvoiceNumber = varInvoices.invoice_number;
                        var varClientId = varInvoices.client_id;
                        var varInvoiceDate = varInvoices.human_invoice_date;
                        var varDueDate = varInvoices.human_due_date;

                        var ai = oTable.fnAddData( [varInvoiceNumber,varClientId,varInvoiceDate,varDueDate, createButtons(varInvoiceId) ] );
                        var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
                        $(nRow).attr('id','row_'+varInvoiceId);


                        //addInvoiceEditClickEvent(varInvoiceId);
                        addInvoiceDeleteClickEvent(varInvoiceId,i);
                    }
                }
            }
        }
    });

    function addInvoiceDeleteClickEvent(varInvoiceId,varRowNum) {
        var invoice_deletion_obj = {
            invoice_id: varInvoiceId,
            row_num: varRowNum,
            printObj: function () {
                return this.invoice_id + ' row : ' + this.row_num;
            }
        }

        $('#del_'+varInvoiceId).click({param_invoice_deletion_obj:invoice_deletion_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this invoice?" ,
                    "Delete Invoice",
                    "Yes", "No", deleteInvoice,e.data.param_invoice_deletion_obj);
        });
    }

    function deleteInvoice( varInvoiceDeletionObj ){
        $('#delete_invoice_id').val(varInvoiceDeletionObj.invoice_id);

        var actionUrl = "/proc_delete_invoice.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_invoice").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processInvoiceDeletion);
    }
    function processInvoiceDeletion (jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    var varIsInvoiceDeleted = jsonResponseObj.is_deleted;

                    if(varIsInvoiceDeleted){
                        var varInvoiceId = jsonResponseObj.deleted_invoice_id;
                        $('#delete_invoice_id').val('');

                        var oTable = objInvoiceTable;
                        if(oTable!='' && oTable!=undefined) {
                            oTable.fnDeleteRow((oTable.$('#row_'+varInvoiceId))[0] );
                        }

                    } else {
                        displayMssgBoxAlert("The registry was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteRegistry - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteRegistry - 2)", true);
        }
    }

    function createButtons( varId ){
        var varButtons = '';
        varButtons = varButtons + createEditButton( varId );
        varButtons = varButtons + '&nbsp;&nbsp;&nbsp;';
        varButtons = varButtons + createDeleteButton( varId );
        return varButtons;
    }

    function createEditButton(varId){
        return '<a id="edit_'+varId+'" class="btn btn-default btn-xs" href="/com/events/dashboard/invoice/edit_invoices.jsp?invoice_id='+varId+'"><i class="fa fa-pencil"></i> Edit</a>';
    }
    function createDeleteButton(varId){
        return '<a id="del_'+varId+'" class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</a>';
    }

    function initializeTable(){

        objInvoiceTable =  $('#every_invoice').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,

            "aoColumns": [
                null,
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>