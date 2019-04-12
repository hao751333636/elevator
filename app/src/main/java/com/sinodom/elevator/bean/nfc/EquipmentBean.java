package com.sinodom.elevator.bean.nfc;

import java.io.Serializable;

public class EquipmentBean implements Serializable{

    /**
     * DeviceNum : 194555
     * CertificateNum : 30102101002006080329
     * InstallationAddress : 辽宁省  沈阳市  大东区东北大马路337号
     * AddressPath : 辽宁省 沈阳市 大东区
     * ID : 154282
     */

    private String DeviceNum;
    private String CertificateNum;
    private String InstallationAddress;
    private String AddressPath;
    private int ID;

    public String getDeviceNum() {
        return DeviceNum;
    }

    public void setDeviceNum(String DeviceNum) {
        this.DeviceNum = DeviceNum;
    }

    public String getCertificateNum() {
        return CertificateNum;
    }

    public void setCertificateNum(String CertificateNum) {
        this.CertificateNum = CertificateNum;
    }

    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public void setInstallationAddress(String InstallationAddress) {
        this.InstallationAddress = InstallationAddress;
    }

    public String getAddressPath() {
        return AddressPath;
    }

    public void setAddressPath(String AddressPath) {
        this.AddressPath = AddressPath;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
