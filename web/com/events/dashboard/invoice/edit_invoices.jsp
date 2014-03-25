<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
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
    <form class="form-horizontal">
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
                <div class="col-md-10">
                    <div class="row">
                        <div class="col-md-12">
                            <label for="invoiceClient" class="form_label">Client</label><span class="required"> *</span>
                            <select class="form-control" id="invoiceClient" name="invoiceClient">
                                <option value="">Select A Client</option>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <label for="invoiceNumber" class="form_label">Invoice Number</label><span class="required"> *</span>
                            <input type="text" class="form-control" id="invoiceNumber" name="invoiceNumber"/>
                        </div>
                        <div class="col-md-6">
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
    <div class="container container-fixed">
        <div class="content_format">
            <div class="row">
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
                        <!--<tr>
                            <td><input type="text" class="form-control" id="item_1" name="item_1"/></td>
                            <td><input type="text" class="form-control" id="item_description_1" name="item_description_1"/></td>
                            <td>
                                <div class="input-group">
                                    <span class="input-group-addon">$</span>
                                    <input type="text" class="form-control" id="unit_cost_1" name="unit_cost_1"/>
                                </div>
                            </td>
                            <td><input type="text" class="form-control" id="quantity_1" name="quantity_1"/></td>
                            <td style="text-align: right;">

                                <span> 56.00</span>
                            </td>
                            <td><button type="button" class="btn btn-xs btn-default" id="delete_1" name="delete_1"><i class="fa fa-trash-o"></i>&nbsp; Delete</button></td>
                        </tr> -->
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
                </div>
                <div class="col-md-6">
                    <div class="row"  >
                        <div class="col-md-4"  style="text-align:right;" >
                            <h5>Sub Total</h5>
                        </div>
                        <div class="col-md-5"  style="text-align:right;">
                            <h5>$123.45</h5>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4" style="text-align:right;">
                            <h5>Discount</h5>
                        </div>
                        <div class="col-md-5"  style="text-align:right;">
                            <h6>$234.00</h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4" style="text-align:right;">
                            <h5>Tax</h5>
                        </div>
                        <div class="col-md-5"  style="text-align:right;">
                            <h6>$67.00</h6>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4" style="text-align:right;">
                            <h5>Balance Due</h5>
                        </div>
                        <div class="col-md-5"  style="text-align:right;">
                            <h4>$1567.34</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
     </form>
</div>
<form id="frm_load_invoice">
    <input type="hidden"  id="load_invoice_id"  name="invoice_id" value="<%=sInvoiceId%>">
</form>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        var varLoadInvoice = <%=loadInvoice%>
        if(varLoadInvoice){
            var varInvoiceId = '<%=sInvoiceId%>';
            //loadPartnerVendor(populatePartnerVendor,varPartnerVendorId);
        }
        $('#btn_save_invoice').click(function(){
            //savePartnerVendor(getResult);
        })
        initializeTable();
        $('#add_item').click(function(){
            var itemId = getTimeStamp();
            fnClickAddRow(itemId);
        })

    });
    function initializeTable(){

        objInvoiceItemsTable =  $('#invoice_item').dataTable({
            "bPaginate": false,
            "bInfo": false,

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
        objInvoiceItemsTable.fnAddData( [
            '<input type="text" class="form-control" id="item_'+varItemId+'" name="item_'+varItemId+'"/>',
            '<input type="text" class="form-control" id="item_description_'+varItemId+'" name="item_description_'+varItemId+'"/>',
            '<div class="input-group"> <span class="input-group-addon">$</span><input type="text" class="form-control" id="unit_cost_'+varItemId+'" name="unit_cost_'+varItemId+'"/></div>',
            '<input type="text" class="form-control" id="quantity_'+varItemId+'" name="quantity_'+varItemId+'"/>',
            '<span id="total_'+varItemId+'"></span>',
            '<button class="btn btn-xs btn-default" id="delete_'+varItemId+'" name="delete_'+varItemId+'"><i class="fa fa-trash-o"></i>&nbsp; Delete</button>'] );



            $('#unit_cost_'+varItemId).bind("keyup paste input", function() {
                console.log( $('#unit_cost_'+varItemId).val() );
            });

            $('#quantity_'+varItemId).bind("keyup paste input", function() {
                console.log( $('#quantity_'+varItemId).val() );
            });

    }

    function getTimeStamp() {
        return Math.round(new Date().getTime() / 1000) ;
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>