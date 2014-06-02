package com.events.bean.common.faqsupport;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 5/24/14
 * Time: 10:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class SupportFaqQuesAndAnsBean {
    //SUPPORTFAQQUESANDANSID VARCHAR(45) NOT NULL, FK_SUPPORTFAQCATEGORIESID VARCHAR(45) NOT NULL, QUESTION TEXT NOT NULL ,
    // ANSWER TEXT NOT NULL, SEQUENCE INT(100) NOT NULL, IS_SHOWN INT(1) NOT NULL DEFAULT 0
    private String supportFaqQuesAnsId = Constants.EMPTY;
    private String supportFaqCategoryId = Constants.EMPTY;
    private String question = Constants.EMPTY;
    private String answer = Constants.EMPTY;
    private Long sequence = 0L;
    private boolean isShown = false;

    public SupportFaqQuesAndAnsBean() {
    }

    public SupportFaqQuesAndAnsBean(HashMap<String, String> hmResult) {
        if(hmResult!=null && !hmResult.isEmpty()) {
            this.supportFaqQuesAnsId =  ParseUtil.checkNull(hmResult.get("SUPPORTFAQQUESANDANSID"));
            this.supportFaqCategoryId =  ParseUtil.checkNull(hmResult.get("FK_SUPPORTFAQCATEGORIESID"));
            this.question =  ParseUtil.checkNull(hmResult.get("QUESTION"));
            this.answer =  ParseUtil.checkNull(hmResult.get("ANSWER"));
            this.sequence = ParseUtil.sToL(hmResult.get("SEQUENCE"));;
            isShown = ParseUtil.sTob( hmResult.get("IS_SHOWN") );;
        }
    }

    public String getSupportFaqQuesAnsId() {
        return supportFaqQuesAnsId;
    }

    public void setSupportFaqQuesAnsId(String supportFaqQuesAnsId) {
        this.supportFaqQuesAnsId = supportFaqQuesAnsId;
    }

    public String getSupportFaqCategoryId() {
        return supportFaqCategoryId;
    }

    public void setSupportFaqCategoryId(String supportFaqCategoryId) {
        this.supportFaqCategoryId = supportFaqCategoryId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SupportFaqQuesAndAnsBean{");
        sb.append("supportFaqQuesAnsId='").append(supportFaqQuesAnsId).append('\'');
        sb.append(", supportFaqCategoryId='").append(supportFaqCategoryId).append('\'');
        sb.append(", question='").append(question).append('\'');
        sb.append(", answer='").append(answer).append('\'');
        sb.append(", sequence=").append(sequence);
        sb.append(", isShown=").append(isShown);
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("support_faq_ques_ans_id", this.supportFaqQuesAnsId );
            jsonObject.put("support_faq_category_id", this.supportFaqCategoryId );
            jsonObject.put("question", this.question );
            jsonObject.put("answer", this.answer );
            jsonObject.put("sequence", this.sequence );
            jsonObject.put("is_shown", this.isShown );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
