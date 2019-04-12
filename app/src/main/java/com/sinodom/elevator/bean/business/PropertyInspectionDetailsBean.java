package com.sinodom.elevator.bean.business;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 安卓 on 2018/1/2.
 * 物业巡检
 */

public class PropertyInspectionDetailsBean implements Serializable {

    /**
     * UserId : 14063
     * User : {"DeptId":6666,"Dept":null,"RoleId":12,"Role":null,"UseLifts":null,"MaintLifts":null,"Maint2Lifts":null,"Maint3Lifts":null,"QualityLifts":null,"UserName":"赵智杰","CheckUserName":null,"LoginName":"13940147008","Password":"E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E","Job":null,"Phone":null,"Mobile":"13940147008","CheckMobile":null,"Email":null,"Gender":null,"CertificateNum":null,"Sort":null,"Remark":null,"ActivationState":1,"AgreeDatatime":null,"IsLookAgreement":false,"UpdateDate":null,"RoleName":null,"NewPassword":null,"ConfirmPassword":null,"UserDataAddressIds":null,"UserDataAddress":null,"BusinessPermissionList":[],"ID":14063,"CreateTime":"2017-08-24T15: 16: 12.937"}
     * LiftId : 67256
     * Lift : null
     * CheckDate : 2017-08-31T16: 25: 00
     * IsPassed : true
     * Remark :
     * UploadDate : 2017-08-31T16: 25: 17.88
     * LongitudeAndLatitude : 123.414696,41.756335
     * PropertyCheckDetails : [{"PropertyCheckId":2,"StepId":2,"PropertyStep":{"StepName":"机房维保完成，曳引机、制动器、编码器、限速器等工作正常","StepDesc":"机房维保完成，曳引机、制动器、编码器、限速器等工作正常","Sort":2,"IsTakePhoto":false,"IsActive":true,"ID":2,"CreateTime":"2015-10-08T00: 00: 00"},"CheckDate":"2017-08-31T16: 25: 00","IsPassed":true,"Remark":"","PhotoUrl":"","ID":3,"CreateTime":"2017-08-31T16: 25: 18.117"},{"PropertyCheckId":2,"StepId":3,"PropertyStep":{"StepName":"电梯运行正常","StepDesc":"电梯运行正常","Sort":1,"IsTakePhoto":true,"IsActive":true,"ID":3,"CreateTime":"2015-10-08T00: 00: 00"},"CheckDate":"2017-08-31T16: 25: 00","IsPassed":true,"Remark":"","PhotoUrl":"~/Upload/Check/day_170831/201708310425183.jpg","ID":4,"CreateTime":"2017-08-31T16: 25: 18.177"}]
     * ID : 2
     * CreateTime : 2017-08-31T16: 25: 17.927
     */

    private int UserId;
    private UserBean User;
    private int LiftId;
    private Object Lift;
    private String CheckDate;
    private boolean IsPassed;
    private String Remark;
    private String UploadDate;
    private String LongitudeAndLatitude;
    private int ID;
    private String CreateTime;
    private List<PropertyCheckDetailsBean> PropertyCheckDetails;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int UserId) {
        this.UserId = UserId;
    }

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean User) {
        this.User = User;
    }

    public int getLiftId() {
        return LiftId;
    }

    public void setLiftId(int LiftId) {
        this.LiftId = LiftId;
    }

    public Object getLift() {
        return Lift;
    }

    public void setLift(Object Lift) {
        this.Lift = Lift;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
    }

    public boolean isIsPassed() {
        return IsPassed;
    }

    public void setIsPassed(boolean IsPassed) {
        this.IsPassed = IsPassed;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String UploadDate) {
        this.UploadDate = UploadDate;
    }

    public String getLongitudeAndLatitude() {
        return LongitudeAndLatitude;
    }

    public void setLongitudeAndLatitude(String LongitudeAndLatitude) {
        this.LongitudeAndLatitude = LongitudeAndLatitude;
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

    public List<PropertyCheckDetailsBean> getPropertyCheckDetails() {
        return PropertyCheckDetails;
    }

    public void setPropertyCheckDetails(List<PropertyCheckDetailsBean> PropertyCheckDetails) {
        this.PropertyCheckDetails = PropertyCheckDetails;
    }

    public static class UserBean {
        /**
         * DeptId : 6666
         * Dept : null
         * RoleId : 12
         * Role : null
         * UseLifts : null
         * MaintLifts : null
         * Maint2Lifts : null
         * Maint3Lifts : null
         * QualityLifts : null
         * UserName : 赵智杰
         * CheckUserName : null
         * LoginName : 13940147008
         * Password : E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E
         * Job : null
         * Phone : null
         * Mobile : 13940147008
         * CheckMobile : null
         * Email : null
         * Gender : null
         * CertificateNum : null
         * Sort : null
         * Remark : null
         * ActivationState : 1
         * AgreeDatatime : null
         * IsLookAgreement : false
         * UpdateDate : null
         * RoleName : null
         * NewPassword : null
         * ConfirmPassword : null
         * UserDataAddressIds : null
         * UserDataAddress : null
         * BusinessPermissionList : []
         * ID : 14063
         * CreateTime : 2017-08-24T15: 16: 12.937
         */

        private int DeptId;
        private Object Dept;
        private int RoleId;
        private Object Role;
        private Object UseLifts;
        private Object MaintLifts;
        private Object Maint2Lifts;
        private Object Maint3Lifts;
        private Object QualityLifts;
        private String UserName;
        private Object CheckUserName;
        private String LoginName;
        private String Password;
        private Object Job;
        private Object Phone;
        private String Mobile;
        private Object CheckMobile;
        private Object Email;
        private Object Gender;
        private Object CertificateNum;
        private Object Sort;
        private Object Remark;
        private int ActivationState;
        private Object AgreeDatatime;
        private boolean IsLookAgreement;
        private Object UpdateDate;
        private Object RoleName;
        private Object NewPassword;
        private Object ConfirmPassword;
        private Object UserDataAddressIds;
        private Object UserDataAddress;
        private int ID;
        private String CreateTime;
        private List<?> BusinessPermissionList;

        public int getDeptId() {
            return DeptId;
        }

        public void setDeptId(int DeptId) {
            this.DeptId = DeptId;
        }

        public Object getDept() {
            return Dept;
        }

        public void setDept(Object Dept) {
            this.Dept = Dept;
        }

        public int getRoleId() {
            return RoleId;
        }

        public void setRoleId(int RoleId) {
            this.RoleId = RoleId;
        }

        public Object getRole() {
            return Role;
        }

        public void setRole(Object Role) {
            this.Role = Role;
        }

        public Object getUseLifts() {
            return UseLifts;
        }

        public void setUseLifts(Object UseLifts) {
            this.UseLifts = UseLifts;
        }

        public Object getMaintLifts() {
            return MaintLifts;
        }

        public void setMaintLifts(Object MaintLifts) {
            this.MaintLifts = MaintLifts;
        }

        public Object getMaint2Lifts() {
            return Maint2Lifts;
        }

        public void setMaint2Lifts(Object Maint2Lifts) {
            this.Maint2Lifts = Maint2Lifts;
        }

        public Object getMaint3Lifts() {
            return Maint3Lifts;
        }

        public void setMaint3Lifts(Object Maint3Lifts) {
            this.Maint3Lifts = Maint3Lifts;
        }

        public Object getQualityLifts() {
            return QualityLifts;
        }

        public void setQualityLifts(Object QualityLifts) {
            this.QualityLifts = QualityLifts;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public Object getCheckUserName() {
            return CheckUserName;
        }

        public void setCheckUserName(Object CheckUserName) {
            this.CheckUserName = CheckUserName;
        }

        public String getLoginName() {
            return LoginName;
        }

        public void setLoginName(String LoginName) {
            this.LoginName = LoginName;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public Object getJob() {
            return Job;
        }

        public void setJob(Object Job) {
            this.Job = Job;
        }

        public Object getPhone() {
            return Phone;
        }

        public void setPhone(Object Phone) {
            this.Phone = Phone;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public Object getCheckMobile() {
            return CheckMobile;
        }

        public void setCheckMobile(Object CheckMobile) {
            this.CheckMobile = CheckMobile;
        }

        public Object getEmail() {
            return Email;
        }

        public void setEmail(Object Email) {
            this.Email = Email;
        }

        public Object getGender() {
            return Gender;
        }

        public void setGender(Object Gender) {
            this.Gender = Gender;
        }

        public Object getCertificateNum() {
            return CertificateNum;
        }

        public void setCertificateNum(Object CertificateNum) {
            this.CertificateNum = CertificateNum;
        }

        public Object getSort() {
            return Sort;
        }

        public void setSort(Object Sort) {
            this.Sort = Sort;
        }

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }

        public int getActivationState() {
            return ActivationState;
        }

        public void setActivationState(int ActivationState) {
            this.ActivationState = ActivationState;
        }

        public Object getAgreeDatatime() {
            return AgreeDatatime;
        }

        public void setAgreeDatatime(Object AgreeDatatime) {
            this.AgreeDatatime = AgreeDatatime;
        }

        public boolean isIsLookAgreement() {
            return IsLookAgreement;
        }

        public void setIsLookAgreement(boolean IsLookAgreement) {
            this.IsLookAgreement = IsLookAgreement;
        }

        public Object getUpdateDate() {
            return UpdateDate;
        }

        public void setUpdateDate(Object UpdateDate) {
            this.UpdateDate = UpdateDate;
        }

        public Object getRoleName() {
            return RoleName;
        }

        public void setRoleName(Object RoleName) {
            this.RoleName = RoleName;
        }

        public Object getNewPassword() {
            return NewPassword;
        }

        public void setNewPassword(Object NewPassword) {
            this.NewPassword = NewPassword;
        }

        public Object getConfirmPassword() {
            return ConfirmPassword;
        }

        public void setConfirmPassword(Object ConfirmPassword) {
            this.ConfirmPassword = ConfirmPassword;
        }

        public Object getUserDataAddressIds() {
            return UserDataAddressIds;
        }

        public void setUserDataAddressIds(Object UserDataAddressIds) {
            this.UserDataAddressIds = UserDataAddressIds;
        }

        public Object getUserDataAddress() {
            return UserDataAddress;
        }

        public void setUserDataAddress(Object UserDataAddress) {
            this.UserDataAddress = UserDataAddress;
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

        public List<?> getBusinessPermissionList() {
            return BusinessPermissionList;
        }

        public void setBusinessPermissionList(List<?> BusinessPermissionList) {
            this.BusinessPermissionList = BusinessPermissionList;
        }
    }

    public static class PropertyCheckDetailsBean {
        /**
         * PropertyCheckId : 2
         * StepId : 2
         * PropertyStep : {"StepName":"机房维保完成，曳引机、制动器、编码器、限速器等工作正常","StepDesc":"机房维保完成，曳引机、制动器、编码器、限速器等工作正常","Sort":2,"IsTakePhoto":false,"IsActive":true,"ID":2,"CreateTime":"2015-10-08T00: 00: 00"}
         * CheckDate : 2017-08-31T16: 25: 00
         * IsPassed : true
         * Remark :
         * PhotoUrl :
         * ID : 3
         * CreateTime : 2017-08-31T16: 25: 18.117
         */

        private int PropertyCheckId;
        private int StepId;
        private PropertyStepBean PropertyStep;
        private String CheckDate;
        private boolean IsPassed;
        private String Remark;
        private String PhotoUrl;
        private int ID;
        private String CreateTime;

        public int getPropertyCheckId() {
            return PropertyCheckId;
        }

        public void setPropertyCheckId(int PropertyCheckId) {
            this.PropertyCheckId = PropertyCheckId;
        }

        public int getStepId() {
            return StepId;
        }

        public void setStepId(int StepId) {
            this.StepId = StepId;
        }

        public PropertyStepBean getPropertyStep() {
            return PropertyStep;
        }

        public void setPropertyStep(PropertyStepBean PropertyStep) {
            this.PropertyStep = PropertyStep;
        }

        public String getCheckDate() {
            return CheckDate;
        }

        public void setCheckDate(String CheckDate) {
            this.CheckDate = CheckDate;
        }

        public boolean isIsPassed() {
            return IsPassed;
        }

        public void setIsPassed(boolean IsPassed) {
            this.IsPassed = IsPassed;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String PhotoUrl) {
            this.PhotoUrl = PhotoUrl;
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

        public static class PropertyStepBean {
            /**
             * StepName : 机房维保完成，曳引机、制动器、编码器、限速器等工作正常
             * StepDesc : 机房维保完成，曳引机、制动器、编码器、限速器等工作正常
             * Sort : 2
             * IsTakePhoto : false
             * IsActive : true
             * ID : 2
             * CreateTime : 2015-10-08T00: 00: 00
             */

            private String StepName;
            private String StepDesc;
            private int Sort;
            private boolean IsTakePhoto;
            private boolean IsActive;
            private int ID;
            private String CreateTime;

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
}
