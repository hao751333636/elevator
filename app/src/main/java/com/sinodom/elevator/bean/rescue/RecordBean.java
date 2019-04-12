package com.sinodom.elevator.bean.rescue;

import java.io.Serializable;

/**
 * Created by GUO on 2017/12/18.
 * 处置记录
 */

public class RecordBean implements Serializable{

    /**
     * ID : 864001
     * LiftNum : 193665
     * InstallationAddress : 辽宁省 沈阳市 沈河区沈阳市沈河区青年大街215号62C楼右(L12) 62C楼右(L12)
     * StatusName : null
     * TotalLossTime : 14
     * CreateTime : 2017-12-18T15:18:50.713
     */

    private int ID;
    private String LiftNum;
    private String InstallationAddress;
    private Object StatusName;
    private String TotalLossTime;
    private String CreateTime;

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

    public Object getStatusName() {
        return StatusName;
    }

    public void setStatusName(Object StatusName) {
        this.StatusName = StatusName;
    }

    public String getTotalLossTime() {
        return TotalLossTime;
    }

    public void setTotalLossTime(String TotalLossTime) {
        this.TotalLossTime = TotalLossTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}
