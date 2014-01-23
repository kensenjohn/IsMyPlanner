
function loadClientEvents(varClientId, varClientDataType , callbackmethod) {
    var actionUrl = "/proc_load_client_events.aeve";
    var methodType = "POST";
    var dataString = 'clientid=' + varClientId + '&clientdatatype='+varClientDataType;
    makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
}