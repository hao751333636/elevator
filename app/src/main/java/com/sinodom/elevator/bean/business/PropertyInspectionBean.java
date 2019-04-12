package com.sinodom.elevator.bean.business;

import java.io.Serializable;

/**
 * Created by 安卓 on 2018/1/3.
 * 物业巡检
 */

public class PropertyInspectionBean implements Serializable {

    /**
     * InstallationAddress : 辽宁省 沈阳市 大东区联合路三洋重工北区地块1#2
     * ID : 2
     * LiftNum : 193668
     * CheckDate : 2017-08-31T16:25:00
     * IsPassed : true
     */

    private String InstallationAddress;
    private int ID;
    private String LiftNum;
    private String CheckDate;
    private boolean IsPassed;

    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public void setInstallationAddress(String InstallationAddress) {
        this.InstallationAddress = InstallationAddress;
    }

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

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
    }

    public boolean isIsPassed() {
        return IsPassed;
    }

    public void setIsPassed(boolean IsPassed) {
        this.IsPassed = IsPassed;
    }
}
