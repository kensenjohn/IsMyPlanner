<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value="Security Warning"/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp"/>
    <jsp:include page="/com/events/common/menu_bar.jsp"/>
    <div class="container">
        <div class="page-title">Secure Area</div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                &nbsp;
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <img src="/img/stop.png" >
            </div>
            <div class="col-md-8">
                <div class="boxedcontent">
                    <div class="widget">
                        <div class="content error_background">
                            <h1>Stop! No Access</h1><h3> Please contact your administrator to access this area.</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>