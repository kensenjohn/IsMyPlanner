<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
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
                                            <h3><span id="num_of_guests_who_viewed_email">0</span></h3>
                                            <h5>Unique Guests</h5>
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
                            <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_viewer_who_viewed_email" >
                                <thead>
                                <tr role="row">
                                    <th class="sorting col-md-3" role="columnheader">Name</th>
                                    <th class="center" role="columnheader">Number of Views</th>
                                </tr>
                                </thead>

                                <tbody role="alert" id="every_user_who_viewed_email_rows">
                                </tbody>
                            </table>
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
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
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
        initializeRegistryTable();
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
                    var numOfGuestsWhoViewedEmail = jsonResponseObj.total_num_of_guests_who_viewed_email;
                    $('#num_of_times_viewed').text(numOfTotalViews);
                    $('#num_of_guests_who_viewed_email').text(numOfGuestsWhoViewedEmail);
                    var varAllGuestsWhoViewedEmail = jsonResponseObj.guests_who_viewed_email;

                    this.emailViewedMetricsModel = new EmailViewedMetricsModel({
                        'bb_num_of_guests_who_viewed_email' : numOfGuestsWhoViewedEmail,
                        'bb_all_guests_who_viewed_email' : varAllGuestsWhoViewedEmail
                    });
                    var emailViewedMetricView = new EmailViewedMetricView({model:this.emailViewedMetricsModel});
                    emailViewedMetricView.render();

                    //check out event website registry to populate table with guests.
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    var EmailViewedMetricsModel = Backbone.Model.extend({});
    var EmailViewedMetricView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfGuestsWhoViewedEmail = this.model.get('bb_num_of_guests_who_viewed_email');
            this.varGuestsWhoViewedEmail = this.model.get('bb_all_guests_who_viewed_email');
        },
        render:function(){

            if(this.varNumOfGuestsWhoViewedEmail>0){
                var oTable = objEveryGuestWhoViewedEmailTable;
                if(oTable!='' && oTable!=undefined){
                    oTable.fnClearTable();
                    for (var i = 0;i < this.varNumOfGuestsWhoViewedEmail;i++) {
                        var vaGuestTrackerBean = this.varGuestsWhoViewedEmail[i];
                        var varEmailAddress = vaGuestTrackerBean.email_address;
                        var varGuestViews = vaGuestTrackerBean.number_of_views;
                        var varGuestId = vaGuestTrackerBean.guest_id;

                        var ai = oTable.fnAddData( [varEmailAddress, varGuestViews ] );
                        var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
                        $(nRow).attr('id','row_'+varGuestId);
                    }
                }
            }
        }
    });

    function initializeRegistryTable(){

        objEveryGuestWhoViewedEmailTable =  $('#every_viewer_who_viewed_email').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,
            "aoColumns":  [
                {"bSortable": true,"sClass":"col-md-5"},
                { "bSortable": true,"sClass": "center" }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>