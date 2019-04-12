package com.sinodom.elevator.bean.rescue;

import com.sinodom.elevator.bean.lift.LiftBean;

import java.io.Serializable;

/**
 * Created by GUO on 2017/11/29.
 */

public class RescueBean implements Serializable {

    /**
     * ID : 862994
     * CreateTime : 2017-11-29T16: 22: 34.983
     * LiftId : 37195
     * SourceId : 83
     * ReasonId : null
     * RemedyId : null
     * ProcessLevelId : null
     * RemedyUserId : null
     * ConfirmUserId : null
     * StatusId : 0
     * StatusName : 已报警，等待确认
     * RescueType : 0
     * TotalLossTime : 50
     */

    private int ID;
    private String CreateTime;
    private int LiftId;
    private LiftBean Lift;
    private int SourceId;
    private Object ReasonId;
    private Object RemedyId;
    private Object ProcessLevelId;
    private Object RemedyUserId;
    private Object ConfirmUserId;
    private int StatusId;
    private String StatusName;
    private int RescueType;
    private String TotalLossTime;

    public LiftBean getLift() {
        return Lift;
    }

    public void setLift(LiftBean lift) {
        Lift = lift;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getLiftId() {
        return LiftId;
    }

    public void setLiftId(int LiftId) {
        this.LiftId = LiftId;
    }

    public int getSourceId() {
        return SourceId;
    }

    public void setSourceId(int SourceId) {
        this.SourceId = SourceId;
    }

    public Object getReasonId() {
        return ReasonId;
    }

    public void setReasonId(Object ReasonId) {
        this.ReasonId = ReasonId;
    }

    public Object getRemedyId() {
        return RemedyId;
    }

    public void setRemedyId(Object RemedyId) {
        this.RemedyId = RemedyId;
    }

    public Object getProcessLevelId() {
        return ProcessLevelId;
    }

    public void setProcessLevelId(Object ProcessLevelId) {
        this.ProcessLevelId = ProcessLevelId;
    }

    public Object getRemedyUserId() {
        return RemedyUserId;
    }

    public void setRemedyUserId(Object RemedyUserId) {
        this.RemedyUserId = RemedyUserId;
    }

    public Object getConfirmUserId() {
        return ConfirmUserId;
    }

    public void setConfirmUserId(Object ConfirmUserId) {
        this.ConfirmUserId = ConfirmUserId;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int StatusId) {
        this.StatusId = StatusId;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }

    public int getRescueType() {
        return RescueType;
    }

    public void setRescueType(int RescueType) {
        this.RescueType = RescueType;
    }

    public String getTotalLossTime() {
        return TotalLossTime;
    }

    public void setTotalLossTime(String TotalLossTime) {
        this.TotalLossTime = TotalLossTime;
    }
}
