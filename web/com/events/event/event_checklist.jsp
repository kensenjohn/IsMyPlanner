<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
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
        <jsp:param name="event_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Event Checklist- <span id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_checklist_active" value="active"/>
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
                    <a href="/com/events/event/checklist/edit_event_checklist.jsp?event_id=<%=sEventId%>" class="btn btn-filled">
                        <span><i class="fa fa-plus"></i> New Checklist </span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_checklist" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-xs-1" role="columnheader">Checklist</th>
                            <th class="sorting col-md-2" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_checklist_template_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/event/event_info.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        initializeTableChecklist();
    });
    function initializeTableChecklist(){

        objChecklistTemplate=  $('#every_checklist').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                { "bSortable": false,"sClass":"center" }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>