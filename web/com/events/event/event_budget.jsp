<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
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
                <div class="col-md-12">

                        <div class="boxedcontent">
                            <div class="widget">
                                <div class="content">
                                    <div class="row">
                                        <div class="col-xs-5">
                                            <h4>Budget</h4>
                                        </div>
                                        <form class="form-horizontal" id="frm_save_todo">
                                            <div class="col-xs-3">
                                                <label for="budget_estimate" class="form_label">Estimate:</label>
                                                <input type="text" class="form-control" id="budget_estimate" name="budget_estimate"/>
                                            </div>
                                            <div class="col-xs-3">
                                                <label for="budget_actual" class="form_label">Actual:</label>
                                                <input type="text" class="form-control" id="budget_actual" name="budget_actual"/>
                                            </div>
                                            <div class="col-xs-3">
                                                &nbsp;
                                            </div>
                                        </form>
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
