package com.events.common.db;

import com.events.common.Constants;
import com.events.common.exception.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/11/13
 * Time: 11:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class DBDAO {
    private static final Logger dbLogging = LoggerFactory.getLogger(Constants.DB_LOGS);

    public static ArrayList<HashMap<String, String>> get(String sResource,
                                                         String sQuery, ArrayList<Object> aParams, boolean bUseCache) {
        ArrayList<HashMap<String, String>> alReturn = new ArrayList<HashMap<String, String>>();
        DBPool p = null;
        Connection c = null;

        try {
            p = DBPool.getInstance();
            c = p.getConnection(sResource);
            PreparedStatement s = c.prepareStatement(sQuery);
            ParameterMetaData pmdMeta = s.getParameterMetaData();

            // Prepared Statement setObject() indexes start at 1
            // ArrayList get() indexes start at 0
            if (pmdMeta.getParameterCount() > 0) {
                for (int i = 0; i < aParams.size(); i++) {
                    s.setObject(i + 1, aParams.get(i));
                }
            }

            ResultSet rs = s.executeQuery();
            ResultSetMetaData rsmdMeta = rs.getMetaData();
            int iColumnCount = rsmdMeta.getColumnCount();
            // reset alReturn to a new arraylist here, since we've
            // clobbered it if bUseCache is true, but there is no result.
            alReturn = new ArrayList<HashMap<String, String>>();

            while (rs.next()) {
                HashMap<String, String> alRecord = new HashMap<String, String>(
                        iColumnCount);
                for (int j = 1; j <= iColumnCount; j++) {
                    alRecord.put(rsmdMeta.getColumnName(j), rs.getString(j));
                }
                alReturn.add(alRecord);
            }

            if (bUseCache) {
                // cache.put(sQuery+aParams.toString(), alReturn);
            }
            aParams = null;
            rsmdMeta = null;
            pmdMeta = null;
            rs.close();
            s.close();

            return alReturn;
        } catch (SQLException ex) {
            // for now, SQL errors are unrecoverable
            throw new RuntimeException(ex);
        } finally {
            // free up the connection.
            if (c != null) {
                p.freeConnection(c);
            }
        }
    }

    public static int put(String sResource, String sQuery,
                          ArrayList<Object> aParams) {
        DBPool p = null;
        Connection c = null;
        int iRowsAffected = 0;

        try {
            p = DBPool.getInstance();
            c = p.getConnection(sResource);

            PreparedStatement s = c.prepareStatement(sQuery);
            ParameterMetaData pmdMeta = s.getParameterMetaData();

            // Prepared Statement setObject() indexes start at 1
            // ArrayList get() indexes start at 0
            if (pmdMeta.getParameterCount() > 0) {
                for (int i = 0; i < aParams.size(); i++) {
                    s.setObject(i + 1, aParams.get(i));
                }
            }
            iRowsAffected = s.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            // free up the connection.
            if (c != null) {
                p.freeConnection(c);
            }
        }

        return iRowsAffected;
    }

    public static int[] putBatch(String sResource, String sQuery,
                                 ArrayList<ArrayList<Object> > aBatchParams) {
        DBPool p = null;
        Connection c = null;
        int[] iRowsAffected = new int[aBatchParams.size()];

        try {
            p = DBPool.getInstance();
            c = p.getConnection(sResource);

            PreparedStatement preparedStatement = c.prepareStatement(sQuery);
            ParameterMetaData pmdMeta = preparedStatement.getParameterMetaData();

            // Prepared Statement setObject() indexes start at 1
            // ArrayList get() indexes start at 0
            if (pmdMeta.getParameterCount() > 0 && aBatchParams!=null && !aBatchParams.isEmpty()) {
                for( ArrayList<Object> aParams : aBatchParams ) {
                    for (int i = 0; i < aParams.size(); i++) {
                        preparedStatement.setObject(i + 1, aParams.get(i));
                        preparedStatement.addBatch();
                    }
                }
            }
            iRowsAffected = preparedStatement.executeBatch();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            // free up the connection.
            if (c != null) {
                p.freeConnection(c);
            }
        }

        return iRowsAffected;
    }

    public static Connection getConnection(String sResource) {
        DBPool p = null;
        Connection c = null;

        try {
            p = DBPool.getInstance();
            c = p.getConnection(sResource);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return c;
    }

    public static ArrayList<Object> createConstraint(Object... params) {
        ArrayList<Object> arrParams = new ArrayList<Object>();

        if (params != null) {
            for (Object paramObj : params) {
                arrParams.add(paramObj);
            }
        }

        return arrParams;
    }

    public static String createParamQuestionMarks(Integer iNumOfQuestionMarks){
        StringBuilder strQuestMarks = new StringBuilder();
        for(int i = 0 ; i<iNumOfQuestionMarks; i++ ) {
            if(i>0){
                strQuestMarks.append(",");
            }
            strQuestMarks.append("?");
        }
        return strQuestMarks.toString();
    }

    public static ArrayList<HashMap<String, String>> getDBData(String sDBName,
                                                               String sQuery, ArrayList<Object> aParams, boolean useCache,
                                                               String sInvokingClass, String sInvokingMethod) {
        ArrayList<HashMap<String, String>> arrResult = new ArrayList<HashMap<String, String>>();

        try {
            arrResult = DBDAO.get(sDBName, sQuery, aParams, useCache);
        } catch (Exception e) {
            dbLogging.error(sQuery + ": " + aParams + "\nGet DB: " + sDBName
                    + " Invoking Class: " + sInvokingClass + " Method :"
                    + sInvokingMethod + "\n"
                    + ExceptionHandler.getStackTrace(e));
        }

        return arrResult;
    }

    public static int putRowsQuery(String sQuery, ArrayList<Object> aParams,
                                   String sDBName, String sInvokingClass, String sInvokingMethod) {
        int numOfRowsAffected = 0;

        try {
            numOfRowsAffected = DBDAO.put(sDBName, sQuery, aParams);
        } catch (Exception e) {
            dbLogging.error(sQuery + ": " + aParams + "\nPut DB: " + sDBName
                    + " " + sInvokingClass + " " + sInvokingMethod + "\n"
                    + ExceptionHandler.getStackTrace(e));
        }

        return numOfRowsAffected;

    }
    public static int[] putBatchRowsQuery(String sQuery, ArrayList<ArrayList<Object> > aBatchParams,
                                          String sDBName, String sInvokingClass, String sInvokingMethod) {
        int[] numOfRowsAffected = new int[aBatchParams.size()];
        try {
            numOfRowsAffected = DBDAO.putBatch(sDBName, sQuery, aBatchParams);
        } catch (Exception e) {
            dbLogging.error(sQuery + ": " + aBatchParams + "\nPut DB: " + sDBName
                    + " " + sInvokingClass + " " + sInvokingMethod + "\n"
                    + ExceptionHandler.getStackTrace(e));
        }

        return numOfRowsAffected;

    }

    public static int putCommitRowsQuesry(String sQuery,
                                          ArrayList<Object> aParams, Connection conn, String sInvokingClass,
                                          String sInvokingMethod) {

        int numOfRowsAffected = 0;

        try {
            numOfRowsAffected = DBDAO.putTransaction(conn, sQuery, aParams);
        } catch (Exception e) {
            dbLogging.error(sQuery + ": " + aParams + "\nPut Conn: " + conn
                    + " " + sInvokingClass + " " + sInvokingMethod + "\n"
                    + ExceptionHandler.getStackTrace(e));
        }

        return numOfRowsAffected;
    }

    public static int putTransaction(Connection conn, String sQuery,
                                     ArrayList<Object> aParams) {
        int iRowsAffected = 0;
        if (conn != null) {
            try {
                PreparedStatement s = conn.prepareStatement(sQuery);
                ParameterMetaData pmdMeta = s.getParameterMetaData();

                // Prepared Statement setObject() indexes start at 1
                // ArrayList get() indexes start at 0
                if (pmdMeta.getParameterCount() > 0) {
                    for (int i = 0; i < aParams.size(); i++) {
                        s.setObject(i + 1, aParams.get(i));
                    }
                }
                iRowsAffected = s.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return iRowsAffected;
    }

    public static void commitChanges(Connection conn, String sResource) {
        try {
            conn.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void undoChanges(Connection conn, String sResource) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void freeConnections(Connection conn, String sResource) {

        DBPool p = DBPool.getInstance();
        if (p != null && conn != null && sResource != null
                && !"".equalsIgnoreCase(sResource)) {
            p.freeConnection(conn);
        }
    }
}
