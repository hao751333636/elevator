package com.sinodom.elevator.bean.nfc;

public class NfcReadBean {

    /**
     * NFCCode : dianti119-000000001
     * NFCNum : 04113692EC5A81
     * ProductName : 安全钳
     * LiftNum : 888888
     * CertificateNum : 30104403002004001092
     * InstallationAddress : 上海 上海市 徐汇区碧海红树园二期
     * CreateTime : 2018-07-29T16:05:25.29
     */

    private String NFCCode;
    private String NFCNum;
    private String ProductName;
    private String LiftNum;
    private String CertificateNum;
    private String InstallationAddress;
    private String CreateTime;

    public String getNFCCode() {
        return NFCCode;
    }

    public void setNFCCode(String NFCCode) {
        this.NFCCode = NFCCode;
    }

    public String getNFCNum() {
        return NFCNum;
    }

    public void setNFCNum(String NFCNum) {
        this.NFCNum = NFCNum;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
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

    public String getInstallationAddress() {
        return InstallationAddress;
    }

    public void setInstallationAddress(String InstallationAddress) {
        this.InstallationAddress = InstallationAddress;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}
