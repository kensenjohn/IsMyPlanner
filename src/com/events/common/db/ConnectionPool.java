package com.events.common.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionPool {
    private String envName= "";
    private String sUrl = "";
    private String sUser = "";
    private String sPassword = "";
    private Integer iInitConn=0;
    private Integer iMaxConn=0;


    private int checkedOutConns=0;

    ArrayList<Connection> arrFreeConnections = new ArrayList<Connection>();

    public ConnectionPool(String envName, String sUrl, String sUser,
                          String sPassword,Integer iInitConn,Integer iMaxConn) throws SQLException {
        super();
        this.envName = envName;
        this.sUrl = sUrl;
        this.sUser = sUser;
        this.sPassword = sPassword;
        this.iInitConn=iInitConn;
        this.iMaxConn=iMaxConn;


        initPool();
    }

    private void initPool() throws SQLException
    {
        for(int i = 0; i < iInitConn; i++)
        {
            Connection conn = newConnection();
            arrFreeConnections.add(conn);
        }
    }

    private Connection newConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(sUrl, sUser, sPassword) ;
        return conn;
    }


    public Connection getConnection() throws SQLException
    {
        Connection conn = getPooledConnection();
        checkedOutConns++;
        return conn;
    }

    private Connection getPooledConnection() throws SQLException {
        Connection conn = null;

        if( arrFreeConnections!=null && !arrFreeConnections.isEmpty())
        {
            conn = arrFreeConnections.get(0);

            arrFreeConnections.remove(0);
        }
        else if(checkedOutConns<iMaxConn)
        {
            conn = newConnection();
        }
        return conn;
    }

    public void freeConnection(Connection conn) {
        arrFreeConnections.add(conn);
        checkedOutConns--;
    }

    public synchronized void releaseConnection() throws SQLException
    {
        if(arrFreeConnections!=null && !arrFreeConnections.isEmpty())
        {
            for(Connection conn: arrFreeConnections)
            {
                conn.close();
            }
            arrFreeConnections = null;
        }
    }
}
