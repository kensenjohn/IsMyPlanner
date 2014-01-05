package com.events.bean.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12/12/13
 * Time: 6:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataSecurityRequestBean {
    private ArrayList<String> definiteUnsafeWords = new ArrayList<String>();
    private ArrayList<String> potentialUnsafeWords = new ArrayList<String>();
    private Long createTime = 0L;
    private boolean isDataInObjectOld = true;
    private boolean isFilterEnabled = true;
    private Long reloadFilterParamIntervalInMins = 15L;
    private HashMap<String,Pattern> hmRegexPatterns = new HashMap<String, Pattern>();

    public ArrayList<String> getDefiniteUnsafeWords() {
        return definiteUnsafeWords;
    }

    public void setDefiniteUnsafeWords(ArrayList<String> definiteUnsafeWords) {
        this.definiteUnsafeWords = definiteUnsafeWords;
    }

    public ArrayList<String> getPotentialUnsafeWords() {
        return potentialUnsafeWords;
    }

    public void setPotentialUnsafeWords(ArrayList<String> potentialUnsafeWords) {
        this.potentialUnsafeWords = potentialUnsafeWords;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public boolean isDataInObjectOld() {
        return isDataInObjectOld;
    }

    public void setDataInObjectOld(boolean dataInObjectOld) {
        isDataInObjectOld = dataInObjectOld;
    }

    public boolean isFilterEnabled() {
        return isFilterEnabled;
    }

    public void setFilterEnabled(boolean filterEnabled) {
        isFilterEnabled = filterEnabled;
    }

    public Long getReloadFilterParamIntervalInMins() {
        return reloadFilterParamIntervalInMins;
    }

    public void setReloadFilterParamIntervalInMins(Long reloadFilterParamIntervalInMins) {
        this.reloadFilterParamIntervalInMins = reloadFilterParamIntervalInMins;
    }

    @Override
    public String toString() {
        return "DataSecurityRequestBean{" +
                "definiteUnsafeWords=" + definiteUnsafeWords +
                ", potentialUnsafeWords=" + potentialUnsafeWords +
                ", createTime=" + createTime +
                ", isDataInObjectOld=" + isDataInObjectOld +
                ", isFilterEnabled=" + isFilterEnabled +
                ", reloadFilterParamIntervalInMins=" + reloadFilterParamIntervalInMins +
                '}';
    }
}
