<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<link rel="stylesheet" href="/css/datepicker/default.time.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
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
            <%if(loadEventInfo) {
                %><div class="page-title">Edit Event - <span id="event_title"></span></div><%
            } else {
                %><div class="page-title">Create Event</div><%
            } %>

        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <%if(loadEventInfo) {%>
                <div class="row">
                    <div class="col-md-12">
                        <div id="tabs">
                            <jsp:include page="/com/events/event/event_tab.jsp">
                                <jsp:param name="event_details_active" value="active"/>
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
                <div class="col-md-12">
                    <form method="post" id="frm_new_event">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-9">
                                    <label for="eventName" class="form_label">Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="eventName" name="eventName" placeholder="Event Name (e.g Ron's Wedding) ">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-3">
                                    <label for="eventDay" class="form_label">Date</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="eventDay" name="eventDay" placeholder="Day of the Event">
                                </div>
                                <div class="col-md-3">
                                    <label for="eventTime" class="form_label">Time</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="eventTime" name="eventTime" placeholder="Time of the Event">
                                </div>
                                <div class="col-md-3">
                                    <label for="eventTimeZone" class="form_label">Time Zone</label><span class="required"> *</span>
                                    <select class="form-control" id="eventTimeZone" name="eventTimeZone">
                                        <%
                                            for(Constants.TIME_ZONE timeZone : Constants.TIME_ZONE.values()) {
                                        %>
                                                <option value="<%=timeZone.toString()%>"><%=timeZone.getTimeZoneDisplay()%></option>
                                        <%
                                            }
                                        %>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-9">
                                    <label for="eventClient" class="form_label">Client</label><span class="required"> *</span>
                                    <select class="form-control" id="eventClient" name="eventClient">
                                        <option value="">Select A Client</option>
                                        <option value="create_client">Create A New Client</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-9">&nbsp;</div>
                            </div>
                            <div class="row" id="row_create_client" style="display:none;">
                                <div class="col-md-offset-1 col-md-4">
                                    <label for="clientName" class="form_label">Client Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="clientName" name="clientName" placeholder="Client Name (e.g Ron and Susan) ">
                                </div>
                                <div class="col-md-4">
                                    <label for="clientEmail" class="form_label">Client's Email</label><span class="required"> *</span>
                                    <input type="email" class="form-control" id="clientEmail" name="clientEmail" placeholder="Client's Email">
                                </div>
                            </div>
                        </div>
                        <button type="button" class="btn btn-filled" id="btn_new_event">Save</button>
                        <input type="hidden" id="eventId" name="eventId" value="<%=sEventId%>">
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_client">
</form>
<form id="frm_load_event" action="/com/events/event/edit_event.jsp">
    <input type="hidden" id="event_id" name="event_id" value="<%=sEventId%>">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/datepicker/picker.js"></script>
<script src="/js/datepicker/picker.date.js"></script>
<script src="/js/datepicker/picker.time.js"></script>
<script src="/js/datepicker/legacy.js"></script>
<script src="/js/event/event_info.js"></script>
<script type="text/javascript">
    var varLoadEventInfo = <%=loadEventInfo%>
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
        if(varLoadEventInfo){
            var varEventId = '<%=sEventId%>';
            loadEventInfo(populateEventInfo,varEventId);
        }
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
                        $('#event_id').val(jsonResponseObj.event_id);
                        $('#frm_load_event').submit();

                    }
                }
            } else {
                alert("Please try again later 1.");
            }
        } else {
            alert("Response is null 3.");
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>