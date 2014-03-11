<%@ page import="com.events.common.Configuration" %>
<%@ page import="com.events.common.Constants" %><%
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    String sApplicationDomain = applicationConfig.get(Constants.APPLICATION_DOMAIN);
%>
<div class="row">
    <div class="col-md-12">
        <div class="panel-group" id="collapse_url_domain">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#collapse_url_domain_body" href="#collapse_url_domain_body">
                            <i id="url_domain_icon" class="fa fa-chevron-circle-right"></i> URL (Domain)
                        </a>
                    </h4>
                </div>
                <div id="collapse_url_domain_body" class="panel-collapse collapse">
                    <div class="panel-body">
                        <!-- Panel Body Content Here -->
                        <form method="post" id="frm_website_url_domain">
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-6">
                                        <label for="website_subdomain" class="form_label">Your Business Name</label><br>
                                        <input type="text" name="website_subdomain"  id="website_subdomain" class="form-control" placeholder="YourBusinessName (no spaces,no punctuations,no special characters)">
                                        http://<span id="preview_website_subdomain">yourbusinessname</span>.<%=sApplicationDomain%>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="vendorwebsite_id" id="url_domain_vendorwebsite_id" value=""/>
                            <input type="hidden" name="vendor_id" id="url_domain_vendor_id" value=""/>
                        </form>


                        <div class="row">
                            <div class="col-md-2">
                                <button type="button" class="btn btn-default btn-sm" id="btn_url_domain_save_publish">Save & Publish</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>