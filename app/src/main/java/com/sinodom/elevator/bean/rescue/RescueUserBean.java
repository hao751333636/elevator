package com.sinodom.elevator.bean.rescue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GUO on 2017/12/5.
 */

public class RescueUserBean implements Serializable{

    /**
     * UserId : 3689
     * User : {"DeptId":5400,"Dept":{"RoleGroupId":7,"RoleGroup":null,"AddressId":null,"DeptName":"沈阳远大智能工业集团股份有限公司","DeptCode":"912101007310057103","Phone":"02425162806","DeptAddress":"辽宁省 沈阳市 沈河区","BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2017-02-10T10:56:26.897","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":[],"ID":5400,"CreateTime":"2017-02-10T10:56:26.897"},"RoleId":15,"Role":null,"UseLifts":null,"MaintLifts":null,"Maint2Lifts":null,"Maint3Lifts":null,"QualityLifts":null,"UserName":"赵铁刚","CheckUserName":null,"LoginName":"15998189007","Password":"E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E","Job":null,"Phone":"","Mobile":"15998189007","CheckMobile":null,"Email":null,"Gender":null,"CertificateNum":null,"Sort":0,"Remark":null,"ActivationState":1,"AgreeDatatime":"2017-12-05T14:43:18.26","IsLookAgreement":true,"UpdateDate":"2016-01-04T09:21:32.203","RoleName":null,"NewPassword":null,"ConfirmPassword":null,"UserDataAddressIds":null,"UserDataAddress":null,"BusinessPermissionList":[],"ID":3689,"CreateTime":"2015-01-01T00:00:00"}
     * PhoneId : AvMX9rhmYd-x4hr-HOfaravMvD85LgCNHhEyy3BZrG0f
     * BaiduMapX : 123.415192
     * BaiduMapY : 41.756361
     * UpdateDate : 2017-12-05T14:43:48.217
     * LongitudeAndLatitude : 123.415192,41.756361
     * ID : 13656
     * CreateTime : 2017-12-05T14:43:48.19
     */

    private int UserId;
    private UserBean User;
    private String PhoneId;
    private double BaiduMapX;
    private double BaiduMapY;
    private String UpdateDate;
    private String LongitudeAndLatitude;
    private int ID;
    private String CreateTime;

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

    public String getPhoneId() {
        return PhoneId;
    }

    public void setPhoneId(String PhoneId) {
        this.PhoneId = PhoneId;
    }

    public double getBaiduMapX() {
        return BaiduMapX;
    }

    public void setBaiduMapX(double BaiduMapX) {
        this.BaiduMapX = BaiduMapX;
    }

    public double getBaiduMapY() {
        return BaiduMapY;
    }

    public void setBaiduMapY(double BaiduMapY) {
        this.BaiduMapY = BaiduMapY;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String UpdateDate) {
        this.UpdateDate = UpdateDate;
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

    public static class UserBean {
        /**
         * DeptId : 5400
         * Dept : {"RoleGroupId":7,"RoleGroup":null,"AddressId":null,"DeptName":"沈阳远大智能工业集团股份有限公司","DeptCode":"912101007310057103","Phone":"02425162806","DeptAddress":"辽宁省 沈阳市 沈河区","BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2017-02-10T10:56:26.897","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":[],"ID":5400,"CreateTime":"2017-02-10T10:56:26.897"}
         * RoleId : 15
         * Role : null
         * UseLifts : null
         * MaintLifts : null
         * Maint2Lifts : null
         * Maint3Lifts : null
         * QualityLifts : null
         * UserName : 赵铁刚
         * CheckUserName : null
         * LoginName : 15998189007
         * Password : E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E
         * Job : null
         * Phone :
         * Mobile : 15998189007
         * CheckMobile : null
         * Email : null
         * Gender : null
         * CertificateNum : null
         * Sort : 0
         * Remark : null
         * ActivationState : 1
         * AgreeDatatime : 2017-12-05T14:43:18.26
         * IsLookAgreement : true
         * UpdateDate : 2016-01-04T09:21:32.203
         * RoleName : null
         * NewPassword : null
         * ConfirmPassword : null
         * UserDataAddressIds : null
         * UserDataAddress : null
         * BusinessPermissionList : []
         * ID : 3689
         * CreateTime : 2015-01-01T00:00:00
         */

        private int DeptId;
        private DeptBean Dept;
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
        private String Phone;
        private String Mobile;
        private Object CheckMobile;
        private Object Email;
        private Object Gender;
        private Object CertificateNum;
        private int Sort;
        private Object Remark;
        private int ActivationState;
        private String AgreeDatatime;
        private boolean IsLookAgreement;
        private String UpdateDate;
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

        public DeptBean getDept() {
            return Dept;
        }

        public void setDept(DeptBean Dept) {
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

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
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

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
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

        public String getAgreeDatatime() {
            return AgreeDatatime;
        }

        public void setAgreeDatatime(String AgreeDatatime) {
            this.AgreeDatatime = AgreeDatatime;
        }

        public boolean isIsLookAgreement() {
            return IsLookAgreement;
        }

        public void setIsLookAgreement(boolean IsLookAgreement) {
            this.IsLookAgreement = IsLookAgreement;
        }

        public String getUpdateDate() {
            return UpdateDate;
        }

        public void setUpdateDate(String UpdateDate) {
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

        public static class DeptBean {
            /**
             * RoleGroupId : 7
             * RoleGroup : null
             * AddressId : null
             * DeptName : 沈阳远大智能工业集团股份有限公司
             * DeptCode : 912101007310057103
             * Phone : 02425162806
             * DeptAddress : 辽宁省 沈阳市 沈河区
             * BaiduMapXY : null
             * LongitudeAndLatitude : null
             * Remark : null
             * Sort : 0
             * UpdateDate : 2017-02-10T10:56:26.897
             * DeptDataAddressIds : null
             * DeptDataAddress : null
             * DeptUsers : []
             * ID : 5400
             * CreateTime : 2017-02-10T10:56:26.897
             */

            private int RoleGroupId;
            private Object RoleGroup;
            private Object AddressId;
            private String DeptName;
            private String DeptCode;
            private String Phone;
            private String DeptAddress;
            private Object BaiduMapXY;
            private Object LongitudeAndLatitude;
            private Object Remark;
            private int Sort;
            private String UpdateDate;
            private Object DeptDataAddressIds;
            private Object DeptDataAddress;
            private int ID;
            private String CreateTime;
            private List<?> DeptUsers;

            public int getRoleGroupId() {
                return RoleGroupId;
            }

            public void setRoleGroupId(int RoleGroupId) {
                this.RoleGroupId = RoleGroupId;
            }

            public Object getRoleGroup() {
                return RoleGroup;
            }

            public void setRoleGroup(Object RoleGroup) {
                this.RoleGroup = RoleGroup;
            }

            public Object getAddressId() {
                return AddressId;
            }

            public void setAddressId(Object AddressId) {
                this.AddressId = AddressId;
            }

            public String getDeptName() {
                return DeptName;
            }

            public void setDeptName(String DeptName) {
                this.DeptName = DeptName;
            }

            public String getDeptCode() {
                return DeptCode;
            }

            public void setDeptCode(String DeptCode) {
                this.DeptCode = DeptCode;
            }

            public String getPhone() {
                return Phone;
            }

            public void setPhone(String Phone) {
                this.Phone = Phone;
            }

            public String getDeptAddress() {
                return DeptAddress;
            }

            public void setDeptAddress(String DeptAddress) {
                this.DeptAddress = DeptAddress;
            }

            public Object getBaiduMapXY() {
                return BaiduMapXY;
            }

            public void setBaiduMapXY(Object BaiduMapXY) {
                this.BaiduMapXY = BaiduMapXY;
            }

            public Object getLongitudeAndLatitude() {
                return LongitudeAndLatitude;
            }

            public void setLongitudeAndLatitude(Object LongitudeAndLatitude) {
                this.LongitudeAndLatitude = LongitudeAndLatitude;
            }

            public Object getRemark() {
                return Remark;
            }

            public void setRemark(Object Remark) {
                this.Remark = Remark;
            }

            public int getSort() {
                return Sort;
            }

            public void setSort(int Sort) {
                this.Sort = Sort;
            }

            public String getUpdateDate() {
                return UpdateDate;
            }

            public void setUpdateDate(String UpdateDate) {
                this.UpdateDate = UpdateDate;
            }

            public Object getDeptDataAddressIds() {
                return DeptDataAddressIds;
            }

            public void setDeptDataAddressIds(Object DeptDataAddressIds) {
                this.DeptDataAddressIds = DeptDataAddressIds;
            }

            public Object getDeptDataAddress() {
                return DeptDataAddress;
            }

            public void setDeptDataAddress(Object DeptDataAddress) {
                this.DeptDataAddress = DeptDataAddress;
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

            public List<?> getDeptUsers() {
                return DeptUsers;
            }

            public void setDeptUsers(List<?> DeptUsers) {
                this.DeptUsers = DeptUsers;
            }
        }
    }
}
