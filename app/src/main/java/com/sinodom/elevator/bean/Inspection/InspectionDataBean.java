package com.sinodom.elevator.bean.Inspection;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/12/8.
 */

public class InspectionDataBean implements Serializable {

    /**
     * ID : 94799
     * LiftNum : 653711
     * InstallationAddress : 辽宁省营口市鲅鱼圈区平安大街南于园子村海湾城17#楼1#梯17#楼1#梯
     * YearInspectionDate : null
     * YearInspectionNextDate : null
     * MaintenancePeriod : 无检验信息
     * YearInspectionReportUrl : null
     */

    private int ID;
    private String LiftNum;
    private String InstallationAddress;
    private Object YearInspectionDate;
    private Object YearInspectionNextDate;
    private String MaintenancePeriod;
    private Object YearInspectionReportUrl;

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

    public Object getYearInspectionDate() {
        return YearInspectionDate;
    }

    public void setYearInspectionDate(Object YearInspectionDate) {
        this.YearInspectionDate = YearInspectionDate;
    }

    public Object getYearInspectionNextDate() {
        return YearInspectionNextDate;
    }

    public void setYearInspectionNextDate(Object YearInspectionNextDate) {
        this.YearInspectionNextDate = YearInspectionNextDate;
    }

    public String getMaintenancePeriod() {
        return MaintenancePeriod;
    }

    public void setMaintenancePeriod(String MaintenancePeriod) {
        this.MaintenancePeriod = MaintenancePeriod;
    }

    public Object getYearInspectionReportUrl() {
        return YearInspectionReportUrl;
    }

    public void setYearInspectionReportUrl(Object YearInspectionReportUrl) {
        this.YearInspectionReportUrl = YearInspectionReportUrl;
    }
}
