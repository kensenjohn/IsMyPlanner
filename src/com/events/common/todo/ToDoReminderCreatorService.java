package com.events.common.todo;

import com.events.bean.common.email.EmailQueueBean;
import com.events.bean.common.email.EmailSchedulerBean;
import com.events.bean.common.email.EmailTemplateBean;
import com.events.bean.common.todo.AssignedToDoUsersBean;
import com.events.bean.common.todo.ToDoBean;
import com.events.bean.common.todo.ToDoRequestBean;
import com.events.bean.common.todo.TodoReminderScheduleBean;
import com.events.bean.users.UserBean;
import com.events.bean.users.UserInfoBean;
import com.events.bean.users.UserRequestBean;
import com.events.common.Configuration;
import com.events.common.Constants;
import com.events.common.DateSupport;
import com.events.common.ParseUtil;
import com.events.common.email.creator.EmailCreator;
import com.events.common.email.creator.MailCreator;
import com.events.common.email.send.QuickMailSendThread;
import com.events.common.exception.ExceptionHandler;
import com.events.data.email.EmailServiceData;
import com.events.data.todo.AccessToDoData;
import com.events.data.todo.BuildToDoData;
import com.events.users.AccessUsers;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/12/14
 * Time: 2:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToDoReminderCreatorService {
    private static final Logger appLogging = LoggerFactory.getLogger(Constants.APPLICATION_LOG);
    private Configuration emailerConfig = Configuration.getInstance(Constants.EMAILER_PROP);

    public void invokeToDoReminderSender() {
        Long lCurrentTime =  DateSupport.getEpochMillis();

        BuildToDoData buildToDoData = new BuildToDoData();
        ArrayList<TodoReminderScheduleBean> arrOldTodoReminderSchedulerBean = getOldToDoSchedules(lCurrentTime);
        if(arrOldTodoReminderSchedulerBean!=null && !arrOldTodoReminderSchedulerBean.isEmpty()) {
            for(TodoReminderScheduleBean todoReminderScheduleBean : arrOldTodoReminderSchedulerBean ){
                todoReminderScheduleBean.setScheduleStatus( Constants.SCHEDULER_STATUS.ERROR.getStatus()  );
                buildToDoData.updateTodoReminderScheduleStatus( todoReminderScheduleBean );
            }
        }

        AccessToDo accessToDo = new AccessToDo();
        ArrayList<TodoReminderScheduleBean> arrNewSchedulerBean = getNewToDoSchedules(lCurrentTime);
        if(arrNewSchedulerBean!=null && !arrNewSchedulerBean.isEmpty())  {
            for(TodoReminderScheduleBean todoNewScheduleBean : arrNewSchedulerBean ) {
                ToDoRequestBean toDoRequestBean = new ToDoRequestBean();
                toDoRequestBean.setTodoId( todoNewScheduleBean.getToDoId() );
                ToDoBean toDoBean = accessToDo.getTodo( toDoRequestBean );

                ArrayList<AssignedToDoUsersBean> arrAssignedToDoUsersBean = accessToDo.getAssignedTodoUsers( toDoRequestBean );
                boolean isSuccess = sendTodoReminderEmail( toDoBean, todoNewScheduleBean, arrAssignedToDoUsersBean );
                Constants.SCHEDULER_STATUS emailSendStatus = Constants.SCHEDULER_STATUS.ERROR;
                if(isSuccess){
                    emailSendStatus = Constants.SCHEDULER_STATUS.COMPLETE;
                }
                todoNewScheduleBean.setScheduleStatus( emailSendStatus.getStatus()  );
                buildToDoData.updateTodoReminderScheduleStatus( todoNewScheduleBean );
            }
        }
    }

    public ArrayList<TodoReminderScheduleBean> getOldToDoSchedules(Long lCurrentTime ) {
        AccessToDoData accessToDoData = new AccessToDoData();
        Long lScheduleTime = DateSupport.subtractTime( lCurrentTime , ParseUtil.sToI(emailerConfig.get(Constants.PROP_EMAIL_SCHEDULE_PICKUPTIME_PADDING)) , Constants.TIME_UNIT.MINUTES );
        ArrayList<TodoReminderScheduleBean> arrSchedulerBean = accessToDoData.getArrTodoReminderSchedule(lScheduleTime - (lCurrentTime - lScheduleTime), lScheduleTime, Constants.SCHEDULER_STATUS.NEW_SCHEDULE, Constants.SCHEDULE_PICKUP_TYPE.OLD_RECORDS);

        return arrSchedulerBean;
    }

    public ArrayList<TodoReminderScheduleBean> getNewToDoSchedules( Long lCurrentTime )  {
        AccessToDoData accessToDoData = new AccessToDoData();
        Long lScheduleTime = DateSupport.subtractTime( lCurrentTime , ParseUtil.sToI(emailerConfig.get(Constants.PROP_EMAIL_SCHEDULE_PICKUPTIME_PADDING)) , Constants.TIME_UNIT.MINUTES );

        ArrayList<TodoReminderScheduleBean> arrSchedulerBean = accessToDoData.getArrTodoReminderSchedule(lScheduleTime, lCurrentTime + (lCurrentTime - lScheduleTime), Constants.SCHEDULER_STATUS.NEW_SCHEDULE, Constants.SCHEDULE_PICKUP_TYPE.NEW_RECORDS);

        return arrSchedulerBean;
    }


    private boolean sendTodoReminderEmail(ToDoBean todoBean, TodoReminderScheduleBean todoReminderScheduleBean, ArrayList<AssignedToDoUsersBean> arrAssignedTodoUsers)  {
        boolean isSuccess = false;
        if( todoBean!=null && todoReminderScheduleBean!=null && arrAssignedTodoUsers!=null && !arrAssignedTodoUsers.isEmpty() ) {

            EmailServiceData emailServiceData = new EmailServiceData();
            EmailTemplateBean emailTemplateBean = emailServiceData.getEmailTemplate(Constants.EMAIL_TEMPLATE.TODO_REMINDER);

            AccessUsers accessUsers = new AccessUsers();
            for(AssignedToDoUsersBean assignedToDoUsersBean : arrAssignedTodoUsers ){

                String sHtmlTemplate = emailTemplateBean.getHtmlBody();
                String sTxtTemplate = emailTemplateBean.getTextBody();

                EmailQueueBean emailQueueBean = new EmailQueueBean();
                emailQueueBean.setEmailSubject(emailTemplateBean.getEmailSubject());
                emailQueueBean.setFromAddress(emailTemplateBean.getFromAddress());
                emailQueueBean.setFromAddressName(emailTemplateBean.getFromAddressName());


                UserRequestBean userRequestBean = new UserRequestBean();
                userRequestBean.setUserId( assignedToDoUsersBean.getUserId() );

                UserBean userBean = accessUsers.getUserById( userRequestBean );
                UserInfoBean userInfoBean = accessUsers.getUserInfoFromUserId( userRequestBean );
                String sFirstName = Constants.EMPTY;
                String sLastName = ParseUtil.checkNull(userInfoBean.getLastName());
                String sGivenName = sFirstName + " " + sLastName;
                String sEmail = Constants.EMPTY;
                if(userInfoBean!=null){
                    sFirstName = ParseUtil.checkNull(userInfoBean.getFirstName());
                    sLastName = ParseUtil.checkNull(userInfoBean.getLastName());
                    sGivenName = sFirstName + " " + sLastName;
                    sEmail = ParseUtil.checkNull(userInfoBean.getEmail());
                }

                emailQueueBean.setToAddress( sEmail );
                emailQueueBean.setToAddressName( sEmail );
                emailQueueBean.setHtmlBody(sHtmlTemplate);
                emailQueueBean.setTextBody(sTxtTemplate);

                // mark it as sent so that it wont get picked up by email service. The email gets sent below
                emailQueueBean.setStatus(Constants.EMAIL_STATUS.SENT.getStatus());

                // We are just creating a record in the database with this action.
                // The new password will be sent separately.
                // This must be changed so that user will have to click link to
                // generate the new password.
                MailCreator dummyEailCreator = new EmailCreator();
                dummyEailCreator.create(emailQueueBean , new EmailSchedulerBean());

                // Now here we will be putting the correct password in the email
                // text and
                // send it out directly.
                // This needs to be changed. Warning bells are rining.
                // Lots of potential to fail.

                Map<String, Object> mapTextEmailValues = new HashMap<String, Object>();
                Map<String, Object> mapHtmlEmailValues = new HashMap<String, Object>();
                mapTextEmailValues.put("USER_GIVEN_NAME",sGivenName);
                mapHtmlEmailValues.put("USER_GIVEN_NAME", sGivenName);

                mapTextEmailValues.put("TO_DO_TEXT", todoBean.getToDo() );
                mapHtmlEmailValues.put("TO_DO_TEXT", todoBean.getToDo() );

                MustacheFactory mf = new DefaultMustacheFactory();
                Mustache mustacheText =  mf.compile(new StringReader(sTxtTemplate), assignedToDoUsersBean.getAssignedTodoUserId()+"_"+Constants.EMAIL_TEMPLATE.TODO_REMINDER.toString()+"_text");
                Mustache mustacheHtml = mf.compile(new StringReader(sHtmlTemplate), assignedToDoUsersBean.getAssignedTodoUserId()+"_"+Constants.EMAIL_TEMPLATE.TODO_REMINDER.toString()+"_html");

                StringWriter txtWriter = new StringWriter();
                StringWriter htmlWriter = new StringWriter();
                try {
                    mustacheText.execute(txtWriter, mapTextEmailValues).flush();
                    mustacheHtml.execute(htmlWriter, mapHtmlEmailValues).flush();
                } catch (IOException e) {
                    appLogging.error("To Do Reminder mustache exception: " + ExceptionHandler.getStackTrace(e));
                    txtWriter = new StringWriter();
                    htmlWriter = new StringWriter();
                }



                emailQueueBean.setHtmlBody(htmlWriter.toString());
                emailQueueBean.setTextBody(txtWriter.toString());

                emailQueueBean.setStatus(Constants.EMAIL_STATUS.NEW.getStatus());

                // This will actually send the email. Spawning a thread and continue
                // execution.
                Thread quickEmail = new Thread(new QuickMailSendThread( emailQueueBean), "Quick Email To Reminder");
                quickEmail.start();
                isSuccess = true;

            }

        }
        return isSuccess;
    }
}
