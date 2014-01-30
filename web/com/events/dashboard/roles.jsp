<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
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
        <jsp:param name="dashboard_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Dashboard - Roles and Permissions</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="roles_and_permissions_active" value="active"/>
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
                    <button  type="button" class="btn  btn-filled" id="btn_add_role">
                        <span><span class="glyphicon glyphicon-plus"></span> Add A Role</span>
                    </button>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_role" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-3" role="columnheader">Role Name</th>
                            <th class="sorting" role="columnheader">Assigned to </th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_role_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {

        loadRoles(populateRolesList);
    });
    function loadRoles(callbackmethod) {
        var actionUrl = "/proc_load_all_roles.aeve";
        var methodType = "POST";
        var dataString = '';
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populateRolesList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfRoles = jsonResponseObj.num_of_roles;
                    if(varNumOfRoles>0){
                        processRolesList(varNumOfRoles, jsonResponseObj.every_role );
                    }
                    initializeTable();
                }
            } else {
                displayMssgBoxAlert("Please try again later (populateEventList - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (populateEventList - 2)", true);
        }
    }

    function processRolesList(varNumOfRoles, everyRoleList) {
        for(i=0;i<varNumOfRoles;i++){
            var varEveryRoleBean = everyRoleList[i];
            var rowEveryRole= $('<tr id="row_'+varEveryRoleBean.role_id+'" ></tr>');
            rowEveryRole.append(
                    '<td>'+varEveryRoleBean.name+'</td>'+
                            '<td>'+varEveryRoleBean.assigned_to_num_of_users+'</td>' +
                            '<td  class="center" >'+
                            '<a href="/com/events/event/email/edit_email.jsp?event_id='+varEveryRoleBean.role_id+'&eventemail_id='+varEveryRoleBean.role_id+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil"></span> Edit</a>'+
                            '&nbsp;&nbsp;&nbsp;'+
                            '<a id="'+varEveryRoleBean.role_id+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span> Delete</a>'+'</td>');
            $('#every_role_rows').append(rowEveryRole);


            // Adding Click Event Functionality for event's delete button.
            /*var eventemail_obj = {
                event_id: varEveryEventEmailBean.event_id,
                eventemail_name: varEveryEventEmailBean.eventemail_name,
                eventemail_id: varEveryEventEmailBean.eventemail_id,
                row_num: i,
                printObj: function () {
                    return this.event_id + ' ' + this.eventemail_name + ' ' + this.eventemail_id + ' row : ' + this.row_num;
                }
            }
            $('#'+varEveryEventEmailBean.eventemail_id).click({param_eventemail_obj:eventemail_obj},function(e){
                displayConfirmBox(
                        "Are you sure you want to delete - " + e.data.param_eventemail_obj.eventemail_name ,
                        "Delete Email",
                        "Yes", "No", deleteEventEmail,e.data.param_eventemail_obj)
            }); */
        }
    }
    function initializeTable(){

        objEveryRoleTable =  $('#every_role').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                { "bSortable": false }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>