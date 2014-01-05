<%
    com.events.common.Configuration applicationConfig = com.events.common.Configuration.getInstance(com.events.common.Constants.APPLICATION_PROP);

    String sCopyrightYear = applicationConfig.get("copyright_year");
    String sCopyrightCompany = applicationConfig.get("copyright_company");
%>