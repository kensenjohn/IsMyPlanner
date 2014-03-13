<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="com.events.bean.users.UserBean" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    String sUserId = ParseUtil.checkNull(request.getParameter("user_id"));
    String sUserInfoId = ParseUtil.checkNull(request.getParameter("userinfo_id"));
    boolean loadTeamMember = false;
    if(!Utility.isNullOrEmpty(sUserId)) {
        loadTeamMember = true;
    }

    boolean isTeamMemberLoggedInUsed = false;
    if(session!=null && session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null){
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId()) && loggedInUserBean.getUserId().equalsIgnoreCase(sUserId))  {
            isTeamMemberLoggedInUsed = true;
        }
    }
%>
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
            <div class="page-title">Team Member</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="our_team_active" value="active"/>
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
                    <form method="post" id="frm_save_team_members">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="team_memberFirstName" class="form_label">First Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="team_memberFirstName" name="team_memberFirstName" placeholder="First Name">
                                </div>
                                <div class="col-md-4">
                                    <label for="team_memberLastName" class="form_label">Last Name</label>
                                    <input type="text" class="form-control" id="team_memberLastName" name="team_memberLastName" placeholder="Last Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="team_memberEmail" class="form_label">Email</label><span class="required"> *</span>
                                    <input type="email" class="form-control" id="team_memberEmail" name="team_memberEmail" placeholder="Email">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="team_memberCellPhone" class="form_label">Cell Phone</label>
                                    <input type="tel" class="form-control" id="team_memberCellPhone" name="team_memberCellPhone" placeholder="Cell Phone">
                                </div>
                                <div class="col-md-4">
                                    <label for="team_memberWorkPhone" class="form_label">Phone</label>
                                    <input type="tel" class="form-control" id="team_memberWorkPhone" name="team_memberWorkPhone" placeholder="Phone">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="team_memberRole" class="form_label">Role (Permissions)</label><span class="required"> *</span>
                                    <select class="form-control" id="team_memberRole" name="team_memberRole">
                                        <option value="">Select A Role</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                &nbsp;
                            </div>
                        </div>
                            <%
                                if(isTeamMemberLoggedInUsed) {
                            %>
                                    <div class="row">
                                        <div class="col-md-8">
                                            <div class="boxedcontent">
                                                <div class="widget">
                                                    <div class="content error_background">
                                                        <h5>Security Warning</h5>
                                                        <span>You are not authorized to update your Role.<br>
                                                        Please use your "My Account" by clicking your login name to update your contact information.</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                            <%
                                } else {
                            %>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <button type="button" class="btn  btn-filled" id="btn_save_teammember">Save Changes</button>
                                        </div>

                                        <!-- <div class="col-md-4">
                                            <button type="button" class="btn  btn-filled" id="btn_reset_passworf">Reset Password</button>
                                        </div> -->
                                    </div>
                            <%
                                }
                            %>
                        <input type="hidden"  id="vendor_id" name="vendor_id" value="">
                        <input type="hidden"  id="user_id" name="user_id" value="<%=sUserId%>">
                        <input type="hidden"  id="userinfo_id" name="userinfo_id" value="<%=sUserInfoId%>">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<form id="frm_load_team_member">
    <input type="hidden" name="user_id" value="<%=sUserId%>">
    <input type="hidden" name="userinfo_id" value="<%=sUserInfoId%>">
</form>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    var varLoadTeamMember = <%=loadTeamMember%>
    $(window).load(function() {

        loadRoles(populateRolesList)
        $('#btn_save_teammember').click(function(){
            saveTeamMember(getResult);
        })
    });

    function loadTeamMember(callbackmethod, userId) {
        if(userId!=undefined) {
            var actionUrl = "/proc_load_team_member.aeve";
            var methodType = "POST";
            var dataString = $('#frm_load_team_member').serialize();
            makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
        }
    }

    function populateTeamMember (jsonResult) {
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
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    processTeamMember(jsonResponseObj.user_bean);
                    processTeamMemberRole(jsonResponseObj.user_role);
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateTeamMember - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateTeamMember - 2)", true);
        }
    }
    function processTeamMemberRole(varUserRole) {
        if(varUserRole != undefined ) {
            var varTotalRules = varUserRole.total_roles;
            for( var i = 0; i<varTotalRules; i++) {
                $('#team_memberRole').val( varUserRole[i].role_id )
            }
        }
    }
    function processTeamMember(varUserBean) {
        if(varUserBean!=undefined) {
            $('#user_id').val(varUserBean.user_id);
            $('#userinfo_id').val(varUserBean.userinfo_id);

            var varUserInfoBean = varUserBean.user_info_bean;
            $('#team_memberFirstName').val(varUserInfoBean.first_name);
            $('#team_memberLastName').val(varUserInfoBean.last_name);
            $('#team_memberEmail').val(varUserInfoBean.email);
            $('#team_memberCellPhone').val(varUserInfoBean.cell_phone);
            $('#team_memberWorkPhone').val(varUserInfoBean.phone_num);
        }
    }

    function loadRoles(callbackmethod) {
        var actionUrl = "/proc_load_all_roles.aeve";
        var methodType = "POST";
        var dataString = '';
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateRolesList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfRoles = jsonResponseObj.num_of_roles;
                    if(varNumOfRoles>0){
                        processRolesList(varNumOfRoles, jsonResponseObj.every_role );


                        if(varLoadTeamMember){
                            var varUserId = '<%=sUserId%>';
                            loadTeamMember(populateTeamMember,varUserId);
                        }
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }
    function processRolesList(varNumOfRoles, everyRoleList) {
        var varDropDownRolesList = $('#team_memberRole');
        for(i=0;i<varNumOfRoles;i++){
            var varEveryRoleBean = everyRoleList[i];
            varDropDownRolesList.append('<option value="'+varEveryRoleBean.role_id+'">'+varEveryRoleBean.name+'</option>');
        }
    }
    function saveTeamMember(callbackmethod) {
        var actionUrl = "/proc_save_team_member.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_team_members").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        $('#user_id').val(jsonResponseObj.user_id);
                        $('#userinfo_id').val(jsonResponseObj.userinfo_id);

                    }
                }
                displayMssgBoxAlert('Your changes were saved successfully.', false);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
