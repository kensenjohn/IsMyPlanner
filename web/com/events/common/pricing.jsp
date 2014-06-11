<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Configuration" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<%
    String sReferer = ParseUtil.checkNull(request.getHeader("referer"));
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    String applicationDomain = ParseUtil.checkNull(applicationConfig.get(Constants.APPLICATION_DOMAIN, "ismyplanner.com"));


    UserBean userBean = new  UserBean();
    if(request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        userBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);
    }
    Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
%>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="none" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Pricing</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12" >
                    <div class="row">
                        <div class="col-md-10" >
                            <div class="boxedcontent">
                                <div class="widget">
                                    <div class="content">
                                        <div class="row">
                                            <div class="col-xs-3" >
                                                <div class="row">
                                                    <div class="col-xs-12" style="text-align: center;">
                                                        <h5 class="pricing-cost-text">3 Months Free</h5>
                                                    </div>
                                                    <div class="col-xs-12" style="text-align: center;">
                                                        <h4 class="pricing-cost-text"> + </h4>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-xs-12" style="text-align: center;">
                                                        <h1 class="pricing-cost">$34.99</h1>
                                                    </div>
                                                    <div class="col-xs-12" style="text-align: center;">
                                                        <h6>per month (No Contract)</h6>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-9" >
                                                <div class="row">
                                                    <div class="col-xs-12" >
                                                        <h5><i class="fa fa-check-square-o"></i>&nbsp;&nbsp;Create personalized url {your business name}.<%=applicationDomain%></h5>
                                                    </div>
                                                    <div class="col-xs-12" >
                                                        <h5><i class="fa fa-check-square-o"></i>&nbsp;&nbsp;Branded landing page with logo and social media feeds</h5>
                                                    </div>
                                                    <div class="col-xs-12" >
                                                        <h5><i class="fa fa-check-square-o"></i>&nbsp;&nbsp;Create unlimited number of branded invoices</h5>
                                                    </div>
                                                    <div class="col-xs-12" >
                                                        <h5><i class="fa fa-check-square-o"></i>&nbsp;&nbsp;Manage Clients Vendors and Team Members</h5>
                                                    </div>
                                                    <div class="col-xs-12" >
                                                        <h5><i class="fa fa-check-square-o"></i>&nbsp;&nbsp;Clients can create websites for their events</h5>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <%
                        if(userBean == null || (userBean!=null && Utility.isNullOrEmpty(userBean.getUserId()) )   ) {
                    %>
                            <div class="row">
                                <div class="col-xs-10" style="text-align:center;">
                                    <a href="/com/events/common/credentials.jsp" class="btn btn-filled btn-lg">Start Your Free Trial</a>
                                </div>
                            </div>
                    <%
                        }
                        appLogging.info("User Bean : " + userBean );
                    %>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script   type="text/javascript">
    if(mixpanel!=undefined) {
        mixpanel.track("pricing.jsp", {'referrer' : '<%=sReferer%>'});
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>