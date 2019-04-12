package com.sinodom.elevator.bean.business;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/12/19.
 * 维保历史记录详细
 */

public class HistoryDetailBean implements Serializable {

    /**
     * ID : 20220
     * LiftNum : 110211
     * InstallationAddress : 辽宁省 沈阳市 沈河区青年大街106号 一号楼1#
     * MaintenancePeriod : 正常
     * CreateTime : 2017-12-15T00:00:00
     * UpdateDatetime : 2017-12-18T14:20:00
     * InspectionNextDate : 2018-01-01T00:00:00
     */

    private int ID;
    private String LiftNum;
    private String InstallationAddress;
    private String MaintenancePeriod;
    private String CreateTime;
    private String UpdateDatetime;
    private String InspectionNextDate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(String LiftNum) {
        this.LiftNum = LiftNum;
    }

    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public void setInstallationAddress(String InstallationAddress) {
        this.InstallationAddress = InstallationAddress;
    }

    public String getMaintenancePeriod() {
        return MaintenancePeriod;
    }

    public void setMaintenancePeriod(String MaintenancePeriod) {
        this.MaintenancePeriod = MaintenancePeriod;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateDatetime() {
        return UpdateDatetime;
    }

    public void setUpdateDatetime(String UpdateDatetime) {
        this.UpdateDatetime = UpdateDatetime;
    }

    public String getInspectionNextDate() {
        return InspectionNextDate;
    }

    public void setInspectionNextDate(String InspectionNextDate) {
        this.InspectionNextDate = InspectionNextDate;
    }
}
