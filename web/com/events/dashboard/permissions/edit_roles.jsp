<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Perm" %>
<%@ page import="com.events.bean.users.permissions.UserRolePermissionRequestBean" %>
<%@ page import="com.events.users.permissions.UserRolePermission" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
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

    HashMap<String, StringBuilder > hmPermissionTables = new HashMap<String, StringBuilder >();
    if(loggedInUserBean!=null && !"".equalsIgnoreCase(loggedInUserBean.getUserId())) {
        CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
        if( checkPermission.can(Perm.VIEW_ROLE_PERMMISIONS ) ) {
            Constants.USER_TYPE loggedInUserType = loggedInUserBean.getUserType();
            UserRolePermissionRequestBean userRolePermRequest = new UserRolePermissionRequestBean();
            userRolePermRequest.setRoleId( sRoleId);
            userRolePermRequest.setUserType(loggedInUserType);
            UserRolePermission userRolePermission = new UserRolePermission();
            hmPermissionTables = userRolePermission.getRolePermissions(userRolePermRequest);
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
            <div class="row">
                <div class="col-md-12">
                    <%
                        if(hmPermissionTables!=null && !hmPermissionTables.isEmpty()) {
                            for(Map.Entry<String,StringBuilder> mapPermissionTable : hmPermissionTables.entrySet()) {
                    %>
                                <%=mapPermissionTable.getValue()%>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_role_permissions">
    <input type="hidden" id="role_id" name="role_id" value="<%=sRoleId%>">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script   type="text/javascript">
    var varArray = '';
    <%
        if(hmPermissionTables!=null && !hmPermissionTables.isEmpty()) {
            for(Map.Entry<String,StringBuilder> mapPermissionTable : hmPermissionTables.entrySet()) {
    %>
        initializeTable('table_'+<%=mapPermissionTable.getKey()%>);

    <%
            }
        }
    %>
    $(window).load(function() {
        //loadRolePermissions(populateRolePermissions);
        //initializeTable();
    });
    function loadRolePermissions(callbackmethod) {
        var actionUrl = "/proc_load_role_permissions.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_role_permissions").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateRolePermissions(jsonResult) {

    }

    function initializeTable(varTableId){

        objEveryRoleTable =  $('#'+varTableId).dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                { "bSortable": false }
            ]
        });
    }
</script>