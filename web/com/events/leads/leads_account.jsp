<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Perm" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<link href="/css/bootstrap-switch.min.css" rel="stylesheet">
<jsp:include page="/com/events/common/header_bottom.jsp"/>

<%
    String sClientId = ParseUtil.checkNull(request.getParameter("client_id"));
    String sTitle = "Add Lead";
    if(!Utility.isNullOrEmpty(sClientId)) {
        sTitle = "Edit Lead";
    }

    boolean canEditLead = false;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
            CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
            if(checkPermission!=null ) {
                canEditLead = checkPermission.can(Perm.EDIT_CLIENT);
            }
        }
    }
    String sParentCommentId = "Kensen";
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="lead_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Lead - Contact Information</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/leads/lead_tab.jsp">
                            <jsp:param name="lead_account_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <h4>Contact Information</h4>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <div class="row">
                        <div class="col-md-6">
                            <label for="clientFirstName" class="form_label">Status</label><span class="required"> *</span>
                            <input type="checkbox" checked data-size="mini" data-on-text="Lead" data-off-text="Client" class="hide-page" id="lead_status" name="lead_status">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            &nbsp;
                        </div>
                    </div>
                    <form method="post" id="frm_save_clients">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="clientFirstName" class="form_label">First Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="clientFirstName" name="clientFirstName" placeholder="First Name">
                                </div>
                                <div class="col-md-6">
                                    <label for="clientLastName" class="form_label">Last Name</label>
                                    <input type="text" class="form-control" id="clientLastName" name="clientLastName" placeholder="Last Name">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="clientEmail" class="form_label">Email</label><span class="required"> *</span>
                                    <input type="email" class="form-control" id="clientEmail" name="clientEmail" placeholder="Email">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="clientCellPhone" class="form_label">Cell Phone</label>
                                    <input type="tel" class="form-control" id="clientCellPhone" name="clientCellPhone" placeholder="Cell Phone">
                                </div>
                                <div class="col-md-6">
                                    <label for="clientWorkPhone" class="form_label">Phone</label>
                                    <input type="tel" class="form-control" id="clientWorkPhone" name="clientWorkPhone" placeholder="Phone">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="clientCompanyName" class="form_label">Company Name</label>
                                    <input type="text" class="form-control" id="clientCompanyName" name="clientCompanyName" placeholder="Company Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="checkbox">
                                        <label for="isCorporateClient" class="form_label">
                                            <input type="checkbox" id="isCorporateClient" name = "isCorporateClient">
                                            Corporate Client
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden"  id="client_id" name="client_id" value="">
                        <input type="hidden"  id="userId" name="userId" value="">
                        <input type="hidden"  id="userInfoId" name="userInfoId" value="">
                        <input type="hidden"  name="is_lead" value="true">
                    </form>
                    <%
                        if(canEditLead) {
                    %>
                        <div class="row">
                            <div class="col-md-3">
                                <button type="button" class="btn  btn-filled" id="btn_save_lead">Save Changes</button>
                            </div>
                        </div>
                    <%
                        }
                    %>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_client" >
    <input type="hidden" id="load_client_id" name="client_id" value="<%=sClientId%>"/>
    <input type="hidden" name="client_datatype" value="contact_info"/>
</form>
<form id="frm_update_lead_status" >
    <input type="hidden" name="client_id" value="<%=sClientId%>"/>
    <input type="hidden" id="is_a_lead" name="is_a_lead" value=""/>
    <input type="hidden" id="source" name="source" value="manual"/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/clients/clientcontactinfo.js"></script>
<script src="/js/bootstrap-switch.min.js"></script>
<script   type="text/javascript">
    var varClientId = '<%=sClientId%>';
    $(window).load(function() {

        $('#lead_status').bootstrapSwitch('size', 'normal');
        $('#lead_status').bootstrapSwitch('readonly', false);
        $('#lead_status').on('switchChange', function (e, data) {

            if( $('#source').val() == 'manual' ) {
                var $element = $(data.el);
                var value = data.value;

                if($element !=undefined && value!=undefined ) {
                    if(value == true) {
                        $('#is_a_lead').val( 'true' );
                    } else {
                        $('#is_a_lead').val( 'false' );
                    }
                    updateLeadStatusAction(getLeadStatusActionResult);
                }
            } else {
                $('#source').val('manual');
            }

        });


        if(varClientId!='') {
            loadClientDetail(varClientId, 'contact_info',populateClientDetail);
        }

        $('#btn_save_lead').click(function(){
            saveClient(getResult);
        });

        /*tinymce.init({
            selector: "#comment_body",
            theme: "modern",
            menubar: false,
            plugins: [
                "autolink link preview ",
                "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                "emoticons paste textcolor"
            ],
            toolbar1: "preview | undo redo |  bold italic | bullist numlist | link"
        });*/
    });

    function updateLeadStatusAction( varCallback ) {
        var actionUrl = "/proc_update_lead_status_action.aeve";
        var methodType = "POST";
        var dataString = $("#frm_update_lead_status").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,varCallback);
    }
    function getLeadStatusActionResult(  jsonResult  ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
                $('#source').val('by_js_script');
                var currentStatus = $('#lead_status').bootstrapSwitch('state');
                $('#lead_status').bootstrapSwitch('state', !currentStatus );
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varIsActionUpdated = jsonResponseObj.is_action_updated;
                    if( varIsActionUpdated ) {

                    } else {
                        var varCurrentAction = jsonResponseObj.current_action;
                        if(varCurrentAction == 'active'){

                        } else if(varCurrentAction == 'done'){

                        }
                        displayMssgBoxAlert("We were unable to process your request. Please refresh and try again later.", true);
                    }
                }
            }
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>

