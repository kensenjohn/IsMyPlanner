<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="net.fckeditor.FCKeditor" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<link rel="stylesheet" href="/css/datepicker/default.time.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventId = ParseUtil.checkNull(ESAPI.encoder().decodeForHTML(request.getParameter("event_id")));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }
    String sEventEmailId = ParseUtil.checkNull(ESAPI.encoder().decodeForHTML(request.getParameter("eventemail_id")));
    boolean isCreateNewEmail = false;
    if(Utility.isNullOrEmpty(sEventEmailId)) {
        isCreateNewEmail = true;
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
            <%
                if(isCreateNewEmail) {
            %>
                    <div class="page-title">Create Event Email -
            <%
                } else {
            %>
                    <div class="page-title">Edit Event Email -
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
                            <jsp:param name="event_emails_active" value="active"/>
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
                    <h3><span id="email_title">New Email</span></h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <form  method="post" id="frm_edit_email">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-9">
                                    <label for="emailSubject" class="form_label">Subject</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="emailSubject" name="email_subject" placeholder="Subject ">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-9">
                                    <label for="emailFrom" class="form_label">From Email</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="emailFrom" name="email_from" placeholder="From Email ">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-9">
                                    <label class="form_label">Body</label><span class="required"> *</span>
                                    <%
                                        FCKeditor fckEditor = new FCKeditor(request, "email_body");
                                        fckEditor.setBasePath ("/com/events/common/fckeditor");
                                        fckEditor.setToolbarSet("SmarasoftDefault");
                                        fckEditor.setConfig("SkinPath","/com/events/common/fckeditor/editor/skins/silver/");
                                        //fckEditor.setValue("This is some <strong>sample text</strong>. You are using <a href=\"http://www.fckeditor.net\">FCKeditor</a>.");
                                        fckEditor.setValue( " New Email ");
                                        out.println(fckEditor);
                                    %>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12">
                                    <label class="form_label">Send To All Guests Who</label><span class="required"> *</span>
                                    <div class="row" id="row_sendToAllEmailRules">
                                        <div class="col-md-2">
                                            <label for="emailAllInvited" class="form_label">
                                                <input type="radio" id="emailAllInvited" name = "email_send_rules" value="<%=Constants.SEND_EMAIL_RULES.ALL_INVITED%>">
                                                were Invited
                                            </label>
                                        </div>
                                        <div class="col-md-2">
                                            <label for="emailAllWhoResponded" class="form_label">
                                                <input type="radio" id="emailAllWhoResponded" name = "email_send_rules" value="<%=Constants.SEND_EMAIL_RULES.ALL_WHO_RESPONDED%>">
                                                responded
                                            </label>
                                        </div>
                                        <div class="col-md-2">
                                            <label for="emailAllDidNotRespond" class="form_label">
                                                <input type="radio" id="emailAllDidNotRespond" name = "email_send_rules" value="<%=Constants.SEND_EMAIL_RULES.DID_NOT_RESPOND%>">
                                                did not respond
                                            </label>
                                        </div>
                                        <div class="col-md-2">
                                            <label for="emailAllWhoWillAttend" class="form_label">
                                                <input type="radio" id="emailAllWhoWillAttend" name = "email_send_rules" value="<%=Constants.SEND_EMAIL_RULES.WILL_ATTEND%>">
                                                will attend
                                            </label>
                                        </div>
                                        <div class="col-md-2">
                                            <label for="emailAllWhoWillNotAttend" class="form_label">
                                                <input type="radio" id="emailAllWhoWillNotAttend" name = "email_send_rules" value="<%=Constants.SEND_EMAIL_RULES.WILL_NOT_ATTEND%>">
                                                will not attend
                                            </label>
                                        </div>
                                    </div>
                                    <div class="row" id="row_sendImmeditelyEmailRules">
                                        <div class="col-md-5">
                                            <label for="emailRSVPThankYou" class="form_label">
                                                <input type="radio" id="emailRSVPThankYou" name = "email_send_rules" value="<%=Constants.SEND_EMAIL_RULES.RSVP_THANKYOU%>">
                                                Send to Guest immediately after they RSVP
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div  id="emailSchedule"  class="form-group" style="display:none;">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-2">
                                            Send Email on
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group">
                                            <div class="col-md-3">
                                                <label for="emailDay" class="form_label">Date</label>
                                                <input type="text" class="form-control" id="emailDay" name="email_send_day" placeholder="Day to Send Email">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="emailTime" class="form_label">Time</label>
                                                <input type="text" class="form-control" id="emailTime" name="email_send_time" placeholder="Time to Send Email">
                                            </div>
                                            <div class="col-md-3">
                                                <label for="eventTimeZone" class="form_label">Time Zone</label>
                                                <select class="form-control" id="eventTimeZone" name="email_send_timezone">
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
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    &nbsp;
                                </div>
                            </div>
                        </div>
                        <input type="hidden" id="email_schedule_enabled"  name="email_schedule_enabled" value=""/>
                        <input type="hidden" id="event_email_id"  name="event_email_id" value=""/>
                        <input type="hidden" id="send_email_now"  name="send_email_now" value=""/>
                        <input type="hidden" id="event_id"  name="event_id" value="<%=ESAPI.encoder().encodeForHTML(sEventId)%>"/>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2">
                    <button  type="button" class="btn  btn-filled" id="btn_save_email">
                        <span><span class="glyphicon glyphicon-floppy-disk"></span> Save Changes</span>
                    </button>
                </div>
                <div  id="emailSendActionButton" >
                    <div class="col-md-3">
                        <button  type="button" class="btn  btn-default" id="btn_schedule_email">
                            <span class="glyphicon glyphicon-calendar">&nbsp;</span> <span id="txtEmailScheduleButton">Schedule a Time to Send</span>
                        </button>
                    </div>
                    <div class="col-md-2">
                        <button  type="button" class="btn  btn-default" id="btn_send_now">
                            <span><span class="glyphicon glyphicon-send"></span> Send Now</span>
                        </button>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    &nbsp;
                </div>
            </div>
            <form id="frm_test_email">
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-9">
                            <label for="emailTest" class="form_label">Test Email</label>
                            <input type="text" class="form-control" id="emailTest" name="emailTest" placeholder="Test Email">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <button  type="button" class="btn  btn-default" id="btn_send_test_email">
                            <span><span class="glyphicon glyphicon-cog"></span> Send Test Email Now</span>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
<form id="frm_load_eventemail">
    <input type="hidden" name="eventemail_id" value="<%=sEventEmailId%>"/>
    <input type="hidden"  name="event_id" value="<%=sEventId%>"/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/datepicker/picker.js"></script>
<script src="/js/datepicker/picker.date.js"></script>
<script src="/js/datepicker/picker.time.js"></script>
<script src="/js/datepicker/legacy.js"></script>
<script src="/js/event/event_info.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    var varEventEmailId = '<%=sEventEmailId%>';
    var createNewEmail = <%=isCreateNewEmail%>;
    $(window).load(function() {
        $('#emailDay').pickadate()
        $('#emailTime').pickatime({
            // Time intervals
            interval: 15,
            // Minimum and Max time to be shown
            min: [6,00],
            max: [23,59]
        });
        loadEventInfo(populateEventInfo,varEventId);
        $('#btn_schedule_email').click(function(){
            if($('#emailSchedule').css('display')=='none'){
                $('#email_schedule_enabled').val('true');
                $('#emailSchedule').show("slow");
                $("#txtEmailScheduleButton").text("Remove Schedule");
            }else{
                $('#email_schedule_enabled').val('false');
                $('#emailSchedule').hide("slow");
                $("#txtEmailScheduleButton").text("Send In The Future");
            }
        });
        $( "input[name='email_send_rules']").click( function(){
            if( $('#emailRSVPConfirmation').is(':checked') ) {
                $('#emailSendActionButton').hide("slow");
            } else {
                $('#emailSendActionButton').show("slow");
            }
        });

        $('#btn_save_email').click( function(){
            $('#send_email_now').val(false);
            saveEmail(processSaveEmailResult);
        });
    });
    function loadEventEmailInfo(callbackmethod, varEventId, varEventEmailId ) {
        var actionUrl = "/proc_load_eventemail.aeve";
        var methodType = "POST";
        var dataString = $('#frm_load_eventemail').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    var varTmpEditorHtml = '';
    function populateEventEmailInfo(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;

                    var varEventEmailBean = jsonResponseObj.event_email_bean;
                    if(varEventEmailBean!=undefined) {

                        $('#event_email_id').val(varEventEmailBean.event_email_id);
                        $('#emailSubject').val(varEventEmailBean.email_subject);
                        $('#emailFrom').val(varEventEmailBean.from_address_email);

                        var fckEmailEditor = FCKeditorAPI.GetInstance('email_body') ;
                        fckEmailEditor.SetHTML( varEventEmailBean.html_body );
                        $('#emailDay').val(jsonResponseObj.email_send_day);
                        $('#emailTime').val(jsonResponseObj.email_send_time);
                        $('#eventTimeZone').val(jsonResponseObj.email_send_timezone);
                        $('input[value='+jsonResponseObj.send_email_rule+']').prop("checked",true);
                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function FCKeditor_OnComplete( editorInstance ) {
        if(createNewEmail == false && varEventEmailId!='') {
            loadEventEmailInfo(populateEventEmailInfo,varEventId, varEventEmailId);
        }
    }

    function saveEmail( callbackmethod) {
        var actionUrl = "/proc_save_email.aeve";
        var methodType = "POST";
        var oEditor = FCKeditorAPI.GetInstance('email_body') ;
        $('#email_body').val( oEditor.GetHTML() );
        var dataString = $('#frm_edit_email').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function processSaveEmailResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;

                    var varEventEmailBean = jsonResponseObj.event_email_bean;
                    if(varEventEmailBean!=undefined) {
                        $('#event_email_id').val(varEventEmailBean.event_email_id);
                        displayMssgBoxAlert('Your changes were successfully saved.', false);
                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>