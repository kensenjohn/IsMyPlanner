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
                        <span><i class="fa fa-plus"></i> Add A Vendor</span>
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
    <input type="hidden" id="delete_partner_vendor_id" name="partner_vendor_id" value="">
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/jquery.dataTables.min.js"></script>
<script   type="text/javascript">
    $(window).load(function() {
        loadPartnerVendors(populatePartnerVendorsList);
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
                    var varNumOfPartnerVendors = jsonResponseObj.num_of_partner_vendors;
                    if(varNumOfPartnerVendors!=undefined && varNumOfPartnerVendors>0){
                        processPartnerVendorsList(varNumOfPartnerVendors, jsonResponseObj.every_partner_vendor_bean );
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
    function processPartnerVendorsList(varNumOfPartnerVendors , everyPartnerVendorList  ) {
        for(i=0;i<varNumOfPartnerVendors;i++){
            var varPartnerVendorBean = everyPartnerVendorList[i];
            var varPartnerVendorId =  varPartnerVendorBean.partner_vendor_id;
            var varPartnerVendorType =  varPartnerVendorBean.type;
            var varPartnerVendorName =  varPartnerVendorBean.name;
            var varPartnerVendorEmail =  varPartnerVendorBean.email;
            var varPartnerVendorPhone =  varPartnerVendorBean.phone;


            var rowEveryPartnerVendor= $('<tr id="row_'+varPartnerVendorId+'" ></tr>');
            rowEveryPartnerVendor.append(
                    '<td>'+varPartnerVendorName +'</td>'+
                            '<td>'+varPartnerVendorType +'</td>' +
                            '<td>'+varPartnerVendorEmail +'</td>' +
                            '<td>'+varPartnerVendorPhone +'</td>' +
                            '<td  class="center" >'+ createButtons(varPartnerVendorId) +'</td>');
            $('#every_partner_vendor_rows').append(rowEveryPartnerVendor);

            addDeleteClickEvent(varPartnerVendorId,varPartnerVendorName, i)
        }
    }
    function addDeleteClickEvent(varPartnerVendorId , varPartnerVendorName ,  varRowNum) {
        var partner_vendor_obj = {
            partner_vendor_id: varPartnerVendorId,
            name: varPartnerVendorName,
            row_num: varRowNum,
            printObj: function () {
                return this.partner_vendor_id + ' row : ' + this.row_num;
            }
        }

        $('#del_'+varPartnerVendorId).click({param_partner_vendor_obj:partner_vendor_obj},function(e){
            displayConfirmBox(
                    "Are you sure you want to delete this vendor - " + e.data.param_partner_vendor_obj.name ,
                    "Delete Vendor",
                    "Yes", "No", deletePartnerVendor,e.data.param_partner_vendor_obj)
        });
    }

    function deletePartnerVendor(varPartnerVendorObj) {
        $('#delete_partner_vendor_id').val(varPartnerVendorObj.partner_vendor_id);
        deletePartnerVendorInvoke(processTeamMemberDeletion);
    }
    function deletePartnerVendorInvoke(callbackmethod) {
        var actionUrl = "/proc_delete_partnervendor.aeve";
        var methodType = "POST";
        var dataString = $("#frm_delete_partner_vendor").serialize();
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
                    var varIsPartnerVendorDeleted = jsonResponseObj.is_deleted;
                    if(varIsPartnerVendorDeleted){
                        $('#partner_vendor_id').val('');
                        var varDeletedPartnerVendorId = jsonResponseObj.deleted_partner_vendor_id;
                        objPartnerVendorTable.fnDeleteRow((objPartnerVendorTable.$('#row_'+varDeletedPartnerVendorId))[0] );
                    } else {
                        displayMssgBoxAlert("We were unable to delete the vendor. Please try again later.", true);
                    }
                }
            } else {
                displayMssgBoxAlert("Please try again later (deleteVendor - 1)", true);
            }
        } else {
            displayMssgBoxAlert("Please try again later (deleteVendor - 2)", true);
        }
    }


    function createButtons( varPartnerVendorId ){
        var varButtons = '';
        varButtons = varButtons + createEditButton( varPartnerVendorId );
        varButtons = varButtons + '&nbsp;&nbsp;&nbsp;';
        varButtons = varButtons + createDeleteButton( varPartnerVendorId );
        return varButtons;
    }
    function createEditButton(varPartnerVendorId){
        return '<a href="/com/events/dashboard/partnervendors/edit_partnervendor.jsp?partner_vendor_id='+varPartnerVendorId+'" class="btn btn-default btn-xs"><i class="fa fa-pencil"></i> Edit</a>';
    }
    function createDeleteButton(varPartnerVendorId){
        return '<a id="del_'+varPartnerVendorId+'" class="btn btn-default btn-xs"><i class="fa fa-trash-o"></i></span> Delete</a>';
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