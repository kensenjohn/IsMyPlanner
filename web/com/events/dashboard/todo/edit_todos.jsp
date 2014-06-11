<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<link rel="stylesheet" href="/css/datepicker/default.time.css" id="theme_time">
<link rel="stylesheet" href="/css/chosen.css">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sessionId = "NA";
    if(session!=null){
        sessionId = ParseUtil.checkNull(session.getId() );
    }

    String sTodoId = ParseUtil.checkNull(request.getParameter("todos_id"));
    String breadCrumbPageTitle = "Todo";
    boolean loadTodo = false;
    if(!Utility.isNullOrEmpty(sTodoId)) {
        loadTodo = true;
        breadCrumbPageTitle = "Todo - Edit";
    }

    String currentUserId = Constants.EMPTY;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null) {
            currentUserId = ParseUtil.checkNull( loggedInUserBean.getUserId() );
        }
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
            <div class="page-title"><%=breadCrumbPageTitle%></div>
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
                    <div class="col-xs-8">
                        <form class="form-horizontal" id="frm_save_todo">
                            <div class="row">
                                <div class="col-xs-12">
                                    <label for="todo" class="form_label">Todo:</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="todo" name="todo"/>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-xs-4">
                                    <label for="todoDate" class="form_label">Date:</label>
                                    <input type="text" class="form-control" id="todoDate" name="todoDate"/>
                                </div>
                                <div class="col-xs-4">
                                    <label for="todoTimeZone" class="form_label">Time Zone:</label>
                                    <select class="form-control" id="todoTimeZone" name="todoTimeZone">
                                        <%
                                            for(Constants.TIME_ZONE timeZone : Constants.TIME_ZONE.values()) {
                                        %>
                                        <option value="<%=timeZone.toString()%>"><%=timeZone.getTimeZoneDisplay()%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </div>
                                <div class="col-xs-4" >
                                    <label for="todoStatus" class="form_label">Status:</label> <br>
                                    <select id="todoStatus" name="todoStatus" class="form-control">
                                        <option value="<%=Constants.TODO_STATUS.ACTIVE.toString()%>">Active</option>
                                        <option value="<%=Constants.TODO_STATUS.COMPLETE.toString()%>">Complete</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-4">
                                    &nbsp;
                                </div>
                            </div>
                            <div class="row" id="todo_options" style="display:none;">
                                <div class="col-xs-6" id="todo_options_events"  style="display:none;" >
                                    <label for="todoEvents" class="form_label">Assign Events</label>
                                    <select id="todoEvents" name="todoEvents"  data-placeholder="Choose an Event" class="form-control chosen-select" single >
                                        <option value=""></option>
                                    </select>
                                </div>
                                <div class="col-xs-6" id="todo_options_users"  style="display:none;">
                                    <label for="todoUsers" class="form_label">Assign Users</label>
                                    <select id="todoUsers" name="todoUsers"  data-placeholder="Select other users involved" class="form-control chosen-select" multiple >
                                        <option value=""></option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-4">
                                    &nbsp;
                                </div>
                            </div>
                            <input type="hidden" name="todo_id" id="todo_id" value="<%=sTodoId%>" />
                            <input type="hidden" name="current_todo_user" id="current_todo_user" value="<%=currentUserId%>" />
                        </form>

                        <div class="row">
                            <div class="col-xs-2">
                                <button type="button" class="btn btn-default" id="btn_save_todo"> <i class="fa fa-floppy-o"></i> Save</button>
                            </div>
                            <div class="col-xs-2">
                                <button type="button" class="btn btn-default" id="btn_delete_todo"> <i class="fa fa-trash-o"></i> Delete</button>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-4">
                                &nbsp;
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-4">
                                &nbsp;
                            </div>
                        </div>
                        <div class="caption toggle-link">
                            <div class="row" id="edit_todo_reminder">
                                <div class="col-xs-12">
                                    <h6 style="cursor:pointer;"><i id="edit_todo_reminder_icon" class="fa fa-chevron-circle-right"></i><span> Schedule a Reminder Email</span></h6>
                                </div>
                            </div>
                        </div>
                        <div id="todo_reminder" style="display:none;">
                            <form class="form-horizontal" id="frm_save_todo_reminder_schedule">
                                <div class="row">
                                    <div class="col-xs-3">
                                        <label for="todo_reminder_day" class="form_label">Date</label>
                                        <input type="text" class="form-control" id="todo_reminder_day" name="todo_reminder_day">
                                    </div>
                                    <div class="col-xs-3">
                                        <label for="todo_reminder_time" class="form_label">Time</label>
                                        <input type="text" class="form-control" id="todo_reminder_time" name="todo_reminder_time">
                                    </div>
                                    <div class="col-md-3">
                                        <label for="todo_reminder_timezone" class="form_label">Time Zone</label>
                                        <select class="form-control" id="todo_reminder_timezone" name="todo_reminder_timezone">
                                            <%
                                                for(Constants.TIME_ZONE timeZone : Constants.TIME_ZONE.values()) {
                                            %>
                                            <option value="<%=timeZone.toString()%>"><%=timeZone.getTimeZoneDisplay()%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                    </div>
                                    <div class="col-xs-3">
                                        <label for="todoReminderStatus" class="form_label">&nbsp;</label> <br>
                                        <span id="todoReminderStatus"> New </span>
                                    </div>
                                </div>
                                <input type="hidden" name="todo_reminder_id" id="scheduler_todo_reminder_id" value="" />
                                <input type="hidden" name="todo_id" id="scheduler_todo_id" value="<%=sTodoId%>" />
                                <input type="hidden" name="current_todo_user" id="scheduler_current_todo_user" value="<%=currentUserId%>" />
                            </form>
                            <div class="row">
                                <div class="col-xs-3">
                                    <label for="btn_schedule" class="form_label">&nbsp;</label> <br>
                                    <button type="button" class="btn btn-default btn-xs" id="btn_schedule"> <i class="fa fa-clock-o"></i> Schedule</button>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-4">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                </div>
            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/chosen.jquery.min.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/bignumber.min.js"></script>
<script src="/js/datepicker/picker.js"></script>
<script src="/js/datepicker/picker.date.js"></script>
<script src="/js/datepicker/picker.time.js"></script>
<script src="/js/datepicker/legacy.js"></script>
<script type="text/javascript">
    var varLoadTodo = <%=loadTodo%>;
    var varCurrentUserId = '<%=currentUserId%>';
    var varLoadTodoReminder = false;
    $(window).load(function() {

        $('#edit_todo_reminder').click(function(){
            $( "#todo_reminder" ).slideToggle( "slow", function() {
                if($('#todo_reminder').css('display') == 'block') {
                    $('#edit_todo_reminder_icon').removeClass("fa-chevron-circle-right").addClass("fa-chevron-circle-down");
                    if(!varLoadTodoReminder){
                        varLoadTodoReminder = true;
                        loadTodoReminderSchedule(getSchedulerResult);
                    }

                    $('#btn_schedule').bind('click',function(){
                        saveTodoReminderSchedule(getSchedulerResult);
                    });
                }
                if($('#todo_reminder').css('display') == 'none') {
                    $('#edit_todo_reminder_icon').removeClass("fa-chevron-circle-down").addClass("fa-chevron-circle-right");
                    $('#btn_schedule').unbind('click');
                }
            });
        });
        $('#todoDate').pickadate();
        $('#todo_reminder_day').pickadate();
        $('#todo_reminder_time').pickatime({
            // Time intervals
            interval: 1,
            // Minimum and Max time to be shown
            min: [6,00],
            max: [23,59]
        });

        $('#btn_save_todo').click( function(){
            saveTodo(getResult);
        });

        if(varLoadTodo){
            loadingOverlay('show');
            loadTodo( populateTodo );
        }
    });
    function saveTodo( callbackmethod ) {
        var actionUrl = "/proc_save_todos.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_todo").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function saveTodoReminderSchedule( callbackmethod ) {
        var actionUrl = "/proc_save_todos_reminder_schedule.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_todo_reminder_schedule").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadTodo( callbackmethod ) {
        var actionUrl = "/proc_load_todo.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_todo").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadTodoReminderSchedule( callbackmethod ) {
        var actionUrl = "/proc_load_todos_reminder_schedule.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_todo_reminder_schedule").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function getSchedulerResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varTodoReminderScheduleBean = jsonResponseObj.todo_reminder_schedule;
                        if(varTodoReminderScheduleBean!=undefined){
                            $('#scheduler_todo_reminder_id').val( varTodoReminderScheduleBean.toDoReminderScheduleId );
                            //$('#todo_reminder_day').val( varTodoReminderScheduleBean.selected_scheduled_send_day );
                            //$('#todo_reminder_time').val( varTodoReminderScheduleBean.selected_scheduled_send_time );
                            $('#todo_reminder_timezone').val( varTodoReminderScheduleBean.selected_scheduled_send_timezone );

                            var pickerDay = $('#todo_reminder_day').pickadate('picker');
                            if(pickerDay!=undefined){
                                pickerDay.set('select', varTodoReminderScheduleBean.selected_scheduled_send_day, { format: 'dd mmmm, yyyy' });
                            }

                            var pickerTime = $('#todo_reminder_time').pickatime('picker');
                            if(pickerTime!=undefined){
                                pickerTime.set('select', varTodoReminderScheduleBean.selected_scheduled_send_time, { format: 'h:i A' });
                            }

                            if(varTodoReminderScheduleBean.schedule_status == '<%=Constants.SCHEDULER_STATUS.NEW_SCHEDULE.getStatus()%>') {
                                $('#todoReminderStatus').text('Scheduled');
                            } else if(varTodoReminderScheduleBean.schedule_status == '<%=Constants.SCHEDULER_STATUS.COMPLETE.getStatus()%>') {
                                $('#todoReminderStatus').text('Sent');
                            } else if(varTodoReminderScheduleBean.schedule_status == '<%=Constants.SCHEDULER_STATUS.ERROR.getStatus()%>') {
                                $('#todoReminderStatus').text('Error');
                            }

                        }
                    }
                }
                displayAjaxOk(varResponseObj);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varTodoBean = jsonResponseObj.todo_bean;
                        if(varTodoBean!=undefined){
                            $('#todo_id').val( varTodoBean.todo_id );
                        }
                    }
                }
                displayAjaxOk(varResponseObj);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function populateTodo(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varTodoBean = jsonResponseObj.todo_bean;
                        if(varTodoBean!=undefined){
                            $('#todo').val( varTodoBean.todo );
                            // varTodoBean.todo_status

                            if( $.fn.pickadate){
                                var pickerDay = $('#todoDate').pickadate('picker');
                                if(pickerDay!=undefined){
                                    pickerDay.set('select', varTodoBean.todo_date, { format: 'dd mmmm, yyyy' });
                                }
                            }
                            $('#todoTimeZone').val( varTodoBean.todo_timezone );
                            $('#todoStatus').val( varTodoBean.todo_status );
                        }

                        $('#todo_options').show();
                        $('#todo_options_events').show();
                        var varIsVendor = jsonResponseObj.is_vendor;
                        if(varIsVendor == true) {
                            $('#todo_options_users').show();
                        }


                        $("#todoUsers").chosen();
                        $("#todoEvents").chosen({allow_single_deselect: true });


                        var varNumOfVendorUsers = jsonResponseObj.num_of_vendor_userbean;
                        if(varNumOfVendorUsers>0){
                            $('#todoUsers').append("<optgroup label='Team Members' id='todo_teammember'></optgroup>");
                            var varAllVendorUsersBean = jsonResponseObj.vendor_users;
                            for( var varVendorUserCount = 0; varVendorUserCount<varNumOfVendorUsers; varVendorUserCount++){
                                var varVendorUsersBean =  varAllVendorUsersBean[varVendorUserCount];
                                var varVendorUserInfoBean = varVendorUsersBean.user_info_bean;
                                if(varCurrentUserId!=varVendorUsersBean.user_id)  {
                                    $('#todo_teammember').append('<option id="'+varVendorUsersBean.user_id+'" value="'+varVendorUsersBean.user_id+'">'+varVendorUserInfoBean.first_name + ' ' + varVendorUserInfoBean.last_name+'</option>');
                                }
                            }
                        }

                        var varNumOfVendorClientUsers = jsonResponseObj.num_of_vendor_client_userbean;
                        if(varNumOfVendorClientUsers>0){
                            $('#todoUsers').append("<optgroup label='Clients' id='todo_clients'></optgroup>");
                            var varAllVendorClientUsersBean = jsonResponseObj.vendor_client_users;
                            for( var varVendorClientUserCount = 0; varVendorClientUserCount<varNumOfVendorClientUsers; varVendorClientUserCount++){
                                var varVendorClientUsersBean =  varAllVendorClientUsersBean[varVendorClientUserCount];
                                var varVendorClientUserInfoBean = varVendorClientUsersBean.user_info_bean;
                                $('#todo_clients').append('<option  id="'+varVendorClientUsersBean.user_id+'" value="'+varVendorClientUsersBean.user_id+'">'+varVendorClientUserInfoBean.first_name + ' ' + varVendorClientUserInfoBean.last_name+'</option>');
                            }
                        }

                        var varNumOfAssignedTodoUsers = jsonResponseObj.num_of_assigned_todo_users;
                        if(varNumOfAssignedTodoUsers>0){
                            var varAllAssignedUsersBean = jsonResponseObj.assigned_todo_users;
                            for( var varAssignedUsersBeanCount = 0; varAssignedUsersBeanCount<varNumOfAssignedTodoUsers; varAssignedUsersBeanCount++){
                                var varAssignedUsersBean =  varAllAssignedUsersBean[varAssignedUsersBeanCount];;
                                $('#'+varAssignedUsersBean.user_id).attr('selected','selected');
                            }
                        }
                        $('#todoUsers').trigger("chosen:updated");

                        var varNumOfEvents = jsonResponseObj.num_of_events;
                        if(varNumOfEvents>0){
                            var varAllEvents = jsonResponseObj.events;
                            for( var varEventCount = 0; varEventCount<varNumOfEvents; varEventCount++){
                                var varEventBean =  varAllEvents[varEventCount];
                                $('#todoEvents').append('<option value="'+varEventBean.event_id+'" id="'+varEventBean.event_id+'" >'+varEventBean.event_name+'</option>');
                            }

                            var varAssignedTodoEvents = jsonResponseObj.assigned_todo_events;
                            if(varAssignedTodoEvents!=undefined){
                                $('#'+varAssignedTodoEvents.event_id).attr('selected','selected');
                            }
                        }
                        $('#todoEvents').trigger("chosen:updated");


                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
        loadingOverlay('hide');
    }

</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>