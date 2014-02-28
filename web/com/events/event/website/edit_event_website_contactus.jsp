<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.event.website.EventContactUsBean" %>
<%@ page import="com.events.bean.event.website.EventContactUsRequest" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.event.website.AccessEventContactUs" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%

    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    String sEventWebsiteId = ParseUtil.checkNull(request.getParameter("event_website_id"));
    String sEventContactUsId = ParseUtil.checkNull(request.getParameter("event_contactus_id"));

    EventContactUsBean eventContactUsBean = new EventContactUsBean();
    if(!Utility.isNullOrEmpty(sEventContactUsId)) {
        EventContactUsRequest eventContactUsRequest = new EventContactUsRequest();
        eventContactUsRequest.setEventContactUsId(sEventContactUsId);

        AccessEventContactUs accessEventContactUss = new AccessEventContactUs();
        eventContactUsBean = accessEventContactUss.getEventContactUs(eventContactUsRequest);
    }
    String sTitle = "Contact Information";
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
                    <form id="frm_save_contactus">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="contactus_name" class="form_label">Name</label>
                                    <input type="text" name="contactus_name" id="contactus_name" class="form-control" value="<%=ParseUtil.checkNull(eventContactUsBean.getName())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="contactus_phone" class="form_label">Phone </label>
                                    <input type="text" name="contactus_phone" id="contactus_phone" class="form-control" value="<%=ParseUtil.checkNull(eventContactUsBean.getPhone())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="contactus_email" class="form_label">Email </label>
                                    <input type="text" name="contactus_email" id="contactus_email" class="form-control"  value="<%=ParseUtil.checkNull(eventContactUsBean.getEmail())%>" >
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="event_website_id" id="save_event_website_id" value="<%=sEventWebsiteId%>"/>
                        <input type="hidden" name="event_contactus_id" id="save_event_contactus_id" value="<%=sEventContactUsId%>"/>
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
            saveWebsitePageContactUs(populateEventContactUs);
        });
    });
    function saveWebsitePageContactUs(callbackmethod ) {
        var actionUrl = "/proc_save_event_website_contactus.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_contactus").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateEventContactUs(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varEventContactUsBean = jsonResponseObj.event_contactus_bean;
                    $('#save_event_contactus_id').val(varEventContactUsBean.event_contactus_id);
                    $('#save_event_website_id').val(varEventContactUsBean.event_website_id);
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