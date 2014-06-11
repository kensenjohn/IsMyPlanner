<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

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
            <div class="page-title">Edit Event Phone - <span id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <%if(loadEventInfo) {%>
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_phone_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-4">
                    <div class="info_tile">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <i class="fa fa-cog"></i>
                                </div>
                                <div>
                                    <h3>Demo Mode</h3>
                                    <h5>Free Minutes and Text Messages</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info_tile">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <i class="fa fa-phone"></i>
                                </div>
                                <div>
                                    <h3>5</h3>
                                    <h5>Call Minutes</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info_tile">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <i class="fa fa-comment-o"></i>
                                </div>
                                <div>
                                    <h3>5</h3>
                                    <h5>Sms Messages</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-3">
                            <h2>(214) 432-5420</h2>
                        </div>
                        <div class="col-md-3">
                            <h2>Key : 34526</h2>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            &nbsp;
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>