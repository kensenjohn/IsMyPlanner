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
            <div class="page-title">Contact</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12" >
                    <div class="row">
                        <div class="offset_0_5 col-md-7" >
                            <span>You may contact us through email or phone for any question regarding our product, issues or cancellations.</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="span2" >
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="offset_0_5 col-md-7" >
                            <h4>Email:</h4><span> kjohn@callseat.com</span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="span2" >
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="offset_0_5 col-md-7" >
                            <h4>Phone:</h4><span> (267) 250 2719</span>
                        </div>
                    </div>

                    <div class="row">
                        <div class="span2" >
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="offset_0_5 col-md-7" >
                            <h4>Address</h4>
                        </div>
                    </div>
                    <div class="row">
                        <div class="offset_0_5 col-md-7" >
                            <span>5316 Carnaby St, Suite 155</span>  <br>
                            <span>Irving, TX 75038</span>
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