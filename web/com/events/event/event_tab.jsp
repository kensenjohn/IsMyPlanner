<%@ page import="com.events.common.ParseUtil" %>

<%
    String sEventDetailsActive = ParseUtil.checkNull(request.getParameter("event_details_active"));
    String sEventGuestsActive = ParseUtil.checkNull(request.getParameter("event_guests_active"));
    String sEventEmailsActive = ParseUtil.checkNull(request.getParameter("event_emails_active"));
    String sEventPhoneActive = ParseUtil.checkNull(request.getParameter("event_phone_active"));
    String sEventWebsiteActive = ParseUtil.checkNull(request.getParameter("event_website_active"));
    String sEventVendorsActive = ParseUtil.checkNull(request.getParameter("event_vendors_active"));
    String sEventEverNoteActive = ParseUtil.checkNull(request.getParameter("event_evernote_active"));
    String sEventBudgetActive = ParseUtil.checkNull(request.getParameter("event_budget_active"));
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
%>
<ul class="tabs">
    <li class="<%=sEventDetailsActive%>"><a href="/com/events/event/edit_event.jsp?event_id=<%=sEventId%>">Details</a></li>
    <li class="<%=sEventGuestsActive%>"><a href="/com/events/event/guest/guests.jsp?event_id=<%=sEventId%>">Guests</a></li>
    <li class="<%=sEventEmailsActive%>"><a href="/com/events/event/email/emails.jsp?event_id=<%=sEventId%>">Emails</a></li>
    <!--<li class="<%=sEventPhoneActive%>"><a href="/com/events/event/phone.jsp?event_id=<%=sEventId%>">Phone</a></li> -->
    <li class="<%=sEventWebsiteActive%>"><a href="/com/events/event/website.jsp?event_id=<%=sEventId%>">Website</a></li>
    <li class="<%=sEventVendorsActive%>"><a href="/com/events/event/event_vendors.jsp?event_id=<%=sEventId%>">Vendors</a></li>
    <!--<li class="<%=sEventEverNoteActive%>"><a href="/com/events/event/event_evernote.jsp?event_id=<%=sEventId%>">Evernote</a></li>-->
    <li class="<%=sEventBudgetActive%>"><a href="/com/events/event/event_budget.jsp?event_id=<%=sEventId%>">Budget</a></li>
</ul>