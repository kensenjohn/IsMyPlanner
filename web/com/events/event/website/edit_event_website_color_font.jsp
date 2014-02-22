<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link href="/css/font-awesome.min.css" rel="stylesheet">
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
                            <jsp:param name="edit_event_website_colorfont_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <h3>Colors</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="theme_colors">

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
            <div class="row">
                <div class="col-md-12">
                    <h3>Fonts</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12" id="theme_fonts">

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
        </div>
    </div>
</div>
</body>
<form id="frm_load_theme_color_font">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_save_selection">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
    <input type="hidden" name="type"  id="save_sel_type"  value=""/>
    <input type="hidden" name="id"  id="save_sel_id" value=""/>
    <input type="hidden" name="event_website_id" id="save_sel_event_website_id" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script id="template_font_name" type="text/x-handlebars-template">

    <div class="row">

        <div class="col-md-12">

            <div class="row">

                <div class="col-md-1" style="text-align:center;">
                    <input type="radio" id="{{website_font_id}}" name="fonts" {{#if is_font_selected}} checked {{/if}}>
                </div>

                <div class="col-md-9"><img class="img-thumbnail" src="/com/events/event/website/static_templates/{{name}}/img/{{font_name}}.png">
                </div>

                <div class="col-md-2"><h4>{{font_name}}</h4>
                </div>
            </div>
        </div>
    </div>
</script>
<script id="template_colors" type="text/x-handlebars-template">

    <div class="col-md-1">
        <div class="row" style="text-align:center;">
            <div class="col-md-12">
                <img class="img-thumbnail" src="/com/events/event/website/static_templates/{{name}}/img/{{color_swatch_name}}">
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;
            </div>
        </div>
        <div class="row" style="text-align:center;">
            <div class="col-md-12">
                <input type="radio" id="{{website_color_id}}" name="colors" {{#if is_color_selected}} checked {{/if}}>
            </div>
        </div>
    </div>

</script>

<script id="template_blank_row" type="text/x-handlebars-template">
        <div class="col-md-12">&nbsp;</div>
</script>

<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script type="text/javascript">

    var EventWebsiteModel = Backbone.Model.extend({
        defaults: {
            bb_event_website: undefined
        }
    });
    var ThemeColorsModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_colors: 0 ,
            bb_colors: undefined
        }
    });
    var ThemeFontsModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_fonts: 0 ,
            bb_fonts: undefined
        }
    });
    var ThemeColorsView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfColors = this.model.get('bb_num_of_colors');
            this.varColors = this.model.get('bb_colors');
            this.varEventWebsite = this.model.get('bb_event_website');
            this.varTheme = this.model.get('bb_theme');
        },
        events : {
            "click input[name='colors']" : 'assignEventHandling'
        },
        template : Handlebars.compile( $('#template_colors').html() ),
        render:function(){

            var iNumOfCols = 0;
            var websiteColorRow = '';
            for(i = 0; i <this.varNumOfColors; i++) {
                if(iNumOfCols == 0 ) {
                    websiteColorRow = $('<div>').addClass('row');
                    $(this.el).append(websiteColorRow);
                }

                var isColorSelected = false;
                if(this.varColors[i].website_color_id == this.varEventWebsite.website_color_id)  {
                    isColorSelected = true;
                }

                var newColorModel = {
                    "name" : this.varTheme.name ,
                    "website_color_id" :this.varColors[i].website_color_id,
                    "color_swatch_name" : this.varColors[i].color_swatch_name,
                    "is_color_selected" : isColorSelected
                }

                var colorColumn =  this.template(  eval(newColorModel)  );
                websiteColorRow.append(colorColumn);

                iNumOfCols++;
                if(iNumOfCols == 8) {
                    iNumOfCols = 0;
                    $(this.el).append( generateBlankRow() );
                    $(this.el).append( generateBlankRow() );
                }
            }
        },
        assignEventHandling : function(event) {
            $('#save_sel_event_website_id').val(this.varEventWebsite.event_website_id);
            $('#save_sel_type').val( '<%=Constants.EVENT_WEBSITE_PARAMS.color%>' );
            $('#save_sel_id').val( $(event.target).attr('id'));
            saveColorFontSelection(getResult)
        }

     });
    var ThemeFontsView = Backbone.View.extend({
        tagName: "div",
        className: "row",
        initialize: function(){
            this.varNumOfFonts = this.model.get('bb_num_of_fonts');
            this.varFonts = this.model.get('bb_fonts');
            this.varEventWebsite = this.model.get('bb_event_website');
            this.varTheme = this.model.get('bb_theme');
            console.log( $('#template_font_name').html() );
        },
        events : {
            "click input[name='fonts']" : 'assignEventHandling'
        },
        template : Handlebars.compile( $('#template_font_name').html() ),
        render:function(){
            for(i = 0; i <this.varNumOfFonts; i++) {

                var isFontSelected = false;
                if(this.varFonts[i].website_font_id == this.varEventWebsite.website_font_id)  {
                    isFontSelected = true;
                }
                var newModel = {
                    "name" : this.varTheme.name ,
                    "website_font_id" :this.varFonts[i].website_font_id,
                    "font_name" : this.varFonts[i].font_name,
                    "is_font_selected" : isFontSelected
                }

                var fontColumn = this.template(  eval(newModel)  );

                $(this.el).append( fontColumn );
                $(this.el).append( generateBlankRow( ) );
                $(this.el).append( generateBlankRow() );
            }
        },
        assignEventHandling : function(event) {
            $('#save_sel_event_website_id').val(this.varEventWebsite.event_website_id);
            $('#save_sel_type').val( '<%=Constants.EVENT_WEBSITE_PARAMS.font%>' );
            $('#save_sel_id').val( $(event.target).attr('id'));
            saveColorFontSelection(getResult)
        }
    });

    var BlankRow = Backbone.View.extend({
        tagName: "div",
        className: "row",
        template: Handlebars.compile( $('#template_blank_row').html() ),
        render:function(){
            var blankRow = this.template( );
            $(this.el).append( blankRow );
        }
    });

    $(window).load(function() {
        loadThemeColorsAndFonts(populateThemeColorsAndFonts);
    });
    function loadThemeColorsAndFonts(callbackmethod) {
        var actionUrl = "/proc_load_theme_colors_and_fonts.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_theme_color_font").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateThemeColorsAndFonts(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varEventWebsite = jsonResponseObj.event_website;
                    var numOfColors = jsonResponseObj.num_of_colors;
                    var numOfFonts = jsonResponseObj.num_of_fonts;
                    var varTheme = jsonResponseObj.theme;
                    var varColors = jsonResponseObj.colors;
                    var varFonts = jsonResponseObj.fonts;

                    generateColorsThumbnailModel(varColors,numOfColors,varTheme, varEventWebsite );
                    var themeColorsView = generateColorsThumbnail();
                    $('#theme_colors').html( themeColorsView.el );

                    generateFontsThumbnailModel(varFonts,numOfFonts,varTheme, varEventWebsite );
                    var themeFontsView = generateFontsThumbnail();
                    $('#theme_fonts').html( themeFontsView.el );

                }
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }

    function saveColorFontSelection(callbackmethod) {
        var actionUrl = "/proc_save_event_website_color_and_font.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_selection").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                //displayMssgBoxAlert('The theme was successfully selected.', false);
            } else {
                displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (1)', true);
            }
        } else {
            displayMssgBoxAlert('Oops!! We were unable to process your request. Please try again later. (3)', true);
        }
    }



    function generateColorsThumbnailModel( varColors , numOfColors , varTheme,  varEventWebsite ) {
        this.themeColorsModel = new ThemeColorsModel({
            'bb_colors' : varColors,
            'bb_num_of_colors' : numOfColors,
            'bb_theme' : varTheme,
            'bb_event_website' : varEventWebsite
        });
    }
    function generateFontsThumbnailModel( varFonts , numOfFonts , varTheme,  varEventWebsite ) {
        this.themeFontsModel = new ThemeFontsModel({
            'bb_fonts' : varFonts,
            'bb_num_of_fonts' : numOfFonts,
            'bb_theme' : varTheme,
            'bb_event_website' : varEventWebsite
        });
    }
    function generateBlankRow( ) {
        var varBlankRow = new BlankRow();
        varBlankRow.render();
        return varBlankRow
    }
    function generateColorsThumbnail( ) {

        var themeColorsView = new ThemeColorsView({model:this.themeColorsModel});
        themeColorsView.render();
        return themeColorsView;
    } //#d9a11a - joyous yellow

    function generateFontsThumbnail( ) {

        var themeFontsView = new ThemeFontsView({model:this.themeFontsModel});
        themeFontsView.render();
        return themeFontsView;
    }

</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
