<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
 <%
     String sUserId = request.getParameter("user");
 %>
<body>
<div class="page_wrap">
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <h1>Test My Routes for user <%=sUserId%></h1>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>

<script type="text/javascript">
    var varUserId = '<%=sUserId%>';
    $(window).load(function() {
        displayMssgBoxAlert('Hello User - ' + varUserId , true);
    });
</script>

<jsp:include page="/com/events/common/footer_bottom.jsp"/>
