<%@ page import="com.events.common.ParseUtil" %>
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
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script type="text/javascript">
    var ThemeColorFontModel = Backbone.Model.extend({
        urlRoot: '/proc_load_theme_colors_and_fonts.aeve'
    });
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
        render:function(){

            var iNumOfCols = 0;
            var websiteColorRow = '';
            for(i = 0; i <this.varNumOfColors; i++) {
                if(iNumOfCols == 0 ) {
                    websiteColorRow = $('<div>').addClass('row');
                    $(this.el).append(websiteColorRow);
                }
                var colorColumn =  createColorThumbnail( this.varColors[i] , this.varTheme , this.varEventWebsite )

                websiteColorRow.append(colorColumn);
                iNumOfCols++;
                if(iNumOfCols == 8) {
                    iNumOfCols = 0;
                    $(this.el).append( createBlankRow() );
                    $(this.el).append( createBlankRow() );
                }
            }
        }
    });
    var ThemeFontsView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfFonts = this.model.get('bb_num_of_fonts');
            this.varFonts = this.model.get('bb_fonts');
            this.varEventWebsite = this.model.get('bb_event_website');
            this.varTheme = this.model.get('bb_theme');
        },
        render:function(){
            for(i = 0; i <this.varNumOfFonts; i++) {
                var websiteFontRow = $('<div>').addClass('row');
                var fontColumn =  createFontThumbnail( this.varFonts[i] , this.varTheme , this.varEventWebsite ) ;
                websiteFontRow.append(fontColumn);

                $(this.el).append( websiteFontRow );
                $(this.el).append( createBlankRow() );
                $(this.el).append( createBlankRow() );
            }
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

    function generateColorsThumbnailModel( varColors , numOfColors , varTheme,  varEventWebsite ) {
        this.themeColorsModel = new ThemeColorsModel();
        this.themeColorsModel.set('bb_colors' , varColors );
        this.themeColorsModel.set('bb_num_of_colors' , numOfColors );
        this.themeColorsModel.set('bb_theme' , varTheme );
        this.themeColorsModel.set('bb_event_website' , varEventWebsite );
    }
    function generateFontsThumbnailModel( varFonts , numOfFonts , varTheme,  varEventWebsite ) {
        this.themeFontsModel = new ThemeFontsModel();
        this.themeFontsModel.set('bb_fonts' , varFonts );
        this.themeFontsModel.set('bb_num_of_fonts' , numOfFonts );
        this.themeFontsModel.set('bb_theme' , varTheme );
        this.themeFontsModel.set('bb_event_website' , varEventWebsite );
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

    function createFontThumbnail(font , theme,  event_website) {
        var varName =createFontName(font)
        var varImg = createFontImage(font, theme);
        var varRadioButton = createRadioButton(font.website_font_id , event_website.website_font_id, 'fonts' );

        var varImgCol = createFontCols( varImg, 9, ''  );
        var varNameCol = createFontCols( varName, 2, ''  );
        var varRadioButtonCol = createFontCols( varRadioButton, 1 , 'text-align:center;'  );
        var varFontRow = createFontRow( varNameCol , varImgCol , varRadioButtonCol );

        var varColumn = $('<div>').addClass('col-md-12');
        varColumn.append(varFontRow);
        return varColumn;
    }
    function createFontCols( varRowContent, varColSize, varStyleContent ){
        var varDivCol = $('<div>').addClass('col-md-' + varColSize );
        varDivCol.append(varRowContent);
        if(varStyleContent!=''){
            varDivCol.attr('style',varStyleContent );
        }
        return varDivCol;
    }
    function createFontRow( varNameCol , varImgCol , varRadioButtonCol, varStyleContent ){

        var varIndividualRow = $('<div>').addClass('row');
        if(varStyleContent!=''){
            varIndividualRow.attr('style',varStyleContent );
        }

        varIndividualRow.append( varRadioButtonCol  );
        varIndividualRow.append( varImgCol  );
        varIndividualRow.append( varNameCol  );

        return varIndividualRow;
    }
    function createFontImage( font , theme ) {
        var varImg = $('<img>').addClass('img-thumbnail');
        varImg.attr('src', '/com/events/event/website/static_templates/' + theme.name + '/img/' + font.font_name +'.png'  );
        return varImg;
    }
    function createFontName( font ){
        var varFontNam = $('<h4>').append( font.font_name );
        return varFontNam;
    }

    function  createColorThumbnail( color , theme,  event_website) {
        var varImg = createColorImage(color, theme);
        var varRadioButton = createRadioButton(color.website_color_id , event_website.website_color_id, 'colors' );

        var varIndividualImgRow = createRow(varImg,'text-align:center;');
        var varIndividualButtonRow = createRow(varRadioButton,'text-align:center;');

        var varColumn = $('<div>').addClass('col-md-1');
        varColumn.append(varIndividualImgRow);
        varColumn.append( createBlankRow() );
        varColumn.append(varIndividualButtonRow);

        return varColumn;
    }
    function createColorImage( color , theme ) {
        var varImg = $('<img>').addClass('img-thumbnail');
        varImg.attr('src', '/com/events/event/website/static_templates/' + theme.name + '/img/' + color.color_swatch_name  );
        return varImg;
    }
    function createRadioButton(buttonId , currentSetId, buttonName) {
        var varButton = $('<input>').attr('type','radio').attr('id',buttonId).attr('name',buttonName);
        if( buttonId == currentSetId) {
            varButton.prop('checked',true);
        }
        return varButton;
    }

    function createBlankRow() {
        var varBlankCol = $('<div>').addClass('col-md-12');
        varBlankCol.append( '&nbsp;' );
        var varBlankRow = $('<div>').addClass('row');
        varBlankRow.append(varBlankCol);
        return varBlankRow;
    }
    function createRow( varRowContents , varStyleContent ){

        var varIndividualColumn = $('<div>').addClass('col-md-12');
        varIndividualColumn.append( varRowContents );

        var varIndividualRow = $('<div>').addClass('row');
        if(varStyleContent!=''){
            varIndividualRow.attr('style',varStyleContent );
        }
        varIndividualRow.append( varIndividualColumn  );

        return varIndividualRow;
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>
