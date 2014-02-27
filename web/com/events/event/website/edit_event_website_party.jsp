<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.event.website.AccessEventParty" %>
<%@ page import="com.events.bean.event.website.EventPartyRequest" %>
<%@ page import="com.events.bean.event.website.EventPartyBean" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="java.util.ArrayList" %><%
    String sPartyType = ParseUtil.checkNull(request.getParameter("party"));
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    String sEventPartyId = ParseUtil.checkNull(request.getParameter("event_party_id"));

    EventPartyRequest eventPartyRequest = new EventPartyRequest();
    eventPartyRequest.setEventPartyId(sEventPartyId);

    AccessEventParty accessEventParty = new AccessEventParty();
    EventPartyBean eventPartyBean = accessEventParty.getEventParty( eventPartyRequest );
    ArrayList<Constants.EVENT_PARTY_TYPE> arrEventParty = new ArrayList<Constants.EVENT_PARTY_TYPE>();
    if("bm".equalsIgnoreCase(sPartyType)){
        arrEventParty.add(Constants.EVENT_PARTY_TYPE.BRIDESMAID);
        arrEventParty.add(Constants.EVENT_PARTY_TYPE.MAIDOFHONOR);
    } else if("gm".equalsIgnoreCase(sPartyType)){
        arrEventParty.add(Constants.EVENT_PARTY_TYPE.GROOMSMAN);
        arrEventParty.add(Constants.EVENT_PARTY_TYPE.BESTMAN);
    }
%>
<html>
<body>
<div class="container">
    <div class="content_format">
        <form id="frm_save_couples">
            <div class="form-group">
                <script id="template_colors" type="text/x-handlebars-template">
                    <div class="row">
                        <div class="col-md-12" id="partner_type">
                            <select>
                            <%
                                for (Constants.EVENT_PARTY_TYPE eventPartyType : arrEventParty) {
                            %>
                                <option <%=ParseUtil.checkNull(eventPartyBean.getEventPartyType().toString()).equalsIgnoreCase(eventPartyType.toString())?"selected":""%>><%=eventPartyType.getText()%></option>
                            <%
                                }
                            %>
                            </select>
                        </div>
                    </div>
                </script>
                <div class="row">
                    <div class="col-md-12">
                        <label for="couples_partner1_name" class="form_label">Name</label>
                        <input type="text" name="partner1_name" id="couples_partner1_name" class="form-control" value="<%=ParseUtil.checkNull(eventPartyBean.getName())%>">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label for="couples_partner1_facebook" class="form_label">Facebook URL </label>
                        <input type="text" name="partner1_facebook" id="couples_partner1_facebook" class="form-control" >
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label for="couples_partner1_twitter" class="form_label">Twitter URL </label>
                        <input type="text" name="partner1_twitter" id="couples_partner1_twitter" class="form-control" >
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label for="couples_partner1_pinterest" class="form_label">Pinterest URL </label>
                        <input type="text" name="partner1_pinterest" id="couples_partner1_pinterest" class="form-control" >
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <label for="couples_partner1_description" class="form_label">Description </label>
                        <input type="text" name="partner1_description" id="couples_partner1_description" class="form-control"  value="<%=ParseUtil.checkNull(eventPartyBean.getDescription())%>" >
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>