<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<style>
    #sortable_chk_list { list-style-type: none;}
</style>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="dashboard_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">CheckList Template</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="checklist_management_active" value="active"/>
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
                    <form class="form-horizontal" id="frm_save_todo">
                        <div class="row">
                            <div class="col-md-6">
                                <label for="checklist_template_name" class="form_label">Checklist Template Name:</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="checklist_template_name" name="checklist_template_name"/>
                            </div>
                            <div class="col-md-2">
                                <label for="checklist_template_name" class="form_label">Enable Comments:</label>
                                <select class="form-control" >
                                    <option>Yes</option>
                                    <option>No</option>
                                </select>
                            </div>
                            <div class="col-md-2">
                                <label for="checklist_template_name" class="form_label">Enable Reminders:</label>
                                <select class="form-control" >
                                    <option>Yes</option>
                                    <option>No</option>
                                </select>
                            </div>
                        </div>
                    </form>
                    <div class="row">
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-2">
                            <button class="btn btn-default btn-xs" id="btn_checklist_item"><i class="fa fa-plus"></i> Add Item</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-10">
                            <ul id="sortable_chk_list">
                                <li class="sort_tracker" id="sort_tracker_item_1">
                                    <div class="row chk_list_row" id="row_item1" param="item1">
                                        <div class="col-xs-6">
                                            <input type="checkbox">&nbsp;&nbsp;&nbsp;<span class="chk_list_name">Hire a photographer</span> &nbsp;&nbsp;&nbsp; <i class="fa fa-chevron-right icon_item_details" id="icon_item1" param="item1"></i>
                                        </div>
                                        <div class="col-xs-6">
                                            <button class="btn btn-default btn-xs"><i class="fa fa-pencil"></i> Edit</button>
                                            &nbsp;&nbsp;&nbsp;
                                            <button class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</button>
                                        </div>
                                        <div class="col-xs-12" id="chk_list_details_item1" style="display:none">
                                            <div class="row">
                                                <div class="col-xs-offset-1 col-xs-5">
                                                    <span style="font-weight: bold;">Todo</span>
                                                </div>
                                            </div>
                                            <div id="todo_list">
                                                <div class="row">
                                                    <div class="col-xs-offset-1 col-xs-5">
                                                        <input type="checkbox">&nbsp;&nbsp;&nbsp;<span>Interview Photographer</span>
                                                    </div>
                                                    <div class="col-xs-6">
                                                        <button class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i></button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li class="sort_tracker" id="sort_tracker_item_2">
                                    <div class="row chk_list_row"  id="row_item2" param="item2">
                                        <div class="col-xs-6">
                                            <input type="checkbox">&nbsp;&nbsp;&nbsp;<span>Hire a Caterer</span> &nbsp;&nbsp;&nbsp; <i class="fa fa-chevron-right icon_item_details"  id="icon_item2"></i>
                                        </div>
                                        <div class="col-xs-6">
                                            <button class="btn btn-default btn-xs"><i class="fa fa-pencil"></i> Edit</button>
                                            &nbsp;&nbsp;&nbsp;
                                            <button class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</button>
                                        </div>
                                    </div>
                                </li>
                                <li class="sort_tracker" id="sort_tracker_item_3">
                                    <div class="row chk_list_row"  id="row_item3" param="item3">
                                        <div class="col-xs-6">
                                            <input type="checkbox">&nbsp;&nbsp;&nbsp;<span>Interview with Caterer</span> &nbsp;&nbsp;&nbsp; <i class="fa fa-ellipsis-h icon_item_details"   id="icon_item3"></i>
                                        </div>
                                        <div class="col-xs-6">
                                            <button class="btn btn-default btn-xs"><i class="fa fa-pencil"></i> Edit</button>
                                            &nbsp;&nbsp;&nbsp;
                                            <button class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</button>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
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
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/datepicker/picker.js"></script>
<script src="/js/datepicker/picker.date.js"></script>
<script src="/js/datepicker/picker.time.js"></script>
<script src="/js/datepicker/legacy.js"></script>
<script src="/js/jquery.colorbox-min.js"></script>
<script src="/js/jquery.ui.touch-punch.min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        $('#btn_checklist_item').click(function(){
            $.colorbox({
                href:'edit_checklist_item_template.jsp',
                iframe:true,
                innerWidth: '90%',
                innerHeight: '85%',
                scrolling: true,
                onClosed : function() {
                    // loadWebsitePageFeatureParty('bridesmaids', populateWebsitePagePartyMembers)
                }});
        });
        $( "#sortable_chk_list" ).sortable( {
            stop: function( event, ui ) {
                finalizeSortChecklist();
            }
        });
        $( "#sortable_chk_list" ).disableSelection();

        $('.icon_item_details').click(function(event){
            var varIconId= event.target.id;
            toggleCollapseIcon(varIconId);

            var varCheckListId = $('#'+varIconId).attr('param');
            openCheckListDetail( varCheckListId )
        });
    });

    function finalizeSortChecklist(){
        var chkListElement = $('#sortable_chk_list').find('.sort_tracker');

        var arrSortedChkList = Array();
        $('#sortable_chk_list .sort_tracker').each(function(){
            arrSortedChkList.push($(this).attr('id').replace('sort_tracker_item_', ''));
        })
        console.log('arrSortedChkList : '+arrSortedChkList);
    }

    function openCheckListDetail( varCheckListId ){
        $('#row_'+varCheckListId).toggleClass("chk_list_row_selected").toggleClass("chk_list_row");
        $('#chk_list_details_'+varCheckListId).slideToggle( "slow", function() {
            console.log('Slide Toggle invoked : ' + varCheckListId);

        });
    }
    function toggleCollapseIcon(varIcon) {
        console.log('varIcon : ' + varIcon);
        $('#'+varIcon).toggleClass("fa-chevron-down").toggleClass("fa-chevron-right");
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>