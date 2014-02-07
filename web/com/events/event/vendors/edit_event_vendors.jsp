<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
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
                    <div class="web_excel row">
                        <div class="col-md-12">
                            <div class="web_excel_header row">
                                <div class="col-md-3 web_excel_cell">
                                    Sort by :
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-default btn-xs" id="sort_by_price">Price</button>
                                        <button type="button" class="btn btn-default btn-xs" id="sort_by_category">Category</button>
                                    </div>
                                </div>
                                <div class="col-md-3 web_excel_cell">
                                    Filter by :
                                    <div class="btn-group"  id="div_filter_by_price">
                                            <button type="button" class="btn btn-default btn-xs" id="filter_by_price"  data-original-title="Price Range" >Price</button>

                                            <div id="filter_price_range" style="display:none;">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <input type="checkbox" id="filter_by_low" value="low"/> Low
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <input type="checkbox" id="filter_by_low_medium" value="low_medium"> Between Low and Medium
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <input type="checkbox" id="filter_by_medium" value="medium"> Medium
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <input type="checkbox" id="filter_by_medium_high" value="medium_high"> Between Medium and High
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <input type="checkbox" id="filter_by_high" value="high"> High
                                                    </div>
                                                </div>
                                            </div>

                                        <button type="button" class="btn btn-default btn-xs" id="filter_by_category"   data-original-title="Categories" >Category</button>
                                            <div id="filter_category_range" style="display:none;">
                                                <%
                                                    String sFilterId = Constants.EMPTY;
                                                    boolean isFirst = true;
                                                    for(Constants.VENDOR_TYPE vendorType : Constants.VENDOR_TYPE.values()){
                                                        if(!isFirst) {
                                                            sFilterId=sFilterId + ",";
                                                        }
                                                        sFilterId=sFilterId + "#filter_by_"+vendorType.getId();
                                                %>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <input type="checkbox" id="filter_by_<%=vendorType.getId()%>" value="<%=vendorType.toString()%>"/> <%=vendorType.getText()%>
                                                            </div>
                                                        </div>
                                                <%
                                                        isFirst = false;
                                                    }
                                                %>
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
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/tooltip_popover.js"></script>
<script src="/js/event/event_info.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);

        $("#filter_by_price").popover({container:'body',trigger:'click',placement:'bottom',
            html : true,
            content:function(){
                return $('#filter_price_range').html();
            }});

        $("#filter_by_category").popover({container:'body',trigger:'click',placement:'bottom',
            html : true,
            content:function(){
                return $('#filter_category_range').html();
            }});
    });

    $(document).on("click", "#filter_by_low , #filter_by_low_medium , #filter_by_medium, #filter_by_medium_high, #filter_by_high", function() {
        alert( $(this).val() );
    });
    $(document).on("click", "<%=sFilterId%>", function() {
        alert( $(this).val() );
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>