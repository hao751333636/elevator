package com.sinodom.elevator.bean.repairrecord;

/**
 * Created by GUO on 2017/12/28.
 * 维修记录
 */

public class RepairRecordBean {
    private String InstallationAddress;
    private int ID;
    private String CreateTime;
    private String LiftNum;

    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public void setInstallationAddress(String installationAddress) {
        InstallationAddress = installationAddress;
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

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(String liftNum) {
        LiftNum = liftNum;
    }
}
