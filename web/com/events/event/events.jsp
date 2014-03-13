<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Perm" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    boolean canCreateEvent = false;
    boolean canDeleteEvent = false;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
            CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
            if(checkPermission!=null ) {
                canCreateEvent =  checkPermission.can(Perm.CREATE_NEW_EVENT);
                canDeleteEvent =  checkPermission.can(Perm.DELETE_EVENT);
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
        <jsp:param name="event_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Events</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <%
                if(canCreateEvent) {
            %>
                    <div class="row">
                        <div class="col-md-12">
                            <button  type="button" class="btn  btn-filled" id="btn_create_new_event">
                                <i class="fa fa-plus"></i>  Create New Event
                            </button>
                        </div>
                    </div>
            <%
                }
            %>

            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_event_table" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-3" role="columnheader">Date</th>
                            <th class="sorting" role="columnheader">Name</th>
                            <th class="sorting" role="columnheader">Client</th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>

                        <tbody role="alert" id="every_event_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_delete_event">
    <input type="hidden" id="event_id" name="event_id" value="">
</form>
<form id="frm_create_new_event" action="/com/events/event/edit_event.jsp">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var varCanDeleteEvent = <%=canDeleteEvent%>;
    var objEveryEventTable = '';
    $(window).load(function() {
        $('#btn_create_new_event').click(function(){
            $('#frm_create_new_event').submit();
        });
        loadEvents(populateEventList);
    });
    function loadEvents(callbackmethod) {
        var actionUrl = "/proc_load_all_events.aeve";
        var methodType = "POST";
        var dataString = ''
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateEventList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfEvents = jsonResponseObj.num_of_events;
                    //displayMssgBoxAlert("varNumOfEvents :" + varNumOfEvents, true);
                    if(varNumOfEvents>0){
                        processEventList(varNumOfEvents, jsonResponseObj.every_event );

                    } else {
                        //displayMssgBoxAlert("Create a new client here.", true);
                    }
                    initializeTable();
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }
    function processEventList(varNumOfEvents,everyEventyList) {
        for(i=0;i<varNumOfEvents;i++){
            var varEveryEventBean = everyEventyList[i];
            var varEveryEventTableRow = $('<tr id="row_'+varEveryEventBean.event_id+'" ></tr>');
            varEveryEventTableRow.append(
                    '<td>'+varEveryEventBean.event_day+' ' +varEveryEventBean.event_time+' ' +varEveryEventBean.event_timezone+'</td>'+
                    '<td>'+varEveryEventBean.event_name+'</td>' +
                    '<td>'+varEveryEventBean.client_name+'</td>'+
                    '<td  class="center" >'+
                            '<a href="/com/events/event/edit_event.jsp?event_id='+varEveryEventBean.event_id+'" class="btn btn-default btn-xs"><i class="fa fa-pencil"></i> Edit</a>'+
                            '&nbsp;&nbsp;&nbsp;'+
                            '<a id="'+varEveryEventBean.event_id+'" class="btn btn-default btn-xs <%=!canDeleteEvent?"disabled":""%>"><i class="fa fa-trash-o"></i> Delete</a>'+'</td>');
            $('#every_event_rows').append(varEveryEventTableRow);


            // Adding Click Event Functionality for event's delete button.
            var event_obj = {
                event_id: varEveryEventBean.event_id,
                event_name: varEveryEventBean.event_name,
                client_id: varEveryEventBean.client_id,
                row_num: i,
                printObj: function () {
                    return this.event_id + ' ' + this.event_name + ' ' + this.client_id;
                }
            }
            $('#'+varEveryEventBean.event_id).click({param_event_obj:event_obj},function(e) {
                if(varCanDeleteEvent) {
                    displayConfirmBox("Are you sure you want to delete event - " + e.data.param_event_obj.event_name ,"Delete Event","Yes", "No", deleteEvent,e.data.param_event_obj);
                } else {
                    displayMssgBoxAlert("You are not authorized to perform this action.", true);
                }

            });
        }

    }

    function deleteEvent(varEventObj) {

        $('#event_id').val(varEventObj.event_id);
        deleteEvents(processEventDeletion);
    }
    function deleteEvents(callbackmethod) {
        var actionUrl = "/proc_delete_event.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_event").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function processEventDeletion(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varIsEventDeleted = jsonResponseObj.is_deleted;
                    if(varIsEventDeleted){
                        $('#event_id').val('');
                        var varDeletedEventId = jsonResponseObj.deleted_event_id;
                        objEveryEventTable.fnDeleteRow((objEveryEventTable.$('#row_'+varDeletedEventId))[0] );
                    } else {
                        displayMssgBoxAlert("The event was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }
    function initializeTable(){

        objEveryEventTable =  $('#every_event_table').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>