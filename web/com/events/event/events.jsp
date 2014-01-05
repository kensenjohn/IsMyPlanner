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
        <jsp:param name="event_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Events</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
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
                </tbody></table>
        </div>
    </div>
</div>
</body>
<form id="frm_delete_event">
    <input type="hidden" id="event_id" name="event_id" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var objEveryEventTable = '';
    $(window).load(function() {
        loadEvents(populateEventList);
    });
    function loadEvents(callbackmethod) {
        var actionUrl = "/proc_load_all_events.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_client").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateEventList(jsonResult) {
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
                            '<a href="/com/events/event/edit_event.jsp?event_id='+varEveryEventBean.event_id+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil"></span> Edit</a>'+
                            '&nbsp;&nbsp;&nbsp;'+
                            '<a id="'+varEveryEventBean.event_id+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span> Delete</a>'+'</td>');
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
            $('#'+varEveryEventBean.event_id).click({param_event_obj:event_obj},function(e) {displayConfirmBox("Are you sure you want to delete event - " + e.data.param_event_obj.event_name ,"Delete Event","Hell Ya!", "Sorry", deleteEvent,e.data.param_event_obj)});
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
                    var varIsEventDeleted = jsonResponseObj.is_deleted;
                    if(varIsEventDeleted){
                        $('#event_id').val('');
                        var varDeletedEventId = jsonResponseObj.deleted_event_id;
                        $('#row_'+varDeletedEventId).remove();
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