<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/font-awesome.min.css" rel="stylesheet">
<link href="/css/bootstrap-switch.min.css" rel="stylesheet">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
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
                                    <input type="checkbox" checked data-size="small" data-on-text="Show" data-off-text="Hide" name="welcome_hide" id="welcome_hide">

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
                                                    <form method="post" id="frm_logo"  action="/proc_upload_image.aeve" method="POST" enctype="multipart/form-data">
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-md-5">
                                                                    <label for="welcome_banner" class="form_label">Banner</label><span class="required"> *</span>
                                                                    <input type="file" name="files[]" id="welcome_banner" class="fileinput-button btn btn-default">
                                                                </div>
                                                                <div class="col-md-2">
                                                                    <label class="form_label">&nbsp;</label>
                                                                    <div>
                                                                        <button type="button" class="btn btn-default btn-sm" id="btn_upload_logo">Upload</button>
                                                                    </div>
                                                                </div>
                                                                <div class="col-md-3">
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
                                                                <div class="col-md-3" id="welcome_banner_thumbnail">
                                                                </div>
                                                            </div>
                                                        </div>
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
                                                    <form>
                                                        <div class="form-group">
                                                            <div class="row">
                                                                <div class="col-md-6">
                                                                    <label for="welcome_banner_title" class="form_label">Title</label>
                                                                    <input type="text" name="welcome_banner_title" id="welcome_banner_title" class="form-control">
                                                                </div>
                                                                <div class="col-md-6">
                                                                    <label for="welcome_banner_tagline" class="form_label">Tag Line </label>
                                                                    <input type="text" name="welcome_banner_tagline" id="welcome_banner_tagline" class="form-control">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <button class="btn btn-filled" id="save_welcome_banner">Save</button>
                                                            </div>
                                                        </div>
                                                    </form>
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
                                        <i id="invitation_collapse_icon" class="fa fa-chevron-circle-right"></i> Invitation
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse_invitation" class="panel-collapse collapse">
                                <div class="panel-body">
                                    <h2>Invitation</h2>
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
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/upload/jquery.iframe-transport.js"></script>
<script src="/js/upload/jquery.fileupload.js"></script>
<script src="/js/collapse.js"></script>
<script src="/js/bootstrap-switch.min.js"></script>
<script type="text/javascript">
    $(window).load(function() {
        $('#welcome_hide').bootstrapSwitch('size', 'mini');
        $('#welcome_hide').bootstrapSwitch('readonly', false);

        $('#collapse_welcome').on('hide.bs.collapse', function () {
            toggleCollapseIcon('welcome_collapse_icon');
        })
        $('#collapse_welcome').on('show.bs.collapse', function () {
            toggleCollapseIcon('welcome_collapse_icon');
        })

        $('#collapse_invitation').on('hide.bs.collapse', function () {
            toggleCollapseIcon('invitation_collapse_icon');
        })
        $('#collapse_invitation').on('show.bs.collapse', function () {
            toggleCollapseIcon('invitation_collapse_icon');
        })
    });

    function toggleCollapseIcon(varIcon) {
        $('#'+varIcon).toggleClass("fa-chevron-circle-down").toggleClass("a-chevron-circle-right");
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>