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
<%@ page import="com.events.bean.event.EventDisplayDateBean" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="org.slf4j.Logger" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value="Guest RSVP"/>
</jsp:include>

<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
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


    appLogging.info("guestBean" + guestBean);

    appLogging.info("eventBean" + eventBean);
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
            String sGuestGivenName =  sGuestFirstName + " " + sGuestLastName;

            Integer iTotalInvitedSeats = ParseUtil.sToI( eventGuestGroupBean.getInvitedSeats() );
            Integer iRsvpSeats = ParseUtil.sToI( eventGuestGroupBean.getRsvpSeats() );

            EventDisplayDateBean eventDisplayDateBean = eventBean.getEventDisplayDateBean();
            String sEventTime = eventDisplayDateBean.getSelectedDay() + " " + eventDisplayDateBean.getSelectedTime() + " " + eventDisplayDateBean.getSelectedTimeZone();

            StringBuilder strInvitationMessage = new StringBuilder("You are invited to attend ");
            strInvitationMessage.append( ParseUtil.checkNull(eventBean.getEventName())).append(" on ")
                    .append(ParseUtil.checkNull(sEventTime)).append(".<br>");
            if(iTotalInvitedSeats>1) {
                strInvitationMessage.append("<br>You may bring ").append( (iTotalInvitedSeats-1));
                if((iTotalInvitedSeats-1) == 1 ) {
                    strInvitationMessage.append(" guest with you.");
                } else {
                    strInvitationMessage.append(" guests with you.");
                }
            }

            boolean isYesSelected = false;
            boolean isNoSelected = false;

            StringBuilder strRSVPMessage = new StringBuilder();

            if( !eventGuestGroupBean.isWillNotAttend() && eventGuestGroupBean.getHasResponded())  {
                if( iRsvpSeats==1){
                    isYesSelected = true;
                    strRSVPMessage.append("<span style='font-weight:bold;font-size: 115%; color: #614b5b; margin: 0px'>Yes</span> I will attend.");
                }
                if(  iRsvpSeats>1){
                    isYesSelected = true;
                    strRSVPMessage.append("<span style='font-weight:bold;font-size: 115%; color: #614b5b; margin: 0px'>Yes</span> I will attend. I will bring <span style='font-weight:bold;font-size: 115%; color: #614b5b; margin: 0px'>").append(iRsvpSeats-1);
                    if((iRsvpSeats-1) == 1 ) {
                        strRSVPMessage.append(" guest</span> with me. (Total ").append(iRsvpSeats).append(")");
                    } else if(iRsvpSeats > 1 ) {
                        strRSVPMessage.append(" guests</span> with me. (Total ").append(iRsvpSeats).append(")");
                    }
                }
            }


            if(eventGuestGroupBean.isWillNotAttend()) {
                isNoSelected = true;
                strRSVPMessage.append("<span style='font-weight:bold;font-size: 115%; color: #614b5b; margin: 0px'>No</span> I will not attend.");
            } else if(!eventGuestGroupBean.getHasResponded()){
                strRSVPMessage.append("Please respond below.");
            }



            String sRequestRSVPHeader = "RSVP";
            String sPreviousRSVPHeader = Constants.EMPTY;
            if(eventGuestGroupBean.getHasResponded()){
                sRequestRSVPHeader = "Update your RSVP";
                sPreviousRSVPHeader = "Current RSVP Status";
            }
        %>


            <div class="row">
                <div class="col-md-12">
                    <h3>Invitation for <%=eventBean.getEventName()%></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    Dear <%=sGuestGivenName%>,
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <p><%=strInvitationMessage.toString()%></p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <h4><%=sPreviousRSVPHeader%></h4>
                                <p><%=strRSVPMessage.toString()%></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <h4><%=sRequestRSVPHeader%></h4>
                                <form id="frm_guest_webresponse_rsvp">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <label for="row_rsvpans" class="form_label">Will You Attend</label><span class="required"> *</span>
                                                <div class="row" id="row_rsvpans">
                                                    <div class="col-md-2">
                                                        <label for="attend_yes" class="form_label">
                                                            <input type="radio" id="attend_yes" name ="will_you_attend" value="yes">&nbsp;Yes
                                                        </label>
                                                    </div>
                                                    <div class="col-md-2">
                                                        <label for="attend_yes" class="form_label">
                                                            <input type="radio" id="attend_no" name ="will_you_attend" value="no">&nbsp;No
                                                        </label>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row" id="row_rsvp_seat_selection">
                                            <div class="col-md-12" >
                                                <label for="row_rsvpans" class="form_label">I will attend along with
                                                    <%
                                                        if( iTotalInvitedSeats>1 ) {
                                                            int optionSelected = (iRsvpSeats-1);
                                                            if(optionSelected<0 && iRsvpSeats==-1) {
                                                                optionSelected =  (iTotalInvitedSeats-1);  // user has not RSVPed before,
                                                            }
                                                    %>
                                                    I will attend along with <select name="num_of_guests" id="num_of_guests">
                                                        <%
                                                            for(int i=0; i < iTotalInvitedSeats; i++ ) {
                                                        %>
                                                        <option value="<%=i%>" <%=(optionSelected==i)?"selected":"" %> ><%=i%></option>
                                                        <%
                                                            }
                                                        %> </select> guests.

                                                    <%
                                                    } else {
                                                    %>
                                                    I will attend.
                                                    <%
                                                        }
                                                    %>
                                                </label>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="button" class="btn  btn-filled" id="btn_update_rsvp">Update</button>
                                    <input type="hidden" id="link_id" name="link_id" value="<%=sLinkId%>">
                                    <input type="hidden" name="event_guestgroup_id" value="<%=ParseUtil.checkNull(eventGuestGroupBean.getEventGuestGroupId())%>">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <% } else {
                response.sendRedirect("/com/events/common/error/404.jsp");
            } %>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    $(window).load(function() {
        $("#btn_update_rsvp").click(function(){
            updateGuestRSVP(getResult);
        });
        $( "#attend_yes" ).change(  function() {
            toggleRsvpSelection();
        });
        $( "#attend_no" ).change(  function() {
            toggleRsvpSelection();
        });
    });

    function toggleRsvpSelection() {
        if($( "#attend_yes" ).is(":checked") ) {
            $("#row_rsvp_seat_selection").show("slow");
        } else {
            $("#row_rsvp_seat_selection").hide("slow");
        }
    }
    function updateGuestRSVP(callbackmethod) {
        var actionUrl = "/proc_guest_webresponse_rsvp.aeve";
        var methodType = "POST";
        var dataString = $('#frm_guest_webresponse_rsvp').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                displayMssgBoxAlert('Thank You!! Your RSVP has been updated.', false);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>