package com.sinodom.elevator.bean.property;

import java.io.Serializable;
import java.util.List;

/**
 * 物业巡查
 */
public class PropertyBean implements Serializable {

    /**
     * LiftNum : 110211
     * InstallationAddress : 青年大街106号 一号楼1#
     * AddressPath : 辽宁省 沈阳市 沈河区
     * CreateTime : null
     * PropertyStep : [{"StepName":"机房维保完成，曳引机、制动器、编码器、限速器等工作正常","StepDesc":"机房维保完成，曳引机、制动器、编码器、限速器等工作正常","Sort":2,"IsTakePhoto":false,"IsActive":true,"ID":2,"CreateTime":"2015-10-08T00:00:00"},{"StepName":"电梯运行正常","StepDesc":"电梯运行正常","Sort":1,"IsTakePhoto":true,"IsActive":true,"ID":3,"CreateTime":"2015-10-08T00:00:00"}]
     * PropertyCheckId : 0
     */

    private String LiftNum;
    private String InstallationAddress;
    private String AddressPath;
    private String CreateTime;
    private int PropertyCheckId;
    private List<PropertyStepBean> PropertyStep;

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

    public String getAddressPath() {
        return AddressPath;
    }

    public void setAddressPath(String AddressPath) {
        this.AddressPath = AddressPath;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getPropertyCheckId() {
        return PropertyCheckId;
    }

    public void setPropertyCheckId(int PropertyCheckId) {
        this.PropertyCheckId = PropertyCheckId;
    }

    public List<PropertyStepBean> getPropertyStep() {
        return PropertyStep;
    }

    public void setPropertyStep(List<PropertyStepBean> PropertyStep) {
        this.PropertyStep = PropertyStep;
    }

    public static class PropertyStepBean implements Serializable {
        /**
         * StepName : 机房维保完成，曳引机、制动器、编码器、限速器等工作正常
         * StepDesc : 机房维保完成，曳引机、制动器、编码器、限速器等工作正常
         * Sort : 2
         * IsTakePhoto : false
         * IsActive : true
         * ID : 2
         * CreateTime : 2015-10-08T00:00:00
         */

        private String StepName;
        private String StepDesc;
        private int Sort;
        private boolean IsTakePhoto;
        private boolean IsActive;
        private int ID;
        private String CreateTime;
        private String Remark;
        private boolean IsPassed;
        private boolean IsSelect;


        public boolean isIsSelect() {
            return IsSelect;
        }

        public void setIsSelect(boolean passed) {
            IsSelect = passed;
        }


        public boolean isIsPassed() {
            return IsPassed;
        }

        public void setIsPassed(boolean passed) {
            IsPassed = passed;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String remark) {
            Remark = remark;
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
