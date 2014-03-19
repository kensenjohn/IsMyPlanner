<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/upload/jquery.fileupload.css">
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    String sGuestGroupId = ParseUtil.checkNull(request.getParameter("guestGroupId"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }

    boolean loadGuestGroupInfo = false;
    if(sGuestGroupId!=null && !"".equalsIgnoreCase(sGuestGroupId)) {
        loadGuestGroupInfo = true;
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
            <div class="page-title">
            <%
                if(loadGuestGroupInfo){
            %>
                    Edit Guest -
            <%
                } else {
            %>
                    Add a Guest -
            <%
                }
            %>
            <span id="event_title"></span></div>

        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <%if(loadEventInfo) {%>
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_guests_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <%}%>

            <div class="row">
                <div class="col-md-7">
                    &nbsp;
                </div>
                <div class="col-md-2">
                    <button  type="button" class="btn  btn-filled" id="btn_add_guest">
                        <span><span class="glyphicon glyphicon-plus"></span> Create Another Guest</span>
                    </button>
                </div>
                <div class="col-md-3">
                    <button  type="button" class="btn  btn-default" id="btn_upload_guests">
                        <span><span class="glyphicon glyphicon-upload"></span> Upload Guests From Excel</span>
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <form method="post" id="frm_new_guest">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-8">
                                    <label for="groupName" class="form_label">Group Name</label>
                                    <input type="text" class="form-control" id="groupName" name="groupName" placeholder="Group Name (e.g Schweitzer Family) ">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="guestFirstName" class="form_label">First Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="guestFirstName" name="guestFirstName" placeholder="First Name">
                                </div>
                                <div class="col-md-4">
                                    <label for="guestLastName" class="form_label">Last Name</label>
                                    <input type="text" class="form-control" id="guestLastName" name="guestLastName" placeholder="Last Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-3">
                                    <label for="guestInvitedSeats" class="form_label">Invited Seats</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="guestInvitedSeats" name="guestInvitedSeats" placeholder="Invited Seats (Number)">
                                </div>
                                <div class="col-md-3">
                                    <label for="guestRSVP" class="form_label">RSVP Seats</label> <span id="rsvp_status"  class="label label-warning">No Response</span>
                                    <input type="text" class="form-control" id="guestRSVP" name="guestRSVP" placeholder="RSVP Seats  (Number)">
                                </div>
                                <div class="col-md-2">
                                    <label for="guestWillNotAttend" class="form_label"></label><br>
                                    <input type="checkbox" id="guestWillNotAttend" name = "guestWillNotAttend">
                                    Will Not Attend
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="guestEmail" class="form_label">Email</label>
                                    <input type="email" class="form-control" id="guestEmail" name="guestEmail" placeholder="Email">
                                </div>
                                <div class="col-md-4">
                                    <label for="guestCompanyName" class="form_label">Company Name</label>
                                    <input type="text" class="form-control" id="guestCompanyName" name="guestCompanyName" placeholder="Company Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-4">
                                    <label for="guestPhone1" class="form_label">Phone 1</label>
                                    <input type="tel" class="form-control" id="guestPhone1" name="guestPhone1" placeholder="Phone">
                                </div>
                                <div class="col-md-4">
                                    <label for="guestPhone2" class="form_label">Phone 2</label>
                                    <input type="tel" class="form-control" id="guestPhone2" name="guestPhone2" placeholder="Phone">
                                </div>
                            </div>
                        </div>
                        <div class="caption">
                            <div class="row" id="edit_guest_address">
                                <div class="col-md-2">
                                    <span> Add/Edit Guest Address  </span>
                                </div>
                            </div>
                            <div class="row" >
                                <div class="col-md-2">
                                    &nbsp;
                                </div>
                            </div>
                        </div>
                        <div id="guest_address" style= "display:none">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="guestAddress1" class="form_label">Address 1</label>
                                        <input type="text" class="form-control" id="guestAddress1" name="guestAddress1" placeholder="Address 1">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="guestAddress2" class="form_label">Address 2</label>
                                        <input type="text" class="form-control" id="guestAddress2" name="guestAddress2" placeholder="Address 2">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="guestCity" class="form_label">City</label>
                                        <input type="text" class="form-control" id="guestCity" name="guestCity" placeholder="City">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="guestState" class="form_label">State</label>
                                        <input type="text" class="form-control" id="guestState" name="guestState" placeholder="State">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-4">
                                        <label for="guestPostalCode" class="form_label">Postal Code</label>
                                        <input type="text" class="form-control" id="guestPostalCode" name="guestPostalCode" placeholder="Postal Code">
                                    </div>
                                    <div class="col-md-4">
                                        <label for="guestCountry" class="form_label">Country</label>
                                        <input type="text" class="form-control" id="guestCountry" name="guestCountry" placeholder="Country">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--<div class="caption">
                            <div class="row" id="edit_guest_list">
                                <div class="col-md-3">
                                    <span> Add/Edit Other Guest in this Group  </span>
                                </div>
                            </div>
                            <div class="row" >
                                <div class="col-md-2">
                                    &nbsp;
                                </div>
                            </div>
                        </div>
                        <div id="guest_name_list" style= "display:none">
                        </div> -->
                        <button type="button" class="btn btn-filled" id="btn_save_guest">Save</button>
                        <input type="hidden" id="eventId" name="eventId" value="<%=sEventId%>">
                        <input type="hidden" id="guestGroupId" name="guestGroupId" value="<%=sGuestGroupId%>">
                        <input type="hidden" id="eventGuestGroupId" name="eventGuestGroupId" value="">

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_guest">
    <input type="hidden" id="event_id" name="event_id" value="<%=sEventId%>">
    <input type="hidden" id="guest_group_Id" name="guestGroupId" value="<%=sGuestGroupId%>">
</form>
<form id="frm_guest" method="POST">
    <input type="hidden" name="event_id" value="<%=sEventId%>">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/event/event_info.js"></script>
<script type="text/javascript">
    var varEditGuestGroupInfo = <%=loadGuestGroupInfo%>
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        if(varEditGuestGroupInfo){
            loadGuestInfo(populateGuestInfo,varEventId);
        }
        $('#edit_guest_address').click(function(){
            $( "#guest_address" ).slideToggle( "slow");
            if($('#guest_address').css('display') == 'block') {
                $('#edit_guest_address_icon').removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
            }
            if($('#guest_address').css('display') == 'none') {
                $('#edit_guest_address_icon').removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
            }
        });
        $('#btn_save_guest').click(function(){
            saveGuest(getResult);
        });
        $('#btn_upload_guests').click(function(){
            $('#frm_load_guest').attr("action","/com/events/event/guest/upload_guests.jsp");
            $('#frm_load_guest').submit();
        });
        $('#btn_add_guest').click(function(){
            $('#frm_guest').attr("action","/com/events/event/guest/edit_guest.jsp");
            $('#frm_guest').submit();
        });
    });
    function loadGuestInfo( callbackmethod ) {
        var actionUrl = "/proc_load_guest.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_guest").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateGuestInfo (jsonResult) {
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
                    processGuestInfo(jsonResponseObj);
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventInfo - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventInfo - 2)", true);
        }
    }
    function saveGuest( callbackmethod ) {
        var actionUrl = "/proc_save_guest.aeve";
        var methodType = "POST";
        var dataString = $("#frm_new_guest").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult)  {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                var varIsMessageExist = varResponseObj.is_message_exist;
                if(varIsMessageExist == true) {
                    var jsonResponseMessage = varResponseObj.messages;
                    var varArrErrorMssg = jsonResponseMessage.error_mssg;
                    if(varArrErrorMssg!=undefined) {
                        displayMssgBoxMessages(varArrErrorMssg, true);
                    } else {
                        displayMssgBoxMessages('Oops!! We were unable to process your request. Please try again later.', true);
                    }

                }

            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                displayMssgBoxAlert('Guest was saved successfully', false);
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        $('#event_id').val(varEventId);
                        $('#eventId').val(varEventId);
                        $('#guestGroupId') .val(jsonResponseObj.guestgroup_id);
                        $('#guest_group_Id') .val(jsonResponseObj.guestgroup_id);
                        $('#eventGuestGroupId') .val(jsonResponseObj.eventguestgroup_id);
                        //$('#frm_load_event').submit();

                    }
                }
            } else {
                alert("Please try again later 1.");
            }
        } else {
            alert("Response is null 3.");
        }
    }

    function processGuestInfo(jsonResponseObj) {
        var varEventGuestGroup = jsonResponseObj.event_guest_group;
        if(varEventGuestGroup!=undefined) {
            $('#eventGuestGroupId').val(varEventGuestGroup.event_guestgroup_id);
            $('#guestInvitedSeats').val(varEventGuestGroup.invite_seats);
            $('#guestRSVP').val(varEventGuestGroup.rsvp_seats);

            if( !varEventGuestGroup.has_responded ) {
                $('#rsvp_status').removeClass().text('No Response').addClass("label label-warning"); //No Response
                $('#guestRSVP').val('');
            } else {
                $('#rsvp_status').removeClass().text('');
            }
            if( varEventGuestGroup.will_not_attend ) {
                $('#guestWillNotAttend').prop('checked', true);
                $('#rsvp_status').removeClass().text('Will Not Attend').addClass("label label-default"); //No Response
            }
        }
        var varGuestGroup = jsonResponseObj.guest_group;
        if(varGuestGroup!=undefined) {
            $('#groupName').val(varGuestGroup.group_name);
        }
        var varGuest = jsonResponseObj.guest;
        if(varGuest!=undefined) {
            $('#guestFirstName').val(varGuest.first_name);
            $('#guestLastName').val(varGuest.last_name);
            $('#guestCompany').val(varGuest.company);
        }

        var varNumOfGuestGroupPhone = jsonResponseObj.num_of_guest_group_phone;
        if(varNumOfGuestGroupPhone!=undefined && varNumOfGuestGroupPhone>0) {
            var varGuestGroupPhone = jsonResponseObj.guest_group_phone;
            for(i = 0; i<varNumOfGuestGroupPhone; i++) {
                var varPhoneNum = varGuestGroupPhone[i].phone_num;
                if( (i+1) == 1 ){
                    $('#guestPhone1').val(varPhoneNum);
                }
                if( (i+1) == 2 ){
                    $('#guestPhone2').val(varPhoneNum);
                }

            }
        }

        var varNumOfGuestGroupEmail = jsonResponseObj.num_of_guest_group_email;
        if(varNumOfGuestGroupEmail!=undefined && varNumOfGuestGroupEmail>0) {
            var varGuestGroupEmail = jsonResponseObj.guest_group_email;
            for(i = 0; i<varNumOfGuestGroupEmail; i++) {
                var varEmail = varGuestGroupEmail[i].email_id;
                $('#guestEmail').val(varEmail);
            }
        }

        var varNumOfGuestGroupAddress = jsonResponseObj.num_of_guest_group_address;
        if(varNumOfGuestGroupAddress!=undefined && varNumOfGuestGroupAddress>0) {
            $('#guest_address').show();
            var varGuestGroupAddress = jsonResponseObj.guest_group_address;
            for(i = 0; i<varNumOfGuestGroupAddress; i++) {
                var varGuestAddress = varGuestGroupAddress[i];

                $('#guestAddress1').val(varGuestAddress.address_1);
                $('#guestAddress2').val(varGuestAddress.address_2);
                $('#guestCity').val(varGuestAddress.city);
                $('#guestState').val(varGuestAddress.state);
                $('#guestCountry').val(varGuestAddress.country);
                $('#guestPostalCode').val(varGuestAddress.zipcode);
            }
            $('#guest_address').hide();
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>