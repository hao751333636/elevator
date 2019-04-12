package com.sinodom.elevator.bean.repairrecord;

public class RepairAddBean {
    private int UserId;
    private int LiftId;
    private String RepairPosition;
    private int ID;
    private String Remark;
    private String BeforePhoto;
    private String AfterPhoto;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getLiftId() {
        return LiftId;
    }

    public void setLiftId(int liftId) {
        LiftId = liftId;
    }

    public String getRepairPosition() {
        return RepairPosition;
    }

    public void setRepairPosition(String repairPosition) {
        RepairPosition = repairPosition;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getBeforePhoto() {
        return BeforePhoto;
    }

    public void setBeforePhoto(String beforePhoto) {
        BeforePhoto = beforePhoto;
    }

    public String getAfterPhoto() {
        return AfterPhoto;
    }

    public void setAfterPhoto(String afterPhoto) {
        AfterPhoto = afterPhoto;
    }
}
