<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.event.website.EventRegistryRequest" %>
<%@ page import="com.events.bean.event.website.EventRegistryBean" %>
<%@ page import="com.events.event.website.AccessEventRegistry" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%

    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    String sEventWebsiteId = ParseUtil.checkNull(request.getParameter("event_website_id"));
    String sEventRegistryId = ParseUtil.checkNull(request.getParameter("event_registry_id"));

    EventRegistryBean eventRegistryBean = new EventRegistryBean();
    if(!Utility.isNullOrEmpty(sEventRegistryId)) {
        EventRegistryRequest eventRegistryRequest = new EventRegistryRequest();
        eventRegistryRequest.setEventRegistryId( sEventRegistryId );

        AccessEventRegistry accessEventRegistrys = new AccessEventRegistry();
        eventRegistryBean = accessEventRegistrys.getEventRegistry(eventRegistryRequest);
    }
    String sTitle = "Registry Information";
%>
<body>
<div class="page_wrap">
    <div class="container">
        <div class="content_format">

            <div class="row">
                <div class="col-md-8">
                    <div class="row">
                        <div class="col-md-12">
                            <h4><%=sTitle%></h4>
                        </div>
                    </div>
                    <form id="frm_save_registry">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="registry_name" class="form_label">Name</label>
                                    <input type="text" name="registry_name" id="registry_name" class="form-control" value="<%=ParseUtil.checkNull(eventRegistryBean.getName())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="registry_url" class="form_label">URL </label>
                                    <input type="text" name="registry_url" id="registry_url" class="form-control" value="<%=ParseUtil.checkNull(eventRegistryBean.getUrl())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="registry_instructions" class="form_label">Instructions </label>
                                    <input type="text" name="registry_instructions" id="registry_instructions" class="form-control"  value="<%=ParseUtil.checkNull(eventRegistryBean.getInstructions())%>" >
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="event_website_id" id="save_event_website_id" value="<%=sEventWebsiteId%>"/>
                        <input type="hidden" name="event_registry_id" id="save_event_registry_id" value="<%=sEventRegistryId%>"/>
                        <input type="hidden" name="event_id" value="<%=sEventId%>"/>
                    </form>


                    <div class="row">
                        <div class="col-md-9">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <button class="btn btn-filled" id="save_event_registry" param="event_registry">Save</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    $(window).load(function() {
        parent.$.colorbox.resize({
            innerWidth:$('body').width(),
            innerHeight:$('body').height()
        });
        $('#save_event_registry').click(function(){
            saveWebsitePageFeaturePartySettings(populateEventRegistry);
        });
    });
    function saveWebsitePageFeaturePartySettings(callbackmethod ) {
        var actionUrl = "/proc_save_event_website_registry.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_registry").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateEventRegistry(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varEventRegistryBean = jsonResponseObj.event_registry_bean;
                    $('#save_event_registry_id').val(varEventRegistryBean.event_registry_id);
                    $('#save_event_website_id').val(varEventRegistryBean.event_website_id);
                }
                displayAjaxOk(varResponseObj);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
</html>