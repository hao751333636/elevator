package com.sinodom.elevator.bean.sys;


import java.util.List;

public class LoginDataBean {

    private String LoginToken;
    private String LastAccessTime;
    private int UserID;
    private UserBean User;
    private String LoginName;
    private String ClientIP;
    private int EnumLoginAccountType;
    private String BusinessPermissionString;
    private int LoginErrorType;
    private int ID;
    private String CreateTime;
    private List<Integer> BusinessPermissionList;

    public String getLoginToken() {
        return LoginToken;
    }

    public void setLoginToken(String LoginToken) {
        this.LoginToken = LoginToken;
    }

    public String getLastAccessTime() {
        return LastAccessTime;
    }

    public void setLastAccessTime(String LastAccessTime) {
        this.LastAccessTime = LastAccessTime;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean User) {
        this.User = User;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }

    public String getClientIP() {
        return ClientIP;
    }

    public void setClientIP(String ClientIP) {
        this.ClientIP = ClientIP;
    }

    public int getEnumLoginAccountType() {
        return EnumLoginAccountType;
    }

    public void setEnumLoginAccountType(int EnumLoginAccountType) {
        this.EnumLoginAccountType = EnumLoginAccountType;
    }

    public String getBusinessPermissionString() {
        return BusinessPermissionString;
    }

    public void setBusinessPermissionString(String BusinessPermissionString) {
        this.BusinessPermissionString = BusinessPermissionString;
    }

    public int getLoginErrorType() {
        return LoginErrorType;
    }

    public void setLoginErrorType(int LoginErrorType) {
        this.LoginErrorType = LoginErrorType;
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

    public List<Integer> getBusinessPermissionList() {
        return BusinessPermissionList;
    }

    public void setBusinessPermissionList(List<Integer> BusinessPermissionList) {
        this.BusinessPermissionList = BusinessPermissionList;
    }

    public static class UserBean {
        /**
         * DeptId : 1637
         * Dept : {"RoleGroupId":1,"RoleGroup":{"RoleCode":"SoftDeptRole","RoleLevel":0,"Roles":null,"RoleName":"软件公司","RoleDesc":"软件公司权限 ","IsSupervision":true,"ID":1,"CreateTime":"2015-09-23T14:05:54.347"},"AddressId":null,"DeptName":"电梯救援管理系统","DeptCode":"000","Phone":null,"DeptAddress":null,"BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2015-09-14T10:20:51.15","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":[],"ID":1637,"CreateTime":"2015-09-14T10:20:51.113"}
         * RoleId : 1
         * Role : null
         * UseLifts : null
         * MaintLifts : null
         * Maint2Lifts : null
         * Maint3Lifts : null
         * QualityLifts : null
         * UserName : admin
         * CheckUserName : null
         * LoginName : admin
         * Password : 03-1F-FD-5D-91-53-DC-F6-14-19-D6-1D-49-16-B6-7E
         * Job : null
         * Phone : admin
         * Mobile : 13804162182
         * CheckMobile : null
         * Email : null
         * Gender : 男
         * CertificateNum : null
         * Sort : null
         * Remark : null
         * ActivationState : 1
         * AgreeDatatime : 2017-01-11T13:19:01.66
         * IsLookAgreement : true
         * UpdateDate : 2017-09-29T10:16:03.463
         * RoleName : null
         * NewPassword : null
         * ConfirmPassword : null
         * UserDataAddressIds : null
         * UserDataAddress : [{"ParentId":null,"ParentAddress":null,"ChildAddresses":null,"DeptDataAddress":null,"UserDataAddress":null,"Code":null,"Name":null,"Level":0,"Path":"37.550339,104.114129,5","ID":0,"CreateTime":"2017-10-30T15:18:32.0038116+08:00"}]
         * BusinessPermissionList : []
         * ID : 3922
         * CreateTime : 2015-09-14T10:21:22.517
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
        private String Gender;
        private Object CertificateNum;
        private Object Sort;
        private Object Remark;
        private int ActivationState;
        private String AgreeDatatime;
        private boolean IsLookAgreement;
        private String UpdateDate;
        private Object RoleName;
        private Object NewPassword;
        private Object ConfirmPassword;
        private Object UserDataAddressIds;
        private int ID;
        private String CreateTime;
        private List<UserDataAddressBean> UserDataAddress;
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

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
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

        public List<UserDataAddressBean> getUserDataAddress() {
            return UserDataAddress;
        }

        public void setUserDataAddress(List<UserDataAddressBean> UserDataAddress) {
            this.UserDataAddress = UserDataAddress;
        }

        public List<?> getBusinessPermissionList() {
            return BusinessPermissionList;
        }

        public void setBusinessPermissionList(List<?> BusinessPermissionList) {
            this.BusinessPermissionList = BusinessPermissionList;
        }

        public static class DeptBean {
            /**
             * RoleGroupId : 1
             * RoleGroup : {"RoleCode":"SoftDeptRole","RoleLevel":0,"Roles":null,"RoleName":"软件公司","RoleDesc":"软件公司权限 ","IsSupervision":true,"ID":1,"CreateTime":"2015-09-23T14:05:54.347"}
             * AddressId : null
             * DeptName : 电梯救援管理系统
             * DeptCode : 000
             * Phone : null
             * DeptAddress : null
             * BaiduMapXY : null
             * LongitudeAndLatitude : null
             * Remark : null
             * Sort : 0
             * UpdateDate : 2015-09-14T10:20:51.15
             * DeptDataAddressIds : null
             * DeptDataAddress : null
             * DeptUsers : []
             * ID : 1637
             * CreateTime : 2015-09-14T10:20:51.113
             */

            private int RoleGroupId;
            private RoleGroupBean RoleGroup;
            private Object AddressId;
            private String DeptName;
            private String DeptCode;
            private Object Phone;
            private Object DeptAddress;
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

            public RoleGroupBean getRoleGroup() {
                return RoleGroup;
            }

            public void setRoleGroup(RoleGroupBean RoleGroup) {
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

            public Object getPhone() {
                return Phone;
            }

            public void setPhone(Object Phone) {
                this.Phone = Phone;
            }

            public Object getDeptAddress() {
                return DeptAddress;
            }

            public void setDeptAddress(Object DeptAddress) {
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

            public static class RoleGroupBean {
                /**
                 * RoleCode : SoftDeptRole
                 * RoleLevel : 0
                 * Roles : null
                 * RoleName : 软件公司
                 * RoleDesc : 软件公司权限
                 * IsSupervision : true
                 * ID : 1
                 * CreateTime : 2015-09-23T14:05:54.347
                 */

                private String RoleCode;
                private int RoleLevel;
                private Object Roles;
                private String RoleName;
                private String RoleDesc;
                private boolean IsSupervision;
                private int ID;
                private String CreateTime;

                public String getRoleCode() {
                    return RoleCode;
                }

                public void setRoleCode(String RoleCode) {
                    this.RoleCode = RoleCode;
                }

                public int getRoleLevel() {
                    return RoleLevel;
                }

                public void setRoleLevel(int RoleLevel) {
                    this.RoleLevel = RoleLevel;
                }

                public Object getRoles() {
                    return Roles;
                }

                public void setRoles(Object Roles) {
                    this.Roles = Roles;
                }

                public String getRoleName() {
                    return RoleName;
                }

                public void setRoleName(String RoleName) {
                    this.RoleName = RoleName;
                }

                public String getRoleDesc() {
                    return RoleDesc;
                }

                public void setRoleDesc(String RoleDesc) {
                    this.RoleDesc = RoleDesc;
                }

                public boolean isIsSupervision() {
                    return IsSupervision;
                }

                public void setIsSupervision(boolean IsSupervision) {
                    this.IsSupervision = IsSupervision;
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

        public static class UserDataAddressBean {
            /**
             * ParentId : null
             * ParentAddress : null
             * ChildAddresses : null
             * DeptDataAddress : null
             * UserDataAddress : null
             * Code : null
             * Name : null
             * Level : 0
             * Path : 37.550339,104.114129,5
             * ID : 0
             * CreateTime : 2017-10-30T15:18:32.0038116+08:00
             */

            private Object ParentId;
            private Object ParentAddress;
            private Object ChildAddresses;
            private Object DeptDataAddress;
            private Object UserDataAddress;
            private Object Code;
            private Object Name;
            private int Level;
            private String Path;
            private int ID;
            private String CreateTime;

            public Object getParentId() {
                return ParentId;
            }

            public void setParentId(Object ParentId) {
                this.ParentId = ParentId;
            }

            public Object getParentAddress() {
                return ParentAddress;
            }

            public void setParentAddress(Object ParentAddress) {
                this.ParentAddress = ParentAddress;
            }

            public Object getChildAddresses() {
                return ChildAddresses;
            }

            public void setChildAddresses(Object ChildAddresses) {
                this.ChildAddresses = ChildAddresses;
            }

            public Object getDeptDataAddress() {
                return DeptDataAddress;
            }

            public void setDeptDataAddress(Object DeptDataAddress) {
                this.DeptDataAddress = DeptDataAddress;
            }

            public Object getUserDataAddress() {
                return UserDataAddress;
            }

            public void setUserDataAddress(Object UserDataAddress) {
                this.UserDataAddress = UserDataAddress;
            }

            public Object getCode() {
                return Code;
            }

            public void setCode(Object Code) {
                this.Code = Code;
            }

            public Object getName() {
                return Name;
            }

            public void setName(Object Name) {
                this.Name = Name;
            }

            public int getLevel() {
                return Level;
            }

            public void setLevel(int Level) {
                this.Level = Level;
            }

            public String getPath() {
                return Path;
            }

            public void setPath(String Path) {
                this.Path = Path;
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
