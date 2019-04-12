package com.sinodom.elevator.bean.repairrecord;

import java.io.Serializable;

public class RepairBean implements Serializable{

    /**
     * Id : 0
     * UserId : 0
     * LiftId : 134819
     * LiftNum : 090003
     * RepairPosition : 上海市 徐汇区上海市 徐汇区 测试
     * Remark : null
     * BeforePhoto : null
     * AfterPhoto : null
     * CreateTime : 0001-01-01T00:00:00
     * is_Repair : 0
     */

    private int Id;
    private int UserId;
    private int LiftId;
    private String LiftNum;
    private String RepairPosition;
    private String Remark;
    private String BeforePhoto;
    private String AfterPhoto;
    private String CreateTime;
    private int is_Repair;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public int getLiftId() {
        return LiftId;
    }

    public void setLiftId(int LiftId) {
        this.LiftId = LiftId;
    }

    public String getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(String LiftNum) {
        this.LiftNum = LiftNum;
    }

    public String getRepairPosition() {
        return RepairPosition;
    }

    public void setRepairPosition(String RepairPosition) {
        this.RepairPosition = RepairPosition;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getBeforePhoto() {
        return BeforePhoto;
    }

    public void setBeforePhoto(String BeforePhoto) {
        this.BeforePhoto = BeforePhoto;
    }

    public String getAfterPhoto() {
        return AfterPhoto;
    }

    public void setAfterPhoto(String AfterPhoto) {
        this.AfterPhoto = AfterPhoto;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getIs_Repair() {
        return is_Repair;
    }

    public void setIs_Repair(int is_Repair) {
        this.is_Repair = is_Repair;
    }
}
