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
    public final static String EMAILER_LOGS = "EmailerLogging";
    public final static String SCHEDULER_LOGS = "SchedulerLogging";


    // Prop Files
    public final static String PROP_FILE_PATH = "/etc/events/props/";
    public final static String DBCONN_PROP = PROP_FILE_PATH + "dbconnections.prop";
    public final static String APPLICATION_PROP = PROP_FILE_PATH + "application.prop";
    public final static String ANALYTICS_PROP = PROP_FILE_PATH + "analytics.prop";
    public final static String UNSAFE_INPUT_PROP = PROP_FILE_PATH + "unsafe_input.prop";
    public final static String SCHEDULER_PROCESS_PROP = PROP_FILE_PATH + "scheduler_processor.prop";
    public final static String EMAILER_PROP = PROP_FILE_PATH + "emailer.prop";
    public final static String COLOR_CSS_TEMPLATE_PROP = PROP_FILE_PATH + "color_css_template.properties";

    public final static String PROP_ENABLE_EMAIL_SENDER_THREAD = "enable_email_sender_thread";
    public final static String PROP_ENABLE_EMAIL_CREATOR_THREAD = "enable_email_creator_thread";
    public final static String PROP_EMAIL_SCHEDULE_PICKUPTIME_PADDING = "schedule_email_pickuptime_padding";

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
    public final static String DASHBOARD_LINK = "/com/events/dashboard/dashboard.jsp";
    public final static String J_RESP_SUCCESS = "success";
    public static String DEFAULT_TIMEZONE = "UTC";
    public final static String COOKIEUSER_ID = "cu_id";


    public final static String FILE_HOST = "file_host";
    public final static String FILE_UPLOAD_LOCATION = "file_upload_location";
    public final static String IMAGE_LOCATION = "image_upload_location";
    public final static String IMAGE_HOST = "image_host";

    public final static String PRODUCT_NAME="product_name";
    public final static String DOMAIN="domain";
    public final static Long HOURS24_IN_MILLISEC = (24*60*60*1000L);


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

    public enum AMAZON {
        ACCESS_KEY("amazon.access_key"), SECRET_KEY("amazon.secret_key");

        private String propName = Constants.EMPTY;

        AMAZON(String propName) {
            this.propName = propName;
        }

        public String getPropName() {
            return this.propName;
        }
    }

    public enum SCHEDULER{
        SEND_EMAIL_STARTUP_DELAY("send_email.startup_delay"),SEND_EMAIL_DELAY_BETWEEN_CALL("send_email.delay_between_call"),
        CREATE_EMAIL_STARTUP_DELAY("create_email.startup_delay"),CREATE_EMAIL_DELAY_BETWEEN_CALL("create_email.delay_between_call");

        private String propName = Constants.EMPTY;

        SCHEDULER(String propName) {
            this.propName = propName;
        }

        public String getPropName() {
            return this.propName;
        }
    }

    public enum EMAIL_STATUS {
        NEW("NEW"), PICKED_TO_SEND("PICKED"), ERROR("ERROR"), SENT("SENT");

        private String emailStatus = "";

        EMAIL_STATUS(String emailStatus) {
            this.emailStatus = emailStatus;
        }

        public String getStatus() {
            return this.emailStatus;
        }
    }

    public enum SCHEDULER_STATUS  {
        NEW_SCHEDULE("NEW_SCHEDULE","Scheduled"),
        PICKED_TO_PROCESS("PICKED_TO_PROCESS","Currently Being Sent"),
        COMPLETE("COMPLETE","Sent"),
        ERROR("ERROR","Send Failed.");

        private String status = "";
        private String description = Constants.EMPTY;
        SCHEDULER_STATUS( String status, String description )  {
            this.status = status;
            this.description = description;
        }

        public String getStatus() {
            return this.status;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public enum SCHEDULE_PICKUP_TYPE {
        NEW_RECORDS("NEW_RECORDS"),
        OLD_RECORDS("OLD_RECORDS"),
        CURRENT_RECORD("CURRENT_RECORDS");

        private String scedhuledPickupType = "";
        SCHEDULE_PICKUP_TYPE(String scedhuledPickupType)
        {
            this.scedhuledPickupType = scedhuledPickupType;
        }

        public String getScheduledPickupType()
        {
            return this.scedhuledPickupType;
        }

    }

    public enum EMAIL_TEMPLATE {
        REGISTRATION("REGISTRATION"),
        NEWPASSWORD("NEWPASSWORD"),
        NEWTELNUMBERPURCHASE("NEWTELNUMBERPURCHASE"),
        RSVP_CONFIRMATION_EMAIL("RSVP_CONFIRMATION"),
        SEATING_CONFIRMATION_EMAIL("SEATING_CONFIRMATION"),
        RSVPRESPONSEDEMO("RSVPRESPONSEDEMO"),
        RSVPRESPONSE("RSVPRESPONSE");

        private String emailTemplate = "";

        EMAIL_TEMPLATE(String emailTemplate) {
            this.emailTemplate = emailTemplate;
        }

        public String getEmailTemplate() {
            return this.emailTemplate;
        }
    }

    public enum SEND_EMAIL_RULES{
        ALL_INVITED("ALL_INVITED","everyone invited"), DID_NOT_RESPOND("DID_NOT_RESPOND","Did Not Respond"), ALL_WHO_RESPONDED("ALL_WHO_RESPONDED","All Who Responded"),
        WILL_ATTEND("WILL_ATTEND","Will Attend"), WILL_NOT_ATTEND("WILL_NOT_ATTEND","Will Not Attend"), RSVP_THANKYOU("RSVP_THANKYOU","RSVP Thank You"), NO_RULE_SELECTED("NO_RULE_SELECTED","NA");
        private String rule = Constants.EMPTY;
        private String description = Constants.EMPTY;
        SEND_EMAIL_RULES(String rule,String description) {
            this.rule = rule;
            this.description = description;
        }

        public String getRule() {
            return this.rule;
        }

        public String getDescription() {
            return this.description;
        }
    }

    public enum EventEmailFeatureType {
        send_email_rule,email_send_day,email_send_time,email_send_timezone,action,none;
    }

    public enum VENDOR_LANDINGPAGE_FEATURETYPE {
        logo,landingpagephoto,facebook_url,pinterest_url,none;
    }

    public enum EMAIL_TEMPLATE_TEXT {
        GUEST_GROUP_NAME("Guest's Group Name","{__GUEST_GROUP_NAME__}","Show the guest's group name eg. The Rainer Family."),
        GUEST_GIVEN_NAME("Guest's Given Name","{__GUEST_GIVEN_NAME__}","Show the first name or last name. Gives first preference to first name."),
        GUEST_FIRST_NAME("Guest's First Name","{__GUEST_FIRST_NAME__}","Show only first name. Will be blank if name is not present."),
        GUEST_LAST_NAME("Guest's Last Name","{__GUEST_LAST_NAME__}","Show only last name. Will be blank if name is not present."),

        GUEST_RSVP_LINK("Guest's RSVP Link","{__GUEST_RSVP_LINK__}","Link for guest to RSVP.");

        private String name = Constants.EMPTY;
        private String template = Constants.EMPTY;
        private String description = Constants.EMPTY;

        EMAIL_TEMPLATE_TEXT( String name, String template, String description) {
            this.name = name;
            this.template = template;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getTemplate() {
            return template;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum GUEST_WEBRESPONSE_TYPE {
        RSVP,none;
    }
    public enum GUEST_WEB_RESPONSE_STATUS {
        NEW,COMPLETED_RESPONSE, ERROR, NONE;
    }

    public static final String  APPLICATION_DOMAIN = "application_domain";


    /*
        String sColorBackground = ParseUtil.checkNull(request.getParameter("website_color_bkg")).replaceAll("%23","#");
    String sColorTabackground = ParseUtil.checkNull(request.getParameter("website_color_tab_bkg")).replaceAll("%23","#");
    String sColorBreadCrumbBackground = ParseUtil.checkNull(request.getParameter("website_color_breadcrumb_bkg")).replaceAll("%23","#");
    String sColorBorder = ParseUtil.checkNull(request.getParameter("website_color_border")).replaceAll("%23","#");


    String sColorFilledButton = ParseUtil.checkNull(request.getParameter("website_color_filled_button")).replaceAll("%23","#");
    String sColorFilledButtonTxt = ParseUtil.checkNull(request.getParameter("website_color_filled_button_txt")).replaceAll("%23","#");
    String sColorHoverDefaultButton = ParseUtil.checkNull(request.getParameter("website_color_default_button")).replaceAll("%23","#");
    String sColorHoverDefaultButtonTxt = ParseUtil.checkNull(request.getParameter("website_color_default_button_txt")).replaceAll("%23","#");

    String sColorDefaultTxt = ParseUtil.checkNull(request.getParameter("website_color_default_text")).replaceAll("%23","#");
     */
    public enum COLOR_TEMPLATE{

        BKG("__COLOR_BACKGROUND__"),
        HIGHLIGHTED("__COLOR_HIGHLIGHTED__"),
        TEXT("__COLOR_TEXT__"),
        NAVBAR_BREADCRUMB_TAB_BKG("__COLOR_NAVBAR_BREADCRUMB_TAB_BKG__"),
        BORDER("__COLOR_BORDER__"),
        FILLED_BUTTON("__COLOR_FILLED_BUTTON__"),
        FILLED_BUTTON_TEXT("__COLOR_FILLED_BUTTON_TEXT__"),
        PLAIN_BUTTON("__COLOR_PLAIN_BUTTON__"),
        PLAIN_BUTTON_TEXT("__COLOR_PLAIN_BUTTON_TEXT__");

        private String text = Constants.EMPTY;
        COLOR_TEMPLATE(String text) {
            this.text = text;
        }

        public String getText() {
            return this.text;
        }
    }
}
