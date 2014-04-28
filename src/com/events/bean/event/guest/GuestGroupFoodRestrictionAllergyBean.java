package com.events.bean.event.guest;

import com.events.common.Constants;
import com.events.common.ParseUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 4/25/14
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class GuestGroupFoodRestrictionAllergyBean {
    //GTGUESTGROUPFOODRESTRICTIONALLERGY( GUESTGROUPFOODRESTRICTIONALLERGYID VARCHAR(45) PRIMARY KEY NOT NULL,
    // FK_GUESTGROUPID VARCHAR(45) NOT NULL, IS_FOODRESTRICTIONALLERGY_EXISTS INT(1) DEFAULT 0 NOT NULL,
    // DETAILS TEXT NOT NULL) ENGINE = MyISAM DEFAULT CHARSET = utf8

    private String guestGroupFoodRestrictionAllergyId = Constants.EMPTY;
    private String guestGroupId = Constants.EMPTY;
    private boolean isFoodRestrcitionAllergyExists = false;
    private String details = Constants.EMPTY;

    public GuestGroupFoodRestrictionAllergyBean(){ }
    public GuestGroupFoodRestrictionAllergyBean(HashMap<String,String> hmResult) {
        this.guestGroupFoodRestrictionAllergyId = ParseUtil.checkNull(hmResult.get("GUESTGROUPFOODRESTRICTIONALLERGYID"));
        this.guestGroupId =  ParseUtil.checkNull(hmResult.get("FK_GUESTGROUPID"));
        isFoodRestrcitionAllergyExists =  ParseUtil.sTob(hmResult.get("IS_FOODRESTRICTIONALLERGY_EXISTS"));
        this.details =  ParseUtil.checkNull(hmResult.get("DETAILS"));
    }

    public String getGuestGroupFoodRestrictionAllergyId() {
        return guestGroupFoodRestrictionAllergyId;
    }

    public void setGuestGroupFoodRestrictionAllergyId(String guestGroupFoodRestrictionAllergyId) {
        this.guestGroupFoodRestrictionAllergyId = guestGroupFoodRestrictionAllergyId;
    }

    public String getGuestGroupId() {
        return guestGroupId;
    }

    public void setGuestGroupId(String guestGroupId) {
        this.guestGroupId = guestGroupId;
    }

    public boolean isFoodRestrcitionAllergyExists() {
        return isFoodRestrcitionAllergyExists;
    }

    public void setFoodRestrcitionAllergyExists(boolean foodRestrcitionAllergyExists) {
        isFoodRestrcitionAllergyExists = foodRestrcitionAllergyExists;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GuestGroupFoodRestrictionAllergyBean{");
        sb.append("guestGroupFoodRestrictionAllergyId='").append(guestGroupFoodRestrictionAllergyId).append('\'');
        sb.append(", guestGroupId='").append(guestGroupId).append('\'');
        sb.append(", isFoodRestrcitionAllergyExists=").append(isFoodRestrcitionAllergyExists);
        sb.append(", details='").append(details).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public JSONObject toJson() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("guestgroup_foodrestriction_allergy_id", this.guestGroupFoodRestrictionAllergyId);
            jsonObject.put("guestgroup_id", this.guestGroupId);
            jsonObject.put("is_foodrestriction_allergy_exists", this.isFoodRestrcitionAllergyExists);
            jsonObject.put("details", this.details);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
