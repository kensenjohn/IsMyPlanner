<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/bootstrap-switch.min.css" rel="stylesheet">
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
                <div class="col-md-8" id="enable_access">
                    &nbsp;
                </div>
            </div>

            <form method="post" id="frm_save_website_access">
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-8">
                            <label for="client_role" class="form_label">Role (Permissions)</label><span class="required"> *</span>
                            <select class="form-control" id="client_role" name="client_role">
                                <option value="">Select A Role</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-md-8">
                            <label for="access_to_website" class="form_label">Website and Tools Enabled</label><span class="required"> *</span><br>
                            <input type="checkbox" checked data-size="small" data-on-text="Yes" data-off-text="No" class="hide-page" id="access_to_website" param="access_to_website">
                        </div>
                    </div>
                </div>
                <input type="hidden" name="client_id" value="<%=sClientId%>"/>
            </form>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <button type="button" class="btn  btn-filled" id="btn_save_website_access">Save</button>
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
<script src="/js/bootstrap-switch.min.js"></script>
<script   type="text/javascript">
    var varClientId = '<%=sClientId%>';
    $(window).load(function() {
        $('.hide-page').bootstrapSwitch('size', 'small');
        $('.hide-page').bootstrapSwitch('readonly', false);

        if(varClientId !='') {  // client was selected

            loadClientDetail(varClientId, 'event_info' , populateClientMinimum);
            loadwebsiteEnableStatus(varClientId, 'event_info' , populateParentSiteEnabled);

            $('#btn_save_website_access').click(function(){
                saveAccess();
            })
        } else {
            this.noClientSelectedModel = new NoClientSelectedModel({});
            var noClientSelectedView = new NoClientSelectedView({model:this.noClientSelectedModel});
            noClientSelectedView.render();
            $("#enable_access").append(noClientSelectedView.el);
            $('#btn_save_website_access').hide();
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

            var isWebsiteAccessAllowed = false;
            if( this.varIsStatusExists == true ) {
                isWebsiteAccessAllowed = this.varParentSiteEnabledStatus.is_allowed_access
            }
            $('#access_to_website').bootstrapSwitch('state', isWebsiteAccessAllowed );
        }
    })

    function saveAccess() {
        var actionUrl = "/proc_save_website_enable_status.aeve";
        var methodType = "POST";
        var dataString = $('#frm_save_website_access').serialize();
        dataString = dataString + '&access_to_website=' + $('#access_to_website').bootstrapSwitch('state');
        makeAjaxCall(actionUrl,dataString,methodType,getResult);
    }
    function getResult(jsonResult){

        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {

                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    displayMssgBoxAlert("Client Role and their website access has been successfully updated.", false);
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