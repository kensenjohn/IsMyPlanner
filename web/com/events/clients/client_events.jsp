<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sClientId = ParseUtil.checkNull(request.getParameter("client_id"));
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="client_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Client <span id="client_name_title"></span> Events</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/clients/client_tab.jsp">
                            <jsp:param name="client_events_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="client_event_table" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-4" role="columnheader">Date</th>
                            <th class="sorting" role="columnheader">Name</th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>

                        <tbody role="alert" id="every_event_rows">
                        </tbody>
                    </table>
                    <form id="frm_delete_event">
                        <input type="hidden" id="event_id" name="event_id" value="">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/clients/clientcontactinfo.js"></script>
<script src="/js/clients/clientEvents.js"></script>
<script   type="text/javascript">
    var varClientId = '<%=sClientId%>';
    $(window).load(function() {
         if(varClientId !='') {
             console.log('invoking load Client detail minimum = ' + varClientId);
             loadClientDetail(varClientId, 'event_info' , populateClientMinimum);
             loadClientEvents(varClientId, 'event_info' , populateClientEvents);
         } else {
             displayMssgBoxAlert("We were unable to process your request. Please try again later (loadClientEvent - 1)", true);
         }
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>