<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.users.AccessUsers" %>
<%@ page import="com.events.bean.users.ParentTypeBean" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Perm" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/chosen.css">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sConversationId = ParseUtil.checkNull( request.getParameter("conversation_id"));
    boolean canAddUsersToConversation = false;
    boolean canDeleteUsersToConversation = false;
    boolean isLoadConversation = false;

    String currentUserId = Constants.EMPTY;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null) {
            currentUserId = ParseUtil.checkNull( loggedInUserBean.getUserId() );

            AccessUsers accessUsers = new AccessUsers();
            ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser( loggedInUserBean );
        }
        CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
        if(checkPermission!=null ) {
            if( checkPermission.can(Perm.ADD_USERS_TO_OLD_CONVERSATION) ) {
                canAddUsersToConversation = true;
            }
        }
    }

    if(  !Utility.isNullOrEmpty(sConversationId) ) {
        isLoadConversation = true;
        canAddUsersToConversation = true;
        canDeleteUsersToConversation = true;
    }

%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="dashboard_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Conversations</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="conversations_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-10">
                    <form class="form-horizontal" id="frm_conversation">
                        <div class="row">
                            <div class="col-md-10">
                                <label for="conversation_name" class="form_label">Name:</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="conversation_name" name="conversation_name"/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-10">
                                <label for="conversation_users" class="form_label">Users:</label>
                                <select id="conversation_users" name="conversation_users" data-placeholder="Select a User" class="form-control chosen-select" multiple >
                                    <option value=""></option>
                                </select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-9">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-10" id="conversation_list">

                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-9">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-10">
                                <textarea id="conversation_body" name="conversation_body"></textarea>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-9">
                                &nbsp;
                            </div>
                        </div>
                        <input type="hidden" name="conversation_id" id="conversation_id" value="<%=sConversationId%>"/>
                        <input type="hidden" name="current_conversation_user" id="current_conversation_user" value="<%=currentUserId%>" />
                    </form>
                    <form method="post" id="frm_upload_file"  action="/proc_upload_shared_file.aeve" method="POST" enctype="multipart/form-data">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-xs-4">
                                    <label for="uploadFile" class="form_label">Attach Files ( Only PNG, JPG, Word, Pdf or Excel )</label><span class="required"> *</span>
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
                        <div class="col-md-9">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <button class="btn btn-default" id="btn_save_conversation">Send</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script id="template_conversation_row" type="text/x-handlebars-template">
    <div class="boxedcontent">
        <div class="widget">
            <div class="content">
                <div class="row">
                    <div class="col-md-12">
                        <h5>{{conversation_name}}</h5>
                        <h6>{{conversation_date_time}}</h6>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        {{{conversation_text}}}
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        &nbsp;
                    </div>
                </div>
                <div class="row" id="attachment_row_{{message_id}}" style="display:none;">
                    <div class="col-md-12">
                        <span style="font-weight:bold;">Attached File/s</span>
                    </div>
                    <div class="col-md-12" id="attachment_list_{{message_id}}">

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
</script>
<script id="template_attachment_link" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-12">
            <a href="{{attachment_location}}">{{attachment_name}}</a>
        </div>
    </div>
</script>
<form id="frm_delete_file">
    <input type="hidden" name="upload_id" id="delete_upload_id" value=""/>
</form>
<form id="frm_update_conversation_user">
    <input type="hidden" name="conversation_id" id="update_conversation_user_id" value="<%=sConversationId%>"/>
    <input type="hidden" name="user_id" id="conversation_user_id" value=""/>
    <input type="hidden" name="action" id="conversation_action" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/chosen.jquery.min.js"></script>
<script src="/js/tinymce/tinymce.min.js"></script>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script   type="text/javascript">
    var varCurrentUserId = '<%=currentUserId%>';
    var varIsLoadConversation = <%=isLoadConversation%>;
    var varConversationId = '<%=sConversationId%>';
    tinymce.init({
        selector: "#conversation_body",
        theme: "modern",
        plugins: [
            "advlist autolink link lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "save table contextmenu directionality emoticons template paste textcolor uploadimage"
        ],
        toolbar1: "preview | insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link uploadimage"
    });

    $(window).load(function() {
        initializeUploadFileTable();
        $("#conversation_users").chosen();
        loadConversation(populateConversation);

        $('#btn_save_conversation').click(function(){
            tinyMCE.triggerSave();
            saveConversation( getResult);
        })
    });
    function saveConversation( callbackmethod ) {
        var actionUrl = "/proc_save_conversation.aeve";
        var methodType = "POST";
        var dataString = $("#frm_conversation").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadConversation( callbackmethod ) {
        var actionUrl = "/proc_load_conversation.aeve";
        var methodType = "POST";
        var dataString = $("#frm_conversation").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function updateConversationUser( callbackmethod ) {
        var actionUrl = "/proc_update_conversation_users.aeve";
        var methodType = "POST";
        var dataString = $("#frm_update_conversation_user").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateConversation(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varConversationBean = jsonResponseObj.conversation_bean;
                        $('#conversation_name').val(varConversationBean.name);
                        setConversationId(varConversationBean.conversation_id);

                        var varNumOfVendorUsers = jsonResponseObj.num_of_vendor_userbean;
                        if(varNumOfVendorUsers>0){
                            $('#conversation_users').append("<optgroup label='Team Members' id='conversation_teammember'></optgroup>");
                            var varAllVendorUsersBean = jsonResponseObj.vendor_users;
                            for( var varVendorUserCount = 0; varVendorUserCount<varNumOfVendorUsers; varVendorUserCount++){
                                var varVendorUsersBean =  varAllVendorUsersBean[varVendorUserCount];
                                var varVendorUserInfoBean = varVendorUsersBean.user_info_bean;
                                if(varCurrentUserId!=varVendorUsersBean.user_id)  {
                                    $('#conversation_teammember').append('<option id="'+varVendorUsersBean.user_id+'" value="'+varVendorUsersBean.user_id+'">'+varVendorUserInfoBean.first_name + ' ' + varVendorUserInfoBean.last_name+'</option>');
                                }
                            }
                        }

                        var varNumOfVendorClientUsers = jsonResponseObj.num_of_vendor_client_userbean;
                        if(varNumOfVendorClientUsers>0){
                            $('#conversation_users').append("<optgroup label='Clients' id='conversation_clients'></optgroup>");
                            var varAllVendorClientUsersBean = jsonResponseObj.vendor_client_users;
                            for( var varVendorClientUserCount = 0; varVendorClientUserCount<varNumOfVendorClientUsers; varVendorClientUserCount++){
                                var varVendorClientUsersBean =  varAllVendorClientUsersBean[varVendorClientUserCount];
                                var varVendorClientUserInfoBean = varVendorClientUsersBean.user_info_bean;
                                $('#conversation_clients').append('<option  id="'+varVendorClientUsersBean.user_id+'" value="'+varVendorClientUsersBean.user_id+'">'+varVendorClientUserInfoBean.first_name + ' ' + varVendorClientUserInfoBean.last_name+'</option>');
                            }
                        }

                        var varNumOfConversationUsers = jsonResponseObj.num_of_conversation_users;
                        if(varNumOfConversationUsers>0){
                            var varAllConversationUsersBean = jsonResponseObj.conversation_users_bean;
                            for( var varConversationUserCount = 0; varConversationUserCount<varNumOfConversationUsers; varConversationUserCount++){
                                var varConversationUsersBean =  varAllConversationUsersBean[varConversationUserCount];
                                $('#'+varConversationUsersBean.user_id).attr('selected','selected');
                            }
                        }
                        $('#conversation_users').trigger("chosen:updated");
                        updateChosenUsers(varConversationBean.conversation_id);


                        var varNumOfConversationMessages = jsonResponseObj.num_of_conversation_messages;
                        if(varNumOfConversationUsers>0){
                            var varAllConversationMessages = jsonResponseObj.conversation_messages_bean;
                            var varAllConversationMessageUsers = jsonResponseObj.conversation_messages_users;
                            var varAllMessageAttachment = jsonResponseObj.conversation_messages_attachments;
                            var varS3Bucket = jsonResponseObj.s3_bucket;
                            var varSharedFileHost = jsonResponseObj.shared_file_host;
                            for( var varMessageCount = 0; varMessageCount<varNumOfConversationMessages; varMessageCount++){

                                var varConversationMessageBean =  varAllConversationMessages[varMessageCount];
                                var varMessage = varConversationMessageBean.message;
                                var varConversationMessageId = varConversationMessageBean.conversation_message_id;
                                var varConversationMessageUser =  varAllConversationMessageUsers[varConversationMessageId];

                                this.conversationModel = new ConversationModel({
                                    'bb_conversation_text' : varConversationMessageBean.message,
                                    'bb_conversation_name' : varConversationMessageUser.user_given_name,
                                    'bb_conversation_date_time' : varConversationMessageUser.formatted_human_create_date,
                                    'bb_message_id' : varConversationMessageId
                                });
                                var conversationView = new ConversationView({model:this.conversationModel});
                                conversationView.render();
                                $("#conversation_list").append(conversationView.el);

                                if(varAllMessageAttachment!=undefined){
                                    var varNumOfAttachments = varAllMessageAttachment[ 'num_of_attachments_'+varConversationMessageId ];
                                    if(varNumOfAttachments!=undefined && varNumOfAttachments>0 ){
                                        var varMessageAttachments = varAllMessageAttachment[varConversationMessageId];

                                        this.attachmentLinkModel = new AttachmentLinkModel({

                                            'bb_conversation_message_attachments' : varMessageAttachments,
                                            'bb_num_of_attachments' : varNumOfAttachments,
                                            'bb_s3_bucket' : varS3Bucket,
                                            'bb_shared_file_host' : varSharedFileHost
                                        });
                                        var attachmentLinkView = new AttachmentLinkView({model:this.attachmentLinkModel});
                                        attachmentLinkView.render();
                                        $("#attachment_list_"+varConversationMessageId).append(attachmentLinkView.el);
                                        $("#attachment_row_"+varConversationMessageId).show();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    function updateChosenUsers( varConversationId ) {
        $('#conversation_users').on('change', function(evt, params) {
            var varSelectedUserId = params.selected;
            var varDeSelectedUserId = params.deselected;

            var varAction = '';
            var varUserId = '';
            if(varSelectedUserId!=undefined && varSelectedUserId!='' ) {
                varAction = 'selected';
                varUserId = varSelectedUserId;
            } else if(varDeSelectedUserId!=undefined && varDeSelectedUserId!='' ) {
                varAction = 'deselected';
                varUserId = varDeSelectedUserId;
            }

            $('#conversation_user_id').val( varUserId  );
            $('#conversation_action').val( varAction  );
            $('#update_conversation_user_id').val( $('#conversation_id').val() );

            updateConversationUser( getConversationUserResult);
        });
    }

    function setConversationId( varConversationId){
        $('#conversation_id').val( varConversationId );
    }

    function getConversationUserResult( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varIsSuccess = jsonResponseObj.is_success;
                    var varUserId = jsonResponseObj.user_id;
                    var varAction = jsonResponseObj.action;

                    if(varIsSuccess){

                    } else {                    $
                        if(varAction == 'selected'){
                            $('#'+varUserId).prop('selected', false);
                        } else if( varAction == 'deselected' ) {
                            $('#'+varUserId).prop('selected', true);
                        }
                        $('#conversation_users').trigger("chosen:updated");
                        displayAjaxOk(varResponseObj);
                    }
                }
            }
        }
        $('#conversation_user_id').val( ''  );
        $('#conversation_action').val( ''  );
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varConversationBean = jsonResponseObj.conversation_bean;
                    setConversationId(varConversationBean.conversation_id);

                    var varConversationMessageBean = jsonResponseObj.conversation_message_bean;
                    var varConversationMessageUserBean = jsonResponseObj.conversation_message_user_bean;
                    var varConversationMessageAttachments = jsonResponseObj.message_attachments;
                    var varNumOfAttachments = jsonResponseObj.num_of_attachments;
                    var varS3Bucket = jsonResponseObj.s3_bucket;
                    var varSharedFileHost = jsonResponseObj.shared_file_host;

                    this.conversationModel = new ConversationModel({
                        'bb_conversation_text' : varConversationMessageBean.message,
                        'bb_conversation_name' : varConversationMessageUserBean.user_given_name,
                        'bb_conversation_date_time' : varConversationMessageUserBean.formatted_human_create_date,
                        'bb_message_id' : varConversationMessageBean.conversation_message_id
                    });
                    var conversationView = new ConversationView({model:this.conversationModel});
                    conversationView.render();
                    $("#conversation_list").append(conversationView.el);


                    if(varNumOfAttachments>0){
                        this.attachmentLinkModel = new AttachmentLinkModel({
                            'bb_message_id' : varConversationMessageBean.conversation_message_id,

                            'bb_conversation_message_attachments' : varConversationMessageAttachments,
                            'bb_num_of_attachments' : varNumOfAttachments,
                            'bb_s3_bucket' : varS3Bucket,
                            'bb_shared_file_host' : varSharedFileHost
                        });
                        var attachmentLinkView = new AttachmentLinkView({model:this.attachmentLinkModel});
                        attachmentLinkView.render();
                        $("#attachment_list_"+varConversationMessageBean.conversation_message_id).append(attachmentLinkView.el);
                        $("#attachment_row_"+varConversationMessageBean.conversation_message_id).show();
                    }
                    var oTable = objEveryUploadFileTable;
                    if(oTable!='' && oTable!=undefined){
                        oTable.fnClearTable();
                    }
                    var tinymce_editor_id = 'conversation_body';
                    tinymce.get(tinymce_editor_id).setContent('');

                    $('.upload_id').remove();
                    $('#update_conversation_user_id').val( varConversationBean.conversation_id );
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    var AttachmentLinkModel = Backbone.Model.extend({
        defaults: {
            bb_conversation_message_attachments : undefined,
            bb_num_of_attachments : 0,
            bb_s3_bucket : '',
            bb_shared_file_host : ''
        }
    });
    var AttachmentLinkView = Backbone.View.extend({
        initialize: function(){
            this.varBBConversationMessageAttachments = this.model.get('bb_conversation_message_attachments');
            this.varBBNumOfAttachments = this.model.get('bb_num_of_attachments');
            this.varBBS3Bucket = this.model.get('bb_s3_bucket');
            this.varBBSharedFileHost = this.model.get('bb_shared_file_host');
        },
        template : Handlebars.compile( $('#template_attachment_link').html() ),
        render : function() {
            if(this.varBBNumOfAttachments>0){
                for(var varCountAttachment = 0; varCountAttachment<this.varBBNumOfAttachments; varCountAttachment++ ) {
                    var varMessageAttachment = this.varBBConversationMessageAttachments[varCountAttachment];

                    var varAttachmentLink = this.varBBSharedFileHost+'/'+ this.varBBS3Bucket +'/'+ varMessageAttachment.path +'/'+ varMessageAttachment.filename;
                    var varTmpAttachment = {
                        "attachment_location"  : varAttachmentLink,
                        "attachment_name" :varMessageAttachment.original_filename
                    }

                    var attachmentRow = this.template(  eval(varTmpAttachment)  );
                    $(this.el).append( attachmentRow );
                }
            }


        }
    });

    var ConversationModel = Backbone.Model.extend({
        defaults: {
            bb_conversation_text: undefined,
            bb_conversation_name: undefined,
            bb_conversation_date_time: undefined,
            bb_message_id : undefined
        }
    });
    var ConversationView = Backbone.View.extend({
        initialize: function(){
            this.varBBConversationText = this.model.get('bb_conversation_text');
            this.varBBConversationName = this.model.get('bb_conversation_name');
            this.varBBConversationDateTime = this.model.get('bb_conversation_date_time');
            this.varBBMessageId = this.model.get('bb_message_id');
        },
        template : Handlebars.compile( $('#template_conversation_row').html() ),
        render : function() {
            var varTmpConversationBean = {
                "conversation_text"  : this.varBBConversationText,
                "conversation_name" :this.varBBConversationName,
                "conversation_date_time" :this.varBBConversationDateTime,
                "message_id" :this.varBBMessageId
            }
            var conversationRow = this.template(  eval(varTmpConversationBean)  );
            $(this.el).append( conversationRow );
        }
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
                        var varUploadedBy = varDataResult.uploaded_by;

                        createFileTableRow(varOriginalFileName , varUploadId, varFileName  , varPath  , varSharedFileHost , varBucket , varUploadedBy );


                        $('#upload_id').val(varUploadId);
                        $('#frm_conversation').append( createHiddenField('upload_id', varUploadId) );
                        $('#progress_status').text('');
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
    function initializeUploadFileTable(){

        objEveryUploadFileTable =  $('#every_file').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,
            "aoColumns":  [
                {"bSortable": true,"sClass":"col-xs-8"},
                {"bSortable": false,"sClass":"center"}
            ]
        });
    }
    function createFileTableRow(varOriginalFileName  , varUploadId,varFileName , varPath , varSharedFileHost , varBucket , varUploadedBy){
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
    function createHiddenField(varName , varValue){
        return '<input type="hidden" class="'+varName+'" name="'+varName+'" value="'+varValue+'">';
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

</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>