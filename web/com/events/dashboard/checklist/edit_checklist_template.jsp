<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="/css/colorbox.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<style>
    #sortable_chk_list { list-style-type: none;}
</style>
<%
    String sChecklistTemplateId = ParseUtil.checkNull(request.getParameter("checklist_template_id"));
    boolean isLoadChecklistTemplate = false;
    if(!Utility.isNullOrEmpty(sChecklistTemplateId)){
        isLoadChecklistTemplate = true;
    }
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="dashboard_active" value="active"/>
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
                    <form class="form-horizontal" id="frm_save_checklist_template">
                        <div class="row">
                            <div class="col-md-8">
                                <label for="checklist_template_name" class="form_label">Checklist Template Name:</label><span class="required"> *</span>
                                <input type="text" class="form-control" id="checklist_template_name" name="checklist_template_name"/>
                            </div>
                        </div>
                        <input type="hidden" name="checklist_template_id" id="checklist_template_id" value="<%=sChecklistTemplateId%>"/>
                    </form>
                    <div class="row">
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <button class="btn btn-filled" id="btn_save_checklist_template"><i class="fa fa-floppy-o"></i> Save</button>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            &nbsp;
                        </div>
                    </div>
                    <div class="row" style="display:none;" id="div_add_checklist_item">
                        <div class="col-md-4">
                            <button class="btn btn-default btn-xs" id="btn_add_checklist_item"><i class="fa fa-plus"></i> Add New Checklist Item</button>
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
<script id="template_checklist_template_item" type="text/x-handlebars-template">
    <li class="sort_tracker" id="sort_tracker_item_{{item_sequence_number}}" param="{{checklist_template_item_id}}">
        <div class="row chk_list_row" id="row_item_{{checklist_template_item_id}}" param="item{{item_number}}">
            <div class="col-xs-6">
                <span class="chk_list_name">{{item_name}}</span> &nbsp;&nbsp;&nbsp; <i class="fa fa-chevron-right icon_item_details" id="icon_item_{{checklist_template_item_id}}" param="item_{{checklist_template_item_id}}"></i>
            </div>
            <div class="col-xs-6">
                <button class="btn btn-default btn-xs" id="edit_item_{{checklist_template_item_id}}"><i class="fa fa-pencil"></i> Edit</button>
                &nbsp;&nbsp;&nbsp;
                <button class="btn btn-default btn-xs" id="delete_item_{{checklist_template_item_id}}"><i class="fa fa-trash-o"></i> Delete</button>
            </div>
            <div class="col-xs-12" id="chk_list_details_item_{{checklist_template_item_id}}" style="display:none">
                <div class="row">
                    <div class="col-xs-offset-1 col-xs-5">
                        <span style="font-weight: bold;">Tasks</span>
                    </div>
                </div>
                <div id="checklist_template_todo_list_{{checklist_template_item_id}}">

                </div>
            </div>
        </div>
    </li>
</script>
<script id="template_checklist_template_item_todo" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-xs-offset-1 col-xs-11">
            <span>{{checklist_template_item_todo_name}}</span>
        </div>
    </div>
</script>
<script id="template_checklist_item_sequence" type="text/x-handlebars-template">
    <input type="hidden" name="{{sort_sequence_number}}" class="hidden_item_sequence" value="{{checklist_template_item_id}}"/>
</script>
<form id="frm_load_checklist_template">
    <input type="hidden" name="checklist_template_id" value="<%=sChecklistTemplateId%>"/>
</form>
<form id="frm_delete_checklist_template_item">
    <input type="hidden" name="checklist_template_id" id="delete_checklist_template_id" value=""/>
    <input type="hidden" name="checklist_template_item_id" id="delete_checklist_template_item_id" value=""/>
    <input type="hidden" name="checklist_template_item_row_num" id="checklist_template_item_row_num" value=""/>
</form>
<form id="frm_sort_checklist_template_item">
    <input type="hidden" name="checklist_template_id" id="sort_checklist_template_id" value="<%=sChecklistTemplateId%>"/>.
    <input type="hidden" name="num_of_items" id="sort_num_of_items" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
<script src="/js/datepicker/picker.js"></script>
<script src="/js/datepicker/picker.date.js"></script>
<script src="/js/datepicker/picker.time.js"></script>
<script src="/js/datepicker/legacy.js"></script>
<script src="/js/jquery.colorbox-min.js"></script>
<script src="/js/jquery.ui.touch-punch.min.js"></script>
<script src="/js/handlebars-v1.3.0.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script   type="text/javascript">
    var varIsLoadLoadChecklistTemplate = <%=isLoadChecklistTemplate%>;
    $(window).load(function() {
        $( "#sortable_chk_list" ).sortable( {
            stop: function( event, ui ) {
                finalizeSortChecklist( true );
            }
        });

        $('#btn_save_checklist_template').bind('click',function(event){
            saveChecklistTemplate(getResult);
        });

        if(varIsLoadLoadChecklistTemplate){
            loadChecklistTemplate(populateChecklistTemplate);
        }
    });

    function createIconEvents(){
        $('.icon_item_details').click(function(event){
            var varIconId= event.target.id;
            toggleCollapseIcon(varIconId);

            var varCheckListId = $('#'+varIconId).attr('param');
            openCheckListDetail( varCheckListId )
        });
    }

    function saveChecklistTemplate( callbackmethod ) {
        var actionUrl = "/proc_save_checklist_template.aeve";
        var methodType = "POST";
        var dataString = $("#frm_save_checklist_template").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function loadChecklistTemplate( callbackmethod ) {
        var actionUrl = "/proc_load_checklist_template.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_checklist_template").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function deleteChecklistTemplateItem( callbackmethod ) {
        var actionUrl = "/proc_delete_checklist_template_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_checklist_template").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function sortChecklistTemplateItem( callbackmethod ) {
        var actionUrl = "/proc_sort_checklist_template_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_sort_checklist_template_item").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function getResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varChecklistTemplateBean = jsonResponseObj.checklist_template_bean;
                    $('#checklist_template_id').val( varChecklistTemplateBean.checklist_template_id );
                    $('#div_add_checklist_item').show();

                    createAddItemEvent( varChecklistTemplateBean.checklist_template_id );
                }
            }
        }
    }

    function getSortResult(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {

                }
            }
        }
    }


    function populateChecklistTemplate(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varChecklistTemplateBean = jsonResponseObj.checklist_template_bean;
                    $('#checklist_template_id').val( varChecklistTemplateBean.checklist_template_id );
                    $('#checklist_template_name').val( varChecklistTemplateBean.name );

                    $('#div_add_checklist_item').show();

                    var varNumberOfItems = jsonResponseObj.num_of_checklist_template_items;
                    if(varNumberOfItems>0){
                        var varNumOfItemWithTodos = jsonResponseObj.num_of_checklist_template_items_with_todos;
                        var varItemsWithTodos = '';
                        if(varNumOfItemWithTodos>0){
                            varItemsWithTodos = jsonResponseObj.items_with_todos
                        }


                        var varAllChecklisTemplateItems = jsonResponseObj.checklist_template_items;
                        for( var varCount = 0; varCount<varNumberOfItems; varCount++ ){

                            var varChecklistTemplateItemBean = varAllChecklisTemplateItems[varCount];
                            var varChecklistTemplateItemId = varChecklistTemplateItemBean.checklist_template_item_id;
                            if(varChecklistTemplateItemBean!=undefined){
                                this.checklistTemplateItemModel = new ChecklistTemplateItemModel({
                                    'bb_item_sequence_number' : varCount,
                                    'bb_checklist_template_item_id' : varChecklistTemplateItemId,
                                    'bb_checklist_template_item_name' : varChecklistTemplateItemBean.name
                                });
                                var checklistTemplateItemView = new ChecklistTemplateItemView({model:this.checklistTemplateItemModel});
                                checklistTemplateItemView.render();
                                $("#sortable_chk_list").append(checklistTemplateItemView.el);

                                createEditItemEvent(varChecklistTemplateItemBean.checklist_template_id,varChecklistTemplateItemBean.checklist_template_item_id);
                                addChecklistTemplateItemDeleteClickEvent(varChecklistTemplateItemBean.checklist_template_id,varChecklistTemplateItemBean.checklist_template_item_id, varCount);


                                if( varItemsWithTodos!='' && varItemsWithTodos!=undefined){
                                    var varItemTodoList =  varItemsWithTodos[ varChecklistTemplateItemId ];

                                    if(varItemTodoList!=undefined){
                                        this.checklistTemplateTodoModel = new ChecklistTemplateTodoModel({
                                            'bb_item_todo_list' : varItemTodoList,
                                            'bb_num_of_todos' : varItemTodoList.num_of_checklist_template_todos
                                        });

                                        var checklistTemplateTodoView = new ChecklistTemplateTodoView({model:this.checklistTemplateTodoModel});
                                        checklistTemplateTodoView.render();
                                        $("#checklist_template_todo_list_"+varChecklistTemplateItemId).append(checklistTemplateTodoView.el);
                                    }
                                }
                            }

                        }
                    }
                    createIconEvents();

                    createAddItemEvent( varChecklistTemplateBean.checklist_template_id  );
                    finalizeSortChecklist( false );
                }
            }
        }
    }
    function addChecklistTemplateItemDeleteClickEvent(varChecklistTemplateId,varChecklistTemplateItemId, varRowNumber) {
        var checklist_template_item_delete_obj = {
            checklist_template_id: varChecklistTemplateId,
            checklist_template_item_id: varChecklistTemplateItemId,
            checklist_template_item_row_number:varRowNumber
        }
        $('#delete_item_'+varChecklistTemplateItemId).click({param_checklist_template_item_delete_obj:checklist_template_item_delete_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this item?" ,
                    "Delete Checklist Template Item",
                    "Yes", "No", deleteChecklistTemplate,e.data.param_checklist_template_item_delete_obj);
        });
    }
    function deleteChecklistTemplate( varChecklistTemplateItemObj){
        $('#delete_checklist_template_id').val(varChecklistTemplateItemObj.checklist_template_id);
        $('#delete_checklist_template_item_id').val(varChecklistTemplateItemObj.checklist_template_item_id);
        $('#checklist_template_item_row_num').val(varChecklistTemplateItemObj.checklist_template_item_row_number);


        var actionUrl = "/proc_delete_checklist_template_item.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_checklist_template_item").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processChecklistTemplateItemDeletion);
    }
    function processChecklistTemplateItemDeletion (jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined){
                        var varIsChecklistTemplateItemDeleted = jsonResponseObj.is_deleted;

                        if(varIsChecklistTemplateItemDeleted){
                            var varChecklistTemplateItemId = jsonResponseObj.deleted_checklist_template_item_id;
                            $('#delete_checklist_template_id').val('');
                            $('#delete_checklist_template_item_id').val('');

                            var varChecklistTemplateItemRowNum = jsonResponseObj.row_number;
                            $('#sort_tracker_item_'+varChecklistTemplateItemRowNum).remove();

                        } else {
                            displayMssgBoxAlert("The checklist template was not deleted. Please try again later.", true);
                        }
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteRegistry - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteRegistry - 2)", true);
        }
    }
    var ChecklistTemplateTodoModel = Backbone.Model.extend({
        defaults: {
            bb_item_todo_list:undefined,
            bb_num_of_todos: 0
        }
    });
    var ChecklistTemplateTodoView = Backbone.View.extend({
        initialize: function(){
            this.varBBItemTodoList = this.model.get('bb_item_todo_list');
            this.varBBNumOfTodos = this.model.get('bb_num_of_todos');
        },
        template : Handlebars.compile( $('#template_checklist_template_item_todo').html() ),
        render : function() {

            if(this.varBBNumOfTodos>0){
                for( var varTodoCount = 0; varTodoCount<this.varBBNumOfTodos; varTodoCount++ ){
                    var varTodoBean = this.varBBItemTodoList[varTodoCount];

                    var varTmpTodoBean = {
                        "checklist_template_item_todo_name": varTodoBean.name
                    }

                    var checklistTodoRow = this.template(  eval( varTmpTodoBean )  );
                    $(this.el).append( checklistTodoRow );
                }
            }


        }
    });

    var ChecklistTemplateItemModel = Backbone.Model.extend({
        defaults: {
            bb_item_sequence_number:undefined,
            bb_checklist_template_item_id: undefined,
            bb_checklist_template_item_name: undefined
        }
    });
    var ChecklistTemplateItemView = Backbone.View.extend({
        initialize: function(){
            this.varBBItemSequenceNumber = this.model.get('bb_item_sequence_number');
            this.varBBChecklistTemplateId = this.model.get('bb_checklist_template_item_id');
            this.varBBChecklistTemplateName = this.model.get('bb_checklist_template_item_name');
        },
        template : Handlebars.compile( $('#template_checklist_template_item').html() ),
        render : function() {
            var varTmpChecklistTemplateItemBean = {
                "item_sequence_number" : this.varBBItemSequenceNumber,
                "checklist_template_item_id"  : this.varBBChecklistTemplateId ,
                "item_name"  : this.varBBChecklistTemplateName
            }
            var checklistTemplateItemRow = this.template(  eval( varTmpChecklistTemplateItemBean )  );
            $(this.el).append( checklistTemplateItemRow );
        }
    });

    function createAddItemEvent(varChecklistTemplateId){
        $('#btn_add_checklist_item').unbind('click');
        $('#btn_add_checklist_item').bind('click',function(){
            $.colorbox({
                href:'edit_checklist_item_template.jsp?checklist_template_id='+varChecklistTemplateId,
                iframe:true,
                innerWidth: '90%',
                innerHeight: '85%',
                scrolling: true,
                onClosed : function() {
                    // loadWebsitePageFeatureParty('bridesmaids', populateWebsitePagePartyMembers)
                    $("#sortable_chk_list").empty();
                    loadChecklistTemplate(populateChecklistTemplate);
                }});
        });
    }

    function createEditItemEvent(varChecklistTemplateId, varChecklistTemplateItemId ){
        $('#edit_item_'+varChecklistTemplateItemId).unbind('click');
        $('#edit_item_'+varChecklistTemplateItemId).bind('click',function(){
            $.colorbox({
                href:'edit_checklist_item_template.jsp?checklist_template_id='+varChecklistTemplateId+'&checklist_template_item_id='+varChecklistTemplateItemId,
                iframe:true,
                innerWidth: '90%',
                innerHeight: '85%',
                scrolling: true,
                onClosed : function() {
                    // loadWebsitePageFeatureParty('bridesmaids', populateWebsitePagePartyMembers)
                    $("#sortable_chk_list").empty();
                    loadChecklistTemplate(populateChecklistTemplate);
                }});
        });
    }
    var arrFinalSortedChkList = Array();
    function finalizeSortChecklist( varExecuteSortDB){
        var chkListElement = $('#sortable_chk_list').find('.sort_tracker');

        var arrSortedChkList = Array();
        $('#sortable_chk_list .sort_tracker').each(function(){
            arrSortedChkList.push($(this).attr('id').replace('sort_tracker_item_', ''));
        })

        var resetSortArray = false;
        for (var trackArrayIndex = 0; trackArrayIndex < arrSortedChkList.length; trackArrayIndex++ ) {
            if( arrFinalSortedChkList[trackArrayIndex] != arrSortedChkList[trackArrayIndex]){
                resetSortArray = true;
            }
        }
        if(resetSortArray && varExecuteSortDB){
            arrFinalSortedChkList = arrSortedChkList;

            $('.hidden_item_sequence').remove();
            for (var trackArrayIndex = 0; trackArrayIndex < arrFinalSortedChkList.length; trackArrayIndex++ ) {
                if( arrFinalSortedChkList[trackArrayIndex] != arrFinalSortedChkList[trackArrayIndex]){
                    resetSortArray = true;
                }
                this.sortChecklistTemplateItemModel = new SortChecklistTemplateItemModel({
                    'bb_sort_sequence_number' : arrFinalSortedChkList[trackArrayIndex],
                    'bb_checklist_template_item_id' : $('#sort_tracker_item_' + trackArrayIndex ).attr('param')
                });

                var sortChecklistTemplateItemView = new SortChecklistTemplateItemView({model:this.sortChecklistTemplateItemModel});
                sortChecklistTemplateItemView.render();
                $("#frm_sort_checklist_template_item").append(sortChecklistTemplateItemView.el);
            }
            $('#sort_num_of_items').val( arrFinalSortedChkList.length );
            sortChecklistTemplateItem( getSortResult )
        }
    }
    //
    var SortChecklistTemplateItemModel = Backbone.Model.extend({
        defaults: {
            bb_sort_sequence_number:undefined,
            bb_checklist_template_item_id: undefined
        }
    });
    var SortChecklistTemplateItemView = Backbone.View.extend({
        initialize: function(){
            this.varBBSortSequenceNumber = this.model.get('bb_sort_sequence_number');
            this.varBBChecklistTemplateId = this.model.get('bb_checklist_template_item_id');
        },
        template : Handlebars.compile( $('#template_checklist_item_sequence').html() ),
        render : function() {
            var varTmpSortItemBean = {
                "sort_sequence_number" : this.varBBSortSequenceNumber,
                "checklist_template_item_id"  : this.varBBChecklistTemplateId
            }
            var inputHiddenItemRow = this.template(  eval( varTmpSortItemBean )  );
            $(this.el).append( inputHiddenItemRow );
        }
    });

    function openCheckListDetail( varCheckListId ){
        $('#row_'+varCheckListId).toggleClass("chk_list_row_selected").toggleClass("chk_list_row");
        $('#chk_list_details_'+varCheckListId).slideToggle( 'fast', function() {

        });
    }
    function toggleCollapseIcon(varIcon) {
        $('#'+varIcon).toggleClass("fa-chevron-down").toggleClass("fa-chevron-right");
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>