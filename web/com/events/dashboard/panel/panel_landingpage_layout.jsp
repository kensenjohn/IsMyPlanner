<div class="row">
    <div class="col-md-12" >
        <div class="panel-group" id="collapse_landingpage_layout">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">
                        <a data-toggle="collapse" data-parent="#collapse_landingpage_layout_body" href="#collapse_landingpage_layout_body">
                            <i id="landingpage_layout_icon" class="fa fa-chevron-circle-right"></i> Layout and Content
                        </a>
                    </h4>
                </div>
                <div id="collapse_landingpage_layout_body" class="panel-collapse collapse">
                    <div class="panel-body">
                        <!-- Panel Body Content Here -->
                        <div class="row">
                            <div class="col-md-7">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <h5 style="margin-top:20px;">Landing Page Picture</h5>
                                                    </div>
                                                    <div class="col-md-2">
                                                        &nbsp;&nbsp;
                                                        <input type="checkbox" data-size="small" data-on-text="Show" data-off-text="Hide"  class="layout-hide-feature" name="landing_page_picture_hide" id="landing_page_picture_hide" param="landing_page_picture">
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-offset-1 col-md-12">
                                                        <form method="post" id="frm_landingpage_picture"  action="/proc_upload_image.aeve" method="POST" enctype="multipart/form-data">
                                                            <div class="form-group">
                                                                <div class="row">
                                                                    <div class="col-md-6">
                                                                        <label for="landingPagePicture" class="form_label">Landing Page Picture</label><span class="required"> *</span>
                                                                        <input type="file" name="files[]" id="landingPagePicture" class="fileinput-button btn btn-default">
                                                                    </div>
                                                                    <div class="col-md-3">
                                                                        <label class="form_label">&nbsp;</label>
                                                                        <div>
                                                                            <button type="button" class="btn btn-default btn-sm" id="btn_upload_landingpage_pic">Upload Image</button>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-3" id="div_preview_pic">
                                                                        <label class="form_label">&nbsp;</label>
                                                                        <div>
                                                                            <button type="button" class="btn btn-default btn-sm" id="btn_preview_landingpage_pic">Preview Image</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        <div id="picture_progress">
                                                                            <div class="bar" style="width: 0%;"></div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        &nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-offset-1 col-md-4" id="landingpage_image_name">
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        &nbsp;
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form>
                                                        <div class="row">
                                                            <div class="col-md-2">
                                                                <button type="button" class="btn btn-default btn-xs" id="btn_landing_page_image_save">Save & Publish</button>
                                                            </div>
                                                        </div>
                                                        <form id="frm_landingpage_image">
                                                            <input type="hidden" name="landingpage_imagehost" id="landingpage_imagehost" value=""/>
                                                            <input type="hidden" name="landingpage_bucket" id="landingpage_bucket" value=""/>
                                                            <input type="hidden" name="landingpage_foldername" id="landingpage_foldername" value=""/>
                                                            <input type="hidden" name="landingpage_picture" id="landingpage_picture" value=""/>
                                                        </form>
                                                        <div class="row">
                                                            <div class="col-md-2">
                                                                &nbsp;
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <h5 style="margin-top:20px;">Landing Page Greeting</h5>
                                                    </div>
                                                    <div class="col-md-2">
                                                        &nbsp;&nbsp;
                                                        <input type="checkbox" data-size="small" data-on-text="Show" data-off-text="Hide"  class="layout-hide-feature" name="landing_page_greeting_hide" id="landing_page_greeting_hide" param="landing_page_greeting">
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-offset-1 col-md-12">

                                                        <form method="post" id="frm_landingpage_greeting">
                                                            <div class="form-group">
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        <label for="landingpage_greeting_header" class="form_label">Header</label><span class="required"> *</span>
                                                                        <input type="text"  rows="3" class="form-control" id="landingpage_greeting_header" name="landingpage_greeting_header" placeholder="Welcome Header"/>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        &nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        <label for="landingpage_greeting_text" class="form_label">Text</label>
                                                                        <input type="text"  rows="3" class="form-control" id="landingpage_greeting_text" name="landingpage_greeting_text" placeholder="One Place to Plan and Manage Your Event"/>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form>
                                                        <div class="row">
                                                            <div class="col-md-2">
                                                                <button type="button" class="btn btn-default btn-xs" id="btn_landing_page_greeting_save">Save & Publish</button>
                                                            </div>
                                                        </div>

                                                        <div class="row">
                                                            <div class="col-md-2">
                                                                &nbsp;
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="row">
                                                    <div class="col-md-4">
                                                        <h5 style="margin-top:20px;">Social Media</h5>
                                                    </div>
                                                    <div class="col-md-2">
                                                        &nbsp;&nbsp;
                                                        <input type="checkbox" data-size="small" data-on-text="Show" data-off-text="Hide"  class="layout-hide-feature" name="landing_page_social_media" id="landing_page_social_media" param="landing_page_social_media">
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-md-offset-1 col-md-12">
                                                        <form method="post" id="frm_landingpage_socialmedia">
                                                            <div class="form-group">
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        <label for="landingpage_facebook" class="form_label">Facebook URL</label><span class="required"> *</span>
                                                                        <textarea type="textarea"  rows="3" class="form-control" id="landingpage_facebook" name="landingpage_facebook" placeholder="Facebook Feed"></textarea>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        &nbsp;
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-md-12">
                                                                        <label for="landingpage_pinterest" class="form_label">Pinterest URL</label><span class="required"> *</span>
                                                                        <textarea type="textarea"  rows="3" class="form-control" id="landingpage_pinterest" name="landingpage_pinterest" placeholder="Pinterest Feed"></textarea>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </form>
                                                        <div class="row">
                                                            <div class="col-md-2">
                                                                <button type="button" class="btn btn-default btn-xs" id="btn_landing_page_social_media_save">Save & Publish</button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
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
                            <div class="col-md-5"  style="text-align: center;">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div>
                                            <img id="theme_img" src="/img/theme_thmb/simple_landingpagephoto.png">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-12">

                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-2">
                                &nbsp;
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-3">
                                <button type="button" class="btn btn-filled btn-lg" id="btn_landing_page_preview">Preview Landing Page</button>
                            </div>
                        </div>
                        <form id="frm_landingpage">
                            <input type="hidden" name="vendorwebsite_id" id="landingpage_vendorwebsite_id" value=""/>
                            <input type="hidden" name="vendor_id" id="landingpage_vendor_id" value=""/>
                            <input type="hidden" name="website_landingpage_panel_action" id="website_landingpage_panel_action" value=""/>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>