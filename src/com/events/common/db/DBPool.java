package com.events.common.db;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import com.events.common.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBPool {
    private static final Logger dbErrorLogging = LoggerFactory.getLogger(Constants.DBERROR_LOGS);
    private ArrayList<Driver> arrDriver = new ArrayList<Driver>();
    private HashMap<String, ConnectionPool> hmConnPool = new HashMap<String, ConnectionPool>();

    private static DBPool dbPool = null;

    private DBPool() {
        // init();
    }

    private void init() {
        Properties dbProps = new Properties();

        try {
            InputStream dbConnPropStream = new FileInputStream(
                    Constants.DBCONN_PROP);
            dbProps.load(dbConnPropStream);
            loadDriver(dbProps);
            loadConnPoolParams(dbProps);
        } catch (FileNotFoundException e) {
            dbErrorLogging.error(ExceptionHandler.getStackTrace(e));
        } catch (IOException e) {
            dbErrorLogging.error(ExceptionHandler.getStackTrace(e));
        }
    }

    private void loadConnPoolParams(Properties dbProps) {
        String sDBEnv = dbProps.getProperty("db_env");

        StringTokenizer stDbEnv = new StringTokenizer(sDBEnv, "|", false);

        while (stDbEnv.hasMoreElements()) {
            String dbEnvName = (String) stDbEnv.nextElement();

            String sUrl = dbProps.getProperty(dbEnvName + ".url");
            String sUserName = dbProps.getProperty(dbEnvName + ".user");
            String sPassword = dbProps.getProperty(dbEnvName + ".password");
            int iInitConn = ParseUtil.sToI(dbProps.getProperty(dbEnvName + ".initConn"));
            int iMaxConn = ParseUtil.sToI(dbProps.getProperty(dbEnvName + ".maxConn"));

            try {
                ConnectionPool connPool = new ConnectionPool(dbEnvName, sUrl, sUserName, sPassword, iInitConn, iMaxConn);
                hmConnPool.put(dbEnvName, connPool);
            } catch (SQLException e) {
                dbErrorLogging.error(ExceptionHandler.getStackTrace(e));
            }

        }
    }

    private void loadDriver(Properties dbProps) {

        // we are assuming default is MySQL
        String sDriverClass = dbProps.getProperty("drivers","com.mysql.jdbc.Driver");

        try {
            Driver dbDriver = (Driver) Class.forName(sDriverClass).newInstance();
            DriverManager.registerDriver(dbDriver);

            if (dbDriver != null) {
                arrDriver.add(dbDriver);
            }

        } catch (InstantiationException e) {
            dbErrorLogging.error(ExceptionHandler.getStackTrace(e));
        } catch (IllegalAccessException e) {
            dbErrorLogging.error(ExceptionHandler.getStackTrace(e));
        } catch (ClassNotFoundException e) {
            dbErrorLogging.error(ExceptionHandler.getStackTrace(e));
        } catch (SQLException e) {
            dbErrorLogging.error(ExceptionHandler.getStackTrace(e));
        }

    }

    public static DBPool getInstance() {
        if (dbPool == null) {
            dbPool = new DBPool();
        }
        return dbPool;
    }

    public Connection getConnection(String sResource) {

        Connection conn = null;
        try {
            // conn = connPool.getConnection();

            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/" + sResource);

            conn = ds.getConnection();

        } catch (SQLException e) {
            dbErrorLogging.error(ParseUtil.checkNull(sResource) + "  " + ExceptionHandler.getStackTrace(e));
        } catch (NamingException e) {
            dbErrorLogging.error(ParseUtil.checkNull(sResource) + "  " + ExceptionHandler.getStackTrace(e));
        } catch (Exception e) {
            dbErrorLogging.error(ParseUtil.checkNull(sResource) + "  " + ExceptionHandler.getStackTrace(e));
        }

        return conn;
    }

    public void freeConnection(Connection conn) {

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                dbErrorLogging.error( ExceptionHandler.getStackTrace(e) );
            }
        }
    }
}
