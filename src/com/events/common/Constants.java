package com.events.common;

/**
 * Created with IntelliJ IDEA.
 * User: kensen
 * Date: 12/11/13
 * Time: 9:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    // Log Files
    public final static String APPLICATION_LOG = "ApplicationLog";
    public final static String CONFIG_LOGS = "ConfigLogging";
    public final static String DB_LOGS = "DBLogging";
    public final static String DBERROR_LOGS = "DBErrorLogging";

    // Prop Files
    public final static String PROP_FILE_PATH = "/etc/events/props/";
    public final static String DBCONN_PROP = PROP_FILE_PATH + "dbconnections.prop";
    public final static String APPLICATION_PROP = PROP_FILE_PATH + "application.prop";
    public final static String ANALYTICS_PROP = PROP_FILE_PATH + "analytics.prop";
    public final static String UNSAFE_INPUT_PROP = PROP_FILE_PATH + "unsafe_input.prop";

    public static String EVENTADMIN_DB = "event_admin_db";
    public static String EVENTHOST_DB = "event_host_db";

    public static String DATE_PATTERN_TZ = "yyyy-MM-dd HH:mm:ss z";
    public static String PRETTY_DATE_PATTERN_1 = "MM/dd/yyyy hh:mm a z";
    public static String PRETTY_DATE_PATTERN_2 = "MM/dd/yyyy";

    public final static String INSECURE_PARAMS_ERROR = "INSECURE_PARAMS_ERROR";
    public final static String IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN";
    public final static String EMPTY = "";
    public final static String AFTER_LOGIN_REDIRECT = "AFTER_LOGIN_REDIRECT";
    public final static String USER_LOGGED_IN_BEAN = "USER_LOGGED_IN_BEAN";
    public final static String DASHBOARD_LINK = "/com/events/dashboard.jsp";
    public final static String J_RESP_SUCCESS = "success";
    public static String DEFAULT_TIMEZONE = "UTC";

    public final static String UPLOAD_LOCATION = "file_upload_location";

    public enum TIME_UNIT {  SECONDS,MINUTES, HOURS, DAYS, MONTHS,YEARS; }

    public enum PROP_UNSAFE_WORD_FILTER {
        POTENTIAL_UNSAFE_WORD("potential_unsafe_words"), DEFINITE_UNSAFE_WORD("definite_unsafe_words"), DELIMITER("delimiter"),
        IS_FILTER_ENABLED("is_filter_enabled","true"),RELOAD_FILTER_PARAM_INTERVAL_IN_MINS("reload_filter_param_interval_in_minutes","15");
        private String unsafeWordFilter = "";
        private String defaultValue = "";

        PROP_UNSAFE_WORD_FILTER(String sUnsafeWordFilter) {
            this.unsafeWordFilter =sUnsafeWordFilter;
        }
        PROP_UNSAFE_WORD_FILTER(String sUnsafeWordFilter, String sDefaultValue) {
            this.unsafeWordFilter = sUnsafeWordFilter;
            this.defaultValue = sDefaultValue;
        }
        public String getUnsafeWordFilterPropKey() {
            return unsafeWordFilter;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    public enum ANALYTICS_KEYS {
        GOOGLE_TRACKING_ID("google_tracking_id"),
        MIXPANEL_TRACKING_ID("mixpanel.trackerid");

        private String sAnalyticKey = "";
        ANALYTICS_KEYS(String sAnalyticKey){
            this.sAnalyticKey = sAnalyticKey;
        }

        public String getKey()  {
            return this.sAnalyticKey;
        }
    }

    public enum PASSWORD_STATUS {
        ACTIVE("act"), OLD("old"), DELETED("del");

        private String passwordStatus = "";

        PASSWORD_STATUS(String passwordStatus) {
            this.passwordStatus = passwordStatus;
        }

        public String getStatus() {
            return this.passwordStatus;
        }
    }

    public enum USER_TYPE {
        CLIENT("CLIENT"),VENDOR("VENDOR"),ADMIN("ADMIN");
        private String userType = Constants.EMPTY;
        USER_TYPE(String userType) {
            this.userType = userType;
        }

        public String getType() {
            return this.userType;
        }
    }

    public enum TIME_ZONE {
        central("Central","US/Central"),
        eastern("Eastern","US/Eastern"),
        pacific("Pacific","US/Pacific"),
        mountain("Mountain","US/Mountain"),
        hawaii("Hawaii","US/Hawaii"),
        alaska("Alaska","US/Alaska"),
        atlantic("Atlantic","Canada/Atlantic"),
        newfoundland("Newfoundland","Canada/Newfoundland");



        private String timeZoneDisplay = Constants.EMPTY;
        private String javaTimeZone = Constants.EMPTY;

        TIME_ZONE(String timeZoneDisplay, String javaTimeZone) {
            this.timeZoneDisplay = timeZoneDisplay;
            this.javaTimeZone = javaTimeZone;
        }

        public String getTimeZoneDisplay() {
            return this.timeZoneDisplay;
        }

        public String getJavaTimeZone() {
            return this.javaTimeZone;
        }
    }

    public enum JOB_STATUS {
        PRELIM_STATE("PRELIM_STATE"),
        READY_TO_PICK("READY_TO_PICK"),
        PROCESSING("READY_TO_PICK"),
        JOB_COMPLETE("JOB_COMPLETE");

        private String status = Constants.EMPTY;

        JOB_STATUS(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }
    }
}
