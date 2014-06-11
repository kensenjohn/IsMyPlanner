<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="none" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Request A Demo</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-8">
                    <form class="form-horizontal" id="frm_save_demo_email">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="demo_name" class="form_label">Email:</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="demo_name" name="demo_name" />
                            </div>
                            <div class="col-md-6">
                                <label for="demo_email" class="form_label">Name:</label>
                                <input type="text" class="form-control" id="demo_email" name="demo_email" />
                            </div>
                        </div>
                    </form>
                    <div class="row">
                        <div class="col-md-3">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <button class="btn btn-filled" id="btn_send_demo_email">&nbsp;Request A Demo</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <span id="reset_email_message"></span>
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
        $('#btn_send_demo_email').click(function(){
            sendRequestDemoEmail(getResult);
        })
    });
    function sendRequestDemoEmail(callbackmethod){
        var actionUrl = "/proc_request_demo.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_demo_email").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var isRequestSent = jsonResponseObj.demo_request_complete;
                        if(isRequestSent){
                            displayAjaxOk(varResponseObj);
                            $('#reset_email_message').text('Thank you. We will get in touch with you to schedule a demo.');
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
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>