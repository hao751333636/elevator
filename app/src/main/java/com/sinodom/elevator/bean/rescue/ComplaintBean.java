package com.sinodom.elevator.bean.rescue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GUO on 2017/12/19.
 * 投诉建议
 */

public class ComplaintBean implements Serializable{

    /**
     * ParentId : null
     * ParentAdvice : null
     * ChildAdvices : null
     * AdviceTypeId : 94
     * AdviceTypeDict : {"ParentId":16,"ParentDict":null,"ChildDict":null,"TreeLeve":2,"FullPath":"投诉建议-投诉","DictName":"投诉","DictCode":"ComplaintType","DictDesc":"","DictValue":"","Sort":0,"ID":94,"CreateTime":"2015-06-06T11:14:00"}
     * AdviceStatusId : 45
     * AdviceStatusDict : {"ParentId":5,"ParentDict":null,"ChildDict":null,"TreeLeve":2,"FullPath":"跟进状态-待跟进","DictName":"待跟进","DictCode":"FollowUp","DictDesc":"","DictValue":"","Sort":0,"ID":45,"CreateTime":"2015-06-06T11:14:00"}
     * Level : 1
     * Title : 11
     * ContactName : 111
     * ContactPhone : 15502473779
     * Remark : 11
     * Feedback : 111
     * LiftId : 0
     * Lift : null
     * LiftNum : null
     * CreateUserId : 3922
     * CreateUser : {"DeptId":1637,"Dept":null,"RoleId":1,"Role":null,"UseLifts":null,"MaintLifts":null,"Maint2Lifts":null,"Maint3Lifts":null,"QualityLifts":null,"UserName":"admin","CheckUserName":null,"LoginName":"admin","Password":"03-1F-FD-5D-91-53-DC-F6-14-19-D6-1D-49-16-B6-7E","Job":null,"Phone":"admin","Mobile":"13804162182","CheckMobile":null,"Email":null,"Gender":"男","CertificateNum":null,"Sort":null,"Remark":null,"ActivationState":1,"AgreeDatatime":"2017-01-11T13:19:01.66","IsLookAgreement":true,"UpdateDate":"2017-12-19T10:27:24.05","RoleName":null,"NewPassword":null,"ConfirmPassword":null,"UserDataAddressIds":null,"UserDataAddress":null,"BusinessPermissionList":[],"ID":3922,"CreateTime":"2015-09-14T10:21:22.517"}
     * CreateDate : 2017-12-11T11:34:04
     * VerifyCode : 1
     * ContactPhoneCode : 1
     * ID : 64
     * CreateTime : 2017-12-11T11:34:04.06
     */

    private Object ParentId;
    private Object ParentAdvice;
    private Object ChildAdvices;
    private int AdviceTypeId;
    private AdviceTypeDictBean AdviceTypeDict;
    private int AdviceStatusId;
    private AdviceStatusDictBean AdviceStatusDict;
    private int Level;
    private String Title;
    private String ContactName;
    private String ContactPhone;
    private String Remark;
    private String Feedback;
    private int LiftId;
    private Object Lift;
    private Object LiftNum;
    private int CreateUserId;
    private CreateUserBean CreateUser;
    private String CreateDate;
    private String VerifyCode;
    private String ContactPhoneCode;
    private int ID;
    private String CreateTime;

    public Object getParentId() {
        return ParentId;
    }

    public void setParentId(Object ParentId) {
        this.ParentId = ParentId;
    }

    public Object getParentAdvice() {
        return ParentAdvice;
    }

    public void setParentAdvice(Object ParentAdvice) {
        this.ParentAdvice = ParentAdvice;
    }

    public Object getChildAdvices() {
        return ChildAdvices;
    }

    public void setChildAdvices(Object ChildAdvices) {
        this.ChildAdvices = ChildAdvices;
    }

    public int getAdviceTypeId() {
        return AdviceTypeId;
    }

    public void setAdviceTypeId(int AdviceTypeId) {
        this.AdviceTypeId = AdviceTypeId;
    }

    public AdviceTypeDictBean getAdviceTypeDict() {
        return AdviceTypeDict;
    }

    public void setAdviceTypeDict(AdviceTypeDictBean AdviceTypeDict) {
        this.AdviceTypeDict = AdviceTypeDict;
    }

    public int getAdviceStatusId() {
        return AdviceStatusId;
    }

    public void setAdviceStatusId(int AdviceStatusId) {
        this.AdviceStatusId = AdviceStatusId;
    }

    public AdviceStatusDictBean getAdviceStatusDict() {
        return AdviceStatusDict;
    }

    public void setAdviceStatusDict(AdviceStatusDictBean AdviceStatusDict) {
        this.AdviceStatusDict = AdviceStatusDict;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String ContactName) {
        this.ContactName = ContactName;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String ContactPhone) {
        this.ContactPhone = ContactPhone;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getFeedback() {
        return Feedback;
    }

    public void setFeedback(String Feedback) {
        this.Feedback = Feedback;
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

    public Object getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(Object LiftNum) {
        this.LiftNum = LiftNum;
    }

    public int getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(int CreateUserId) {
        this.CreateUserId = CreateUserId;
    }

    public CreateUserBean getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(CreateUserBean CreateUser) {
        this.CreateUser = CreateUser;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String CreateDate) {
        this.CreateDate = CreateDate;
    }

    public String getVerifyCode() {
        return VerifyCode;
    }

    public void setVerifyCode(String VerifyCode) {
        this.VerifyCode = VerifyCode;
    }

    public String getContactPhoneCode() {
        return ContactPhoneCode;
    }

    public void setContactPhoneCode(String ContactPhoneCode) {
        this.ContactPhoneCode = ContactPhoneCode;
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

    public static class AdviceTypeDictBean implements Serializable{
        /**
         * ParentId : 16
         * ParentDict : null
         * ChildDict : null
         * TreeLeve : 2
         * FullPath : 投诉建议-投诉
         * DictName : 投诉
         * DictCode : ComplaintType
         * DictDesc :
         * DictValue :
         * Sort : 0
         * ID : 94
         * CreateTime : 2015-06-06T11:14:00
         */

        private int ParentId;
        private Object ParentDict;
        private Object ChildDict;
        private int TreeLeve;
        private String FullPath;
        private String DictName;
        private String DictCode;
        private String DictDesc;
        private String DictValue;
        private int Sort;
        private int ID;
        private String CreateTime;

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }

        public Object getParentDict() {
            return ParentDict;
        }

        public void setParentDict(Object ParentDict) {
            this.ParentDict = ParentDict;
        }

        public Object getChildDict() {
            return ChildDict;
        }

        public void setChildDict(Object ChildDict) {
            this.ChildDict = ChildDict;
        }

        public int getTreeLeve() {
            return TreeLeve;
        }

        public void setTreeLeve(int TreeLeve) {
            this.TreeLeve = TreeLeve;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getDictName() {
            return DictName;
        }

        public void setDictName(String DictName) {
            this.DictName = DictName;
        }

        public String getDictCode() {
            return DictCode;
        }

        public void setDictCode(String DictCode) {
            this.DictCode = DictCode;
        }

        public String getDictDesc() {
            return DictDesc;
        }

        public void setDictDesc(String DictDesc) {
            this.DictDesc = DictDesc;
        }

        public String getDictValue() {
            return DictValue;
        }

        public void setDictValue(String DictValue) {
            this.DictValue = DictValue;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
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

    public static class AdviceStatusDictBean implements Serializable{
        /**
         * ParentId : 5
         * ParentDict : null
         * ChildDict : null
         * TreeLeve : 2
         * FullPath : 跟进状态-待跟进
         * DictName : 待跟进
         * DictCode : FollowUp
         * DictDesc :
         * DictValue :
         * Sort : 0
         * ID : 45
         * CreateTime : 2015-06-06T11:14:00
         */

        private int ParentId;
        private Object ParentDict;
        private Object ChildDict;
        private int TreeLeve;
        private String FullPath;
        private String DictName;
        private String DictCode;
        private String DictDesc;
        private String DictValue;
        private int Sort;
        private int ID;
        private String CreateTime;

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }

        public Object getParentDict() {
            return ParentDict;
        }

        public void setParentDict(Object ParentDict) {
            this.ParentDict = ParentDict;
        }

        public Object getChildDict() {
            return ChildDict;
        }

        public void setChildDict(Object ChildDict) {
            this.ChildDict = ChildDict;
        }

        public int getTreeLeve() {
            return TreeLeve;
        }

        public void setTreeLeve(int TreeLeve) {
            this.TreeLeve = TreeLeve;
        }

        public String getFullPath() {
            return FullPath;
        }

        public void setFullPath(String FullPath) {
            this.FullPath = FullPath;
        }

        public String getDictName() {
            return DictName;
        }

        public void setDictName(String DictName) {
            this.DictName = DictName;
        }

        public String getDictCode() {
            return DictCode;
        }

        public void setDictCode(String DictCode) {
            this.DictCode = DictCode;
        }

        public String getDictDesc() {
            return DictDesc;
        }

        public void setDictDesc(String DictDesc) {
            this.DictDesc = DictDesc;
        }

        public String getDictValue() {
            return DictValue;
        }

        public void setDictValue(String DictValue) {
            this.DictValue = DictValue;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
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

    public static class CreateUserBean implements Serializable{
        /**
         * DeptId : 1637
         * Dept : null
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
         * UpdateDate : 2017-12-19T10:27:24.05
         * RoleName : null
         * NewPassword : null
         * ConfirmPassword : null
         * UserDataAddressIds : null
         * UserDataAddress : null
         * BusinessPermissionList : []
         * ID : 3922
         * CreateTime : 2015-09-14T10:21:22.517
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
}
