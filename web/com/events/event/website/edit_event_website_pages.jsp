<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/font-awesome.min.css" rel="stylesheet">
<link href="/css/bootstrap-switch.min.css" rel="stylesheet">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<%
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
                            <jsp:param name="edit_event_website_pages_active" value="active"/>
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
                <div class="col-md-12">
                    <div class="panel-group" id="collapse_website_welcome">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#collapse_website_welcome" href="#collapse_welcome">
                                        <i id="welcome_collapse_icon" class="fa fa-chevron-circle-right"></i> Welcome</a>
                                    &nbsp;&nbsp;
                                    <input type="checkbox" checked data-size="small" data-on-text="Show" data-off-text="Hide"  class="hide-page" name="welcome_hide" id="welcome_hide" param="welcome">

                                </h4>
                            </div>
                            <div id="collapse_welcome" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            &nbsp;
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-8">
                                                    <form method="post" id="frm_welcome_banner"  action="/proc_upload_event_image.aeve" method="POST" enctype="multipart/form-data">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-md-5">
                                                                    <label for="welcome_banner" class="form_label">Banner</label><span class="required"> *</span>
                                                                    <input type="file" name="files[]" id="welcome_banner" class="fileinput-button btn btn-default">
                                                                </div>
                                                                <div class="col-md-2">
                                                                    <label class="form_label">&nbsp;</label>
                                                                    <div>
                                                                        <button type="button" class="btn btn-default btn-sm" id="btn_upload_banner">Upload</button>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <label class="form_label">&nbsp;</label>
                                                                    <div id="welcome_banner_progress">
                                                                        <div class="bar" style="width: 0%;"></div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-12">
                                                                    &nbsp;
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-md-offset-1 col-md-4" id="welcome_banner_image_name">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <input type="hidden" name="event_id" value="<%=sEventId%>" />
                                                        <input type="hidden" name="page_type" value="welcome" />
                                                        <input type="hidden" name="feature_type" value="<%=Constants.EVENT_WEBSITE_PAGE_FEATURETYPE.banner_image_name%>" />
                                                    </form>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8">
                                                    &nbsp;
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-8">
                                                    <form id="frm_save_welcome">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label for="welcome_caption_title" class="form_label">Title</label>
                                                                    <input type="text" name="caption_title" id="welcome_caption_title" class="form-control">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <label for="welcome_caption_tag_line" class="form_label">Tag Line </label>
                                                                    <input type="text" name="caption_tag_line" id="welcome_caption_tag_line" class="form-control">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <input type="hidden" name="page_type" value="welcome"/>
                                                        <input type="hidden" name="event_id" value="<%=sEventId%>"/>
                                                    </form>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            <button class="btn btn-filled save-website-page" id="save_welcome" param="welcome">Save</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#collapse_website_welcome" href="#collapse_invitation">
                                        <i id="invitation_collapse_icon" class="fa fa-chevron-circle-right"></i> Invitation</a>
                                        &nbsp;&nbsp;
                                        <input type="checkbox" checked data-size="small" data-on-text="Show" data-off-text="Hide" class="hide-page" name="invitation_hide" id="invitation_hide" param="invitation">
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse_invitation" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <form id="frm_save_invitation">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_name" class="form_label">Invitation Names</label>
                                                            <input type="text" name="invite_name" id="invitation_invite_name" class="form-control" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_text" class="form_label">Invite Text </label>
                                                            <input type="text" name="invite_text" id="invitation_invite_text" class="form-control" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_date" class="form_label">Date of Event</label>
                                                            <input type="text" name="invite_date" id="invitation_invite_date" class="form-control" >
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_location_name" class="form_label">Location Name</label>
                                                            <input type="text" name="invite_location_name" id="invitation_invite_location_name" class="form-control">
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="invitation_invite_address" class="form_label">Location Address </label>
                                                            <input type="text" name="invite_address" id="invitation_invite_address" class="form-control">
                                                        </div>
                                                    </div>
                                                </div>
                                                <input type="hidden" name="page_type" value="invitation"/>
                                                <input type="hidden" name="event_id"  value="<%=sEventId%>"/>
                                            </form>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <button class="btn btn-filled save-website-page" id="save_invitation" param="invitation">Save</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#collapse_website_welcome" href="#collapse_couples">
                                        <i id="couples_collapse_icon" class="fa fa-chevron-circle-right"></i> Couples</a>
                                    &nbsp;&nbsp;
                                    <input type="checkbox" checked data-size="small" data-on-text="Show" data-off-text="Hide" class="hide-page" name="couples_hide" id="couples_hide" param="couples">
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse_couples" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-8">

                                            <div class="row">
                                                <div class="col-md-12">
                                                    <h3>The Bride</h3>
                                                </div>
                                            </div>
                                            <form method="post" id="frm_partner1_image"  action="/proc_upload_partner_image.aeve" method="POST" enctype="multipart/form-data">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <label for="partner1_image" class="form_label">Photo</label>
                                                            <input type="file" name="files[]" id="partner1_image" class="fileinput-button btn btn-default">
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form_label">&nbsp;</label>
                                                            <div>
                                                                <button type="button" class="btn btn-default btn-sm" id="btn_upload_partner1_image">Upload</button>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label class="form_label">&nbsp;</label>
                                                            <div id="partner1_image_progress">
                                                                <div class="bar" style="width: 0%;"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            &nbsp;
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-offset-1 col-md-4" id="partner1_image_name">
                                                        </div>
                                                    </div>
                                                </div>
                                                <input type="hidden" name="event_id" value="<%=sEventId%>" />
                                                <input type="hidden" name="page_type" value="couples" />
                                                <input type="hidden" name="party" value="partner1" />
                                            </form>
                                            <form id="frm_save_couples_partner1">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner1_name" class="form_label">Bride's Name</label>
                                                            <input type="text" name="partner1_name" id="couples_partner1_name" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner1_facebook" class="form_label">Facebook URL </label>
                                                            <input type="text" name="partner1_facebook" id="couples_partner1_facebook" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner1_twitter" class="form_label">Twitter URL </label>
                                                            <input type="text" name="partner1_twitter" id="couples_partner1_twitter" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner1_pinterest" class="form_label">Pinterest URL </label>
                                                            <input type="text" name="partner1_pinterest" id="couples_partner1_pinterest" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner1_description" class="form_label">Description </label>
                                                            <input type="text" name="partner1_description" id="couples_partner1_description" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <input type="hidden" name="couple_partner_num"  value="1" >
                                                    <input type="hidden" name="event_website_id"   id="couples_partner1_event_website_id" value="" >
                                                    <input type="hidden" name="event_party_id"   id="couples_partner1_event_party_id" value="" >
                                                    <input type="hidden" name="upload_id"   id="couples_partner1_upload_id" value="" >
                                                    <input type="hidden" name="page_type" value="couples" />
                                                    <input type="hidden" name="event_party_type" value="<%=Constants.EVENT_PARTY_TYPE.BRIDE.toString()%>" />

                                                </div>
                                            </form>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    &nbsp;
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-6">
                                                    <button class="btn btn-filled save-website-party" id="save_couples_partner1" param="couples_partner1">Save</button>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    &nbsp;
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <h3>The Groom</h3>
                                                </div>
                                            </div>

                                            <form method="post" id="frm_partner2_image"  action="/proc_upload_partner_image.aeve" method="POST" enctype="multipart/form-data">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <label for="partner2_image" class="form_label">Photo</label>
                                                            <input type="file" name="files[]" id="partner2_image" class="fileinput-button btn btn-default">
                                                        </div>
                                                        <div class="col-md-2">
                                                            <label class="form_label">&nbsp;</label>
                                                            <div>
                                                                <button type="button" class="btn btn-default btn-sm" id="btn_upload_partner2_image">Upload</button>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-3">
                                                            <label class="form_label">&nbsp;</label>
                                                            <div id="partner2_image_progress">
                                                                <div class="bar" style="width: 0%;"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            &nbsp;
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-offset-1 col-md-4" id="partner2_image_name">
                                                        </div>
                                                    </div>
                                                </div>
                                                <input type="hidden" name="event_id" value="<%=sEventId%>" />
                                                <input type="hidden" name="page_type" value="couples" />
                                                <input type="hidden" name="party" value="partner2" />
                                            </form>
                                            <form id="frm_save_couples_partner2">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner2_name" class="form_label">Groom's Name</label>
                                                            <input type="text" name="partner2_name" id="couples_partner2_name" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner2_facebook" class="form_label">Facebook URL </label>
                                                            <input type="text" name="partner2_facebook" id="couples_partner2_facebook" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner2_twitter" class="form_label">Twitter URL </label>
                                                            <input type="text" name="partner2_twitter" id="couples_partner2_twitter" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner2_pinterest" class="form_label">Pinterest URL </label>
                                                            <input type="text" name="partner2_pinterest" id="couples_partner2_pinterest" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-12">
                                                            <label for="couples_partner2_description" class="form_label">Description </label>
                                                            <input type="text" name="partner2_description" id="couples_partner2_description" class="form-control" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-6">
                                                            &nbsp;
                                                        </div>
                                                    </div>
                                                    <input type="hidden" name="couple_partner_num"  value="2" >
                                                    <input type="hidden" name="event_website_id"   id="couples_partner2_event_website_id" value="" >
                                                    <input type="hidden" name="event_party_id"   id="couples_partner2_event_party_id" value="" >
                                                    <input type="hidden" name="upload_id"   id="couples_partner2_upload_id" value="" >
                                                    <input type="hidden" name="page_type" value="couples" />
                                                    <input type="hidden" name="event_party_type" value="<%=Constants.EVENT_PARTY_TYPE.GROOM.toString()%>" />
                                                </div>
                                            </form>

                                            <div class="row">
                                                <div class="col-md-6">
                                                    <button class="btn btn-filled save-website-party" id="save_couples_partner2" param="couples_partner2">Save</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Bride's Maid -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#collapse_website_welcome" href="#collapse_bridesmaids">
                                        <i id="bridesmaids_collapse_icon" class="fa fa-chevron-circle-right"></i> Bridesmaids</a>
                                    &nbsp;&nbsp;
                                    <input type="checkbox" checked data-size="small" data-on-text="Show" data-off-text="Hide" class="hide-page" name="bridesmaids_hide" id="bridesmaids_hide" param="bridesmaids">
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse_bridesmaids" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <button type="button" class="btn btn-filled" id="btn_add_bridesmaid">Create New</button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-8">
                                            &nbsp;
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-8">
                                            <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_bridesmaid" >
                                                <thead>
                                                <tr role="row">
                                                    <th role="columnheader">Name</th>
                                                    <th role="columnheader"></th>
                                                </tr>
                                                </thead>

                                                <tbody role="alert" id="every_bridesmaids_rows">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Groom's Men  -->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" data-parent="#collapse_website_welcome" href="#collapse_groomsmen">
                                        <i id="groomsmen_collapse_icon" class="fa fa-chevron-circle-right"></i> Groomssmen</a>
                                    &nbsp;&nbsp;
                                    <input type="checkbox" checked data-size="small" data-on-text="Show" data-off-text="Hide" class="hide-page" name="groomsmen_hide" id="groomsmen_hide" param="groomsmen">
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse_groomsmen" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <button type="button" class="btn btn-filled" id="btn_add_groomsmen">Create New</button>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-8">
                                            &nbsp;
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-8">
                                            <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_groomsmen" >
                                                <thead>
                                                <tr role="row">
                                                    <th class="sorting col-md-3" role="columnheader">Name</th>
                                                    <th class="center" role="columnheader"></th>
                                                </tr>
                                                </thead>

                                                <tbody role="alert" id="every_groomsmen_rows">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_web_page">
    <input type="hidden"  name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_load_web_page_features">
    <input type="hidden"  name="event_id" value="<%=sEventId%>"/>
    <input type="hidden" id="load_page_type" name="page_type" value=""/>
</form>
<form id="frm_load_couples_event_party">
    <input type="hidden"  name="event_party_type[]" value="<%=Constants.EVENT_PARTY_TYPE.BRIDE%>"/>
    <input type="hidden"  name="event_party_type[]" value="<%=Constants.EVENT_PARTY_TYPE.GROOM%>"/>
    <input type="hidden"  name="page_type" value="couples"/>
    <input type="hidden"  name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_load_bridesmaids_event_party">
    <input type="hidden"  name="event_party_type[]" value="<%=Constants.EVENT_PARTY_TYPE.BRIDESMAID%>"/>
    <input type="hidden"  name="event_party_type[]" value="<%=Constants.EVENT_PARTY_TYPE.MAIDOFHONOR%>"/>
    <input type="hidden"  name="page_type" value="bridesmaids"/>
    <input type="hidden"  name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_load_groomsmen_event_party">
    <input type="hidden"  name="event_party_type[]" value="<%=Constants.EVENT_PARTY_TYPE.GROOMSMAN%>"/>
    <input type="hidden"  name="event_party_type[]" value="<%=Constants.EVENT_PARTY_TYPE.BESTMAN%>"/>
    <input type="hidden"  name="page_type" value="groomsmen"/>
    <input type="hidden"  name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_save_web_page">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
    <input type="hidden" id="save_web_page_type" name="page_type" value=""/>
    <input type="hidden" id="save_action" name="action" value=""/>
</form>
<form id="frm_delete_event_party_partner">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
    <input type="hidden" id="delete_event_party_id" name="event_party_id" value=""/>
    <input type="hidden" id="delete_page_type" name="page_type" value=""/>
</form>

<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/jquery.colorbox-min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script src="/js/collapse.js"></script>
<script src="/js/bootstrap-switch.min.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        $('.hide-page').bootstrapSwitch('size', 'mini');
        $('.hide-page').bootstrapSwitch('readonly', false);

        $('#collapse_welcome').on('hide.bs.collapse', function () {
            toggleCollapseIcon('welcome_collapse_icon');
        })
        $('#collapse_welcome').on('show.bs.collapse', function () {
            toggleCollapseIcon('welcome_collapse_icon');
            loadWebsitePageFeatures('welcome', populateWebsitePageFeatures)
        })

        $('#collapse_invitation').on('hide.bs.collapse', function () {
            toggleCollapseIcon('invitation_collapse_icon');
        })
        $('#collapse_invitation').on('show.bs.collapse', function () {
            toggleCollapseIcon('invitation_collapse_icon');
            loadWebsitePageFeatures('invitation', populateWebsitePageFeatures)
        })

        $('#collapse_couples').on('hide.bs.collapse', function () {
            toggleCollapseIcon('couples_collapse_icon');
        })
        $('#collapse_couples').on('show.bs.collapse', function () {
            toggleCollapseIcon('couples_collapse_icon');
            loadWebsitePageFeatureParty('couples', populateWebsitePageCouples)
        })

        $('#collapse_bridesmaids').on('hide.bs.collapse', function () {
            toggleCollapseIcon('bridesmaids_collapse_icon');
        })
        $('#collapse_bridesmaids').on('show.bs.collapse', function () {
            toggleCollapseIcon('bridesmaids_collapse_icon');
            loadWebsitePageFeatureParty('bridesmaids', populateWebsitePagePartyMembers)
        })

        $('#collapse_groomsmen').on('hide.bs.collapse', function () {
            toggleCollapseIcon('groomsmen_collapse_icon');
        })
        $('#collapse_groomsmen').on('show.bs.collapse', function () {
            toggleCollapseIcon('groomsmen_collapse_icon');
            loadWebsitePageFeatureParty('groomsmen', populateWebsitePagePartyMembers);
        })

        $('.save-website-page').click(function(){
            saveWebsitePageFeatureSetting(getResult , $(this).attr('param'));
        });
        $('.save-website-party').click(function(){
            saveWebsitePageFeaturePartySettings(getResult , $(this).attr('param'));
        });

        $('.hide-page').on('switchChange', function (e, data) {
            var $element = $(data.el);
            var value = data.value;

            if($element !=undefined && value!=undefined ) {
                if(value == false) {
                    $('#save_action').val( 'hide' );
                } else {
                    $('#save_action').val( 'show' );
                }

                $('#save_web_page_type').val( $element.attr('param') );
                saveWebsitePageSetting(getResult)
            } else {

            }
        });

        loadWebsitePage(populateWebsitePages);
        initializeBridesMaidTable();
        initializeGroomsMenTable();

        $('#btn_add_bridesmaid').click( function(){
            $.colorbox({
                href:'edit_event_website_party.jsp?party=bm&event_id=<%=sEventId%>',
                iframe:true,
                innerWidth: '90%',
                innerHeight: '85%',
                scrolling: true,
                onClosed : function() {
                    loadWebsitePageFeatureParty('bridesmaids', populateWebsitePagePartyMembers)
                }});
        })

        $('#btn_add_groomsmen').click( function(){
            $.colorbox({
                href:'edit_event_website_party.jsp?party=gm&event_id=<%=sEventId%>',
                iframe:true,
                innerWidth: '90%',
                innerHeight: '85%',
                scrolling: true,
                onClosed : function() {
                    loadWebsitePageFeatureParty('groomsmen', populateWebsitePagePartyMembers)
                }});
        })

    });

    function toggleCollapseIcon(varIcon) {
        $('#'+varIcon).toggleClass("fa-chevron-circle-down").toggleClass("a-chevron-circle-right");
    }
    function saveWebsitePageSetting(callbackmethod ) {
        var actionUrl = "/proc_save_event_website_page.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_web_page" ).serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function saveWebsitePageFeaturePartySettings(callbackmethod , pageType) {
        var actionUrl = "/proc_save_event_website_features_party.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_"+pageType).serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function saveWebsitePageFeatureSetting(callbackmethod , pageType) {
        var actionUrl = "/proc_save_event_website_page_features.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_"+pageType).serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadWebsitePage(callbackmethod) {
        var actionUrl = "/proc_load_website_page.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_web_page").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadWebsitePageFeatures(pageType,callbackmethod) {
        if(pageType!=undefined) {
            $('#load_page_type').val( pageType );
            var actionUrl = "/proc_load_website_page_features.aeve";
            var methodType = "POST";
            var dataString = $("#frm_load_web_page_features").serialize();
            makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
        } else {
            displayMssgBoxAlert('Oops!! We were unable to load any information for this page. Please try again later', true);
        }
    }
    function loadWebsitePageFeatureParty(pageType,callbackmethod) {
        if(pageType!=undefined) {
            $('#load_page_type').val( pageType );
            var actionUrl = "/proc_load_event_website_features_party.aeve";
            var methodType = "POST";
            var dataString = $("#frm_load_"+pageType+"_event_party").serialize();
            makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
        } else {
            displayMssgBoxAlert('Oops!! We were unable to load any information for this page. Please try again later', true);
        }
    }
    var WebsitePageModel = Backbone.Model.extend({});
    var WebsitePageView = Backbone.View.extend({
        initialize: function(){
            this.varArrayOfEventWebsitePages = this.model.get('bb_arr_event_website_page');
            this.varEventWebsite = this.model.get('bb_event_website');
        },
        render:function(){
            for (var key in this.varArrayOfEventWebsitePages) {
                $('#'+key+'_hide').bootstrapSwitch('state', this.varArrayOfEventWebsitePages[key].is_show );
            }
            $('#couples_partner1_event_website_id').val( this.varEventWebsite.event_website_id);
            $('#couples_partner2_event_website_id').val( this.varEventWebsite.event_website_id);
        }
    } );

    var WebsitePageFeaturesModel = Backbone.Model.extend({});
    var WebsitePageFeaturesView = Backbone.View.extend({
        initialize: function(){
            this.varPage = this.model.get('bb_event_website_page');
            this.varPageType = this.model.get('bb_page_type');
            this.varFeatures = this.model.get('bb_event_website_page_features');
            this.varImageHost = this.model.get('bb_image_host');
            this.varImageFolderLocation = this.model.get('bb_image_folder_location');

        },
        render:function(){
            for (var key in this.varFeatures) {
                var testKey = key.split('_image_name');
                if(testKey == key) {
                    $('#'+this.varPageType+'_'+key).val( this.varFeatures[key] );
                } else {
                    //welcome_banner_image_name
                    if( this.varFeatures[key]!=undefined &&  this.varFeatures[key]!='') {
                        var imagePath = this.varImageHost + '/' + this.varImageFolderLocation + '/' +  this.varFeatures[key];
                        createImage(imagePath, this.varPageType+'_'+key);
                        enableImagePreview(imagePath,this.varPageType+'_'+key);

                    }
                }

            }
        }
    });

    var WebsitePageCouplesModel = Backbone.Model.extend({});
    var WebsitePageCouplesView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfEventParty = this.model.get('bb_num_of_event_party');
            this.varEventParty = this.model.get('bb_event_party');
            this.varEventPartyPage = this.model.get('bb_event_party_page');
            this.varImageHost = this.model.get('bb_image_host');
            this.varImageFolderLocation = this.model.get('bb_image_folder_location');


        },
        render:function(){
            if(this.varNumOfEventParty>0){
                for (var i = 0;i < this.varNumOfEventParty;i++) {
                    var varCoupleEventParty = this.varEventParty[i];
                    var varPartnerPos = 0;
                    if(varCoupleEventParty.event_party_type == '<%=Constants.EVENT_PARTY_TYPE.BRIDE%>') {
                        varPartnerPos = 1;
                    } else  if(varCoupleEventParty.event_party_type == '<%=Constants.EVENT_PARTY_TYPE.GROOM%>') {
                        varPartnerPos = 2;
                    }

                    $('#couples_partner'+varPartnerPos+'_name').val( varCoupleEventParty.name );
                    $('#couples_partner'+varPartnerPos+'_description').val( varCoupleEventParty.description );
                    $('#couples_partner'+varPartnerPos+'_upload_id').val( varCoupleEventParty.upload_id );

                    var numOfSocialMedia = varCoupleEventParty.num_of_social_media;
                    var varSocialMediaList = varCoupleEventParty.social_media_bean;
                    if(numOfSocialMedia>0 && varSocialMediaList!=undefined){
                        for(var j = 0;j<numOfSocialMedia; j++){
                            var varSocialMedia = varSocialMediaList[j];
                            $('#couples_partner'+varPartnerPos+'_'+varSocialMedia.social_media_name.toLowerCase() ).val( varSocialMedia.url );
                        }
                    }

                    var varUploadedImage = varCoupleEventParty.image_uploaded;


                    var imagePath = this.varImageHost+'/'+this.varImageFolderLocation+'/'+varUploadedImage.filename;
                    createImage(imagePath, 'partner'+varPartnerPos+'_image_name');

                    enableImagePreview(imagePath,'partner'+varPartnerPos+'_image_name');
                }
            }
        }
    });

    var WebsitePagePartyPartnerModel = Backbone.Model.extend({});
    var WebsitePagePartyPartnerView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfEventParty = this.model.get('bb_num_of_event_party');
            this.varEventParty = this.model.get('bb_event_party');
            this.varEventPartyPage = this.model.get('bb_event_party_page');
            this.varImageHost = this.model.get('bb_image_host');
            this.varImageFolderLocation = this.model.get('bb_image_folder_location');
            this.varPageType = this.model.get('bb_page_type');



        },
        render:function(){
            if(this.varNumOfEventParty>0){

                var oTable = getTable(this.varPageType);

                if(oTable!='' && oTable!=undefined){
                    oTable.fnClearTable();
                    for (var i = 0;i < this.varNumOfEventParty;i++) {
                        var vaEventPartyPartner = this.varEventParty[i];
                        var varEventPartyId = vaEventPartyPartner.event_party_id;
                        var varEventPartyPartnerName = vaEventPartyPartner.name;

                        var ai = oTable.fnAddData( [varEventPartyPartnerName, createPartyPartnerButtons(varEventPartyId) ] );
                        var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
                        $(nRow).attr('id','row_'+varEventPartyId);


                        addPartyPartnerEditClickEvent(varEventPartyId, this.varPageType, i);
                        addPartyPartnerDeleteClickEvent(varEventPartyId,varEventPartyPartnerName,this.varPageType, i);
                    }
                }

            }
        }
    });
    function getTable(varPageType){
        var oTable = '';
        if(varPageType == 'bridesmaids') {
            oTable = objEveryBridesMaidTable;
        } else if(varPageType == 'groomsmen') {
            oTable = objEveryGroomsMenTable;
        }
        return oTable;
    }
    function addPartyPartnerEditClickEvent(varEventPartyId , varPageType, varRowNum) {
        var varParty = '';
        if(varPageType == 'bridesmaids') {
            varParty = 'bm';
        } else if(varPageType == 'groomsmen') {
            varParty = 'gm';
        }
        $('#edit_'+varEventPartyId).click( function(){
            $.colorbox({
                href:'edit_event_website_party.jsp?party='+varParty+'&event_id=<%=sEventId%>&event_party_id='+varEventPartyId,
                iframe:true,
                innerWidth: '90%',
                innerHeight: '85%',
                scrolling: true,
                onClosed : function() {
                    loadWebsitePageFeatureParty(varPageType, populateWebsitePagePartyMembers)
                }});
        });

    }
    function addPartyPartnerDeleteClickEvent(varEventPartyId ,varEventPartyName , varPageType, varRowNum) {
        var party_partner_obj = {
            event_party_id: varEventPartyId,
            event_party_name:varEventPartyName,
            page_type:varPageType,
            row_num: varRowNum,
            printObj: function () {
                return this.event_party_id + ' row : ' + this.row_num;
            }
        }

        $('#del_'+varEventPartyId).click({param_party_partner_obj:party_partner_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to remove - " + e.data.param_party_partner_obj.event_party_name ,
                    "Delete",
                    "Yes", "No", deleteEventPartyPartner,e.data.param_party_partner_obj);
        });
    }
    function deleteEventPartyPartner( varEventPartyObj ){
        $('#delete_event_party_id').val(varEventPartyObj.event_party_id);
        $('#delete_page_type').val(varEventPartyObj.page_type);

        var actionUrl = "/proc_delete_event_party.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_event_party_partner").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processEventPartyPartnerDeletion);
    }
    function processEventPartyPartnerDeletion(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    var varIsRoleDeleted = jsonResponseObj.is_deleted;

                    if(varIsRoleDeleted){

                        $('#delete_event_party_id').val('');

                        var varPageType = jsonResponseObj.page_type;
                        var varEventPartyId = jsonResponseObj.deleted_event_party_id;
                        var oTable = getTable(varPageType);
                        if(oTable!='' && oTable!=undefined) {
                            oTable.fnDeleteRow((oTable.$('#row_'+varEventPartyId))[0] );
                        }

                    } else {
                        displayMssgBoxAlert("The role was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteRole - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteRole - 2)", true);
        }
    }

    function createPartyPartnerButtons( varEventPartyId ){
        var varButtons = '';
            varButtons = varButtons + createPartyPartnerEditButton( varEventPartyId );
            varButtons = varButtons + '&nbsp;&nbsp;&nbsp;';
            varButtons = varButtons + createPartyPartnerDeleteButton( varEventPartyId );
        return varButtons;
    }
    function createPartyPartnerEditButton(varEventPartyId){
        return '<a id="edit_'+varEventPartyId+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil"></span> Edit</a>';
    }
    function createPartyPartnerDeleteButton(varEventPartyId){
        return '<a id="del_'+varEventPartyId+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span> Delete</a>';
    }

    function generateWebsitePages( varJsonResponse ) {
        this.websitePageModel = new WebsitePageModel({
            'bb_arr_event_website_page' : varJsonResponse.event_website_pages,
            'bb_event_website' : varJsonResponse.event_website

        });

        var webpagesView = new WebsitePageView({model:this.websitePageModel});
        webpagesView.render();
    }
    function generateWebsitePageFeatures( varJsonResponse ) {
        this.websitePageFeaturesModel = new WebsitePageFeaturesModel({
            'bb_event_website_page' : varJsonResponse.event_website_page,
            'bb_page_type' : varJsonResponse.page_type,
            'bb_event_website_page_features' : varJsonResponse.event_website_page_feature ,
            'bb_image_host' : varJsonResponse.image_host,
            'bb_image_folder_location' : varJsonResponse.image_folder_location ,
            'bb_event_party' : varJsonResponse.event_party
        });
        var webpageFeaturesView = new WebsitePageFeaturesView({model:this.websitePageFeaturesModel});
        webpageFeaturesView.render();
    }
    function generateWebsiteCouples( varJsonResponse ) {
        this.websitePageCouplesModel = new WebsitePageCouplesModel({

            'bb_num_of_event_party' : varJsonResponse.num_of_event_party,
            'bb_event_party' : varJsonResponse.event_party,
            'bb_event_party_page' : varJsonResponse.event_website_page,
            'bb_image_host' : varJsonResponse.image_host ,
            'bb_image_folder_location' : varJsonResponse.image_folder_location
        });
        var websitePageCouplesView = new WebsitePageCouplesView({model:this.websitePageCouplesModel});
        websitePageCouplesView.render();
    }
    function generateWebsitePartyPartner( varJsonResponse ) {
        this.websitePagePartyPartnerModel = new WebsitePagePartyPartnerModel({

            'bb_num_of_event_party' : varJsonResponse.num_of_event_party,
            'bb_event_party' : varJsonResponse.event_party,
            'bb_event_party_page' : varJsonResponse.event_website_page,
            'bb_image_host' : varJsonResponse.image_host ,
            'bb_image_folder_location' : varJsonResponse.image_folder_location ,
            'bb_page_type' : varJsonResponse.page_type
        });
        var websitePagePartyPartnerView = new WebsitePagePartyPartnerView({model:this.websitePagePartyPartnerModel});
        websitePagePartyPartnerView.render();
    }

    function populateWebsitePages(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        generateWebsitePages( jsonResponseObj );
                    }

                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }
    function populateWebsitePageFeatures(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        generateWebsitePageFeatures( jsonResponseObj );
                    }

                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function populateWebsitePageCouples(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        generateWebsiteCouples( jsonResponseObj );
                    }

                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (populateWPC 1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (populateWPC 3)', true);
        }
    }

    function populateWebsitePagePartyMembers(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined) {
                        generateWebsitePartyPartner( jsonResponseObj );
                    }

                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (populateWPC 1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (populateWPC 3)', true);
        }
    }

    $(function () {
        $('#frm_welcome_banner').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                data.context = $('#btn_upload_banner').click(function () {
                    data.submit();
                });
            },
            done: function (e, data) {
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];

                    if(varDataResult.success) {
                        displayMssgBoxAlert("The banner was successfully uploaded", false);
                        $('#banner_imagename').val( varDataResult.name );
                        $('#banner_imagehost').val( varDataResult.imagehost);
                        $('#banner_foldername').val(varDataResult.foldername);
                        var imagePath = varDataResult.imagehost+'/'+varDataResult.foldername+'/'+varDataResult.name;

                        createImage(imagePath, 'welcome_banner_image_name');

                        enableImagePreview(imagePath,'welcome_banner_image_name');
                    } else {
                        displayMssgBoxAlert("Oops!! We were unable to upload the banner  . Please try again later.", true);
                    }
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#welcome_banner_progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });
    $(function () {
        $('#frm_partner1_image').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                data.context = $('#btn_upload_partner1_image').click(function () {
                    data.submit();
                });
            },
            done: function (e, data) {
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];

                    if(varDataResult.success) {
                        displayMssgBoxAlert("The image was successfully uploaded", false);
                        var imagePath = varDataResult.imagehost+'/'+varDataResult.foldername+'/'+varDataResult.name;
                        //console.log(varDataResult.upload_image.upload_id);
                        $('#couples_partner1_upload_id').val(varDataResult.upload_image.upload_id);
                        createImage(imagePath, 'partner1_image_name');

                        enableImagePreview(imagePath,'partner1_image_name');
                    } else {
                        displayMssgBoxAlert("Oops!! We were unable to upload the banner  . Please try again later.", true);
                    }
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#partner1_image_progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });
    $(function () {
        $('#frm_partner2_image').fileupload({
            dataType: 'json',
            replaceFileInput: false,
            add: function (e, data) {
                data.context = $('#btn_upload_partner2_image').click(function () {
                    data.submit();
                });
            },
            done: function (e, data) {
                if( data.result != undefined ) {
                    var varDataResult = data.result[0];

                    if(varDataResult.success) {
                        displayMssgBoxAlert("The image was successfully uploaded", false);
                        //$('#banner_imagename').val( varDataResult.name );
                        //$('#banner_imagehost').val( varDataResult.imagehost);
                        //$('#banner_foldername').val(varDataResult.foldername);
                        //couples_partner1_upload_id
                        var imagePath = varDataResult.imagehost+'/'+varDataResult.foldername+'/'+varDataResult.name;
                        //console.log(varDataResult.upload_image.upload_id);
                        $('#couples_partner2_upload_id').val(varDataResult.upload_image.upload_id);
                        createImage(imagePath, 'partner2_image_name');

                        enableImagePreview(imagePath,'partner2_image_name');
                    } else {
                        displayMssgBoxAlert("Oops!! We were unable to upload the image. Please try again later.", true);
                    }
                }
            },
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#partner2_image_progress .bar').css(
                        'width',
                        progress + '%'
                );
            }
        });
    });
    function createImage(imagePath, image_div){
        var varImg = $('<img>');
        varImg.addClass('img-thumbnail');
        varImg.attr('src',imagePath );

        $('#'+image_div).empty();
        $('#'+image_div).append(varImg);
    }

    function enableImagePreview( varImagePath ,varImgDivId ) {
        $( '#btn_logo_preview').unbind('click');
        if(varImagePath!='' && varImgDivId!='') {

            $( '#'+varImgDivId).click( function(){
                $.colorbox({href:varImagePath});
            });
        } else {
            $( '#btn_logo_preview').click( function(){
                displayMssgBoxAlert('Oops!! We were unable to find the logo.', true)
            });
        }

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

    function initializeBridesMaidTable(){

        objEveryBridesMaidTable =  $('#every_bridesmaid').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,
            "aoColumns": [
                {"bSortable": true,"sClass":"col-md-5"},
                { "bSortable": false,"sClass": "center" }
            ]
        });
    }

    function initializeGroomsMenTable(){

        objEveryGroomsMenTable =  $('#every_groomsmen').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "bFilter": false,
            "aoColumns":  [
                {"bSortable": true,"sClass":"col-md-5"},
                { "bSortable": false,"sClass": "center" }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>