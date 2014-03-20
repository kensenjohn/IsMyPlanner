<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/font-awesome.min.css">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<body>
<div class="page_wrap">
<%
    String sEventEmailId = ParseUtil.checkNull(request.getParameter("eventemail_id"));
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
%>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-4 col-sm-4">
                            <div class="info_tile">
                                <div class="widget">
                                    <div class="content">
                                        <div class="icon_display">
                                            <i id="email_options_icon" class="fa fa-group"></i>
                                        </div>
                                        <div>
                                            <h3><span id="num_of_users_who_viewed_email">0</span></h3>
                                            <h5>Read Email</h5>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 col-sm-4">
                            <div class="info_tile green">
                                <div class="widget">
                                    <div class="content">
                                        <div class="icon_display">
                                            <i class="fa fa-eye"></i>
                                        </div>
                                        <div>
                                            <h3><span id="num_of_times_viewed">0</span></h3>
                                            <h5>Total Views</h5>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12 col-sm-12">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<form id="frm_load_eventemail_metrics">
    <input type="hidden" name="eventemail_id" value="<%=sEventEmailId%>"/>
    <input type="hidden"  name="event_id" value="<%=sEventId%>"/>
    <input type="hidden"  name="metric_type" value="<%=Constants.EMAIL_METRIC_TYPE.EMAIL_OPENED%>"/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script type="text/javascript">
    var varEventEmailId = '<%=sEventEmailId%>';
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        $('#btn_create_email').click(function(){
            $("#frm_create_email").submit();
        });
        if(varEventId!='' && varEventEmailId!='') {
            loadEventEmailInfo(populateEmailMetrics,varEventId, varEventEmailId);
        } else{
            displayMssgBoxAlert("Oops!! We were unable to process your request at this time. Please try again later - (js-001)", true);
        }
    });
    function loadEventEmailInfo(callbackmethod, varEventId, varEventEmailId ) {
        var actionUrl = "/proc_load_eventemail_metrics.aeve";
        var methodType = "POST";
        var dataString = $('#frm_load_eventemail_metrics').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateEmailMetrics(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;

                    var numOfTotalViews= jsonResponseObj.total_num_of_times_viewed;
                    var numOfUsersWhoViewedEmail = jsonResponseObj.total_num_of_users_who_viewed_email;
                    $('#num_of_times_viewed').text(numOfTotalViews);
                    $('#num_of_users_who_viewed_email').text(numOfUsersWhoViewedEmail);
                    var varAllUsersWhoViewedEmail = jsonResponseObj.users_who_viewed_email;

                    //check out event website registry to populate table with guests.
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