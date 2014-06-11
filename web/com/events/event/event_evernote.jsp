<%@ page import="com.events.common.ParseUtil" %>
<%@ page import="com.events.common.Constants" %>
<%@ page import="com.evernote.auth.EvernoteService" %>
<%@ page import="org.scribe.builder.api.EvernoteApi" %>
<%@ page import="org.scribe.oauth.OAuthService" %>
<%@ page import="org.scribe.builder.ServiceBuilder" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="org.scribe.model.Token" %>
<%@ page import="org.scribe.model.Verifier" %>
<%@ page import="com.evernote.auth.EvernoteAuth" %>
<%@ page import="com.evernote.clients.ClientFactory" %>
<%@ page import="com.evernote.edam.type.Notebook" %>
<%@ page import="com.evernote.clients.NoteStoreClient" %>
<%@ page import="java.util.List" %>
<%@ page import="com.evernote.edam.notestore.NoteFilter" %>
<%@ page import="com.evernote.edam.notestore.NoteList" %>
<%@ page import="com.evernote.edam.type.Note" %>
<jsp:include page="/com/events/common/header_top.jsp">
    <jsp:param name="page_title" value=""/>
</jsp:include>
<link rel="stylesheet" href="/css/datepicker/default.css" id="theme_base">
<link rel="stylesheet" href="/css/datepicker/default.date.css" id="theme_date">
<link rel="stylesheet" href="/css/datepicker/default.time.css" id="theme_time">
<jsp:include page="/com/events/common/header_bottom.jsp"/>
<%
    String sEventId = ParseUtil.checkNull(request.getParameter("event_id"));
    boolean loadEventInfo = false;
    if(sEventId!=null && !"".equalsIgnoreCase(sEventId)) {
        loadEventInfo = true;
    }

    /*
 * Fill in your Evernote API key. To get an API key, go to
 * http://dev.evernote.com/documentation/cloud/
 */
    final String CONSUMER_KEY = "smarasoftdeveloper";
    final String CONSUMER_SECRET = "c12b8f87904014d1";

/*
 * Replace this value with EvernoteService.PRODUCTION to switch from the Evernote
 * sandbox server to the Evernote production server.
 */
    final EvernoteService EVERNOTE_SERVICE = EvernoteService.SANDBOX;
    final String CALLBACK_URL = "/com/events/event/event_evernote.jsp?action=callbackReturn";

    String accessToken = (String)session.getAttribute("accessToken");
    String requestToken = (String)session.getAttribute("requestToken");
    String requestTokenSecret = (String)session.getAttribute("requestTokenSecret");
    String verifier = (String)session.getAttribute("verifier");
    String noteStoreUrl = (String)session.getAttribute("noteStoreUrl");

    String action = request.getParameter("action");

    String thisUrl = request.getRequestURL().toString();
    String cbUrl = thisUrl.substring(0, thisUrl.lastIndexOf('/') + 1) + CALLBACK_URL;

    Class<? extends EvernoteApi> providerClass = EvernoteApi.Sandbox.class;
    if (EVERNOTE_SERVICE == EvernoteService.PRODUCTION) {
        providerClass = org.scribe.builder.api.EvernoteApi.class;
    }
    OAuthService service = new ServiceBuilder()
            .provider(providerClass)
            .apiKey(CONSUMER_KEY)
            .apiSecret(CONSUMER_SECRET)
            .callback(cbUrl)
            .build();
%>
<body>
<div class="page_wrap">
    <jsp:include page="/com/events/common/top_nav.jsp">
        <jsp:param name="AFTER_LOGIN_REDIRECT" value="index.jsp"/>
    </jsp:include>
    <jsp:include page="/com/events/common/menu_bar.jsp">
        <jsp:param name="event_active" value="active"/>
    </jsp:include>
    <div class="breadcrumb_format">
        <div class="container">
            <div class="page-title">Event Evernote - <span id="event_title"></span></div>
        </div>
    </div>
    <div class="container">
        <div class="content_format">
            <div class="row">
                <div class="col-md-12">
                    &nbsp;
                </div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <%
                        try {
                            if ("reset".equals(action)) {
                                System.err.println("Resetting");
                                // Empty the server's stored session information for the current
                                // browser user so we can redo the test.
                                for (Enumeration<?> names = session.getAttributeNames(); names.hasMoreElements();) {
                                    session.removeAttribute((String)names.nextElement());
                                }
                                accessToken = null;
                                requestToken = null;
                                requestTokenSecret = null;
                                verifier = null;
                                noteStoreUrl = null;
                                out.println("Removed all attributes from user session");

                            } else if ("getRequestToken".equals(action)) {
                                // Send an OAuth message to the Provider asking for a new Request
                                // Token because we don't have access to the current user's account.
                                Token scribeRequestToken = service.getRequestToken();

                                out.println("<br/><b>Reply:</b> <br/> <span style=\"word-wrap: break-word\">" + scribeRequestToken.getRawResponse() + "</span>");
                                requestToken = scribeRequestToken.getToken();
                                requestTokenSecret = scribeRequestToken.getSecret();
                                session.setAttribute("requestToken", requestToken);
                                session.setAttribute("requestTokenSecret", requestTokenSecret);

                            } else if ("getAccessToken".equals(action)) {
                                // Send an OAuth message to the Provider asking to exchange the
                                // existing Request Token for an Access Token
                                Token scribeRequestToken = new Token(requestToken, requestTokenSecret);
                                Verifier scribeVerifier = new Verifier(verifier);
                                Token scribeAccessToken = service.getAccessToken(scribeRequestToken, scribeVerifier);
                                EvernoteAuth evernoteAuth = EvernoteAuth.parseOAuthResponse(EVERNOTE_SERVICE, scribeAccessToken.getRawResponse());
                                out.println("<br/><b>Reply:</b> <br/> <span style=\"word-wrap: break-word\">" + scribeAccessToken.getRawResponse() + "</span>");
                                accessToken = evernoteAuth.getToken();
                                noteStoreUrl = evernoteAuth.getNoteStoreUrl();
                                session.setAttribute("accessToken", accessToken);
                                session.setAttribute("noteStoreUrl", noteStoreUrl);

                            } else if ("callbackReturn".equals(action)) {
                                requestToken = request.getParameter("oauth_token");
                                verifier = request.getParameter("oauth_verifier");
                                session.setAttribute("verifier", verifier);

                            } else if ("listNotebooks".equals(action)) {
                                out.println("Listing notebooks from: " + noteStoreUrl);
                                EvernoteAuth evernoteAuth = new EvernoteAuth(EVERNOTE_SERVICE, accessToken);
                                NoteStoreClient noteStoreClient = new ClientFactory(evernoteAuth).createNoteStoreClient();

                                List<Notebook> notebooks = noteStoreClient.listNotebooks();
                                out.println("<br>");
                                for (Notebook notebook : notebooks) {
                                    out.println("Notebook: " + notebook.getName() + "<br>");
                                }

                            }  else if ("displayNotes".equals(action)) {
                                EvernoteAuth evernoteAuth = new EvernoteAuth(EVERNOTE_SERVICE, accessToken);
                                NoteStoreClient noteStoreClient = new ClientFactory(evernoteAuth).createNoteStoreClient();


                                List<Notebook> notebooks = noteStoreClient.listNotebooks();
                                for (Notebook tmpNotebook : notebooks) {
                                    out.println("<h3>" + tmpNotebook.getName() + "</h3>");

                                    NoteFilter noteFilter = new NoteFilter();
                                    noteFilter.setNotebookGuid( tmpNotebook.getGuid() );

                                    NoteList notes = noteStoreClient.findNotes(noteFilter, 0 ,10);

                                    for(Note note : notes.getNotes() )  {
                                        out.println(" <h4>" + note.getTitle() + "</h4>");


                                        out.println( noteStoreClient.getNoteContent( note.getGuid() ) + "<br>******************<br>");
                                    }
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            out.println(e.toString());
                        }
                    %>

                    <!-- Information used by consumer -->
                    <h3>Evernote EDAM API Web Test State</h3>
                    Consumer key: <%= CONSUMER_KEY %><br />
                    Request token URL: <%= EVERNOTE_SERVICE.getRequestTokenEndpoint() %> <br />
                    Access token URL: <%= EVERNOTE_SERVICE.getAccessTokenEndpoint() %> <br />
                    Authorization URL Base: <%= EVERNOTE_SERVICE.getHost() %> <br />
                    <br />
                    User request token: <%= requestToken %><br />
                    User request token secret: <%= requestTokenSecret %><br />
                    User oauth verifier: <%= verifier %><br />
                    User access token: <%= accessToken %><br />
                    User NoteStore URL: <%= noteStoreUrl %>

                    <!-- Manual operation controls -->
                    <hr />
                    <h3>Actions</h3>

                    <ol>
                    <!-- Step 1 in OAuth authorization: obtain an unauthorized request token from the provider -->
                    <li>
                        <%
                            if (requestToken == null && accessToken == null) {
                        %>
                        <a href='?action=getRequestToken'>Get OAuth Request Token from Provider</a>
                        <%
                        } else {
                        %>
                        Get OAuth Request Token from Provider
                        <%
                            }
                        %>
                    </li>

                    <!-- Step 2 in OAuth authorization: redirect the user to the provider to authorize the request token -->
                    <li>
                        <%
                            if (requestToken != null && verifier == null && accessToken == null) {
                        %>
                        <a href='<%= EVERNOTE_SERVICE.getAuthorizationUrl(requestToken) %>'>Send user to get authorization</a>
                        <%
                        } else {
                        %>
                        Send user to get authorization
                        <%
                            }
                        %>
                    </li>

                    <!-- Step 3 in OAuth authorization: exchange the authorized request token for an access token -->
                    <li>
                        <%
                            if (requestToken != null && verifier != null && accessToken == null) {
                        %>
                        <a href="?action=getAccessToken"> Get OAuth Access Token from Provider </a>
                        <%
                        } else {
                        %>
                        Get OAuth Access Token from Provider
                        <%
                            }
                        %>
                    </li>

                    <!-- Step 4 in OAuth authorization: use the access token that you obtained -->
                    <!-- In this sample, we simply list the notebooks in the user's Evernote account -->
                    <li>
                        <%
                            if (accessToken != null) {
                        %>
                        <a href="?action=listNotebooks">List notebooks in account</a><br >
                        <%
                        } else {
                        %>
                        List notebooks in account
                        <%
                            }
                        %>
                    </li>
                        <li>
                            <%
                                if (accessToken != null) {
                            %>
                            <a href="?action=displayNotes">Display Notes in Notebook</a><br >
                            <%
                            } else {
                            %>
                            Display Notes in Notebook
                            <%
                                }
                            %>
                        </li>

                    </ol>
                    <a href="?action=reset">Reset user session</a>
                </div>
            </div>


        </div>
    </div>
</div>
</body>
<jsp:include page="/com/events/common/footer_top.jsp"/>
<script src="/js/event/event_info.js"></script>
<script type="text/javascript">
    var varEventId = '<%=sEventId%>';
    $(window).load(function() {
        loadEventInfo(populateEventInfo,varEventId);
    });
</script>
<jsp:include page="/com/events/common/footer_bottom.jsp"/>