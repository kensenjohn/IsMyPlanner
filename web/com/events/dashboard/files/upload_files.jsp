<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.users.AccessUsers" %>
<%@ page import="com.events.bean.users.ParentTypeBean" %>
<%@ page import="com.events.bean.vendors.VendorRequestBean" %>
<%@ page import="com.events.vendors.AccessVendors" %>
<%@ page import="com.events.bean.vendors.VendorBean" %>
<%@ page import="com.events.bean.clients.ClientBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.events.bean.clients.ClientRequestBean" %>
<%@ page import="com.events.clients.AccessClients" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.events.bean.users.UserRequestBean" %>
<%@ page import="com.events.bean.users.UserInfoBean" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link rel="stylesheet" href="/css/chosen.css">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sUploadFileId = ParseUtil.checkNull(request.getParameter("upload_file_id"));
    String sFileGroupId = ParseUtil.checkNull(request.getParameter("file_group_id"));

    String breadCrumbPageTitle = "Upload File - New";
    boolean loadUploadFile = false;
    if(!Utility.isNullOrEmpty(sUploadFileId)) {
        loadUploadFile = true;
        breadCrumbPageTitle = "Upload File - Edit";
    }

    boolean showClientDropDown = false;
    String sName = Constants.EMPTY;
    HashMap<Integer,ClientBean> hmClientBean = new HashMap<Integer, ClientBean>();
    String sClientId = Constants.EMPTY;
    boolean isLoggedInUserAClient = false;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        AccessUsers accessUser = new AccessUsers();
        ParentTypeBean parentTypeBean = accessUser.getParentTypeBeanFromUser( loggedInUserBean );
        if(parentTypeBean!=null) {
            if(parentTypeBean.isUserAVendor()) {
                showClientDropDown = true;

                VendorRequestBean vendorRequestBean = new VendorRequestBean();
                vendorRequestBean.setUserId( loggedInUserBean.getUserId() );
                AccessVendors accessVendor = new AccessVendors();
                VendorBean vendorBean = accessVendor.getVendorByUserId( vendorRequestBean ) ;  // get  vendor from user id

                if(vendorBean!=null && !Utility.isNullOrEmpty(vendorBean.getVendorId()) )  {
                    ClientRequestBean clientRequestBean = new ClientRequestBean();
                    clientRequestBean.setVendorId(vendorBean.getVendorId());

                    AccessClients accessClients = new AccessClients();
                    hmClientBean  = accessClients.getAllClientsSummary(clientRequestBean);
                }
            }
            if(parentTypeBean.isUserAClient()) {
                sClientId = parentTypeBean.getClientdId();
                isLoggedInUserAClient = true;
            }
            UserRequestBean userRequestBean = new UserRequestBean();
            userRequestBean.setUserId( loggedInUserBean.getUserId() );
            UserInfoBean userInfoBean = accessUser.getUserInfoFromUserId( userRequestBean );

            sName = ParseUtil.checkNull( ParseUtil.checkNull( userInfoBean.getFirstName() ) + " " + ParseUtil.checkNull( userInfoBean.getLastName() ) ) ;
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
            <div class="page-title"><%=breadCrumbPageTitle%></div>
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
                <div class="col-xs-6">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <form method="post" id="frm_upload_file"  action="/proc_upload_shared_file.aeve" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label for="uploadFile" class="form_label">File ( Only PNG, JPG, Word, Pdf or Excel )</label><span class="required"> *</span>
                                    <input type="file" name="files[]" id="uploadFile" class="fileinput-button btn btn-default">
                                </div>
                                <div class="col-xs-3">
                                    <label class="form_label">&nbsp;</label>
                                    <div>
                                        <button type="button" class="btn btn-default btn-sm" id="btn_upload_file">Upload File</button>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-3">
                                    <label class="form_label">&nbsp;</label>
                                    <div id="upload_file_progress">
                                        <div id="bar_upload_file_progress" class="bar" style="width: 0%;"></div>
                                    </div>
                                </div>
                                <div class="col-xs-3">
                                    <label class="form_label">&nbsp;</label>
                                    <div id="progress_status"></div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_file" >
                        <thead>
                        <tr role="row">
                            <th class="sorting" role="columnheader">Name</th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_file_rows">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-xs-8">
                    <form method="post" id="frm_file_group_details">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-xs-12">
                                    <label for="uploadFileGroupName" class="form_label">Title</label><span class="required"> *</span>
                                    <input type="text" name="uploadFileGroupName" id="uploadFileGroupName"  class="form-control">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-2">
                                    &nbsp;
                                </div>
                            </div>
                            <%if(showClientDropDown){
                            %>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label for="uploadFileViewer" class="form_label">Client</label><span class="required"> *</span>
                                        <select id="uploadFileViewer" name="uploadFileViewer"  data-placeholder="Choose Clients..." class="form-control chosen-select" multiple >
                                            <option value=""></option>
                                            <%
                                                if(hmClientBean!=null && !hmClientBean.isEmpty()) {
                                                    for(Map.Entry<Integer,ClientBean> mapClientBeans : hmClientBean.entrySet()){
                                                        ClientBean clientBean = mapClientBeans.getValue();
                                            %>
                                                        <option value="<%=clientBean.getClientId()%>" id="<%=clientBean.getClientId()%>"><%=clientBean.getClientName()%></option>
                                            <%
                                                    }
                                                }
                                            %>
                                        </select>
                                    </div>
                                </div>
                            <%
                            } else{
                            %>
                                <input type="hidden" name="uploadFileViewer" value="<%=sClientId%>" />
                            <%
                            }
                            %>

                            <div class="row">
                                <div class="col-xs-2">
                                    &nbsp;
                                </div>
                            </div>

                            <!--
                            <div class="row">
                                <div class="col-xs-2">
                                    &nbsp;
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12">
                                    <label for="uploadFileGroupComment" class="form_label"><%=sName%></label>
                                    <textarea type="text" name="uploadFileGroupComment" id="uploadFileGroupComment"  class="form-control" placeholder="Add a Comment"></textarea>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-2">
                                    &nbsp;
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="boxedcontent">
                                        <div class="widget">
                                            <div class="content">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <h6 style="margin-bottom: 3px;font-weight: bold;">Kensen John</h6>
                                                    </div>
                                                    <div class="col-xs-12">
                                                        Scam devil meets everyone
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-xs-2">
                                            &nbsp;
                                        </div>
                                    </div>
                                    <div class="boxedcontent">
                                        <div class="widget">
                                            <div class="content">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <h6 style="margin-bottom: 3px;font-weight: bold;">Nisha Mathews</h6>
                                                    </div>
                                                    <div class="col-xs-12">
                                                        <h6 style="margin-bottom: 0px">Scam devil meets everyone</h6>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div> -->
                        </div>
                        <input type="hidden" name="file_group_id" id="file_group_id"  value="<%=sFileGroupId%>"/>
                        <input type="hidden" name="button_clicked" value="true"/>
                    </form>
                    <div class="row">
                        <div class="col-xs-2">
                            <button type="button" class="btn btn-filled" id="btn_save_uploaded_files">Save</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    &nbsp;
                </div>
            </div>
        </div>
    </div>
</div>
<form id="frm_save_file">
    <input type="hidden" name="file_group_id" id="save_file_group_id" value="<%=sFileGroupId%>"/>
    <input type="hidden" name="upload_id" id="upload_id" value=""/>
    <input type="hidden" name="button_clicked" value="false"/>
</form>
<form id="frm_load_file">
    <input type="hidden" name="file_group_id" id="load_file_group_id" value="<%=sFileGroupId%>"/>
</form>
<form id="frm_delete_file">
    <input type="hidden" name="upload_id" id="delete_upload_id" value=""/>
</form>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/chosen.jquery.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var varFileGroupId = '<%=sFileGroupId%>';
    var varIsLoggedInUserAClient = <%=isLoggedInUserAClient%>;
    $(window).load(function() {
        $("#uploadFileViewer").chosen();
        initializeUploadFileTable();

        if(varFileGroupId!=''){
            loadFile( getResult );
        }

        $('#btn_save_uploaded_files').bind("click", function() {
            saveFile(getResult,$("#frm_file_group_details").serialize()  );
        });
    });

    $(function () {
        $('#frm_upload_file').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                $('#btn_upload_file').unbind('click');
                $('#progress_status').text('');
                data.context = $('#btn_upload_file').click(function () {
                    $('#progress_status').text('Processing ...');
                    data.submit();
                });
            },
            done: function (e, data) {
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];

                    if(varDataResult.success) {


                        var varOriginalFileName = varDataResult.original_name;
                        var varSharedFileId = varDataResult.original_name;
                        var varFileName = varDataResult.name;
                        var varUploadId = varDataResult.upload_id;

                        var varSharedFileHost = varDataResult.shared_file_host;
                        var varBucket = varDataResult.bucket;
                        var varPath = varDataResult.foldername;

                        createFileTableRow(varOriginalFileName , varUploadId, varFileName  , varPath  , varSharedFileHost , varBucket );


                        $('#upload_id').val(varUploadId);
                        $('#progress_status').text('Uploading Your File...');
                        saveFile( getResult, $("#frm_save_file").serialize() );
                    } else {

                        var varErrMessage = varDataResult.err_mssg;
                        if(varErrMessage==''){
                            varErrMessage = "Oops!! We were unable to upload the banner. Please try again later.";
                        }
                        displayMssgBoxAlert(varErrMessage, true);
                    }
                    $('#upload_file_progress .bar').css(
                            'width','0%'
                    );
                    $('#uploadFile').val("");
                    $('#btn_upload_file').unbind('click');


                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#upload_file_progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });

    function createFileTableRow(varOriginalFileName  , varUploadId,varFileName , varPath , varSharedFileHost , varBucket){
        var oTable = objEveryUploadFileTable;
        var ai = oTable.fnAddData( [varOriginalFileName , createButtons(varUploadId,varPath , varSharedFileHost , varBucket, varFileName ) ] );
        var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
        $(nRow).attr('id','row_'+varUploadId);



        addSharedFileDeleteClickEvent(varUploadId);
    }
    function addSharedFileDeleteClickEvent(varUploadId) {
        var shared_files_deletion_obj = {
            upload_id: varUploadId,
            printObj: function () {
                return 'upload_id : ' + this.upload_id;
            }
        }

        $('#del_'+varUploadId).click({param_files_deletion_obj:shared_files_deletion_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this file?" ,
                    "Delete File",
                    "Yes", "No", deleteSharedFile,e.data.param_files_deletion_obj);
        });
    }



    function deleteSharedFile( varSharedFileDeletionObj ){
        $('#delete_upload_id').val(varSharedFileDeletionObj.upload_id);

        var actionUrl = "/proc_delete_shared_file.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_file").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processSharedFileDeletion);
    }
    function processSharedFileDeletion (jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    var varIsInvoiceDeleted = jsonResponseObj.is_deleted;

                    if(varIsInvoiceDeleted){
                        var varUploadId = jsonResponseObj.deleted_upload_id;
                        $('#deleted_upload_id').val('');

                        var oTable = objEveryUploadFileTable;
                        if(oTable!='' && oTable!=undefined) {
                            oTable.fnDeleteRow((oTable.$('#row_'+varUploadId))[0] );
                        }

                    } else {
                        displayMssgBoxAlert("The file was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (processSharedFileDeletion - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (processSharedFileDeletion - 2)", true);
        }
    }


    function saveFile( callbackmethod, varDataString  ) {
        var actionUrl = "/proc_save_shared_file.aeve";
        var methodType = "POST";
        var dataString =  varDataString;
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadFile( callbackmethod, varDataString) {
        var actionUrl = "/proc_load_shared_file.aeve";
        var methodType = "POST";
        var dataString =  $("#frm_load_file").serialize();
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
                        var varFileGroupId = jsonResponseObj.file_group_id;
                        $('#file_group_id').val( varFileGroupId );
                        $('#save_file_group_id').val( varFileGroupId );
                        $('#load_file_group_id').val( varFileGroupId );

                        var varSharedFileGroup = jsonResponseObj.shared_files_group;
                        if(varSharedFileGroup!=undefined) {
                            if( varSharedFileGroup.files_group_name!=undefined && varSharedFileGroup.files_group_name!='' ){
                                $('#uploadFileGroupName').val( varSharedFileGroup.files_group_name );
                            }
                        }
                        var varNumOfFiles = jsonResponseObj.num_of_files;
                        var varFilesBean = jsonResponseObj.files_bean;

                        var varSharedFileHost = jsonResponseObj.shared_file_host;
                        var varBucket = jsonResponseObj.bucket;

                        for(var varCount = 0; varCount<varNumOfFiles; varCount++ ){
                            var varShareFile = varFilesBean[varCount+'_shared_files'];
                            var varUploadedFile = varFilesBean[varCount+'_uploaded_files'];
                            createFileTableRow(varUploadedFile.original_filename , varUploadedFile.upload_id , varUploadedFile.filename  , varUploadedFile.path  , varSharedFileHost , varBucket );
                        }

                        var varNumOfFilesViewers = jsonResponseObj.num_of_files_viewers;
                        var varFilesViewersBean =  jsonResponseObj.file_viewers_bean
                        for(var varViewerCount = 0; varViewerCount<varNumOfFilesViewers; varViewerCount++ ){
                            var varViewer = varFilesViewersBean[varViewerCount];

                            $('#'+varViewer.parent_id).attr('selected','selected');
                        }

                        $('#uploadFileViewer').trigger("chosen:updated");

                        if( jsonResponseObj.show_alert ) {
                            displayAjaxOk(varResponseObj);
                            $('#progress_status').text('Success!!');
                        }

                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function createButtons( varId,varPath , varSharedFileHost , varBucket ,varFileName  ){
        var varButtons = '';
        varButtons = varButtons + createDownloadButton( varId,varPath , varSharedFileHost , varBucket , varFileName  );
        //if(!varIsLoggedInUserAClient){
            varButtons = varButtons + '&nbsp;&nbsp;&nbsp;';
            varButtons = varButtons + createDeleteButton( varId );
        //}
        return varButtons;
    }
    function createDownloadButton(varId,varPathName, varSharedFileHost , varBucket, varFileName ){
        var varDownloadLink =  varSharedFileHost +'/'+varBucket+'/'+varPathName  +'/'+varFileName;
        return '<a id="download_'+varId+'" class="btn btn-default btn-xs" href=\"'+varDownloadLink+'\" target=\"_blank\"><i class="fa fa-download"></i> Download</a>';
    }
    function createDeleteButton(varId){
        return '<a id="del_'+varId+'" class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</a>';
    }

    function initializeUploadFileTable(){

        objEveryUploadFileTable =  $('#every_file').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,
            "aoColumns":  [
                {"bSortable": true,"sClass":"col-xs-5"},
                {"bSortable": false,"sClass":"center"}
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>

