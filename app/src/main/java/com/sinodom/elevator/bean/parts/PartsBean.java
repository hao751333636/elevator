package com.sinodom.elevator.bean.parts;

import java.io.Serializable;
import java.util.List;

public class PartsBean implements Serializable{

    /**
     * LiftId : 155940
     * LiftNum : 090004
     * CertificateNum : 00009000080000700006
     * AddressPath : 辽宁省 沈阳市 沈河区
     * InstallationAddress : 华盾大数据研究院
     * list : [{"Manufacturer":null,"Brand":null,"Model":"789","ProductNumber":"789       ","InstallationTime":"2018-10-11T00:00:00","ProductName":"制动器","ProductDesc":null,"LiftId":155940,"State":null,"Picture":"Parts/d181011/01401447fd9db2-b458-4ff3-ba74-7b8fa0e178fd.jpg","PartsTypeId":1,"PartsType":null,"ID":12,"CreateTime":"2018-10-11T13:07:39.75"}]
     * listType : [{"PartsName":"安全钳","Picture":null,"PartsDesc":null,"ID":2,"CreateTime":"2018-09-09T00:00:00"},{"PartsName":"门锁","Picture":null,"PartsDesc":null,"ID":3,"CreateTime":"2018-09-09T00:00:00"},{"PartsName":"限速器","Picture":null,"PartsDesc":null,"ID":4,"CreateTime":"2018-09-09T00:00:00"}]
     */

    private int LiftId;
    private String LiftNum;
    private String CertificateNum;
    private String AddressPath;
    private String InstallationAddress;
    private List<ListBean> list;
    private List<ListTypeBean> listType;

    public int getLiftId() {
        return LiftId;
    }

    public void setLiftId(int LiftId) {
        this.LiftId = LiftId;
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<ListTypeBean> getListType() {
        return listType;
    }

    public void setListType(List<ListTypeBean> listType) {
        this.listType = listType;
    }

    public static class ListBean implements Serializable{
        /**
         * Manufacturer : null
         * Brand : null
         * Model : 789
         * ProductNumber : 789
         * InstallationTime : 2018-10-11T00:00:00
         * ProductName : 制动器
         * ProductDesc : null
         * LiftId : 155940
         * State : null
         * Picture : Parts/d181011/01401447fd9db2-b458-4ff3-ba74-7b8fa0e178fd.jpg
         * PartsTypeId : 1
         * PartsType : null
         * ID : 12
         * CreateTime : 2018-10-11T13:07:39.75
         */

        private Object Manufacturer;
        private String Brand;
        private String Model;
        private String ProductNumber;
        private String InstallationTime;
        private String ProductName;
        private String ProductDesc;
        private int LiftId;
        private Object State;
        private String Picture;
        private int PartsTypeId;
        private Object PartsType;
        private int ID;
        private String CreateTime;

        public Object getManufacturer() {
            return Manufacturer;
        }

        public void setManufacturer(Object Manufacturer) {
            this.Manufacturer = Manufacturer;
        }

        public String getBrand() {
            return Brand;
        }

        public void setBrand(String Brand) {
            this.Brand = Brand;
        }

        public String getModel() {
            return Model;
        }

        public void setModel(String Model) {
            this.Model = Model;
        }

        public String getProductNumber() {
            return ProductNumber;
        }

        public void setProductNumber(String ProductNumber) {
            this.ProductNumber = ProductNumber;
        }

        public String getInstallationTime() {
            return InstallationTime;
        }

        public void setInstallationTime(String InstallationTime) {
            this.InstallationTime = InstallationTime;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getProductDesc() {
            return ProductDesc;
        }

        public void setProductDesc(String ProductDesc) {
            this.ProductDesc = ProductDesc;
        }

        public int getLiftId() {
            return LiftId;
        }

        public void setLiftId(int LiftId) {
            this.LiftId = LiftId;
        }

        public Object getState() {
            return State;
        }

        public void setState(Object State) {
            this.State = State;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
        }

        public int getPartsTypeId() {
            return PartsTypeId;
        }

        public void setPartsTypeId(int PartsTypeId) {
            this.PartsTypeId = PartsTypeId;
        }

        public Object getPartsType() {
            return PartsType;
        }

        public void setPartsType(Object PartsType) {
            this.PartsType = PartsType;
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

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }

    public static class ListTypeBean implements Serializable{
        /**
         * PartsName : 安全钳
         * Picture : null
         * PartsDesc : null
         * ID : 2
         * CreateTime : 2018-09-09T00:00:00
         */

        private String PartsName;
        private String Picture;
        private String PartsDesc;
        private int ID;
        private String CreateTime;

        public String getPartsName() {
            return PartsName;
        }

        public void setPartsName(String PartsName) {
            this.PartsName = PartsName;
        }

        public String getPicture() {
            return Picture;
        }

        public void setPicture(String Picture) {
            this.Picture = Picture;
        }

        public String getPartsDesc() {
            return PartsDesc;
        }

        public void setPartsDesc(String PartsDesc) {
            this.PartsDesc = PartsDesc;
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

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
