package com.events;

import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.Utility;
import com.events.common.db.DBDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/9/13
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJava {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    public Integer sum(Integer a, Integer b) {
        appLogging.info("Sum is good : a = " +  a + " b = " + b);
        return (a + b);
    }
    Configuration applicationConfig = Configuration.getInstance(Constants.APPLICATION_PROP);

    private String EVENTADMIN_DB = applicationConfig.get(Constants.EVENTADMIN_DB);
    public void testDB(String sUid) {
        appLogging.info("EVENTADMIN_DB = " +  EVENTADMIN_DB );

        String sInsertQuery = "INSERT INTO GTUSER (USERID, USERTYPE, FK_USERINFOID, CREATEDATE, IS_TMP, DEL_ROW) VALUES(?,?,?,?,?,?)";
        ArrayList<Object> aParams = DBDAO.createConstraint(sUid, "PLANNER", Utility.getNewGuid(), DateSupport.getEpochMillis(), 0, 0);

        int numOfRowsInserted = DBDAO.putRowsQuery(sInsertQuery, aParams, EVENTADMIN_DB, "TestJava.java", "insertAdmin()");

        appLogging.info("UUID = " +  sUid + " numOfRowsInserted = " + numOfRowsInserted + " sInsertQuery = " + sInsertQuery );
    }
}
