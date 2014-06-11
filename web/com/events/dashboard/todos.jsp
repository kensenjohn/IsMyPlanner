<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Perm" %>
<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.users.ParentTypeBean" %>
<%@ page import="com.events.users.AccessUsers" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<link rel="stylesheet" href="/css/datepicker/default.time.css" id="theme_time">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link rel="stylesheet" href="/css/chosen.css">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String currentUserId = Constants.EMPTY;
    boolean isUserAClient = false;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null) {
            currentUserId = ParseUtil.checkNull(loggedInUserBean.getUserId());
        }

        AccessUsers accessUsers = new AccessUsers();
        ParentTypeBean parentTypeBean = accessUsers.getParentTypeBeanFromUser( loggedInUserBean );
        isUserAClient = parentTypeBean.isUserAClient();
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
            <div class="page-title">Dashboard - Todo</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="todo_active" value="active"/>
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
                    <a href="/com/events/dashboard/todo/edit_todos.jsp" class="btn btn-filled">
                        <span><i class="fa fa-plus"></i> New Todo</span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12"  id="edit_todo_filter" >
                    <h6 style="cursor:pointer;"><i id="edit_todo_filter_icon" class="fa fa-chevron-circle-right"></i><span> Filter Options </span></h6>
                </div>
            </div>
            <div  id="todo_filter" style="display:none;">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="boxedcontent">
                            <div class="widget">
                                <div class="content">
                                    <form  class="form-horizontal" id="frm_todo_filter" >
                                        <div class="row">
                                            <div class="col-xs-2">
                                                <label for="todo_filter_start_date" class="form_label">Start Date:</label>
                                                <input type="text" class="form-control" id="todo_filter_start_date" name="todo_filter_start_date">
                                            </div>
                                            <div class="col-xs-2">
                                                <label for="todo_filter_end_date" class="form_label">End Date:</label>
                                                <input type="text" class="form-control" id="todo_filter_end_date" name="todo_filter_end_date">
                                            </div>
                                            <div class="col-xs-2">
                                                <label for="todo_filter_time_zone" class="form_label">Time Zone:</label>
                                                <select class="form-control" id="todo_filter_time_zone" name="todo_filter_time_zone">
                                                    <%
                                                        for(Constants.TIME_ZONE timeZone : Constants.TIME_ZONE.values()) {
                                                    %>
                                                    <option value="<%=timeZone.toString()%>"><%=timeZone.getTimeZoneDisplay()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                            <div class="col-xs-offset-1 col-xs-2">
                                                <label for="todo_filter_status" class="form_label">To Do Status:</label>
                                                <select id="todo_filter_status" name="todo_filter_status" class="form-control">
                                                    <option value="ALL">All</option>
                                                    <option value="<%=Constants.TODO_STATUS.ACTIVE.toString()%>" selected>Active</option>
                                                    <option value="<%=Constants.TODO_STATUS.COMPLETE.toString()%>">Complete</option>
                                                </select>
                                            </div>
                                            <%
                                                if(!isUserAClient) {
                                            %>

                                                        <div class="col-xs-3">
                                                            <label for="todo_filter_users" class="form_label">Users:</label>
                                                            <select id="todo_filter_users" name="todo_filter_users" data-placeholder="Select other users involved" class="form-control chosen-select" multiple >
                                                                <option value=""></option>
                                                            </select>
                                                        </div>
                                            <%
                                                }
                                            %>

                                        </div>
                                        <div class="row">
                                            <div class="col-xs-3">
                                                &nbsp;
                                            </div>
                                        </div>
                                        <input type="hidden" id="search_type" name="search_type" value="filter"/>
                                    </form>
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <button type="button" class="btn btn-default btn-xs" id="btn_todo_filter"> <i class="fa fa-search"></i> Search</button>
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
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_todo" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-xs-1" role="columnheader"></th>
                            <th class="sorting col-md-2" role="columnheader">ToDo</th>
                            <th class="sorting col-md-2" role="columnheader">Date</th>
                            <th class="sorting col-md-2" role="columnheader">Event</th>
                            <th class="center col-md-2" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_todo_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_update_todo_status">
    <input type="hidden" name="todo_id" id="updatestatus_todo_id" value=""/>
    <input type="hidden" name="todo_status" id="updatestatus_todo_status" value=""/>
</form>
<form id="frm_delete_todo">
    <input type="hidden" name="todo_id" id="delete_todo_id" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/chosen.jquery.min.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/datepicker/picker.js"></script>
<script src="/js/datepicker/picker.date.js"></script>
<script src="/js/datepicker/picker.time.js"></script>
<script src="/js/datepicker/legacy.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script   type="text/javascript">
    var varLoadTodoFilterDate = false;
    var varCurrentUserId = '<%=currentUserId%>';
    var varIsUserAClient = <%=isUserAClient%>;
    $(window).load(function() {
        //loadInvoices(populateInvoiceList);
        initializeTableToDo();

        loadingOverlay('show');
        loadToDo( getResult );

        $('#edit_todo_filter').click(function(){
            $( "#todo_filter" ).slideToggle( "slow", function() {
                if($('#todo_filter').css('display') == 'block') {
                    $('#edit_todo_filter_icon').removeClass("fa-chevron-circle-right").addClass("fa-chevron-circle-down");

                    if(!varLoadTodoFilterDate){
                        varLoadTodoFilterDate = true;
                        if(!varIsUserAClient) {
                            $("#todo_filter_users").chosen();
                            loadingOverlay('show');
                            loadToDoFilterParams(populateToDoFilterParams, '' );
                        }
                    }

                    $('#btn_todo_filter').bind('click',function(){
                        loadingOverlay('show');
                        var varFilterParams = $('#frm_todo_filter').serialize();
                        loadToDo(getResult,varFilterParams);
                    });
                }
                if($('#todo_filter').css('display') == 'none') {
                    $('#edit_todo_filter_icon').removeClass("fa-chevron-circle-down").addClass("fa-chevron-circle-right");
                    $('#btn_todo_filter').unbind('click');
                }
            });
        });

        $('#todo_filter_start_date').pickadate();
        $('#todo_filter_end_date').pickadate();
    });
    function loadToDo(callbackmethod , varDataString) {
        var oTable = objFileToDo;
        if(oTable!='' && oTable!=undefined){
            oTable.fnClearTable();
        }
        var actionUrl = "/proc_load_todo_summary.aeve";
        var methodType = "POST";
        var dataString =  varDataString;
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadToDoFilterParams(callbackmethod) {
        var actionUrl = "/proc_load_todo_filter_params.aeve";
        var methodType = "POST";
        var dataString =  $("#frm_load_file_group").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateToDoFilterParams( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {

                        var varNumOfVendorUsers = jsonResponseObj.num_of_vendor_userbean;
                        if(varNumOfVendorUsers>0){
                            $('#todo_filter_users').append("<optgroup label='Team Members' id='todo_teammember'></optgroup>");
                            var varAllVendorUsersBean = jsonResponseObj.vendor_users;
                            for( var varVendorUserCount = 0; varVendorUserCount<varNumOfVendorUsers; varVendorUserCount++){
                                var varVendorUsersBean =  varAllVendorUsersBean[varVendorUserCount];
                                var varVendorUserInfoBean = varVendorUsersBean.user_info_bean;
                                $('#todo_teammember').append('<option id="'+varVendorUsersBean.user_id+'" value="'+varVendorUsersBean.user_id+'">'+varVendorUserInfoBean.first_name + ' ' + varVendorUserInfoBean.last_name+'</option>');
                            }
                        }

                        var varNumOfVendorClientUsers = jsonResponseObj.num_of_vendor_client_userbean;
                        if(varNumOfVendorClientUsers>0){
                            $('#todo_filter_users').append("<optgroup label='Clients' id='todo_clients'></optgroup>");
                            var varAllVendorClientUsersBean = jsonResponseObj.vendor_client_users;
                            for( var varVendorClientUserCount = 0; varVendorClientUserCount<varNumOfVendorClientUsers; varVendorClientUserCount++){
                                var varVendorClientUsersBean =  varAllVendorClientUsersBean[varVendorClientUserCount];
                                var varVendorClientUserInfoBean = varVendorClientUsersBean.user_info_bean;
                                $('#todo_clients').append('<option  id="'+varVendorClientUsersBean.user_id+'" value="'+varVendorClientUsersBean.user_id+'">'+varVendorClientUserInfoBean.first_name + ' ' + varVendorClientUserInfoBean.last_name+'</option>');
                            }
                        }

                        $('#todo_filter_users').trigger("chosen:updated");
                    }
                }
            }
        }
        loadingOverlay('hide');
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
                        var varNumOfTodos = jsonResponseObj.num_of_todos;
                        if(varNumOfTodos>0) {
                            var varAllTodos = jsonResponseObj.all_todos;
                            var varMapNumOfUsers = jsonResponseObj.todo_num_of_assigned_users;
                            var varMapEvents = jsonResponseObj.todo_event_bean;

                            this.todoListModel = new TodoListModel({

                                'bb_num_of_todos' : varNumOfTodos,
                                'bb_all_todos' : varAllTodos,
                                'bb_map_todo_num_of_assigned_users': varMapNumOfUsers,
                                'bb_map_todo_event_bean': varMapEvents
                            });
                            var todoListView = new TodoListView({model:this.todoListModel});
                            todoListView.render();
                        }
                    }
                }
            }
        }
        loadingOverlay('hide');
    }
    var TodoListModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_todos: 0 ,
            bb_all_todos: undefined,
            bb_map_todo_num_of_assigned_users: undefined,
            bb_map_todo_event_bean: undefined
        }
    });
    var TodoListView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfTodos = this.model.get('bb_num_of_todos');
            this.varAllTodosBean = this.model.get('bb_all_todos');
            this.varMapNumOfUsers = this.model.get('bb_map_todo_num_of_assigned_users');
            this.varMapEvents = this.model.get('bb_map_todo_event_bean');
        },
        render:function(){
            if(this.varNumOfTodos>0){
                var oTable = objFileToDo;
                if(oTable!='' && oTable!=undefined){
                    oTable.fnClearTable();
                    for (var i = 0;i < this.varNumOfTodos;i++) {
                        var varTodos = this.varAllTodosBean[i];
                        var varToDoId = varTodos.todo_id;
                        var varTodo = varTodos.todo;
                        var varTodoDate = varTodos.todo_date;
                        var varTimeZoneDateFormatted = varTodos.todo_timezone_formatted;

                        var varNumOfUsers = 0;
                        if( this.varMapNumOfUsers!=undefined ){
                            varNumOfUsers = this.varMapNumOfUsers[varToDoId];
                        }

                        var varEventBean = '';
                        if(this.varMapEvents!=undefined){
                            varEventBean = this.varMapEvents[varToDoId];
                        }
                        var varEventName = ' ';
                        if(varEventBean!=undefined && varEventBean!='' ){
                            varEventName = varEventBean.event_name;
                        }

                        var ai = oTable.fnAddData( [createUpdateTodoStatusCheckbox(varToDoId, varTodos.todo_status ), varTodo,varTodoDate + ' ' + varTimeZoneDateFormatted, varEventName , createButtons(varToDoId) ] );
                        var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
                        $(nRow).attr('id','row_'+varToDoId);


                        addTodoDeleteClickEvent(varToDoId);
                        addTodoUpdateStatusClickEvent(varToDoId,i);
                    }
                }
            }
        }
    });
    function addTodoDeleteClickEvent(varToDoId,varRowNum) {
        var todo_delete_obj = {
            todo_id: varToDoId,
            row_num: varRowNum,
            printObj: function () {
                return this.todo_id + ' row : ' + this.row_num;
            }
        }
        $('#del_'+varToDoId).click({param_todo_delete_obj:todo_delete_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this item?" ,
                    "Delete To Do",
                    "Yes", "No", deleteTodo,e.data.param_todo_delete_obj);
        });
    }
    function addTodoUpdateStatusClickEvent(varToDoId,varRowNum) {
        var todo_update_status_obj = {
            todo_id: varToDoId,
            row_num: varRowNum,
            printObj: function () {
                return this.todo_id + ' row : ' + this.row_num;
            }
        }
        $('#update_'+varToDoId).click({param_todo_update_status_obj:todo_update_status_obj},function(e){
            updateToDoStatus(  e.data.param_todo_update_status_obj  );
        });
    }

    function deleteTodo( varTodoObj ){
        $('#delete_todo_id').val(varTodoObj.todo_id);

        var actionUrl = "/proc_delete_todo.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_todo").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processTodoDeletion);
    }
    function updateToDoStatus(  varToDoUpdateStatusObj ){
        $('#updatestatus_todo_id').val(varToDoUpdateStatusObj.todo_id);
        $('#updatestatus_todo_status').val(  $('#update_'+varToDoUpdateStatusObj.todo_id).prop('checked')  );

        var actionUrl = "/proc_update_todo_status.aeve";
        var methodType = "POST";
        var dataString = $("#frm_update_todo_status").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processTodoStatus);
    }

    function processTodoDeletion (jsonResult) {
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
                        var varTodoId = jsonResponseObj.deleted_todo_id;
                        $('#delete_todo_id').val('');

                        var oTable = objFileToDo;
                        if(oTable!='' && oTable!=undefined) {
                            oTable.fnDeleteRow((oTable.$('#row_'+varTodoId))[0] );
                        }

                    } else {
                        displayMssgBoxAlert("The registry was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteRegistry - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteRegistry - 2)", true);
        }
    }

    function processTodoStatus( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        // var varNumOfTodos = jsonResponseObj.num_of_todos;

                    }
                }
            }
        }
        loadingOverlay('hide');
    }

    function createUpdateTodoStatusCheckbox(varId , varTodoStatus){
        var varSelected = '';
        if( varTodoStatus == 'COMPLETE' ){
            varSelected = "checked"
        }

        var varCheckBox = '<input type="checkbox" id="update_'+varId+'" name="todo_status" '+varSelected+'/>';
        return varCheckBox ;

    }
    function createButtons( varId ){
        var varButtons = '';
        varButtons = varButtons + createEditButton( varId );
        varButtons = varButtons + '&nbsp;&nbsp;&nbsp;';
        varButtons = varButtons + createDeleteButton( varId );
        return varButtons;
    }
    function createEditButton(varId){
        var varButtonLink = '<a id="edit_'+varId+'" class="btn btn-default btn-xs" href="/com/events/dashboard/todo/edit_todos.jsp?todos_id='+varId+'"><i class="fa fa-pencil"></i> Edit</a>';

        return varButtonLink ;
    }
    function createDeleteButton(varId){
        return '<a id="del_'+varId+'" class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</a>';
    }

    function initializeTableToDo(){

        objFileToDo=  $('#every_todo').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                { "bSortable": false,"sClass":"right" },
                null,
                null,
                null,
                { "bSortable": false,"sClass":"center" }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>