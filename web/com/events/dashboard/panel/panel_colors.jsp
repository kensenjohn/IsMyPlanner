
<form method="post" id="frm_website_colors">
    <div class="form-group">
        <div class="row">
            <div class="col-md-12">
                <div class="panel-group" id="collapse_website_colors">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">
                                <a data-toggle="collapse" data-parent="#collapse_website_colors" href="#collapse_colors">
                                    <i id="website_color_icon" class="fa fa-chevron-circle-right"></i> Colors
                                </a>
                            </h4>
                        </div>
                        <div id="collapse_colors" class="panel-collapse collapse">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-md-3">
                                        <label for="website_color_bkg" class="form_label">Background</label><br>
                                        <input type="text" value="FFFFFF" name="website_color_bkg"  id="website_color_bkg" class="pick-a-color">
                                    </div>
                                    <div class="col-md-3">
                                        <label for="website_color_highlighted" class="form_label">Highlighted Button/Tabs/Links</label><br>
                                        <input type="text" value="FFFFFF" name="website_color_highlighted"  id="website_color_highlighted" class="pick-a-color">
                                    </div>
                                    <div class="col-md-3">
                                        <label for="website_color_text" class="form_label">Text</label><br>
                                        <input type="text" value="FFFFFF" name="website_color_text"  id="website_color_text" class="pick-a-color">
                                    </div>
                                    <div class="col-md-3">
                                        <label for="website_color_nav_bread_tab_bkg" class="form_label">Top Nav/Bread Crumb/Tabs Background</label><br>
                                        <input type="text" value="FFFFFF" name="website_color_nav_bread_tab_bkg"  id="website_color_nav_bread_tab_bkg" class="pick-a-color">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        &nbsp;
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <label for="website_color_filled_button" class="form_label">Filled Button Text</label><br>
                                        <input type="text" value="FFFFFF" name="website_color_filled_button"  id="website_color_filled_button" class="pick-a-color">
                                    </div>
                                    <div class="col-md-3">
                                        <label for="website_color_filled_button_txt" class="form_label">Filled Button Text</label><br>
                                        <input type="text" value="FFFFFF" name="website_color_filled_button_txt"  id="website_color_filled_button_txt" class="pick-a-color">
                                    </div>
                                    <div class="col-md-3">
                                        <label for="website_color_plain_button" class="form_label">Plain Button</label><br>
                                        <input type="text" value="FFFFFF" name="website_color_plain_button"  id="website_color_plain_button" class="pick-a-color">
                                    </div>
                                    <div class="col-md-3">
                                        <label for="website_color_filled_button_txt" class="form_label">Plain Button Text</label><br>
                                        <input type="text" value="FFFFFF" name="website_color_plain_button_txt"  id="website_color_plain_button_txt" class="pick-a-color">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        &nbsp;
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3">
                                        <label for="website_color_border" class="form_label">Border</label><br>
                                        <input type="text" value="dbf1ff" name="website_color_border"  id="website_color_border" class="pick-a-color">
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-5">
                                        <label for="website_color_combination" class="form_label">Color Combinations for Inspiration</label><span class="required"> *</span>
                                        <select id="website_color_combination" class="form-control">
                                            <option value="modern">Modern (Basic)</option>
                                            <option value="warm_love">Warm Love</option>
                                            <option value="hot_fire">Hot Fire</option>
                                            <option value="ocean_blue">Ocean Blue</option>
                                            <option value="nature_green">Nature Green</option>
                                            <option value="royal_purple">Royal Purple</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col-md-3">
                                        &nbsp;
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-default btn-sm" id="btn_website_color_preview">Preview</button>
                                    </div>
                                    <div class="col-md-2">
                                        <button type="button" class="btn btn-default btn-sm" id="btn_website_color_save">Save</button>
                                    </div>
                                    <div class="col-md-3">
                                        <button type="button" class="btn btn-default btn-sm" id="btn_website_color_publish">Publish (To Public Site)</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <input type="hidden" name="vendorwebsite_id"  id="color_vendorwebsite_id" value=""/>
    <input type="hidden" name="vendor_id"  id="color_vendor_id" value=""/>
    <input type="hidden" name="website_color_panel_action"  id="website_color_panel_action" value=""/>
</form>