<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
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
            <div class="page-title">Create Event</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <jsp:include page="/com/events/event/event_info_form.jsp">
                <jsp:param name="action_type" value="create"/>
            </jsp:include>
        </div>
    </div>
</div>
</body>
<form id="frm_load_client">
</form>
<form id="frm_load_event" action="/com/events/event/edit_event.jsp">
    <input type="hidden" id="eventId" name="eventId" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    $(window).load(function() {
        $('#eventDay').pickadate()
        $('#eventTime').pickatime({
            // Time intervals
            interval: 15,
            // Minimum and Max time to be shown
            min: [6,00],
            max: [23,59]
        });
        $('#eventClient').change(function(){
            if( $('#eventClient').val() == 'create_client' ){
                $('#row_create_client').show("slow");
            } else {
                $('#row_create_client').hide("slow");
            }
        });
        $('#btn_new_event').click(function(){
            saveEvent(getResult);
        });
        loadClients(populateClientList);
    });
    function loadClients(callbackmethod) {
        var actionUrl = "/proc_load_clients.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_client").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function saveEvent( callbackmethod ) {
        var actionUrl = "/proc_save_event.aeve";
        var methodType = "POST";
        var dataString = $("#frm_new_event").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateClientList(jsonResult) {
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
        var varDropDownClientList = $('#eventClient');
        for(i=0;i<varNumOfClients;i++){
            var varClientBean = clientSummaryList[i];
            varDropDownClientList.append('<option value="'+varClientBean.client_id+'">'+varClientBean.client_name+'</option>');
        }
    }
    function getResult(jsonResult)
    {
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
                //displayMssgBoxAlert('User was logged in successfully', false);
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        $('#eventId').val(jsonResponseObj.event_id);
                        $('#frm_load_event').submit();

                    }
                }
            } else {
                alert("Please try again later 1.");
            }
        } else {
            alert("Response is nullr 3.");
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>