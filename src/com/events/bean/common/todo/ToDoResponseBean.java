package com.events.bean.common.todo;

import com.events.bean.event.EventBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/13/14
 * Time: 9:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class ToDoResponseBean {
    private ArrayList<ToDoBean> arrTodoBean = new ArrayList<ToDoBean>();
    private HashMap<String,Long> hmNumOfAssignedUsers = new HashMap<String,Long>();
    HashMap<String, EventBean> hmEventBean = new HashMap<String, EventBean>();

    public ArrayList<ToDoBean> getArrTodoBean() {
        return arrTodoBean;
    }

    public void setArrTodoBean(ArrayList<ToDoBean> arrTodoBean) {
        this.arrTodoBean = arrTodoBean;
    }

    public HashMap<String, Long> getHmNumOfAssignedUsers() {
        return hmNumOfAssignedUsers;
    }

    public void setHmNumOfAssignedUsers(HashMap<String, Long> hmNumOfAssignedUsers) {
        this.hmNumOfAssignedUsers = hmNumOfAssignedUsers;
    }

    public HashMap<String, EventBean> getHmEventBean() {
        return hmEventBean;
    }

    public void setHmEventBean(HashMap<String, EventBean> hmEventBean) {
        this.hmEventBean = hmEventBean;
    }
}
