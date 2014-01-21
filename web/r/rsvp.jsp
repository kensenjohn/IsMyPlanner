<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.event.guest.response.WebRespRequest" %>
<%@ page import="com.events.event.guest.GuestWebResponse" %>
<%@ page import="com.events.bean.event.guest.response.GuestWebResponseBean" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.bean.event.guest.GuestResponseBean" %>
<%@ page import="com.events.bean.event.EventResponseBean" %>
<%@ page import="com.events.bean.event.guest.GuestGroupBean" %>
<%@ page import="com.events.bean.event.EventBean" %>
<%@ page import="com.events.bean.event.guest.GuestBean" %>
<%@ page import="com.events.bean.event.guest.EventGuestGroupBean" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value="Guest RSVP"/>
</jsp:include>

<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sLinkId = ParseUtil.checkNull(request.getParameter("id"));

    WebRespRequest  webRsvpRequestBean = new WebRespRequest();
    webRsvpRequestBean.setLinkId(sLinkId);
    webRsvpRequestBean.setGuestWebResponseType(Constants.GUEST_WEBRESPONSE_TYPE.RSVP);

    GuestWebResponse guestWebResponse = new GuestWebResponse();
    GuestWebResponseBean guestWebResponseBean = guestWebResponse.isValidLinkId(webRsvpRequestBean);

    GuestGroupBean guestGroupBean = new GuestGroupBean();
    EventBean eventBean = new EventBean();
    GuestBean guestBean = new GuestBean();
    EventGuestGroupBean eventGuestGroupBean = new EventGuestGroupBean();
    if(guestWebResponseBean!=null && !Utility.isNullOrEmpty(guestWebResponseBean.getGuestWebResponseId()) ) {
        webRsvpRequestBean.setGuestGroupId(ParseUtil.checkNull(guestWebResponseBean.getGuestGroupId()));
        webRsvpRequestBean.setEventId( ParseUtil.checkNull(guestWebResponseBean.getEventId()) );

        GuestResponseBean guestResponseBean = guestWebResponse.getGuestGroupFromLink(webRsvpRequestBean);
        if(guestResponseBean!=null){
            guestGroupBean = guestResponseBean.getGuestGroupBean();
            guestBean = guestResponseBean.getGuestBean();
            eventGuestGroupBean = guestResponseBean.getEventGuestGroupBean();
        }


        EventResponseBean eventResponseBean = guestWebResponse.getEventFromLink(webRsvpRequestBean);
        if(eventResponseBean!=null) {
            eventBean = eventResponseBean.getEventBean();
        }
    }


%>
<body>
<div class="page_wrap">
    <div class="menu_bar">
        <div class="container">
            <div class="menu_logo">
                <a href="#"><img src="/img/logo.png" alt=""></a>
            </div>
            <div class="menu_links">
            </div>
        </div>
    </div>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">RSVP <span style="display:none;" id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
        <%if(guestGroupBean!=null && !Utility.isNullOrEmpty(guestGroupBean.getGuestGroupId()) && eventBean!=null && !Utility.isNullOrEmpty(eventBean.getEventId())
                && guestBean!=null && !Utility.isNullOrEmpty(guestBean.getGuestId()) ) {
            String sGuestFirstName = ParseUtil.checkNull(guestBean.getFirstName());
            String sGuestLastName = ParseUtil.checkNull(guestBean.getLastName());

            Integer iTotalInvitedSeats = ParseUtil.sToI( eventGuestGroupBean.getInvitedSeats() );
            Integer iRsvpSeats = ParseUtil.sToI( eventGuestGroupBean.getRsvpSeats() );
        %>

        <% } %>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    $(window).load(function() {

    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>