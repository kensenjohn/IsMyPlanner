package com.events.common.service;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/20/14
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class LinkReplacer {

    private Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private String sApplicationDomain = applicationConfig.get(Constants.APPLICATION_DOMAIN);
    private String sProtocol = applicationConfig.get(Constants.PROP_LINK_PROTOCOL,"http");

    public static void main(String[] args){
        String sHtmlBody = "<html><body>There is random stuff<br><a href=\"http://www.google.com?ui=werw-sdfsd-&si=sdfsw kensen&_jopd=snihoo\">www.google.com</a><br>" +
                "<table><tr><td><div class=\"topbar-dialog siteSwitcher-dialog dno\"> <div class=\"header\"> <h3><a href=\"//stackoverflow.com\">current community</a></h3> </div> <div class=\"modal-content current-site-container\"> <ul class=\"current-site\"> <li> <div class=\"related-links\"> <a href=\"http://chat.stackoverflow.com\"     data-gps-track=\"site_switcher.click({ item_type:6 })\">chat</a> <a href=\"http://blog.stackexchange.com\"     data-gps-track=\"site_switcher.click({ item_type:7 })\"\n" +
                ">blog</a> </div> <a href=\"//stackoverflow.com\" class=\"current-site-link site-link js-gps-track\" data-id=\"1\"data-gps-track=\" site_switcher.click({ item_type:3 })\"> <div class=\"site-icon favicon favicon-stackoverflow\" title=\"Stack Overflow\"></div> Stack Overflow </a></td></tr></table>" +
                "</body></html>";
        LinkReplacer linkReplacer = new LinkReplacer();
        String sFinalEmailBody = linkReplacer.createLinkTrackers(sHtmlBody );

        Map<String, Object> mapTextEmailValues = new HashMap<String, Object>();
        mapTextEmailValues.put("EVENT_ID","12345");
        mapTextEmailValues.put("GUEST_ID", "qwerty");

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustacheText =  mf.compile(new StringReader(sFinalEmailBody), "url_tracker_link_text");
        StringWriter urlTrackerLinkWriter = new StringWriter();
        try {
            mustacheText.execute(urlTrackerLinkWriter, mapTextEmailValues).flush();
        } catch (IOException e) {
            urlTrackerLinkWriter = new StringWriter();
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println("\n\nafter mustache |" + urlTrackerLinkWriter + "|");
    }

    private String createLinkTrackers(String emailBody ){
        System.out.println("before emailBody = |" + emailBody + "|" );
        String finalEmailBody = emailBody;
        if(!Utility.isNullOrEmpty(emailBody) ) {
            String sTmpHtmlBody = Constants.EMPTY;
            boolean isParseComplete = false;
            int indexStartPos = 0;
            int indexEndPos = 0;
            while(!isParseComplete) {
                int hrefPosition = emailBody.indexOf("href=\"");
                if(hrefPosition<0 ){
                    isParseComplete = true;
                    sTmpHtmlBody = sTmpHtmlBody + emailBody;
                    finalEmailBody = sTmpHtmlBody;
                    break;
                } else {
                    indexEndPos = hrefPosition;
                }
                //System.out.println("hrefPosition = " + hrefPosition );
                sTmpHtmlBody = sTmpHtmlBody + emailBody.substring( indexStartPos , indexEndPos+6 );
                //System.out.println("sTmpHtmlBody (1) = |" + sTmpHtmlBody + "|" );

                String urlLink = emailBody.substring( indexEndPos+6);
                //System.out.println("urlLink (1)  = |" + urlLink + "|" );
                urlLink = urlLink.substring( 0 , urlLink.indexOf("\""));
                //System.out.println("urlLink (2)  = |" + urlLink + "|" );

                try {
                    sTmpHtmlBody = sTmpHtmlBody + generateTrackerLink( urlLink );
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    break;
                }

                //System.out.println("sTmpHtmlBody (2)  = |" + sTmpHtmlBody + "|" );
                emailBody = emailBody.substring( indexEndPos+6 + urlLink.length() );
                //System.out.println("emailBody before cycle starts again = |" + emailBody + "|" );


               // isParseComplete = true;
            }
        }

        System.out.println("finalEmailBody = |" + finalEmailBody + "|" );
        return finalEmailBody;
    }

    private String generateTrackerLink(String sUrl) throws UnsupportedEncodingException {
        String sFinalUrl = ParseUtil.checkNull(sUrl);
        if(!Utility.isNullOrEmpty(sUrl)) {
            String sTmpLink = sProtocol+"://"+sApplicationDomain+"/linker?_url="+ URLEncoder.encode(sUrl,"UTF-8")+"&_ek={{EVENT_ID}}&_gk={{GUEST_ID}}";
            sFinalUrl = sTmpLink;
        }
        return sFinalUrl;
    }
}
