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
        <jsp:param name="event_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">About</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-8" >
                                <p>IsMyPlanner.com is an essential hub and portal for Event Planners and Wedding Planners.
                                    Planners can quickly create branded sites targeted for their clients.
                                    Manage clients, team members and partner vendors from here.
                                    Keep track of all progress while planning.
                                    Clients can collaborate and communicate quickly with the planner.
                                </p>

                                <p>The Event Planner can give access to their clients to specialized planning tools (website creation, rsvp management, guest list tracker etc.)
                                    Clients can build website with modern themes with high resolution for every device.
                                     </p>

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