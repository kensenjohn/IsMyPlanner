<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sClientId = ParseUtil.checkNull(request.getParameter("client_id"));
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="client_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Client <span id="client_name_title"></span> Website Access</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/clients/client_tab.jsp">
                            <jsp:param name="client_website_access_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
            <div class="row" id="enable_access" style="display:none;">
                <div class="col-md-12">
                    <button type="button" class="btn  btn-filled" id="btn_enable_access">Enable Access To Website And Tools</button> &nbsp;&nbsp;
                    <span>You client will be emailed a link to access the website.</span>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
            <div class="row" id="disable_access"  style="display:none;">
                <div class="col-md-3">
                    <button type="button" class="btn  btn-filled" id="btn_disable_access"><i class="fa fa-ban red"></i> Disable Access To Website</button>
                </div>
                <div class="col-md-3">
                    <button type="button" class="btn" id="btn_reset_password"><i class="fa fa-refresh"></i> Reset Password</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_parent_site_enabled_status">
    <input type="hidden" name="client_id" value="<%=sClientId%>"/>
</form>
<form id="frm_save_parent_site_enabled_status">
    <input type="hidden" name="client_id" value="<%=sClientId%>"/>
</form>
<script  id="template_no_client_selected" type="text/x-handlebars-template">
    <h3>Please create or select a client. We were unable to process your request at this time.</h3>
</script>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="/js/clients/clientcontactinfo.js"></script>
<script   type="text/javascript">
    var varClientId = '<%=sClientId%>';
    $(window).load(function() {
        if(varClientId !='') {
            loadClientDetail(varClientId, 'event_info' , populateClientMinimum);
            loadwebsiteEnableStatus(varClientId, 'event_info' , populateParentSiteEnabled);
        } else {

            $("#enable_access").empty();
            $("#disable_access").empty();
            $("#enable_access").show();
            this.noClientSelectedModel = new NoClientSelectedModel({});
            var noClientSelectedView = new NoClientSelectedView({model:this.noClientSelectedModel});
            noClientSelectedView.render();
            $("#enable_access").append(noClientSelectedView.el);
            displayMssgBoxAlert("We were unable to process your request. Please try again later (loadClientEvent - 1)", true);
        }
    });

    function loadwebsiteEnableStatus(varClientId, varClientDataType , callbackmethod) {
        var actionUrl = "/proc_load_website_enable_status.aeve";
        var methodType = "POST";
        var dataString = $('#frm_load_parent_site_enabled_status').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateParentSiteEnabled(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    refreshView(jsonResponseObj)
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateClientDetail - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateClientDetail - 2)", true);
        }
    }

    function refreshView(jsonResponseObj){
        var varIsStatusExists = jsonResponseObj.is_status_exists;
        var varParentSiteEnabled = '';
        if(varIsStatusExists == true){
            varParentSiteEnabled = jsonResponseObj.parent_site_enabled_status;
        }


        this.clientsParentSiteEnableStatusModel = new ClientsParentSiteEnableStatusModel({
            'bb_is_status_exists' : varIsStatusExists,
            'bb_parent_site_enabled_status' : varParentSiteEnabled
        });
        var clientsParentSiteEnableStatusView = new ClientsParentSiteEnableStatusView({model:this.clientsParentSiteEnableStatusModel});
        clientsParentSiteEnableStatusView.render();

    }
    var NoClientSelectedModel = Backbone.Model.extend({ });
    var NoClientSelectedView = Backbone.View.extend({
        template : Handlebars.compile( $('#template_no_client_selected').html() ),
        render : function() {
            $(this.el).append( this.template() );
        }
    });
    var ClientsParentSiteEnableStatusModel = Backbone.Model.extend({
        defaults: {
            bb_is_status_exists: false ,
            bb_parent_site_enabled_status: undefined
        }
    });
    var ClientsParentSiteEnableStatusView = Backbone.View.extend({
        initialize: function(){
            this.varIsStatusExists = this.model.get('bb_is_status_exists');
            this.varParentSiteEnabledStatus = this.model.get('bb_parent_site_enabled_status');
        },
        render : function() {
            var client_obj = {
                'client_id' : varClientId
            }
            console.log('Cleint obj : ' + client_obj.client_id);
            console.log('Is Status Exist : ' + this.varIsStatusExists );
            if(!this.varIsStatusExists || (this.varIsStatusExists && !this.varParentSiteEnabledStatus.is_allowed_access) ) {

                var confirm_message_obj = {
                    'message'   : "Are you sure you enable access to the website for this client? ",
                    'header'    : "Enable Access to Website"
                }

                $('#enable_access').show();
                $('#disable_access').hide();
                this.bindClick('btn_enable_access',client_obj,enableAccess, confirm_message_obj);
                this.unBindClick('btn_disable_access');
                this.unBindClick('btn_reset_password');
            } else {
                $('#disable_access').show();
                $('#enable_access').hide();

                var disable_access_message_obj = {
                    'message'   : "Are you sure you enable access to the website for this client? ",
                    'header'    : "Enable Access to Website"
                }
                var reset_password_message_obj = {
                    'message'   : "Are you sure you enable access to the website for this client? ",
                    'header'    : "Enable Access to Website"
                }

                this.bindClick('btn_disable_access',client_obj,disableAccess, disable_access_message_obj);
                this.bindClick('btn_password_reset',client_obj,resetPassword, reset_password_message_obj);
                this.unBindClick('btn_enable_access');
            }
        },
        bindClick : function(varButtonId, varClientObj, varActionMethod, varConfirmMessage) {

            $('#'+varButtonId).click(function(){
                displayConfirmBox(
                        varConfirmMessage.message,
                        varConfirmMessage.header,
                        "Yes", "No", varActionMethod ,varClientObj);
            });
        },
        unBindClick : function(varButtonId){
            $('#'+varButtonId).unbind('click');
        }
    })

    function enableAccess(varClientObj) {
        console.log('Simple Enable access to website for client : ' + varClientObj.client_id);

        var actionUrl = "/proc_save_website_enable_status.aeve";
        var methodType = "POST";
        var dataString = $('#frm_save_parent_site_enabled_status').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,getResult);
    }
    function disableAccess(varClientObj) {
        console.log('Simple Enable access to website for client : ' + varClientObj.client_id);

        var actionUrl = "/proc_save_website_enable_status.aeve";
        var methodType = "POST";
        var dataString = $('#frm_save_parent_site_enabled_status').serialize();
        makeAjaxCall(actionUrl,dataString,methodType,getResult);
    }
    function resetPassword(varClientObj) {
        console.log('Simple Enable access to website for client : ' + varClientObj.client_id);
    }
    function getResult(jsonResult){

        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {

                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    refreshView(jsonResponseObj)
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteClient - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteClient - 2)", true);
        }
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>