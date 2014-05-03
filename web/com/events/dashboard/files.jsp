<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Perm" %>
<%@ page import="com.events.bean.users.ParentTypeBean" %>
<%@ page import="com.events.users.AccessUsers" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sVendorId = Constants.EMPTY;
    String sClientId = Constants.EMPTY;
    boolean isLoggedInUserAClient = false;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        AccessUsers accessUser = new AccessUsers();
        ParentTypeBean parentTypeBean = accessUser.getParentTypeBeanFromUser( loggedInUserBean );
        if(parentTypeBean!=null) {
            if(parentTypeBean.isUserAVendor()) {
                sVendorId = parentTypeBean.getVendorId();
                isLoggedInUserAClient = false;
            }

            if(parentTypeBean.isUserAClient()) {
                sClientId = parentTypeBean.getClientdId();
                isLoggedInUserAClient = true;
            }
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
            <div class="page-title">Dashboard - Files</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="files_active" value="active"/>
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
                <div class="col-md-2">
                    <a href="/com/events/dashboard/files/upload_files.jsp" class="btn btn-filled">
                        <span><i class="fa fa-cloud-upload"></i> Upload New File</span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_file_uploaded" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-xs-2" role="columnheader">Title (File Group)</th>
                            <th class="sorting col-xs-2" role="columnheader">Number of Files</th>
                            <%
                                if(!isLoggedInUserAClient){
                            %>
                                    <th class="sorting col-xs-2" role="columnheader">Clients</th>
                            <%
                                }
                            %>

                            <th class="center col-md-2" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_file_uploaded_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_file_group">

</form>
<form id="frm_delete_file_group">
    <input type="hidden" name="shared_file_id" name="shared_file_id" />
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script   type="text/javascript">
    var varIsLoggedInUserAClient = <%=isLoggedInUserAClient%>;
    $(window).load(function() {
        if(varIsLoggedInUserAClient) {
            initializeTableForClient();
        } else {
            initializeTableForVendor();
        }


        loadFileGroups( getResult );
    });

    function loadFileGroups(callbackmethod) {
        var actionUrl = "/proc_load_file_groups.aeve";
        var methodType = "POST";
        var dataString =  $("#frm_load_file_group").serialize();
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
                        var varNumOfFileGroups = jsonResponseObj.num_of_files_groups;
                        if(varNumOfFileGroups>0){
                            var varFileGroupsBean =  jsonResponseObj.file_group_bean;
                            var varFileCountObj =   jsonResponseObj.file_count_obj;
                            var varFileGroupClientObj =   jsonResponseObj.file_group_client_obj;
                            for(var varFileGroupCount = 0; varFileGroupCount<varNumOfFileGroups; varFileGroupCount++  ){
                                var varFileGroup = varFileGroupsBean[varFileGroupCount];
                                var varShareFileGroupId = varFileGroup.shared_files_group_id;

                                var varFileGroupClient =  varFileGroupClientObj[varShareFileGroupId];
                                var varFileGroupClientCount =  varFileGroupClientObj[varShareFileGroupId + '_count'];

                                var varClientName = '';
                                for( var varClientCount = 0; varClientCount<varFileGroupClientCount; varClientCount++ ){
                                    varClientName = varClientName + varFileGroupClient[varClientCount] + ' ;  ';
                                }

                                createFileTableRow( varFileGroup, varFileCountObj[varShareFileGroupId],varClientName );
                            }

                        }
                    }
                }
            }
        }
    }

    function createFileTableRow(varFileGroupsBean , varFileCount, varClientName){
        var oTable = objFileGroupTable;
        var varFileGroupName = varFileGroupsBean.files_group_name;
        if(varFileGroupName ==''){
            varFileGroupName = 'Files'
        }

        var varColumnData = [];
        if(varIsLoggedInUserAClient) {
            varColumnData = [varFileGroupName ,varFileCount , createViewButton(varFileGroupsBean.shared_files_group_id) ];
        } else{
            varColumnData = [varFileGroupName ,varFileCount,varClientName, createViewButton(varFileGroupsBean.shared_files_group_id) ]
        }
        var ai = oTable.fnAddData( varColumnData );
        var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
        $(nRow).attr('id','row_'+varFileGroupsBean.shared_files_group_id);
    }

    function createViewButton(varFilesGroupId ){
        return '<a id="view_'+varFilesGroupId+'" class="btn btn-default btn-xs" href="/com/events/dashboard/files/upload_files.jsp?file_group_id='+varFilesGroupId+'"><i class="fa fa-search"></i> View</a>';
    }
    function initializeTableForVendor(){

        console.log('initializeTableForVendor');
        var objVendorFileGroupTable =  $('#every_file_uploaded').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                null,
                { "bSortable": false,"sClass":"center" }
            ]
        });
        objFileGroupTable = objVendorFileGroupTable;
    }
    function initializeTableForClient(){

        console.log('initializeTableForClient');
        var objClientFileGroupTable =  $('#every_file_uploaded').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                { "bSortable": false,"sClass":"center" }
            ]
        });
        objFileGroupTable = objClientFileGroupTable;
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>