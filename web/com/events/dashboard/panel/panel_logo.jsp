<div class="row">
    <div class="col-md-12">
        <div class="panel-group" id="collapse_logo">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#collapse_logo_body" href="#collapse_logo_body">
                            <i id="logo_icon" class="fa fa-chevron-circle-right"></i> Logo
                        </a>
                    </h4>
                </div>
                <div id="collapse_logo_body" class="panel-collapse collapse">
                    <div class="panel-body">
                        <!-- Panel Body Content Here -->
                        <div class="row">
                            <div class="col-md-6">
                                <form method="post" id="frm_logo"  action="/proc_upload_image.aeve" method="POST" enctype="multipart/form-data">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-7">
                                                <label for="landingPageLogo" class="form_label">Logo</label><span class="required"> *</span>
                                                <input type="file" name="files[]" id="landingPageLogo" class="fileinput-button btn btn-default">
                                            </div>
                                            <div class="col-md-3">
                                                <label class="form_label">&nbsp;</label>
                                                <div>
                                                    <button type="button" class="btn btn-default btn-sm" id="btn_upload_logo">Upload Image</button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div id="logo_progress">
                                                    <div class="bar" style="width: 0%;"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                &nbsp;
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-md-offset-1 col-md-2" id="logo_image_name">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                &nbsp;
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                <button type="button" class="btn btn-default btn-sm" id="btn_logo_preview">Preview</button>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-default btn-sm" id="btn_logo_save">Save</button>
                            </div>
                            <div class="col-md-3">
                                <button type="button" class="btn btn-default btn-sm" id="btn_logo_publish">Publish (To Public Site)</button>
                            </div>
                        </div>
                        <form id="frm_website_logo">
                            <input type="hidden" name="logo_imagehost" id="logo_imagehost" value=""/>
                            <input type="hidden" name="logo_foldername" id="logo_foldername" value=""/>
                            <input type="hidden" name="logo_imagename" id="logo_imagename" value=""/>
                            <input type="hidden" name="vendorwebsite_id" id="logo_vendorwebsite_id" value=""/>
                            <input type="hidden" name="vendor_id" id="logo_vendor_id" value=""/>
                            <input type="hidden" name="website_logo_panel_action" id="website_logo_panel_action" value=""/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>