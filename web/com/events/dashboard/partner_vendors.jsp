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
            <div class="page-title">Dashboard - Vendors</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/dashboard/dashboard_tab.jsp">
                            <jsp:param name="vendors_active" value="active"/>
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
                    <a href="/com/events/dashboard/partnervendors/edit_partnervendor.jsp" class="btn btn-filled">
                        <span><span class="glyphicon glyphicon-plus"></span> Add A Vendor</span>
                    </a>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <table cellpadding="0" cellspacing="0" border="0" class="display table dataTable" id="every_partner_vendor" >
                        <thead>
                        <tr role="row">
                            <th class="sorting col-md-3" role="columnheader">Name</th>
                            <th class="sorting" role="columnheader">Type</th>
                            <th class="sorting" role="columnheader">Email </th>
                            <th class="sorting" role="columnheader">Phone </th>
                            <th class="center" role="columnheader"></th>
                        </tr>
                        </thead>
                        <tbody role="alert" id="every_partner_vendor_rows">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_delete_partner_vendor">
    <input type="hidden" id="delete_partner_vendor_id" name="user_id" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        //loadPartnerVendors(populatePartnerVendorsList);
        initializeTable();
    });
    function loadPartnerVendors(callbackmethod) {
        var actionUrl = "/proc_load_all_partner_vendors.aeve";
        var methodType = "POST";
        var dataString = '';
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function populatePartnerVendorsList(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varNumOfTeamMembers = jsonResponseObj.num_of_team_members;
                    if(varNumOfTeamMembers!=undefined && varNumOfTeamMembers>0){
                        processTeamMembersList(varNumOfTeamMembers, jsonResponseObj.team_members );
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
    function processTeamMembersList(varNumOfTeamMembers , everyTeamMemberList  ) {
        for(i=0;i<varNumOfTeamMembers;i++){
            var varUserBean = everyTeamMemberList[i];
            var varUserId =  varUserBean.user_id;
            var varUserInfoId =  varUserBean.user_info_id;

            var varUserInfoBean = varUserBean.user_info_bean;
            var varFirstName = varUserInfoBean.first_name;
            var varLastName = varUserInfoBean.last_name;
            var varEmail = varUserInfoBean.email;

            var rowEveryTeamMember= $('<tr id="row_'+varUserId+'" ></tr>');
            rowEveryTeamMember.append(
                    '<td>'+varFirstName + ' ' +varLastName +'</td>'+
                            '<td>'+varEmail +'</td>' +
                            '<td  class="center" >'+ createButtons(varUserId, varUserInfoId) +'</td>');
            $('#every_team_member_rows').append(rowEveryTeamMember);

            addDeleteClickEvent(varUserId,varEmail, i)
        }
    }
    function addDeleteClickEvent(varUserId , varEmail ,  varRowNum) {
        var userbean_obj = {
            user_id: varUserId,
            user_email: varEmail,
            row_num: varRowNum,
            printObj: function () {
                return this.user_id + ' row : ' + this.row_num;
            }
        }

        $('#del_'+varUserId).click({param_userbean_obj:userbean_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this team member - " + e.data.param_userbean_obj.user_email ,
                    "Delete Team Member",
                    "Yes", "No", deleteTeamMember,e.data.param_userbean_obj)
        });
    }

    function deleteTeamMember(varUserBeanObj) {
        $('#delete_teammember_user_id').val(varUserBeanObj.user_id);
        deleteTeamMemberInvoke(processTeamMemberDeletion);
    }
    function deleteTeamMemberInvoke(callbackmethod) {
        var actionUrl = "/proc_delete_teammember.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_team_member").serialize();
        makeAjaxCall(actionUrl,dataString,methodType,callbackmethod);
    }
    function processTeamMemberDeletion(jsonResult) {
        if(jsonResult!=undefined) {
            var varResponseObj = jsonResult.response;
            if(jsonResult.status == 'error'  && varResponseObj !=undefined ) {
                displayAjaxError(varResponseObj);
            } else if( jsonResult.status == 'ok' && varResponseObj !=undefined) {
                var varIsPayloadExist = varResponseObj.is_payload_exist;
                if(varIsPayloadExist == true) {
                    var jsonResponseObj = varResponseObj.payload;
                    var varIsTeamMemberDeleted = jsonResponseObj.is_deleted;
                    if(varIsTeamMemberDeleted){
                        $('#user_id').val('');
                        var varDeletedTeamMemberUserId = jsonResponseObj.deleted_user_id;
                        objTeamMemberTable.fnDeleteRow((objTeamMemberTable.$('#row_'+varDeletedTeamMemberUserId))[0] );
                    } else {
                        displayMssgBoxAlert("The team member was not deleted. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteTeamMember - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteTeamMember - 2)", true);
        }
    }


    function createButtons( varUserId , varUserInfoId ){
        var varButtons = '';
        varButtons = varButtons + createEditButton( varUserId , varUserInfoId );
        varButtons = varButtons + '&nbsp;&nbsp;&nbsp;';
        varButtons = varButtons + createDeleteButton( varUserId );
        return varButtons;
    }
    function createEditButton(varUserId , varUserInfoId){
        return '<a href="/com/events/dashboard/team/edit_teammember.jsp?user_id='+varUserId+'&userinfo_id=' + varUserInfoId +'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-pencil"></span> Edit</a>';
    }
    function createDeleteButton(varUserId){
        return '<a id="del_'+varUserId+'" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-trash"></span> Delete</a>';
    }

    function initializeTable(){

        objPartnerVendorTable =  $('#every_partner_vendor').dataTable({
            "bPaginate": false,
            "bInfo": false,

            "aoColumns": [
                null,
                null,
                null,
                null,
                { "bSortable": false }
            ]
        });
    }
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>