package com.sinodom.elevator.bean.parts;

public class PartsDetailBean {

    /**
     * PartsTypeId : 1
     * PartsName : 制动器
     * PartsDesc :
     * Picture :
     */

    private int PartsTypeId;
    private String PartsName;
    private String PartsDesc;
    private String Picture;

    public int getPartsTypeId() {
        return PartsTypeId;
    }

    public void setPartsTypeId(int PartsTypeId) {
        this.PartsTypeId = PartsTypeId;
    }

    public String getPartsName() {
        return PartsName;
    }

    public void setPartsName(String PartsName) {
        this.PartsName = PartsName;
    }

    public String getPartsDesc() {
        return PartsDesc;
    }

    public void setPartsDesc(String PartsDesc) {
        this.PartsDesc = PartsDesc;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String Picture) {
        this.Picture = Picture;
    }
}
