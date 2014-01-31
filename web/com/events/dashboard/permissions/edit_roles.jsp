<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Perm" %>
<%@ page import="com.events.users.permissions.UserRolePermission" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.events.bean.users.permissions.*" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sRoleId = ParseUtil.checkNull(request.getParameter("role_id"));
    boolean loadRolePermissions = false;
    if(!Utility.isNullOrEmpty(sRoleId)) {
        loadRolePermissions = true;
    }

    UserBean loggedInUserBean = (UserBean)request.getSession().getAttribute(Constants.USER_LOGGED_IN_BEAN);

    ArrayList<PermissionGroupBean> arrPermissionGroupBean = new ArrayList<PermissionGroupBean>();
    ArrayList<PermissionsBean> arrDefaultPermissionsBean = new ArrayList<PermissionsBean>();
    ArrayList<RolePermissionsBean> arrRolePermissionsBean = new ArrayList<RolePermissionsBean>();
    RolesBean roleBean  = new RolesBean();

    boolean canViewRolePermissions = false;
    boolean canEditRolePermissions = false;
    if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {
        CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
        if( checkPermission.can(Perm.VIEW_ROLE_PERMMISIONS ) ) {
            Constants.USER_TYPE loggedInUserType = loggedInUserBean.getUserType();
            UserRolePermissionRequestBean userRolePermRequest = new UserRolePermissionRequestBean();
            userRolePermRequest.setRoleId( sRoleId);
            userRolePermRequest.setUserType(loggedInUserType);
            UserRolePermission userRolePermission = new UserRolePermission();
            UserRolePermissionResponseBean userRolePermissionResponseBean = userRolePermission.getRolePermissions(userRolePermRequest);
            if(userRolePermissionResponseBean!=null){
                arrPermissionGroupBean = userRolePermissionResponseBean.getArrPermissionGroupBean();
                arrDefaultPermissionsBean = userRolePermissionResponseBean.getArrDefaultPermissionsBean();
                arrRolePermissionsBean = userRolePermissionResponseBean.getArrRolePermissionsBean();
                roleBean = userRolePermissionResponseBean.getRoleBean();
            }
            canViewRolePermissions = true;
        } else {
            response.sendRedirect("/com/events/common/error/stop.jsp");
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
            <div class="page-title">Roles and Permissions <span id="role_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="roles_and_permissions_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <%
                if(canViewRolePermissions){

            %>
            <div class="row">
                <div class="col-md-12">
                    <form method="post" id="frm_role_permissions">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="roleName" class="form_label">Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="roleName" name="roleName" placeholder="Role Name" value="<%=roleBean.getName()%>" >
                                </div>
                            </div>
                        </div>
                    <%
                        int columnNum = 0;
                        if(arrPermissionGroupBean!=null && !arrPermissionGroupBean.isEmpty() ) {
                            for(PermissionGroupBean permissionGroupBean : arrPermissionGroupBean ) {


                                if(columnNum == 0) {
                    %>
                                <div  class="row" >
                    <%
                                }
                    %>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <h5><%=permissionGroupBean.getGroupName()%></h5>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                    <%
                                                    if( arrDefaultPermissionsBean!=null && !arrDefaultPermissionsBean.isEmpty() ) {
                                                        for(PermissionsBean permissionsBean : arrDefaultPermissionsBean ){
                                                            if(permissionsBean.getPermissionGroupId().equalsIgnoreCase( permissionGroupBean.getPermissionGroupId() )) {
                    %>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <input type="checkbox" name="perm_checkbox" value="<%=permissionsBean.getPermissionId()%>"
                    <%
                                                                if( arrRolePermissionsBean!=null && !arrRolePermissionsBean.isEmpty() ){
                                                                    for(RolePermissionsBean rolePermissionsBean : arrRolePermissionsBean ) {
                                                                        if(rolePermissionsBean.getPermissionId().equalsIgnoreCase( permissionsBean.getPermissionId() )) {
                    %>
                                                                            checked
                    <%
                                                                        }
                                                                    }
                                                                }
                    %>
                                                                /> &nbsp;&nbsp;&nbsp;<%=permissionsBean.getDisplayText()%>
                                                            </div>
                                                        </div>

                    <%
                                                            }
                                                        }
                                                    }
                    %>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                    <%
                                if( columnNum == 2 ) {
                                    columnNum=0 ;
                    %>
                                    <div class="row">
                                        <div class="col-md-12">
                                            &nbsp;
                                        </div>
                                    </div>
                            </div>
                    <%
                                } else {
                                    columnNum++;
                                }
                    %>



                    <%
                            }
                        }
                    %>
                        <input type="hidden" id="role_id"  name="role_id" value="<%=roleBean.getRoleId()%>">
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <button type="button" class="btn btn-filled" id="btn_save_role">Save</button>
                </div>
            </div>
            <%

                }
                else {
            %>
                <jsp:include page="/com/events/common/error/stop.jsp"/>
            <%
                }
            %>

        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script   type="text/javascript">
    $(window).load(function() {
        //loadRolePermissions(populateRolePermissions);
        //initializeTable();
        $('#btn_save_role').click(function(){
            saveRolePermissions(getResult)
        });
    });
    function saveRolePermissions(callbackmethod) {
        var actionUrl = "/proc_save_role_permissions.aeve";
        var methodType = "POST";
        var dataString = $("#frm_role_permissions").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {

    }
</script>