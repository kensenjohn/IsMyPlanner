<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.vendors.website.AccessVendorWebsite" %>
<%@ page import="com.events.bean.vendors.website.VendorWebsiteFeatureBean" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Configuration" %>
<%@ page import="com.events.bean.clients.ClientRequestBean" %>
<%@ page import="com.events.clients.AccessClients" %>
<%@ page import="com.events.bean.clients.ClientBean" %>
<%@ page import="com.events.bean.vendors.VendorBean" %>
<%@ page import="com.events.bean.vendors.VendorRequestBean" %>
<%@ page import="com.events.vendors.AccessVendors" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.bean.users.ParentTypeBean" %>
<%@ page import="com.events.users.AccessUsers" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }
%>

<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
        <jsp:param name="disable_account_link" value="true"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="hide_menu" value="true"/>
    </jsp:include>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/website/event_website_editor_tab.jsp">
                            <jsp:param name="edit_event_website_links_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="div_unique_url">
                    <!-- template_unqiue_url -->

                </div>
            </div>
        </div>
    </div>
</div>
<form id="frm_load_event_website_link">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
</form>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script id="template_unqiue_url" type="text/x-handlebars-template">
    <div class="col-md-10" >
        <form method="post" id="frm_save_website_url">
            <div class="form-group">
                <div class="row">
                    <div class="col-md-12">
                        <label for="website_url_link" class="form_label">Link to your Website</label><span class="required"> *</span>
                        <div class="row">
                            <div class="col-md-5" style="margin-top:8px;text-align:right;padding-right: 0px;">
                                {{website_url_domain_prefix}}
                            </div>
                            <div class="col-md-7" style="padding-left: 2px;">
                                <input type="text" class="form-control" id="website_url_link" name="website_url_link" placeholder="Unique Name for site." value="{{website_url_id}}">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <input type="hidden" name="event_id" value="<%=sEventId%>"/>
        </form>
        <div class="row">
            <div class="col-md-12">
                &nbsp;
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <button id="btn_save_website_url" class="btn btn-filled">Save</button>
            </div>
        </div>
    </div>
</script>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        loadEventWebsiteSiteLink(populateWebsiteLink);
    });
    function loadEventWebsiteSiteLink(callbackmethod) {
        var actionUrl = "/proc_load_event_website_link.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_event_website_link").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateWebsiteLink(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;

                    var varEventWebsiteBean = jsonResponseObj.event_website_bean;
                    if(varEventWebsiteBean!=undefined ){

                        this.eventWebsiteUrlModel = new EventWebsiteUrlModel({
                            'bb_event_website_bean' : varEventWebsiteBean,
                            'bb_website_url_domain' : jsonResponseObj.event_website_url_domain_prefix
                        });
                        var eventWebsiteUrlView = new EventWebsiteUrlView({model:this.eventWebsiteUrlModel});
                        eventWebsiteUrlView.render();
                        $('#div_unique_url').html(eventWebsiteUrlView.el);
                        //displayMssgBoxAlert('Site Name : ' + varEventWebsiteBean.url_unique_name );
                    }
                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function saveWebsiteUrl(callbackmethod) {
        var actionUrl = "/proc_save_event_website_link.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_website_url").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                displayMssgBoxAlert('The website url was successfully saved.', false);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    var EventWebsiteUrlModel = Backbone.Model.extend({
        defaults: {
            bb_event_website_bean: undefined,
            bb_website_url_domain:''
        }
    });
    var EventWebsiteUrlView = Backbone.View.extend({
        tagName     : "div",
        className   : "row",
        initialize  : function(){
            this.varEventWebsiteBean = this.model.get('bb_event_website_bean');
            this.varUrlDomainPrefix = this.model.get('bb_website_url_domain');
        },
        events : {
            "click #btn_save_website_url" : 'saveEventWebsiteURL'
        },
        template    : Handlebars.compile( $('#template_unqiue_url').html() ),
        render      : function(){
            if(this.varUrlDomainPrefix!='' && this.varEventWebsiteBean!=undefined ) {
                var newEventWebsiteUrlModel = {
                    "website_url_id" : this.varEventWebsiteBean.url_unique_name ,
                    "website_url_domain_prefix" :this.varUrlDomainPrefix
                }
                var displayColumn =  this.template(  eval(newEventWebsiteUrlModel)  );
                $(this.el).append( displayColumn );
            }
        },
        saveEventWebsiteURL : function(event) {
            saveWebsiteUrl(getResult);
        }
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>