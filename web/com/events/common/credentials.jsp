<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    // If a user is loggin in from a sub domain do not show the registration form
    // This will have to be fixed based on some feed back
    boolean isShowRegistrationForm = true;
    if( session.getAttribute("SUBDOMAIN_SHOW_REGISTRATION") != null ) {
        isShowRegistrationForm = (Boolean)session.getAttribute("SUBDOMAIN_SHOW_REGISTRATION") ;
    }

    if(session!=null && session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null){
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()))  {
            response.sendRedirect( "/com/events/common/my_account.jsp" );
        }
    }
%>
<body>
    <div class="page_wrap">
        <jsp:include page="/com/events/common/top_nav.jsp"/>
        <jsp:include page="/com/events/common/menu_bar.jsp"/>
        <div class="breadcrumb_format">
            <div class="container">
                <div class="page-title">Login or Register</div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    &nbsp;
                </div>
            </div>
                <div class="row">
                    <div class="col-md-6" style="margin-top: 10px">
                        <div class="boxedcontent">
                            <div class="widget">
                                <div class="content">
                                    <h4>Login</h4>
                                    <form method="post" id="frm_login" action="/proc_login.aeve">
                                        <div class="form-group">
                                            <label for="loginEmail" class="form_label">Email address</label><span class="required"> *</span>
                                            <input type="email" class="form-control" id="loginEmail" name="loginEmail" placeholder="Enter email">
                                        </div>
                                        <div class="form-group">
                                            <label for="loginPassword"  class="form_label">Password</label><span class="required"> *</span>
                                            <input type="password" class="form-control" id="loginPassword" name="loginPassword" placeholder="Password">
                                        </div>
                                        <div class="checkbox">
                                            <label for="loginRememberMe" class="form_label">
                                                <input type="checkbox" id="loginRememberMe" name = "loginRememberMe">
                                                Remember Me
                                            </label>
                                        </div>
                                        <button type="button" class="btn  btn-filled" id="btn_login">Sign In</button>
                                        <a href="/com/events/common/forgot.jsp">Forgot Your Password?</a>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6" style="margin-top: 10px">
                        <div class="boxedcontent">
                            <div class="widget">
                                <div class="content">
                                    <h4>Register</h4>
                                    <%
                                        if(isShowRegistrationForm) {
                                    %>

                                            <form method="post"  id="frm_register" action="/proc_register.aeve">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="registerEmail" class="form_label">Email address</label> <span class="required"> *</span>
                                                            <input type="email" class="form-control" id="registerEmail" name="registerEmail" placeholder="Enter email">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="registerPassword"  class="form_label">Password</label><span class="required"> *</span>
                                                            <input type="password" class="form-control" id="registerPassword" name="registerPassword" placeholder="Password">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group" id="div_business_name">
                                                    <div class="row" >
                                                        <div class="col-md-12">
                                                            <label for="registerBusinessName"  class="form_label">Business Name</label><span class="required"> *</span>
                                                            <input type="text" class="form-control" id="registerBusinessName" name="registerBusinessName" placeholder="Business Name">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="checkbox">
                                                    <label for="registerIsPlanner" class="form_label">
                                                        <input type="checkbox" id="registerIsPlanner" name = "registerIsPlanner"  onclick="return false" checked>
                                                        I am a planner
                                                    </label>
                                                </div>
                                                <button type="button" class="btn  btn-filled"  id="btn_register">Submit</button>
                                            </form>
                                    <%
                                        }else{
                                    %>
                                            <span>You may already be registered!<br>Please check your inbox or contact your Event Planner for your login credentials</span>
                                    <%
                                        }
                                    %>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    </div>
</body>
<%
    String sRedirectAfterLoginReg = ParseUtil.checkNullObject(session.getAttribute(Constants.AFTER_LOGIN_REDIRECT));
%>
<form id="frm_passthru">

</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script   type="text/javascript">
    $(window).load(function() {
        $('#btn_login').click(function(){
            loginUser(getResult);
        });
        $('#btn_register').click(function(){
            registerUser(getResult);
        });
        if(mixpanel!=undefined) {
            mixpanel.track("credentials.jsp");
        }
    });
    function loginUser( callbackmethod) {
        var actionUrl = "/proc_login.aeve";
        var methodType = "POST";
        var dataString = $("#frm_login").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function registerUser( callbackmethod) {
        var actionUrl = "/proc_register.aeve";
        var methodType = "POST";
        var dataString = $("#frm_register").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult)
    {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                var varIsMessageExist = varResponseObj.is_message_exist;
                if(varIsMessageExist == true) {
                    var jsonResponseMessage = varResponseObj.messages;
                    var varArrErrorMssg = jsonResponseMessage.error_mssg;
                    displayMssgBoxMessages(varArrErrorMssg, true);
                }

            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                //displayMssgBoxAlert('User was logged in successfully', false);
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varCookieUserId = jsonResponseObj.cookieuser_id;

                    if(varCookieUserId!='' && varCookieUserId!=undefined){
                        setUserCookie('<%=Constants.COOKIEUSER_ID%>',varCookieUserId);
                    }

                    var varPassThruLink = jsonResponseObj.pass_thru_link;
                    if(varPassThruLink!=undefined) {
                        $('#frm_passthru').attr('action',varPassThruLink );
                    }
                    $('#frm_passthru').submit();
                }
            } else {
                alert("Please try again later 1.");
            }
        } else {
            alert("Response is nullr 3.");
        }
    }
    function setUserCookie(cookieName, cookieValue) {
        var exdays = 1;
        var exdate=new Date();
        exdate.setDate(exdate.getDate() + exdays);
        var c_value= cookieValue + ((exdays==null) ? "" : "; expires="+exdate.toUTCString()) + ("; path=/");

        document.cookie=cookieName + "=" + c_value;
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>