
function loadClientEvents(varClientId, varClientDataType , callbackmethod) {
    var actionUrl = "/proc_load_client_events.aeve";
    var methodType = "POST";
    var dataString = 'client_id=' + varClientId + '&client_datatype='+varClientDataType;
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}

function populateClientEvents(jsonResult) {

    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                var varNumOfEvents = jsonResponseObj.num_of_events;
                if(varNumOfEvents>0){
                    processEventList(varNumOfEvents, jsonResponseObj.client_every_event );
                }
                initializeTable();
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientEvent - 1)", true);
        }
    } else {
        displayMssgBoxAlert("Please try again later (populateClientEvent - 2)", true);
    }
}

function processEventList(varNumOfEvents,clientEveryEventyList) {
    for(i=0;i<varNumOfEvents;i++){
        var varClientEveryEventBean = clientEveryEventyList[i];
        var varClientEveryEventTableRow = $('<tr id="row_'+varClientEveryEventBean.event_id+'" ></tr>');
        varClientEveryEventTableRow.append(
            '<td>'+varClientEveryEventBean.event_day+' ' +varClientEveryEventBean.event_time+' ' +varClientEveryEventBean.event_timezone+'</td>'+
                '<td>'+varClientEveryEventBean.event_name+'</td>' +
                '<td  class="center" >'+
                '<a href="/com/events/event/edit_event.jsp?event_id='+varClientEveryEventBean.event_id+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil"></span> Edit</a>'+
                '&nbsp;&nbsp;&nbsp;'+
                '<a id="'+varClientEveryEventBean.event_id+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span> Delete</a>'+'</td>');
        $('#every_event_rows').append(varClientEveryEventTableRow);


        // Adding Click Event Functionality for event's delete button.
        var event_obj = {
            event_id: varClientEveryEventBean.event_id,
            event_name: varClientEveryEventBean.event_name,
            client_id: varClientEveryEventBean.client_id,
            row_num: i,
            printObj: function () {
                return this.event_id + ' ' + this.event_name + ' ' + this.client_id;
            }
        }
        $('#'+varClientEveryEventBean.event_id).click({param_event_obj:event_obj},function(e) {displayConfirmBox("Are you sure you want to delete event - " + e.data.param_event_obj.event_name ,"Delete Event","Yes", "No", deleteEvent,e.data.param_event_obj)});
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

var objEveryEventTable = '';
function initializeTable(){
    objEveryEventTable =  $('#client_event_table').dataTable({
        "bPaginate": false,
        "bInfo": false,
        "bFilter": true,
        "aoColumns": [
            null,
            null,
            { "bSortable": false }
        ]
    });
}