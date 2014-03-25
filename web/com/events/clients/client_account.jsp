<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Utility" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.events.bean.users.UserBean" %>
<%@ page import="com.events.users.permissions.CheckPermission" %>
<%@ page import="com.events.common.Perm" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>

<link rel="stylesheet" href="/css/dataTables/jquery.dataTables.css" id="theme_date">
<link rel="stylesheet" href="/css/dataTables/jquery.dataTables_styled.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sClientId = ParseUtil.checkNull(request.getParameter("client_id"));
    String sTitle = "Add Client";
    if(!Utility.isNullOrEmpty(sClientId)) {
        sTitle = "Edit Client";
    }

    boolean canEditClient = false;
    if(session.getAttribute(Constants.USER_LOGGED_IN_BEAN)!=null) {
        UserBean loggedInUserBean = (UserBean)session.getAttribute(Constants.USER_LOGGED_IN_BEAN);
        if(loggedInUserBean!=null && !Utility.isNullOrEmpty(loggedInUserBean.getUserId())) {
            CheckPermission checkPermission = new CheckPermission(loggedInUserBean);
            if(checkPermission!=null ) {
                canEditClient = checkPermission.can(Perm.EDIT_CLIENT);
            }
        }
    }


%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="client_active" value="currently_active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Client - Contact Information</div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    <div id="tabs">
                        <jsp:include page="/com/events/clients/client_tab.jsp">
                            <jsp:param name="client_account_active" value="active"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <h4>Contact Information</h4>
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    <form method="post" id="frm_save_clients">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="clientName" class="form_label">Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="clientName" name="clientName" placeholder="Client Name (e.g Ron and Susan) ">
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="clientEmail" class="form_label">Email</label><span class="required"> *</span>
                                    <input type="email" class="form-control" id="clientEmail" name="clientEmail" placeholder="Email">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-6">
                                    <label for="clientFirstName" class="form_label">First Name</label><span class="required"> *</span>
                                    <input type="text" class="form-control" id="clientFirstName" name="clientFirstName" placeholder="First Name">
                                </div>
                                <div class="col-md-6">
                                    <label for="clientLastName" class="form_label">Last Name</label>
                                    <input type="text" class="form-control" id="clientLastName" name="clientLastName" placeholder="Last Name">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="clientCompanyName" class="form_label">Company Name</label>
                                    <input type="text" class="form-control" id="clientCompanyName" name="clientCompanyName" placeholder="Company Name">
                                </div>
                            </div>
                        </div>

                        <div class="caption toggle-link">
                            <div class="row" id="edit_client_contact">
                                <div class="col-md-8">
                                    <h5><i id="edit_client_contact_icon" class="fa fa-chevron-circle-right"></i><span> Add/Edit Client Contact Information  </span></h5>
                                </div>
                            </div>
                        </div>
                        <div id="client_contact" style="display:none;">
                            <div class="row">
                                <div class="col-md-12">

                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="clientCellPhone" class="form_label">Cell Phone</label>
                                                <input type="tel" class="form-control" id="clientCellPhone" name="clientCellPhone" placeholder="Cell Phone">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="clientWorkPhone" class="form_label">Phone</label>
                                                <input type="tel" class="form-control" id="clientWorkPhone" name="clientWorkPhone" placeholder="Phone">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="clientAddress1" class="form_label">Address 1</label>
                                                <input type="text" class="form-control" id="clientAddress1" name="clientAddress1" placeholder="Address 1">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="clientAddress2" class="form_label">Address 2</label>
                                                <input type="text" class="form-control" id="clientAddress2" name="clientAddress2" placeholder="Address 2">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="clientCity" class="form_label">City</label>
                                                <input type="text" class="form-control" id="clientCity" name="clientCity" placeholder="City">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="clientState" class="form_label">State</label>
                                                <input type="text" class="form-control" id="clientState" name="clientState" placeholder="State">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="clientPostalCode" class="form_label">Postal Code</label>
                                                <input type="text" class="form-control" id="clientPostalCode" name="clientPostalCode" placeholder="Postal Code">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="clientCountry" class="form_label">Country</label>
                                                <input type="text" class="form-control" id="clientCountry" name="clientCountry" placeholder="Country">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="checkbox">
                                        <label for="isCorporateClient" class="form_label">
                                            <input type="checkbox" id="isCorporateClient" name = "isCorporateClient">
                                            Corporate Client
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden"  id="client_id" name="client_id" value="">
                        <input type="hidden"  id="userId" name="userId" value="">
                        <input type="hidden"  id="userInfoId" name="userInfoId" value="">
                    </form>
                    <%
                        if(canEditClient) {
                    %>
                            <div class="row">
                                <div class="col-md-3">
                                    <button type="button" class="btn  btn-filled" id="btn_save_client">Save Changes</button>
                                </div>
                            </div>
                    <%
                        }
                    %>

                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    &nbsp;
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<form id="frm_load_client" >
    <input type="hidden" id="load_client_id" name="client_id" value="<%=sClientId%>"/>
    <input type="hidden" name="client_datatype" value="contact_info"/>
</form>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/clients/clientcontactinfo.js"></script>
<script   type="text/javascript">
    var varClientId = '<%=sClientId%>';
    $(window).load(function() {
        if(varClientId!='') {
            loadClientDetail(varClientId, 'contact_info',populateClientDetail);
        }


        $('#btn_save_client').click(function(){
            saveClient(getResult);
        });

        $('#edit_client_contact').click(function(){
            $( "#client_contact" ).slideToggle( "slow", function() {
                if($('#client_contact').css('display') == 'block') {
                    $('#edit_client_contact_icon').removeClass("fa-chevron-circle-right").addClass("fa-chevron-circle-down");
                }
                if($('#client_contact').css('display') == 'none') {
                    $('#edit_client_contact_icon').removeClass("fa-chevron-circle-down").addClass("fa-chevron-circle-right");
                }
            });


        });
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>

