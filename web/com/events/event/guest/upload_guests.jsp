<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.common.Configuration" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/upload/jquery.fileupload.css">
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }

    String fileUploadHost = ParseUtil.checkNull(Utility.getFileUploadHost());
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
            <div class="page-title">Upload Event Guests from CSV - <span id="event_title"></span></div>
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
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <h4>Step 1 : Create a CSV file with Guest Details</h4>
                </div>
                <%
                    if(!Utility.isNullOrEmpty(fileUploadHost)){
                %>
                        <div class="col-md-3" style="padding-top:10px;">
                            <a href="<%=fileUploadHost+"/csv_guestlist.csv"%>"  target="_blank" >Download a Sample CSV</a>
                        </div>
                <%
                    }
                %>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-5">
                    <h4>Step 2 : Upload Excel</h4>
                </div>
            </div>
            <div class="row">
                <form id="fileupload" action="/proc_upload_csv.aeve" method="POST" enctype="multipart/form-data">
                    <div class="col-md-offset-1 col-md-4">

                        <input type="file" name="files[]" class="fileinput-button btn btn-default">
                    </div>
                    <div class="col-md-2" >
                        <div id="btn_upload">

                        </div>
                    </div>

                </form>
            </div>
            <div class="row">
                <div class="col-md-offset-1 col-md-3">
                    <div id="progress">
                        <div class="bar" style="width: 0%;"></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-offset-1 col-md-5">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-offset-1 col-md-5" style="display:none;" id="div_download_guestlist_csv">
                    <a id="link_download_guestlist_csv" href="">Click to download the uploaded file.</a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row" id="btn_process_guest_excel" style="display:none;">
                <div class="col-md-5">
                    <h4>Step 3 :
                        <button  type="button" class="btn  btn-filled" id="btn_process_guest">
                            <span><span class="glyphicon glyphicon-plus"></span> Create Guests From Uploaded CSV</span>
                        </button></h4>
                </div>
            </div>
            <div class="row">
                <div class="col-md-1">
                </div>
                <div class="col-md-5">
                    <span id="guestcreaterecord_button_status"></span>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_process_excel">
    <input type="hidden" id="event_id" name="event_id" value="<%=sEventId%>">
    <input type="hidden" id="upload_id" name="upload_id" value="">
    <input type="hidden" id="job_status" name="job_status" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script src="/js/event/event_info.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        loadGuestCreateJobRecord(populateGuestCreateJob);
    });
    $(function () {
        $('#fileupload').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                $('#btn_upload').empty();
                var varUploadButton = $('<button/>').attr("id","upload_button").addClass("btn btn-default").appendTo($('#btn_upload'));
                $('#upload_button').append( $('<span/>').addClass("glyphicon glyphicon-upload"));
                $("#upload_button").text('Upload');

                data.context = varUploadButton.click(function () {
                    data.context = $('<p/>').text('Uploading...').replaceAll($(this));
                    data.submit();
                });

            },
            done: function (e, data) {
                data.context.text('Upload Complete.');
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];
                    $('#upload_id').val(varDataResult.upload_id);
                    $('#job_status').val('<%=Constants.JOB_STATUS.PRELIM_STATE.getStatus()%>');
                    $('#guestcreaterecord_file_status').text('Currently uploaded file : ' + varDataResult.name );

                    if( varDataResult.success ) {
                        var linkToDownloadFile = varDataResult.fileuploadhost+"/"+  varDataResult.foldername+"/"+varDataResult.name;
                        $('#link_download_guestlist_csv').attr("href", linkToDownloadFile );
                        $('#div_download_guestlist_csv').show();

                    }
                }
                createGuestsCreationJob( processCreateGuestCreationJob ); //this will create a job record with - Prelim status
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });
    function loadGuestCreateJobRecord(callbackmethod) {
        var actionUrl = "/proc_load_guestcreationjob_records.aeve";
        var methodType = "POST";
        var dataString = $("#frm_process_excel").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function activateGuestsCreationJob(callbackmethod) {
        var actionUrl = "/proc_edit_guestcreationjob_records.aeve";
        var methodType = "POST";
        var dataString = $("#frm_process_excel").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function processActivateGuestCreationJob(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                $('#btn_process_excel').show();
            } else {
                alert("Please try again later 1.");
            }
        } else {
            alert("Response is null 3.");
        }
    }

    function createGuestsCreationJob(callbackmethod) {
        var actionUrl = "/proc_edit_guestcreationjob_records.aeve";
        var methodType = "POST";
        var dataString = $("#frm_process_excel").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function processCreateGuestCreationJob(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                $('#btn_process_guest_excel').show();
                //$('#guestcreaterecord_button_status').text('Your guests from the excel will be created soon.');
                invokeActivateGuestsCreation();
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    function populateGuestCreateJob(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varFileName = jsonResponseObj.file_name;
                        var varUploadId = jsonResponseObj.upload_id;
                        var varJobStatus = jsonResponseObj.job_status;
                        if(varFileName!=undefined && varFileName!='') {
                            $('#guestcreaterecord_file_status').text('Currently uploaded file : ' + varFileName );
                        }
                        if(varUploadId!=undefined && varUploadId!='') {
                            $('#upload_id').val(varUploadId);
                        }
                        if(varJobStatus!=undefined && varJobStatus!='') {
                            $('#job_status').val(varJobStatus);
                        }

                        if(varJobStatus == '<%=Constants.JOB_STATUS.PRELIM_STATE.getStatus()%>' ) {
                            $('#btn_process_guest_excel').show();
                            invokeActivateGuestsCreation();
                        }

                        if(varJobStatus == '<%=Constants.JOB_STATUS.READY_TO_PICK.getStatus()%>' ) {
                            $('#guestcreaterecord_button_status').text('Your guests from the excel will be created soon.');
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

    function invokeActivateGuestsCreation(){
        $('#btn_process_guest_excel').click(function(){
            $('#job_status').val('<%=Constants.JOB_STATUS.READY_TO_PICK.getStatus()%>');
            displayMssgBoxAlert('You will be emailed after the excel gets processed.', false);
            activateGuestsCreationJob(processActivateGuestCreationJob);   //this will create a job record with - Ready To Pick status

        });
    }
</script>