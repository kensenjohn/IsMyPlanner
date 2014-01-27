<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.bean.vendors.website.ColorCSSBean" %>
<%@ page import="com.events.vendors.website.css.BuildColorCss" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<meta http-equiv="x-ua-compatible" content="IE=10">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/css/spectrum.css">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%

    String sColorBackground = ParseUtil.checkNull(request.getParameter("website_color_bkg")).replaceAll("%23","#");
    String sColorHighlighted = ParseUtil.checkNull(request.getParameter("website_color_highlighted")).replaceAll("%23","#");
    String sColorText = ParseUtil.checkNull(request.getParameter("website_color_text")).replaceAll("%23","#");
    String sColorNavBreadTabBackground = ParseUtil.checkNull(request.getParameter("website_color_nav_bread_tab_bkg")).replaceAll("%23","#");

    String sColorFilledButton = ParseUtil.checkNull(request.getParameter("website_color_filled_button")).replaceAll("%23","#");
    String sColorFilledButtonTxt = ParseUtil.checkNull(request.getParameter("website_color_filled_button_txt")).replaceAll("%23","#");
    String sColorPlainButton = ParseUtil.checkNull(request.getParameter("website_color_plain_button")).replaceAll("%23","#");
    String sColorPlainButtonTxt = ParseUtil.checkNull(request.getParameter("website_color_plain_button_txt")).replaceAll("%23","#");

    String sColorBorder = ParseUtil.checkNull(request.getParameter("website_color_border")).replaceAll("%23","#");

    ColorCSSBean colorCSSBean = new ColorCSSBean();
    colorCSSBean.setBackground( sColorBackground );
    colorCSSBean.setHighlightedTextOrLink(sColorHighlighted);
    colorCSSBean.setText(sColorText);
    colorCSSBean.setNavBreadTabBackground( sColorNavBreadTabBackground );

    colorCSSBean.setFilledButton( sColorFilledButton);
    colorCSSBean.setFilledButtonText( sColorFilledButtonTxt);
    colorCSSBean.setPlainButton(sColorPlainButton);
    colorCSSBean.setPlainButtonText(sColorPlainButtonTxt);

    colorCSSBean.setBorder( sColorBorder);

    BuildColorCss buildColorCss = new BuildColorCss();
    String sOverridingColorCss = buildColorCss.getColorCss( colorCSSBean ) ;
%>
<style type="text/css">
    <%=sOverridingColorCss%>
</style>
<body>
<div class="page_wrap">
    <div class="top_navbar_format">
        <div class="container">
            <div class="top_navbar_links">
                <ul class="nav navbar-nav navbar-right menu">
                    <li><a href="">Hi Tester</a></li>
                    <li><a href="">Default Text</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="menu_bar">
        <div class="container">
            <div class="menu_logo">
                <a href="#"><img src="/img/logo.png" alt=""></a>
            </div>
            <div class="menu_links">
                <ul class="nav navbar-nav navbar-right menu">
                    <li class="navbar-btn navbar-btn-format">
                        <button  type="button" class="btn  btn-filled" id="btn_create_event">
                            <span class="glyphicon glyphicon-plus"></span><a>  Create Event </a>
                        </button>
                    </li>
                    <li class="currently_active"><a href="#">Active</a></li>
                    <li class=""><a href="#">Inactive</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Breadcrumb area. See Border top and below.</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <ul class="tabs">
                        <li class="active"><a href="#">Active Tab</a></li>
                        <li><a href="#">Inactive 1</a></li>
                        <li><a href="#">Inactive 2</a></li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <h1>Default text color 1</h1>
                    <h2>Default text color 2</h2>
                    <h3>Default text color 3</h3>
                </div>
                <div class="col-md-6">
                    <form method="post" id="frm_website_colors">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="preview_formname" class="form_label">Name</label><span class="required"> *</span>
                                    <input type="text"  class="form-control"  name="preview_formname" id="preview_formname"  placeholder="Name" value="Preview Test">
                                </div>
                                <div class="col-md-6">
                                    <label for="preview_formage" class="form_label">Age</label><span class="required"> *</span>
                                    <input type="text"  class="form-control"  name="preview_formage" id="preview_formage"  placeholder="Your Age ">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    &nbsp;
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <button type="button" class="btn btn-default" >Default Button</button>
                                </div>
                                <div class="col-md-6">
                                    <button type="button" class="btn btn-filled">Filled Button</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_event_table" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-3" role="columnheader">Date</th>
                            <th class="sorting" role="columnheader">Name</th>
                            <th class="sorting" role="columnheader">Client</th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>

                        <tbody role="alert" id="every_event_rows">
                            <tr role="row">
                                <td>23 Oct 2015</td>
                                <td>Wedding Plans</td>
                                <td>Party Inc.</td>
                                <td><a href=""  class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil"></span> Edit</a></td>
                            </tr>
                            <tr role="row">
                                <td>23 Dec 2014</td>
                                <td>Birthday Party</td>
                                <td>Ron and Sue</td>
                                <td><a href=""  class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span> Delete</a></td>
                            </tr>
                        </tbody></table>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script type="text/javascript">
    var objEveryEventTable = '';
    $(window).load(function() {
        objEveryEventTable =  $('#every_event_table').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });
    });

</script>