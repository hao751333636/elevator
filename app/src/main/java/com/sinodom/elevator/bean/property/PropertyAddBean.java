package com.sinodom.elevator.bean.property;

import java.io.Serializable;

/**
 * 物业巡查提交
 */
public class PropertyAddBean implements Serializable {
    private int PropertyCheckId;
    private int StepId;
    private String CheckDate;
    private boolean IsPassed;
    private String Remark;
    private String PhotoUrl;
    private String UserId;
    private String LiftNum;
    private String LongitudeAndLatitude;

    public String getLongitudeAndLatitude() {
        return LongitudeAndLatitude;
    }

    public void setLongitudeAndLatitude(String longitudeAndLatitude) {
        LongitudeAndLatitude = longitudeAndLatitude;
    }

    public boolean isIsPassed() {
        return IsPassed;
    }

    public void setIsPassed(boolean passed) {
        IsPassed = passed;
    }

    public int getPropertyCheckId() {
        return PropertyCheckId;
    }

    public void setPropertyCheckId(int propertyCheckId) {
        PropertyCheckId = propertyCheckId;
    }

    public int getStepId() {
        return StepId;
    }

    public void setStepId(int stepId) {
        StepId = stepId;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(String liftNum) {
        LiftNum = liftNum;
    }
}
