<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.bean.clients.ClientRequestBean" %>
<%@ page import="com.events.clients.AccessClients" %>
<%@ page import="com.events.bean.clients.ClientBean" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">

<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }

    boolean isLoggedInUserAClient = false;
    ClientBean clientBean = new ClientBean();
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
            ClientRequestBean clientRequestBean = new ClientRequestBean();
            clientRequestBean.setClientId( loggedInUserBean.getParentId());

            AccessClients accessClients = new AccessClients();
            clientBean = accessClients.getClient( clientRequestBean );
            if(clientBean!=null && !Utility.isNullOrEmpty(clientBean.getClientId())) {
                isLoggedInUserAClient = true;
            }
        }
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
            <div class="page-title">Event Vendor Selection - <span id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <%if(loadEventInfo) {%>
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_vendors_active" value="active"/>
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
                    <a href="/com/events/event/event_vendors.jsp?event_id=<%=sEventId%>" class="btn btn-filled">
                        <i class="fa fa-chevron-left"></i> <span> Back to Event Vendor List</span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_potential_partner_vendor" >
                        <thead>
                        <tr role="row">
                            <th class="sorting  col-md-3" role="columnheader">Name</th>
                            <th class="sorting" role="columnheader">Type</th>
                            <th class="sorting" role="columnheader">Website</th>
                            <th class="sorting" role="columnheader">Phone</th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>

                        <tbody role="alert" id="every_potential_partner_vendor_rows">
                        </tbody>
                    </table>
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
            <%
                if(isLoggedInUserAClient) {
            %>
                    <div class="row">
                        <div class="col-md-12">
                            <h5>Do not see your preferred Vendor in the list above?</h5>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <form id="frm_send_note">
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-9">
                                            <label for="preferred_vendor_note" class="form_label">Send a note to your planner</label><span class="required"> *</span>
                                            <textarea class="form-control" id="preferred_vendor_note" name="preferred_vendor_note" placeholder="Include vendors you prefer in your note." ></textarea>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="note_from" value="<%=ParseUtil.checkNull(clientBean.getClientId())%>"/>
                                <input type="hidden" name="note_from_type" value="<%=Constants.USER_TYPE.CLIENT.getType()%>"/>
                                <input type="hidden" name="note_to"  value="<%=Constants.NOTIFICATION_RECEPIENTS.ALL_PLANNERS.toString()%>"/>
                            </form>
                            <div class="row">
                                <div class="col-md-12">
                                    <button class="btn btn-filled btn-small" id="btn_send_note">Send</button>
                                </div>
                            </div>
                        </div>
                    </div>

            <%
                }
            %>

            <!-- <div class="row">
                <div class="col-md-8">
                    <div class="boxedcontent">
                        <div class="widget">
                            <div class="content">
                                <div class="row">
                                    <div class="col-md-12">
                                        <h4>Prefer a different vendor?</h4>
                                    </div>
                                </div>
                                <form method="post" id="frm_manual_vendor_recommend">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="partner_vendorName" class="form_label">Business Name</label><span class="required"> *</span>
                                                <input type="email" class="form-control" id="partner_vendorName" name="partner_vendorName" placeholder="Business Name">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="partner_vendorType" class="form_label">Vendor Type</label><span class="required"> *</span>
                                                <select class="form-control" id="partner_vendorType" name="partner_vendorType">
                                                    <option value="">Select Vendor Type</option>
                                                    <%
                                                        for(Constants.VENDOR_TYPE vendorType : Constants.VENDOR_TYPE.values()){
                                                    %>
                                                    <option value="<%=vendorType.toString()%>"><%=vendorType.getText()%></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="partner_vendorWorkPhone" class="form_label">Phone</label>
                                                <input type="tel" class="form-control" id="partner_vendorWorkPhone" name="partner_vendorWorkPhone" placeholder="Phone">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="partner_vendorWebsite" class="form_label">Website</label>
                                                <input type="email" class="form-control" id="partner_vendorWebsite" name="partner_vendorWebsite" placeholder="Website">
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                &nbsp;
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-8">
                                                <button  type="button" class="btn  btn-filled btn-sm" id="btn_create_email">
                                                    <span>Create New Email</span>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div> -->
        </div>
    </div>
</div>
</body>
<form id="frm_load_potential_event_vendors">
    <input type="hidden" id="ld_pot_event_vendor_event_id" name="event_id" value="<%=sEventId%>" />
</form>
<form id="frm_assign_event_vendors">
    <input type="hidden" id="assign_pot_event_vendor_event_id" name="event_id" value="<%=sEventId%>" />
    <input type="hidden" id="assign_partnervendor_id" name="partner_vendor_id" value="" />
    <input type="hidden" id="assign_action" name="action" value="" />
</form>
<form id="frm_recommend_event_vendors">
    <input type="hidden" id="recommend_pot_event_vendor_event_id" name="event_id" value="<%=sEventId%>" />
    <input type="hidden" id="recommend_partnervendor_id" name="partner_vendor_id" value="" />
    <input type="hidden" id="recommend_action" name="action" value="" />
</form>

<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="/js/event/event_info.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        loadEventPartnerVendors(populateEventPartnerVendors);

        $('#btn_send_note').click(function(){
            //displayMssgBoxAlert("Send Note", true);
            sendPreferredVendorNote(getResult)
        })
    });
    function sendPreferredVendorNote( callbackmethod ) {
        var actionUrl = "/proc_send_note.aeve";
        var methodType = "POST";
        var dataString = $("#frm_send_note").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function loadEventPartnerVendors(callbackmethod) {
        var actionUrl = "/proc_load_all_event_partner_vendors.aeve";
        var methodType = "POST";
        var dataString = $('#frm_load_potential_event_vendors').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateEventPartnerVendors(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfPotentialEventVendors = jsonResponseObj.num_of_potential_event_vendors;
                    if(varNumOfPotentialEventVendors!=undefined && varNumOfPotentialEventVendors>0){
                        processPotentialEventVendorsList(varNumOfPotentialEventVendors, jsonResponseObj.potential_event_vendors , jsonResponseObj.is_loggedin_user_a_client );
                    }
                    initializeTable();
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }

    }
    function processPotentialEventVendorsList(varNumOfPotentialEventVendors , everyPotEventVendorList, isLoggedInUserClient  ) {
        for(i=0;i<varNumOfPotentialEventVendors;i++){
            var varPotEventVendorBean = everyPotEventVendorList[i];
            var varEventId =  varPotEventVendorBean.event_id;
            var varEventVendorBean = varPotEventVendorBean.event_vendor_bean;

            var varEventVendorId =  varEventVendorBean.eventvendor_id;
            var varVendorId =  varEventVendorBean.vendor_id;

            var varPartnerVendorBean = varPotEventVendorBean.every_partner_vendor_bean;

            var varEventVendorName = varPartnerVendorBean.name;
            var varEventVendorEmail = varPartnerVendorBean.email;
            var varEventVendorPhone = varPartnerVendorBean.phone;
            var varEventVendorWebsite = varPartnerVendorBean.website;
            var varEventVendorType =  varPartnerVendorBean.type;

            var varIsAssignedToEvent = varPotEventVendorBean.is_assigned_to_event;
            var varIsRecommendedForEvent = varPotEventVendorBean.is_recommended_for_event;
            var varIsShortListedForEvent = varPotEventVendorBean.is_shortlisted_for_event;

            var varPartnerVendorId =  varPartnerVendorBean.partner_vendor_id;

            var varAssigedAction = 'assign';
            if(varIsAssignedToEvent) {
                varAssigedAction = 'remove_assign';
            }

            var varRecommendAction = 'recommend';
            if(varIsRecommendedForEvent) {
                varRecommendAction = 'remove_recommend';
            }
            var varTagMessage = '';
            var varTagClasses = '';
            if(varIsAssignedToEvent) {
                varTagMessage = 'assigned';
                varTagClasses = 'label label-success';
            } else if (varIsRecommendedForEvent) {
                varTagMessage = 'recommended';
                varTagClasses = 'label label-info';
            }

            var rowEveryEventVendor= $('<tr id="row_'+varPartnerVendorId+'" ></tr>');
            rowEveryEventVendor.append(
                    '<td>'+varEventVendorName +'&nbsp;&nbsp;<span id="event_vendor_status_'+varPartnerVendorId+'" class="'+varTagClasses+'">'+varTagMessage+'</span></td>'+
                            '<td>'+varEventVendorType +'</td>' +
                            '<td>'+varEventVendorWebsite +'</td>' +
                            '<td>'+varEventVendorPhone +'</td>' +
                            '<td  class="center" >'+ createButtons(varPartnerVendorId , varAssigedAction , varRecommendAction, isLoggedInUserClient) +'</td>');
            $('#every_potential_partner_vendor_rows').append(rowEveryEventVendor);

            addAssignClickEvent(varPartnerVendorId , varEventId , varAssigedAction);

            addRecommendClickEvent(varPartnerVendorId , varEventId , varRecommendAction)
        }
    }
    function createButtons( varPartnerVendorId   , varAssigedAction , varRecommendAction, isLoggedInUserClient ){
        var varButtons = '';
        if(!isLoggedInUserClient) {
            varButtons = varButtons + createAssignButton( varPartnerVendorId , varAssigedAction );
            varButtons = varButtons + '&nbsp;&nbsp;&nbsp;';
        }
        varButtons = varButtons + createRecommendButton( varPartnerVendorId , varRecommendAction );
        return varButtons;
    }
    function createAssignButton(varPartnerVendorId , varAssigedAction ){
        var varAssignButton = '';
        if(varAssigedAction  == 'assign') {
            varAssignButton = '<a id="assign_'+varPartnerVendorId+'" class="btn btn-default btn-xs"><i id="assign_icon_'+varPartnerVendorId+'" class="fa fa-check"></i> <span id="assign_text_'+varPartnerVendorId+'">Assign</span></a>';
        } else if (varAssigedAction  == 'remove_assign') {
            varAssignButton = '<a id="assign_'+varPartnerVendorId+'" class="btn btn-default btn-xs"><i id="assign_icon_'+varPartnerVendorId+'" class="fa fa-ban"></i> <span id="assign_text_'+varPartnerVendorId+'">Unassign</span></a>';
        }
        return varAssignButton;
    }
    function createRecommendButton(varPartnerVendorId , varAssigedAction ){
        var varRecommendButton = '';
        if(varAssigedAction  == 'recommend') {
            varRecommendButton = '<a id="recommend_'+varPartnerVendorId+'" class="btn btn-default btn-xs"><i id="recommend_icon_'+varPartnerVendorId+'" class="fa fa-thumbs-o-up"></i> <span id="recommend_text_'+varPartnerVendorId+'">Recommend</span></a>';
        } else if (varAssigedAction  == 'remove_recommend') {
            varRecommendButton = '<a id="recommend_'+varPartnerVendorId+'" class="btn btn-default btn-xs"><i id="recommend_icon_'+varPartnerVendorId+'" class="fa fa-thumbs-o-down"></i> <span id="recommend_text_'+varPartnerVendorId+'">Remove Recommendation</span></a>';
        }
        return varRecommendButton;
    }

    function addAssignClickEvent(varPartnerVendorId , varEventId , varAction) {
        var event_vendor_obj = {
            partner_vendor_id: varPartnerVendorId,
            event_id: varEventId,
            action: varAction,
            printObj: function () {
                return this.partner_vendor_id + ' row : ' + this.row_num;
            }
        }
        $('#assign_'+varPartnerVendorId).unbind('click');
        if( varAction == 'assign') {
            $('#assign_'+varPartnerVendorId).click({param_event_vendor_obj:event_vendor_obj},function(e){
                displayConfirmBox(
                        "Are you sure you want to assign this vendor to the event?" ,
                        "Assign Vendor To Event",
                        "Yes", "No", assignEventVendor,e.data.param_event_vendor_obj)
            });
        } else if ( varAction == 'remove_assign' ) {
            $('#assign_'+varPartnerVendorId).click({param_event_vendor_obj:event_vendor_obj},function(e){
                displayConfirmBox(
                        "Are you sure you want to remove this vendor from the event?",
                        "Remove Vendor Assignment To Event",
                        "Yes", "No", assignEventVendor,e.data.param_event_vendor_obj)
            });
        }

    }
    function assignEventVendor(varEventVendorObj) {
        $('#assign_partnervendor_id').val(varEventVendorObj.partner_vendor_id);
        $('#assign_action').val(varEventVendorObj.action);
        assignEventVendorInvoke(processAssignEventVendor);
    }
    function assignEventVendorInvoke(callbackmethod) {
        var actionUrl = "/proc_assign_eventvendor.aeve";
        var methodType = "POST";
        var dataString = $("#frm_assign_event_vendors").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function processAssignEventVendor(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varActionCompleted = jsonResponseObj.action_complete;
                    var varEventId = jsonResponseObj.event_id;
                    var varPartnerVendorId = jsonResponseObj.partnervendor_id;
                    if(varActionCompleted == 'assign') {
                        $('#event_vendor_status_'+varPartnerVendorId).removeClass("label   label-info   label-success   label-warning").addClass("label label-success").text("assigned");

                        $('#assign_text_'+varPartnerVendorId).text("Unassign");
                        $('#assign_icon_'+varPartnerVendorId).removeClass("fa-check fa-ban").addClass("fa-ban");

                        addAssignClickEvent(varPartnerVendorId ,varEventId , 'remove_assign' );

                        resetRecommendButton(varPartnerVendorId , varEventId)

                    } else if ( varActionCompleted == 'remove_assign'  ) {
                        $('#event_vendor_status_'+varPartnerVendorId).removeClass("label   label-info   label-success   label-warning").text("");

                        $('#assign_text_'+varPartnerVendorId).text("Assign");
                        $('#assign_icon_'+varPartnerVendorId).removeClass("fa-ban fa-check ").addClass("fa-check");

                        addAssignClickEvent(varPartnerVendorId ,varEventId , 'assign' );

                        resetRecommendButton(varPartnerVendorId , varEventId)

                    } else {
                        displayMssgBoxAlert("We were unable to complete your assignment request. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (processAssignEventVendor - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (processAssignEventVendor - 2)", true);
        }
    }

    function resetRecommendButton(varPartnerVendorId , varEventId) {
        $('#recommend_text_'+varPartnerVendorId).text("Recommend");
        $('#recommend_icon_'+varPartnerVendorId).removeClass("fa-thumbs-o-down fa-thumbs-o-up").addClass("fa-thumbs-o-up");
        addRecommendClickEvent(varPartnerVendorId ,varEventId , 'recommend' );
    }

    function resetAssignButton(varPartnerVendorId , varEventId) {
        $('#assign_text_'+varPartnerVendorId).text("Assign");
        $('#assign_icon_'+varPartnerVendorId).removeClass("fa-ban fa-check ").addClass("fa-check");

        addAssignClickEvent(varPartnerVendorId ,varEventId , 'assign' );
    }

    function addRecommendClickEvent(varPartnerVendorId , varEventId , varAction) {
        var event_vendor_obj = {
            partner_vendor_id: varPartnerVendorId,
            event_id: varEventId,
            action: varAction,
            printObj: function () {
                return this.partner_vendor_id + ' row : ' + this.row_num;
            }
        }
        $('#recommend_'+varPartnerVendorId).unbind('click');
        if( varAction == 'recommend') {
            $('#recommend_'+varPartnerVendorId).click({param_event_vendor_obj:event_vendor_obj},function(e){
                displayConfirmBox(
                        "Are you sure you want to recommend this vendor for this event?" ,
                        "Recommend Vendor for Event",
                        "Yes", "No", recommendEventVendor,e.data.param_event_vendor_obj)
            });
        } else if ( varAction == 'remove_recommend' ) {
            $('#recommend_'+varPartnerVendorId).click({param_event_vendor_obj:event_vendor_obj},function(e){
                displayConfirmBox(
                        "Are you sure you want to remove this recommendation from this event?",
                        "Remove Vendor Recommendation For Event",
                        "Yes", "No", recommendEventVendor,e.data.param_event_vendor_obj)
            });
        }

    }

    function recommendEventVendor(varEventVendorObj) {
        $('#recommend_partnervendor_id').val(varEventVendorObj.partner_vendor_id);
        $('#recommend_action').val(varEventVendorObj.action);
        recommendEventVendorInvoke(processRecommendEventVendor);
    }
    function recommendEventVendorInvoke(callbackmethod) {
        var actionUrl = "/proc_recommend_eventvendor.aeve";
        var methodType = "POST";
        var dataString = $("#frm_recommend_event_vendors").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function processRecommendEventVendor(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varActionCompleted = jsonResponseObj.action_complete;
                    var varEventId = jsonResponseObj.event_id;
                    var varPartnerVendorId = jsonResponseObj.partnervendor_id;
                    if(varActionCompleted == 'recommend') {
                        $('#event_vendor_status_'+varPartnerVendorId).removeClass("label   label-info   label-success   label-warning").addClass("label label-info").text("recommended");

                        $('#recommend_text_'+varPartnerVendorId).text("Remove Recommendation");
                        $('#recommend_icon_'+varPartnerVendorId).removeClass("fa-thumbs-o-up fa-thumbs-o-down").addClass("fa-thumbs-o-down");

                        addRecommendClickEvent(varPartnerVendorId ,varEventId , 'remove_recommend' );

                        resetAssignButton(varPartnerVendorId , varEventId)

                    } else if ( varActionCompleted == 'remove_recommend'  ) {
                        $('#event_vendor_status_'+varPartnerVendorId).removeClass("label   label-info   label-success   label-warning").text("");

                        $('#recommend_text_'+varPartnerVendorId).text("Recommend");
                        $('#recommend_icon_'+varPartnerVendorId).removeClass("fa-thumbs-o-down fa-thumbs-o-up ").addClass("fa-thumbs-o-up");

                        addRecommendClickEvent(varPartnerVendorId ,varEventId , 'recommend' );

                        resetAssignButton(varPartnerVendorId , varEventId)
                    } else {
                        displayMssgBoxAlert("We were unable to complete your request. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (processRecommendEventVendor - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (processRecommendEventVendor - 2)", true);
        }
    }

    function initializeTable(){

        objPotentialEventVendorTable =  $('#every_potential_partner_vendor').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });
    }

    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                }
                displayAjaxOk(varResponseObj);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>