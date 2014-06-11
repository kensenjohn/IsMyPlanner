<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sFaqQuestionId = ParseUtil.checkNull(request.getParameter("faq_question_id"));
%>
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
            <div class="page-title">FAQ - Question and Answer</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-10">
                            <h3 id="div_faq_question"></h3>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-10" id="div_faq_answer">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_faq_question">
    <input type="hidden" name="faq_question_id" value="<%=sFaqQuestionId%>"/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    $(window).load(function() {
        loadFaqQuestionAnswer( populateQuestionAnswer);
    });
    function loadFaqQuestionAnswer(callbackmethod) {
        var actionUrl = "/proc_load_faq_question_answer.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_faq_question").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateQuestionAnswer(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist){
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        var varFaqQuestionAnswerBean = jsonResponseObj.faq_question_and_answer;
                        $('#div_faq_question').text( varFaqQuestionAnswerBean.question );
                        $('#div_faq_answer').html( varFaqQuestionAnswerBean.answer );
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