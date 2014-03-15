<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.bean.users.UserBean" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<%
    String sSecureTokenId = ParseUtil.checkNull(request.getParameter("lotophagi"));
%>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp"/>
    <jsp:include page="/com/events/common/menu_bar.jsp"/>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Welcome <span id="name"></span> - Set New Password</div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <form method="post" id="frm_send_reset_password_link">
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-5">
                                <label for="resetEmail" class="form_label">Email</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="resetEmail" name="resetEmail" placeholder="Email">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-5">
                                <label for="resetPassword" class="form_label">Password</label><span class="required"> *</span>
                                <input type="password" class="form-control" id="resetPassword" name="resetPassword" placeholder="Password">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-5">
                                <label for="resetConfirmPassword" class="form_label">Confirm Password</label><span class="required"> *</span>
                                <input type="password" class="form-control" id="resetConfirmPassword" name="resetConfirmPassword" placeholder="Confirm Password">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-7">
                                <span id="status_mssg"></span>
                            </div>
                        </div>
                    </div>

                    <button  type="button" class="btn  btn-filled" id="btn_reset_password">
                        <span>Set New Password</span>
                    </button>
                    <input type="hidden" id="lotophagi" name="lotophagi" value="<%=sSecureTokenId%>"/>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script   type="text/javascript">
    $(window).load(function() {
        $('#btn_reset_password').click(function(){
            resetPassword(getResult);
        });
    });
    function resetPassword( callbackmethod) {
        var actionUrl = "/proc_reset_password.aeve";
        var methodType = "POST";
        var dataString = $("#frm_send_reset_password_link").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varStatusMssgText = 'Password has been reset. Please login with the new password.'
                displayMssgBoxAlert(varStatusMssgText, false);
                $('#status_mssg').text(varStatusMssgText);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>