<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
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
            <div class="page-title">Dashboard</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="dashboard_active" value="active"/>
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
                <div class="col-md-4">
                    <div class="info_tile">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <span class="glyphicon glyphicon-bell"></span>
                                </div>
                                <div>
                                    <h3>5</h3>
                                    <h5>Num of TODOs</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info_tile red">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <span class="glyphicon glyphicon-signal"></span>
                                </div>
                                <div>
                                    <h3>35</h3>
                                    <h5>Events This Week</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="info_tile green">
                        <div class="widget">
                            <div class="content">
                                <div class="icon_display">
                                    <span class="glyphicon glyphicon-envelope"></span>
                                </div>
                                <div>
                                    <h3>34</h3>
                                    <h5>Emails Scheduled ForToday</h5>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class = row>
                <div class="col-md-6">
                    &nbsp;
                </div>
            </div>
            <div class = row>
                <div class="col-md-6">
                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <h4>Notifications</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <h4>TODO</h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
