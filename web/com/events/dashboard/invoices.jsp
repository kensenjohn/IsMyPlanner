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
                            <th class="sorting" role="columnheader">Invoice Num</th>
                            <th class="sorting  col-md-3 " role="columnheader">Client</th>
                            <th class="sorting" role="columnheader">Date </th>
                            <th class="sorting" role="columnheader">Total </th>
                            <th class="sorting" role="columnheader">Balance </th>
                            <th class="sorting" role="columnheader">Due Date </th>
                            <th class="sorting" role="columnheader">Status </th>
                            <th class="center" role="columnheader"></th>
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
<form id="frm_delete_partner_vendor">
    <input type="hidden" id="delete_partner_vendor_id" name="partner_vendor_id" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        loadPartnerVendors(populatePartnerVendorsList);
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>