<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
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
        <jsp:param name="event_active" value="currently_active"/>
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
                        <span><span class="glyphicon glyphicon-upload"></span> Upload Guests From Excel</span>
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
                            '<a href="/com/events/event/guest/edit_guest.jsp?event_id='+varEventId+'&guestGroupId='+varEveryEventGuestBean.guestgroup_id+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil"></span> Edit</a>'+
                            '&nbsp;&nbsp;&nbsp;'+
                            '<a id="'+varEveryEventGuestBean.guestgroup_id+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span> Delete</a>'+'</td>');
            $('#every_guest_rows').append(varEveryEventGuestTableRow);
        }
    }
    function initializeTable(){

        objEveryEventTable =  $('#every_guest_table').dataTable({
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