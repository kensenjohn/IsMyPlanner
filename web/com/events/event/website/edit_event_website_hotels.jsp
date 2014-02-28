<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.event.website.EventHotelRequest" %>
<%@ page import="com.events.bean.event.website.EventHotelsBean" %>
<%@ page import="com.events.event.website.AccessEventHotels" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%

    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    String sEventWebsiteId = ParseUtil.checkNull(request.getParameter("event_website_id"));
    String sEventHotelId = ParseUtil.checkNull(request.getParameter("event_hotel_id"));

    EventHotelsBean eventHotelsBean = new EventHotelsBean();
    if(!Utility.isNullOrEmpty(sEventHotelId)) {
        EventHotelRequest eventHotelRequest = new EventHotelRequest();
        eventHotelRequest.setEventHotelId( sEventHotelId );

        AccessEventHotels accessEventHotels = new AccessEventHotels();
        eventHotelsBean = accessEventHotels.getEventHotel(eventHotelRequest);
    }
    String sTitle = "Hotel Information";
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
                    <form id="frm_save_hotel">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="hotel_name" class="form_label">Name</label>
                                    <input type="text" name="hotel_name" id="hotel_name" class="form-control" value="<%=ParseUtil.checkNull(eventHotelsBean.getName())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="hotel_phone" class="form_label">Phone </label>
                                    <input type="text" name="hotel_phone" id="hotel_phone" class="form-control" value="<%=ParseUtil.checkNull(eventHotelsBean.getPhone())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="hotel_address" class="form_label">Address </label>
                                    <input type="text" name="hotel_address" id="hotel_address" class="form-control" value="<%=ParseUtil.checkNull(eventHotelsBean.getAddress())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="hotel_url" class="form_label">URL </label>
                                    <input type="text" name="hotel_url" id="hotel_url" class="form-control" value="<%=ParseUtil.checkNull(eventHotelsBean.getUrl())%>">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="hotel_instructions" class="form_label">Instructions </label>
                                    <input type="text" name="hotel_instructions" id="hotel_instructions" class="form-control"  value="<%=ParseUtil.checkNull(eventHotelsBean.getInstructions())%>" >
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="event_website_id" id="save_event_website_id" value="<%=sEventWebsiteId%>"/>
                        <input type="hidden" name="event_hotel_id" id="save_event_hotel_id" value="<%=sEventHotelId%>"/>
                        <input type="hidden" name="event_id" value="<%=sEventId%>"/>
                    </form>


                    <div class="row">
                        <div class="col-md-9">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-9">
                            <button class="btn btn-filled" id="save_event_hotel" param="event_hotel">Save</button>
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
        $('#save_event_hotel').click(function(){
            saveWebsitePageFeaturePartySettings(populateEventHotel);
        });
    });
    function saveWebsitePageFeaturePartySettings(callbackmethod ) {
        var actionUrl = "/proc_save_event_website_hotel.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_hotel").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateEventHotel(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varEventHotelBean = jsonResponseObj.event_hotel_bean;
                    $('#save_event_hotel_id').val(varEventHotelBean.event_hotel_id);
                    $('#save_event_website_id').val(varEventHotelBean.event_website_id);
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