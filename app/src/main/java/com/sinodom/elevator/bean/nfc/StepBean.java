package com.sinodom.elevator.bean.nfc;

import java.io.Serializable;

public class StepBean implements Serializable{

    /**
     * CheckType : 4
     * StepName : 年度机房维保(曳引机、制动器、编码器、限速器、控制柜、反绳轮等工作正常)
     * StepDesc : 年度机房维保(曳引机、制动器、编码器、限速器、控制柜、反绳轮等工作正常)
     * Sort : 401
     * IsTakePhoto : false
     * IsActive : true
     * IsNFC : true
     * NFCCode : 001
     * TermName : 限速器安全钳联动试验
     * NFCNum : 123
     */

    private int CheckType;
    private String StepName;
    private String StepDesc;
    private int Sort;
    private boolean IsTakePhoto;
    private boolean IsActive;
    private boolean IsNFC;
    private String NFCCode;
    private String TermName;
    private String TermDesc;
    private String NFCNum;
    private String Desc;
    private int ID;
    private boolean isNfcScan;
    private int selectType;

    public String getTermDesc() {
        return TermDesc;
    }

    public void setTermDesc(String termDesc) {
        TermDesc = termDesc;
    }

    public int getSelectType() {
        return selectType;
    }

    public void setSelectType(int selectType) {
        this.selectType = selectType;
    }

    public boolean isNfcScan() {
        return isNfcScan;
    }

    public void setNfcScan(boolean nfcScan) {
        isNfcScan = nfcScan;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCheckType() {
        return CheckType;
    }

    public void setCheckType(int CheckType) {
        this.CheckType = CheckType;
    }

    public String getStepName() {
        return StepName;
    }

    public void setStepName(String StepName) {
        this.StepName = StepName;
    }

    public String getStepDesc() {
        return StepDesc;
    }

    public void setStepDesc(String StepDesc) {
        this.StepDesc = StepDesc;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int Sort) {
        this.Sort = Sort;
    }

    public boolean isIsTakePhoto() {
        return IsTakePhoto;
    }

    public void setIsTakePhoto(boolean IsTakePhoto) {
        this.IsTakePhoto = IsTakePhoto;
    }

    public boolean isIsActive() {
        return IsActive;
    }

    public void setIsActive(boolean IsActive) {
        this.IsActive = IsActive;
    }

    public boolean isIsNFC() {
        return IsNFC;
    }

    public void setIsNFC(boolean IsNFC) {
        this.IsNFC = IsNFC;
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

    public String getNFCNum() {
        return NFCNum;
    }

    public void setNFCNum(String NFCNum) {
        this.NFCNum = NFCNum;
    }
}
