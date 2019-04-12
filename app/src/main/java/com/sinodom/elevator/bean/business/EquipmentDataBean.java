package com.sinodom.elevator.bean.business;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/12/3.
 */

public class EquipmentDataBean implements Serializable {

    /**
     * ID : 42081
     * LiftNum : 090003
     * InstallationAddress : 上海市 徐汇区上海市 徐汇区 长白路01号
     * MaintenancePeriod : (2/10)
     * UpdateDatetime : 2018-05-11T17:08:21.197
     * IsPassed : true
     * CheckDate : 2018-05-11T17:08:21.197
     * CType : 3
     */

    private int ID;
    private String LiftNum;
    private String InstallationAddress;
    private String MaintenancePeriod;
    private String UpdateDatetime;
    private boolean IsPassed;
    private String CheckDate;
    private String CType;

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

    public String getUpdateDatetime() {
        return UpdateDatetime;
    }

    public void setUpdateDatetime(String UpdateDatetime) {
        this.UpdateDatetime = UpdateDatetime;
    }

    public boolean isIsPassed() {
        return IsPassed;
    }

    public void setIsPassed(boolean IsPassed) {
        this.IsPassed = IsPassed;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
    }

    public String getCType() {
        return CType;
    }

    public void setCType(String CType) {
        this.CType = CType;
    }
}
