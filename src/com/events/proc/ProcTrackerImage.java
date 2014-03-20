package com.events.proc;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 3/19/14
 * Time: 8:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcTrackerImage extends HttpServlet {
    private static final Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);

    public void doGet(HttpServletRequest request,  HttpServletResponse response)  throws ServletException, IOException {
        String sGuestId = ParseUtil.checkNull(request.getParameter("_gk"));
        String sEventId = ParseUtil.checkNull(request.getParameter("_ek"));
        appLogging.info("Guest Id : " + sGuestId + " Event Id : " + sEventId );
    }
}
