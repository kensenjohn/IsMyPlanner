<%@ page import="com.events.common.ParseUtil" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
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
        <jsp:param name="event_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Event Checklist- <span id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/event/event_tab.jsp">
                            <jsp:param name="event_checklist_active" value="active"/>
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
                <div class="col-md-2">
                    <a href="/com/events/event/checklist/edit_event_checklist.jsp?event_id=<%=sEventId%>" class="btn btn-filled">
                        <span><i class="fa fa-plus"></i> New Checklist </span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_checklist" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-xs-1" role="columnheader">Checklist</th>
                            <th class="sorting col-md-2" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_checklist_template_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
<form id="frm_load_all_event_checklists">
    <input type="hidden" name="event_id" value="<%=sEventId%>"/>
</form>
<form id="frm_delete_event_checklist">
    <input type="hidden" name="event_checklist_id" id="delete_event_checklist_id" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/event/event_info.js"></script>
<script src="/js/jquery.dataTables.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.0/backbone-min.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
        initializeTableChecklist();

        loadAllEventChecklists( populateAllEventChecklistsTable)
    });
    function loadAllEventChecklists( callbackmethod ) {
        var actionUrl = "/proc_load_all_event_checklists.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_all_event_checklists").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }

    function populateAllEventChecklistsTable(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    // var varNumOfAllChecklistTemplates = jsonResponseObj.num_of_checklist_templates;

                    var varNumOfEventChecklists = jsonResponseObj.num_of_event_checklists ;
                    if(varNumOfEventChecklists>0){
                        var varAllEventChecklists = jsonResponseObj.all_event_checklists;

                        this.eventChecklistModel = new EventChecklistModel({

                            'bb_num_of_event_checklists' : varNumOfEventChecklists,
                            'bb_all_event_checklists' : varAllEventChecklists
                        });

                        var eventChecklistView = new EventChecklistView({model:this.eventChecklistModel});
                        eventChecklistView.render();
                    }

                }
            }
        }
    }

    var EventChecklistModel = Backbone.Model.extend({
        defaults: {
            bb_num_of_event_checklists: 0 ,
            bb_all_event_checklists: undefined
        }
    });
    var EventChecklistView = Backbone.View.extend({
        initialize: function(){
            this.varNumOfEventChecklists = this.model.get('bb_num_of_event_checklists');
            this.varAllEventChecklists = this.model.get('bb_all_event_checklists');
        },
        render:function(){
            var oTable = objEventChecklist;
            if(oTable!='' && oTable!=undefined){
                oTable.fnClearTable();
                for( var varCount = 0;varCount<this.varNumOfEventChecklists ;varCount++ ){
                    var varEventChecklist = this.varAllEventChecklists[varCount];

                    var varEventChecklistId = varEventChecklist.event_checklist_id;
                    var ai = oTable.fnAddData( [varEventChecklist.name, createButtons(varEventChecklistId) ] );
                    var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
                    $(nRow).attr('id','row_'+varEventChecklistId);

                    addEventChecklistDeleteClickEvent(varEventChecklistId, varCount );
                }
            }
        }
    });

    function addEventChecklistDeleteClickEvent(varEventChecklistId,varRowNum) {
        var event_checklist_delete_obj = {
            event_checklist_id: varEventChecklistId,
            printObj: function () {
                return this.event_checklist_id;
            }
        }
        $('#del_'+varEventChecklistId).click({param_event_checklist_delete_obj:event_checklist_delete_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this checklist?" ,
                    "Delete Checklist",
                    "Yes", "No", deleteEventChecklist,e.data.param_event_checklist_delete_obj);
        });
    }
    function deleteEventChecklist( varEventChecklistObj ){
        $('#delete_event_checklist_id').val(varEventChecklistObj.event_checklist_id);

        var actionUrl = "/proc_delete_event_checklist.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_event_checklist").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processEventChecklistDeletion);
    }

    function processEventChecklistDeletion( jsonResult ){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    var varIsEventChecklistDeleted = jsonResponseObj.is_deleted;

                    if(varIsEventChecklistDeleted){
                        var varEventChecklistId = jsonResponseObj.deleted_event_checklist_id;
                        $('#delete_event_checklist_id').val('');

                        var oTable = objEventChecklist;
                        if(oTable!='' && oTable!=undefined) {
                            oTable.fnDeleteRow((oTable.$('#row_'+varEventChecklistId))[0] );
                        }

                    } else {
                        displayMssgBoxAlert("The checklist was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteRegistry - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteRegistry - 2)", true);
        }
    }

    function createButtons( varId ){
        var varButtons = '';
        varButtons = varButtons + createEditButton( varId );
        varButtons = varButtons + '&nbsp;&nbsp;&nbsp;';
        varButtons = varButtons + createDeleteButton( varId );
        return varButtons;
    }
    function createEditButton(varId){
        var varButtonLink = '<a id="edit_'+varId+'" class="btn btn-default btn-xs" href="/com/events/event/checklist/edit_event_checklist.jsp?event_checklist_id='+varId+'&event_id='+varEventId+'"><i class="fa fa-pencil"></i> Edit</a>';

        return varButtonLink ;
    }
    function createDeleteButton(varId){
        return '<a id="del_'+varId+'" class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i> Delete</a>';
    }


    function initializeTableChecklist(){

        objEventChecklist=  $('#every_checklist').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                { "bSortable": false,"sClass":"center" }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>