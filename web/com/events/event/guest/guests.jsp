<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link rel="stylesheet" href="/css/font-awesome.min.css">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/upload/jquery.fileupload.css">
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }
%>

<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="event_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Event Guests - <span id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
                <%if(loadEventInfo) {%>
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_guests_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
                <%}%>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <button  type="button" class="btn  btn-filled" id="btn_add_guest">
                        <span><span class="glyphicon glyphicon-plus"></span> Add A Guest</span>
                    </button>
                </div>
                <div class="col-md-3">
                    <button  type="button" class="btn  btn-default" id="btn_upload_guests">
                        <span><span class="glyphicon glyphicon-upload"></span> Upload Guests From CSV</span>
                    </button>
                </div>
                <div class="col-md-7">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_guest_table" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-3" role="columnheader">Guest Name</th>
                            <th class="sorting" role="columnheader">Invited Seats</th>
                            <th class="sorting" role="columnheader">RSVP Seats</th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>

                        <tbody role="alert" id="every_guest_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_guest" method="POST">
    <input type="hidden" id="event_id"  name="event_id" value="<%=sEventId%>">
</form>
<form id="frm_delete_guest">
    <input type="hidden" id="delete_guestgroup_id" name="guestgroup_id" value="">
    <input type="hidden" id="delete_event_id" name="event_id" value="">
    <input type="hidden" id="delete_eventguestgroup_id" name="event_guestgroup_id" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/event/event_info.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        loadGuests(populateGuestList);

        $('#btn_add_guest').click(function(){
            $('#frm_guest').attr("action","/com/events/event/guest/edit_guest.jsp");
            $('#frm_guest').submit();
        });
        $('#btn_upload_guests').click(function(){
            $('#frm_guest').attr("action","/com/events/event/guest/upload_guests.jsp");
            $('#frm_guest').submit();
        });
    });
    function loadGuests(callbackmethod) {
        var actionUrl = "/proc_load_all_guests.aeve";
        var methodType = "POST";
        var dataString = $("#frm_guest").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateGuestList(jsonResult) {
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
                    var varNumOfGuests = jsonResponseObj.num_of_guests;
                    if(varNumOfGuests>0){
                        processGuestList(varNumOfGuests, jsonResponseObj.every_event_guests );

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
    function processGuestList(varNumOfGuests,everyEventGuestList) {
        for(i=0;i<varNumOfGuests;i++){
            var varEveryEventGuestBean = everyEventGuestList[i];
            var varEveryEventGuestTableRow = $('<tr id="row_'+varEveryEventGuestBean.event_guestgroup_id+'" ></tr>');
            var varRsvpStatus = varEveryEventGuestBean.rsvp_seats;
            if( varEveryEventGuestBean.has_responded == false ) {
                varRsvpStatus = '<span id="rsvp_status"  class="label label-warning">No Response</span>';
            }
            if( varEveryEventGuestBean.will_not_attend == true ) {
                varRsvpStatus = '<span id="rsvp_status"  class="label label-default">Will Not Attend</span>';
            }

            varEveryEventGuestTableRow.append(
                    '<td>'+varEveryEventGuestBean.group_name+'</td>'+
                    '<td>'+varEveryEventGuestBean.invited_seats+'</td>' +
                    '<td>'+varRsvpStatus+'</td>' +
                    '<td  class="center" >'+
                            '<a href="/com/events/event/guest/edit_guest.jsp?event_id='+varEventId+'&guestGroupId='+varEveryEventGuestBean.guestgroup_id+'" class="btn btn-default btn-xs"><i class="fa fa-pencil"></i> Edit</a>'+
                            '&nbsp;&nbsp;&nbsp;'+
                            '<a id="'+varEveryEventGuestBean.guestgroup_id+'" class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</a>'+'</td>');
            $('#every_guest_rows').append(varEveryEventGuestTableRow);

            // Adding Click Event Functionality for event's delete button.
            var guest_obj = {
                guestgroup_id: varEveryEventGuestBean.guestgroup_id,
                group_name: varEveryEventGuestBean.group_name,
                eventguestgroup_id:varEveryEventGuestBean.event_guestgroup_id,
                event_id: varEventId,
                row_num: i,
                printObj: function () {
                    return this.guestgroup_id + ' ' + this.group_name + ' ' + this.event_id;
                }
            }
            $('#'+varEveryEventGuestBean.guestgroup_id).click({ param_guest_obj:guest_obj},function(e) {
                displayConfirmBox("Are you sure you want to delete this guest? - " + e.data.param_guest_obj.group_name ,
                        "Delete Guest","Yes", "No", deleteGuest,e.data.param_guest_obj)
            });
        }
    }
    function deleteGuest(varGuestObj) {
        $('#delete_guestgroup_id').val(varGuestObj.guestgroup_id);
        $('#delete_event_id').val(varGuestObj.event_id);
        $('#delete_eventguestgroup_id').val(varGuestObj.eventguestgroup_id);
        deleteGuests(processGuestDeletion);
    }
    function deleteGuests(callbackmethod) {
        var actionUrl = "/proc_delete_guest.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_guest").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function processGuestDeletion(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varIsGuestDeleted = jsonResponseObj.is_deleted;
                    if(varIsGuestDeleted){
                        $('#delete_event_id').val('');
                        $('#delete_guestgroup_id').val('');
                        var varDeletedEventGuestGroupId = jsonResponseObj.deleted_eventguestgroup_id;
                        $('#row_'+varDeletedEventGuestGroupId).remove();
                    } else {
                        displayMssgBoxAlert("The guest as not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteGuest - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteGuest - 2)", true);
        }
    }
    function initializeTable(){

        objEveryEventTable =  $('#every_guest_table').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "aaSorting": [],
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