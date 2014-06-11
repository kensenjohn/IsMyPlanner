<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
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
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <a href="/com/events/common/conversation/edit_conversations.jsp" class="btn btn-filled">
                        <span><i class="fa fa-weixin"></i> Start a Conversation</span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_conversation" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-xs-2" role="columnheader">Conversation Name</th>
                            <th class="sorting col-xs-2" role="columnheader">Last Message Time</th>
                            <th class="sorting col-xs-2" role="columnheader">Users</th>
                            <th class="center col-md-2" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_every_conversation_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        initializeTableForConversations();
        loadAllUserConversation(populateAllConversation);
    });
    function loadAllUserConversation( callbackmethod ) {
        var actionUrl = "/proc_load_all_user_conversation.aeve";
        var methodType = "POST";
        var dataString = $("#frm_conversation").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateAllConversation(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        // console.log('forever console.');
                        var varNumOfConversation = jsonResponseObj.num_of_conversations;
                        var varCurrentUserId = jsonResponseObj.current_user_id;
                        if(varNumOfConversation>0){
                            var varAllConversation = jsonResponseObj.all_conversations;
                            var varAllConversationUsers = jsonResponseObj.conversation_users
                            for(var varCount =0 ; varCount<varNumOfConversation; varCount++ ){
                                var varConversationBean = varAllConversation[ varCount ];

                                var varConversationId = varConversationBean.conversation_id;

                                var varConversationUserName = getConversationUserList( varAllConversationUsers , varConversationId, varCurrentUserId);

                                createConversationTableRow(varConversationBean , varConversationUserName);
                            }
                        }
                    }
                }
            }
        }
    }
    function isNewMessage(){

    }
    function getConversationUserList(varAllConversationUsers , varConversationId, varCurrentUserId ){
        var varNumOfConversationUser = varAllConversationUsers['users_per_conversation_'+varConversationId];
        var varConversationUsers = varAllConversationUsers[varConversationId];

        var varConversationUserName = '';
        var varIsUnRead = false
        if(varNumOfConversationUser>0){
            var isFirstName = true;
            for(var varTrackUserName=0; varTrackUserName<varNumOfConversationUser; varTrackUserName++ ){
                if(!isFirstName){
                    varConversationUserName = varConversationUserName + '; ';
                }
                varConversationUserName = varConversationUserName + varConversationUsers[varTrackUserName];
                isFirstName = false;
            }
        }
        return varConversationUserName;
    }

    function createConversationTableRow( varConversationBean , varConversationUserName ){
        var oTable = objConversationTable;
        var varConversationId = varConversationBean.conversation_id;
        var varConversationName = varConversationBean.name ;
        var varConversationTime = varConversationBean.formatted_human_modified_date;

        if(varConversationBean.has_read_message == false){
            varConversationName = varConversationName + '&nbsp;&nbsp;<span id="rsvp_status"  class="label label-warning">New</span>' ;
        }

        var varColumnData = [varConversationName,varConversationTime,varConversationUserName, createViewButton(varConversationId) ];

        var ai = oTable.fnAddData( varColumnData );
        var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
        $(nRow).attr('id','row_'+varConversationId);
    }

    function createViewButton( varConversationId ){
        var varViewLink = '<a id="view_'+varConversationId+'" class="btn btn-default btn-xs" href="/com/events/common/conversation/edit_conversations.jsp?conversation_id='+varConversationId+'"><i class="fa fa-search"></i> View</a>';
        return varViewLink;
    }

    function initializeTableForConversations(){

        objConversationTable =  $('#every_conversation').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                null,
                { "bSortable": false,"sClass":"center" }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
