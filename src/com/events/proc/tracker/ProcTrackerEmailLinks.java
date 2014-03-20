package com.events.proc.tracker;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/20/14
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcTrackerEmailLinks extends HttpServlet {
    private static final Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void doGet(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        String sGuestId = ParseUtil.checkNull(request.getParameter("_gk"));
        String sEventEmailId = ParseUtil.checkNull(request.getParameter("_ek"));
        String sUrl = ParseUtil.checkNull(request.getParameter("_url"));
        appLogging.info("Guest Id : " + sGuestId + " Event Email Id : " + sEventEmailId + " URL : " + sUrl );

        if(!Utility.isNullOrEmpty(sUrl)) {
            sUrl = URLDecoder.decode(sUrl,"UTF-8");
        }
        appLogging.info("sUrl after decode : " + sUrl );
        response.sendRedirect( sUrl );

    }
}