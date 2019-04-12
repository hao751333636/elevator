package com.sinodom.elevator.bean.inspect;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/11/13.
 */

public class InspectListBean implements Serializable {


    private int ID;
    private String LiftNum;
    private String CertificateNum;
    private String AddressPath;
    private String InstallationAddress;
    private Object UserName;
    private boolean IsPassed;
    private int IsOver;
    private String CreateTime;

    public int getIsOver() {
        return IsOver;
    }

    public void setIsOver(int isOver) {
        IsOver = isOver;
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

    public String getCertificateNum() {
        return CertificateNum;
    }

    public void setCertificateNum(String CertificateNum) {
        this.CertificateNum = CertificateNum;
    }

    public String getAddressPath() {
        return AddressPath;
    }

    public void setAddressPath(String AddressPath) {
        this.AddressPath = AddressPath;
    }

    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public void setInstallationAddress(String InstallationAddress) {
        this.InstallationAddress = InstallationAddress;
    }

    public Object getUserName() {
        return UserName;
    }

    public void setUserName(Object UserName) {
        this.UserName = UserName;
    }

    public boolean getIsPassed() {
        return IsPassed;
    }

    public void setIsPassed(boolean IsPassed) {
        this.IsPassed = IsPassed;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

}
