<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="org.owasp.esapi.ESAPI" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<link rel="stylesheet" href="/css/datepicker/default.time.css" id="theme_time">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link rel="stylesheet" href="/css/font-awesome.min.css">
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
        <jsp:param name="event_active" value="active"/>
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
                    <h3><span id="email_title">New Email</span></h3>
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
                                    <label for="emailTo" class="form_label">To: (Guests)</label><span class="required"> *</span>
                                    <select class="form-control" id="emailTo" name="email_send_rules">
                                        <option value="<%=Constants.SEND_EMAIL_RULES.ALL_INVITED%>">Everyone invited</option>
                                        <option value="<%=Constants.SEND_EMAIL_RULES.ALL_WHO_RESPONDED%>">Responded to your invitation</option>
                                        <option value="<%=Constants.SEND_EMAIL_RULES.DID_NOT_RESPOND%>">Did not respond to invitation</option>
                                        <option value="<%=Constants.SEND_EMAIL_RULES.WILL_ATTEND%>">Will attend</option>
                                        <option value="<%=Constants.SEND_EMAIL_RULES.WILL_NOT_ATTEND%>">Will NOT attend</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-9">
                                    <textarea id="email_body" name="email_body"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <h5 id="email_options" style="cursor:pointer;"><i id="email_options_icon" class="fa fa-chevron-circle-right"></i>Email Options</h5>
                            </div>
                        </div>
                        <div  id="email_template_text"  class="form-group"  style="display:none;">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-offset-1 col-md-11">
                                            <h6>Use the following templates in your emails. We will replace them with relevant text.</h6>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-offset-1 col-md-8">
                                            <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="email_template_text_table" >
                                                <thead>
                                                <tr role="row">
                                                    <th> Template </th>
                                                    <th> Description  </th>
                                                </tr>
                                                </thead>

                                                <tbody role="alert" id="email_template_text_rows">
                                                    <tr><td> {{GUEST_GIVEN_NAME}} </td><td> Your guest's name. Will include first name and last name if available.  </td></tr>
                                                    <tr><td> {{GUEST_FIRST_NAME}} </td><td> Your guest's first name.</td></tr>
                                                    <tr><td> {{GUEST_LAST_NAME}} </td><td> Your guest's last name.</td></tr>
                                                    <tr><td> {{GUEST_RSVP_LINK}} </td><td> RSVP link specific to each guest.   </td></tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div  id="emailSchedule"  class="form-group" style="display:none;">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <span id="email_schedule_txt">Send Email on </span>
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
                        <span><i class="fa fa-floppy-o"></i> Save Changes</span>
                    </button>
                </div>
                <div  id="emailSendActionButton" >
                    <div class="col-md-3">
                        <button  type="button" class="btn  btn-default" id="btn_schedule_email">
                            <i class="fa fa-calendar"></i> <span id="txtEmailScheduleButton">Schedule a Time to Send</span>
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
                            <span><i class="fa fa-cog"></i> Send Test Email Now</span>
                        </button>
                    </div>
                </div>
                <input type="hidden" name="event_email_id" value="">
                <input type="hidden" name="event_id" value="<%=ESAPI.encoder().encodeForHTML(sEventId)%>"/>
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
<script src="/js/tinymce/tinymce.min.js"></script>
<script src="/js/event/event_info.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    var varEventEmailId = '<%=sEventEmailId%>';
    var createNewEmail = <%=isCreateNewEmail%>;

    tinymce.init({
        selector: "#email_body",
        theme: "modern",
        plugins: [
            "advlist autolink link lists charmap print preview hr anchor pagebreak spellchecker",
            "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
            "save table contextmenu directionality emoticons template paste textcolor uploadimage"
        ],
        toolbar1: "preview | insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link uploadimage"
    });

    $(window).load(function() {
        var emailDayPicker = $('#emailDay').pickadate();
        $('#emailTime').pickatime({
            // Time intervals
            interval: 5,
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
                $("#txtEmailScheduleButton").text("Schedule a Time to Send");
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
            tinyMCE.triggerSave();
            saveEmail(processSaveEmailResult);
        });
        if(createNewEmail == false && varEventEmailId!='') {
            loadEventEmailInfo(populateEventEmailInfo,varEventId, varEventEmailId);
        }

        $('#btn_send_test_email').click(function(){
            sendTestEmail( getResult);
        });
        $('#email_options').click(function(){
            $('#email_template_text').toggle('slow');
            $('#email_options_icon').toggleClass( "fa-chevron-circle-down" );
        });

        initializeTable();
    });

    function sendTestEmail(callbackmethod, varEventId, varEventEmailId ) {
        var actionUrl = "/proc_send_test_eventemail.aeve";
        var methodType = "POST";
        var dataString = $('#frm_test_email').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                displayMssgBoxAlert('The test email will be sent out in the next few minutes.', false);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

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

                        $('#frm_test_email input[name=event_email_id]').val(varEventEmailBean.event_email_id); // to send test emails

                        $('#event_email_id').val(varEventEmailBean.event_email_id);
                        $('#emailSubject').val(varEventEmailBean.email_subject);
                        $('#email_title').text(varEventEmailBean.email_subject);
                        $('#emailFrom').val(varEventEmailBean.from_address_email);

                        tinyMCE.activeEditor.setContent(varEventEmailBean.html_body )
                        $('#emailDay').val(jsonResponseObj.email_send_day);
                        picker.set('select', jsonResponseObj.email_send_day, { format: 'yyyy-mm-dd' })
                        $('#emailTime').val(jsonResponseObj.email_send_time);
                        $('#eventTimeZone').val(jsonResponseObj.email_send_timezone);
                        $('input[value='+jsonResponseObj.send_email_rule+']').prop("checked",true);

                        if( jsonResponseObj.action  == 'SCHEDULE_ENABLED' ) {
                            $('#email_schedule_enabled').val('true');
                            $('#emailSchedule').show();
                            $("#txtEmailScheduleButton").text("Remove Schedule");
                        } else {
                            $('#email_schedule_enabled').val('false');
                            $('#emailSchedule').hide();
                            $("#txtEmailScheduleButton").text("Schedule a Time to Send");
                        }

                        if( jsonResponseObj.schedule_status  == 'COMPLETE'  ) {
                            $('#email_schedule_txt').text('Email was Sent on');
                        }

                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function saveEmail( callbackmethod) {
        var actionUrl = "/proc_save_email.aeve";
        var methodType = "POST";
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
                        $('#email_title').text( $('#emailSubject').val() );
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

    function initializeTable(){
        objEmailTemplateTextTable =  $('#email_template_text_table').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,
            "aoColumns": [
                null,
                { "bSortable": false }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>