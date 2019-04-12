package com.sinodom.elevator.bean.nfc;

import java.io.Serializable;

public class NfcBean implements Serializable{

    /**
     * LiftID : 134829
     * LiftNum : 406213
     * NFCCode : 1234567
     * TermName : 缓冲器
     * CreateTime : 0001-01-01T00:00:00
     * InstallationAddress : 管委会院内1#
     * AddressPath : 辽宁省 鞍山市 经济开发区(达道湾)
     * CertificateNum : 31302103002012080001
     * CheckTermId : 77
     * TermDesc : 固定，无松动
     * IsNFC : true
     */

    private int LiftID;
    private String LiftNum;
    private String NFCCode;
    private String TermName;
    private String CreateTime;
    private String InstallationAddress;
    private String AddressPath;
    private String CertificateNum;
    private int CheckTermId;
    private String TermDesc;
    private boolean IsNFC;

    public int getLiftID() {
        return LiftID;
    }

    public void setLiftID(int LiftID) {
        this.LiftID = LiftID;
    }

    public String getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(String LiftNum) {
        this.LiftNum = LiftNum;
    }

    public String getNFCCode() {
        return NFCCode;
    }

    public void setNFCCode(String NFCCode) {
        this.NFCCode = NFCCode;
    }

    public String getTermName() {
        return TermName;
    }

    public void setTermName(String TermName) {
        this.TermName = TermName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
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

    public String getCertificateNum() {
        return CertificateNum;
    }

    public void setCertificateNum(String CertificateNum) {
        this.CertificateNum = CertificateNum;
    }

    public int getCheckTermId() {
        return CheckTermId;
    }

    public void setCheckTermId(int CheckTermId) {
        this.CheckTermId = CheckTermId;
    }

    public String getTermDesc() {
        return TermDesc;
    }

    public void setTermDesc(String TermDesc) {
        this.TermDesc = TermDesc;
    }

    public boolean isIsNFC() {
        return IsNFC;
    }

    public void setIsNFC(boolean IsNFC) {
        this.IsNFC = IsNFC;
    }
}
