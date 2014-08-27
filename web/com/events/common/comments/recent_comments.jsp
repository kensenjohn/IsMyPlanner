<%@ page import="com.events.common.ParseUtil" %><%
    String sParentCommentId = ParseUtil.checkNull(request.getParameter("parent_comment_id"));
%>
<div class="row">
    <div class="col-md-12">
        <h5>Recent Comments</h5>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div id="comment_section"></div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <form id="frm_comment">
            <textarea id="comment_body" name="comment_body"></textarea>
            <input type="hidden" name="parent_comment_id" id="parent_comment_id" value="<%=sParentCommentId%>" />
        </form>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        &nbsp;
    </div>
</div>
<div class="row">
    <div class="col-md-2 col-md-push-10">
        <button type="button" class="btn btn-sm btn-default" id="btn_save_comment">Comment</button>
    </div>
</div>
<form id="frm_load_comments" >
    <input type="hidden" id="load_parent_id" name="parent_id" value="<%=sParentCommentId%>"/>
</form>
<script id="template_comment_row" type="text/x-handlebars-template">
    <div class="boxedcontent">
        <div class="widget">
            <div class="content" style="padding-top: 5px;padding-bottom: 5px;">
                <div class="row">
                    <div class="col-md-12">
                        {{{comment_text}}}
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-9 col-md-push-3 text-right">
                        {{comment_username}} at {{comment_date_time}}
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
</script>