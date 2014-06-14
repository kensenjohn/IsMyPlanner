<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
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
            <div class="page-title">Dashboard - CheckList </div>
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
                <div class="col-md-2">
                    <a href="/com/events/dashboard/checklist/edit_checklist_template.jsp" class="btn btn-filled">
                        <span><i class="fa fa-plus"></i> New Checklist Template</span>
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
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_checklist_template" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-xs-1" role="columnheader">Template Name</th>
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
</div>
</body>
<form id="frm_delete_checklist_template">
    <input type="hidden" name="checklist_template_id" id="delete_checklist_template_id" value=""/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        initializeTableChecklistTemplate();
        loadAllChecklistTemplate( populateAllChecklistTemplates );
    });
    function loadAllChecklistTemplate( callbackmethod ) {
        var actionUrl = "/proc_load_all_checklist_templates.aeve";
        var methodType = "POST";
        var dataString = $("#frm_load_checklist_template").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateAllChecklistTemplates(jsonResult){
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var jsonResponseObj = varResponseObj.payload;
                if(jsonResponseObj!=undefined) {
                    var varNumOfAllChecklistTemplates = jsonResponseObj.num_of_checklist_templates;
                    if(varNumOfAllChecklistTemplates>0){
                        var varAllChecklistTemplates = jsonResponseObj.all_checklist_templates;
                        for(var varCount =0 ; varCount<varNumOfAllChecklistTemplates; varCount++ ){
                            var varChecklistTemplate = varAllChecklistTemplates[ varCount ];
                            createChecklistTemplateTableRow( varChecklistTemplate );
                        }
                    }
                }
            }
        }
    }
    function deleteChecklistTemplate( varChecklistTemplateObj){
        $('#delete_checklist_template_id').val(varChecklistTemplateObj.checklist_template_id);

        var actionUrl = "/proc_delete_checklist_template.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_checklist_template").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,processChecklistTemplateDeletion);
    }
    function processChecklistTemplateDeletion (jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {

                    var jsonResponseObj = varResponseObj.payload;
                    if(jsonResponseObj!=undefined){
                        var varIsChecklistTemplateDeleted = jsonResponseObj.is_deleted;

                        if(varIsChecklistTemplateDeleted){
                            var varChecklistTemplateId = jsonResponseObj.deleted_checklist_template_id;
                            $('#delete_checklist_template_id').val('');

                            var oTable = objChecklistTemplate;
                            if(oTable!='' && oTable!=undefined) {
                                oTable.fnDeleteRow((oTable.$('#row_'+varChecklistTemplateId))[0] );
                            }

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
    function addChecklistTemplateDeleteClickEvent(varChecklistTemplateId) {
        var checklist_template_delete_obj = {
            checklist_template_id: varChecklistTemplateId,
            printObj: function () {
                return this.checklist_template_id;
            }
        }
        $('#del_'+varChecklistTemplateId).click({param_checklist_template_delete_obj:checklist_template_delete_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this checklist template? All your changes will be permanently lost." ,
                    "Delete Checklist Template",
                    "Yes", "No", deleteChecklistTemplate,e.data.param_checklist_template_delete_obj);
        });
    }
    function createChecklistTemplateTableRow( varChecklistTemplate ){
        var oTable = objChecklistTemplate;
        if(varChecklistTemplate!=undefined){
            var varChecklistTemplateId = varChecklistTemplate.checklist_template_id;
            var varColumnData = [varChecklistTemplate.name, createButton(varChecklistTemplateId) ];

            var ai = oTable.fnAddData( varColumnData );
            var nRow = oTable.fnSettings().aoData[ ai[0] ].nTr;
            $(nRow).attr('id','row_'+varChecklistTemplateId);

            addChecklistTemplateDeleteClickEvent(varChecklistTemplateId);
        }
    }
    function createButton( varChecklistTemplateId ){
        var varButton = createEditButton( varChecklistTemplateId ) + '&nbsp;&nbsp;&nbsp;' + createDeletedButton( varChecklistTemplateId );
        return varButton;
    }
    function createEditButton( varChecklistTemplateId ){
        var varEditLink = '<a id="edit_'+varChecklistTemplateId+'" class="btn btn-default btn-xs" href="/com/events/dashboard/checklist/edit_checklist_template.jsp?checklist_template_id='+varChecklistTemplateId+'"><i class="fa fa-pencil"></i> Edit</a>';
        return varEditLink;
    }
    function createDeletedButton( varChecklistTemplateId ){
        var varDeleteLink = '<button id="del_'+varChecklistTemplateId+'" class="btn btn-default btn-xs" ><i class="fa fa-trash-o"></i> Delete</button>';
        return varDeleteLink;
    }
    function initializeTableChecklistTemplate(){

        objChecklistTemplate=  $('#every_checklist_template').dataTable({
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