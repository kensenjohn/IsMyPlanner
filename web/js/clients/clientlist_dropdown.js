function loadClients(callbackmethod) {
    var actionUrl = "/proc_load_clients.aeve";
    var methodType = "POST";
    var dataString = '';
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}
function populateClientList(jsonResult) {
    if(jsonResult!=undefined) {
        var varResponseObj = jsonResult.response;
        if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
            displayAjaxError(varResponseObj);
        } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
            var varIsPayloadExist = varResponseObj.is_payload_exist;
            if(varIsPayloadExist == true) {
                var jsonResponseObj = varResponseObj.payload;
                var varNumOfClients = jsonResponseObj.num_of_clients;
                if(varNumOfClients>0){
                    processClientListSummary(varNumOfClients,jsonResponseObj.all_client_summary);
                }
                else {
                    //displayMssgBoxAlert("Create a new client here.", true);
                }

            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientList - 1)", true);
        }
    } else {
        displayMssgBoxAlert("Please try again later (populateClientList - 2)", true);
    }
}
function processClientListSummary(varNumOfClients,clientSummaryList) {
    var varDropDownClientList = $('#client_selector');
    for(i=0;i<varNumOfClients;i++){
        var varClientBean = clientSummaryList[i];
        varDropDownClientList.append('<option value="'+varClientBean.client_id+'">'+varClientBean.client_name+'</option>');
    }
}