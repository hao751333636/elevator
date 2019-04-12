package com.sinodom.elevator.bean.business;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 安卓 on 2017/12/18.
 * 维保记录详细
 */

public class RecordDetailsBean implements Serializable {

    /**
     * UserId : 3922
     * User : {"DeptId":1637,"Dept":null,"RoleId":1,"Role":null,"UseLifts":null,"MaintLifts":null,"Maint2Lifts":null,"Maint3Lifts":null,"QualityLifts":null,"UserName":"admin","CheckUserName":null,"LoginName":"admin","Password":"E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E","Job":null,"Phone":"admin","Mobile":"18640100982","CheckMobile":null,"Email":null,"Gender":"男","CertificateNum":null,"Sort":null,"Remark":null,"ActivationState":1,"AgreeDatatime":"2017-01-11T13:19:01.66","IsLookAgreement":true,"UpdateDate":"2018-04-17T16:32:44.953","RoleName":null,"NewPassword":null,"ConfirmPassword":null,"UserDataAddressIds":null,"UserDataAddress":null,"ID":3922,"CreateTime":"2015-09-14T10:21:22.517"}
     * LiftId : 94797
     * Lift : {"LiftSiteId":40,"LiftSiteDict":null,"LiftTypeId":null,"LiftTypeDict":null,"LiftStatusId":81,"LiftStatusDict":null,"LiftMaintenanceTypeId":null,"LiftMaintenanceTypeDict":null,"UseDepartmentId":10307,"UseDepartment":{"RoleGroupId":6,"RoleGroup":null,"AddressId":null,"DeptName":"鞍山星光物业服务有限公司","DeptCode":"91210302319014842R","Phone":null,"DeptAddress":null,"BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2017-11-02T13:56:48.977","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":null,"ID":10307,"CreateTime":"2017-11-02T13:56:48.82"},"MaintenanceDepartmentId":6505,"MaintenanceDepartment":{"RoleGroupId":7,"RoleGroup":null,"AddressId":null,"DeptName":"鞍山市长征机电设备工程有限公司","DeptCode":"912103026837217460","Phone":"0412-2239935","DeptAddress":"辽宁省 鞍山市 铁东区 铁东三道街10栋21层267号","BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2017-06-29T13:06:56.977","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":null,"ID":6505,"CreateTime":"2017-06-29T13:06:55.807"},"YearInspectionDepartmentId":null,"YearInspectionDepartment":null,"RegisterDepartmentId":null,"RegisterDepartment":null,"MadeDepartmentId":3846,"MadeDepartment":null,"UpdateUserId":3922,"UpdateUser":{"DeptId":1637,"Dept":null,"RoleId":1,"Role":null,"UseLifts":null,"MaintLifts":null,"Maint2Lifts":null,"Maint3Lifts":null,"QualityLifts":null,"UserName":"admin","CheckUserName":null,"LoginName":"admin","Password":"E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E","Job":null,"Phone":"admin","Mobile":"18640100982","CheckMobile":null,"Email":null,"Gender":"男","CertificateNum":null,"Sort":null,"Remark":null,"ActivationState":1,"AgreeDatatime":"2017-01-11T13:19:01.66","IsLookAgreement":true,"UpdateDate":"2018-04-17T16:32:44.953","RoleName":null,"NewPassword":null,"ConfirmPassword":null,"UserDataAddressIds":null,"UserDataAddress":null,"ID":3922,"CreateTime":"2015-09-14T10:21:22.517"},"AddressId":49,"Address":null,"UseUsers":null,"MaintUsers":null,"Maint2Users":null,"Maint3Users":null,"QualityUsers":null,"ListTask":null,"ListCheck":[],"ListYearInspection":null,"ListPropertyCheck":null,"LiftNum":"406213","DeviceNum":"405374","LiftOnline":null,"ProblemDeviceNum":null,"LiftProblem":null,"CertificateNum":"30102103412008102735","MachineNum":"C0062356","CustomNum":null,"ManufacturingLicenseNumber":null,"InstallationAddress":"时代服装商品批发城8#","AddressPath":"辽宁省 鞍山市 铁西区","Brand":null,"Model":null,"BaiduMapXY":"123.033299,41.15408","LongitudeAndLatitude":"123.02105736006,41.146171689272","BaiduMapZoom":"18","MaintenancePeriod":null,"FloorNumber":null,"StationNumber":null,"GateNumber":null,"RatedSpeed":null,"Deadweight":null,"InstallationDatetime":null,"ManufactureDate":"2008-10-27T00:00:00","SignStatus":1,"SignInstall":1,"DeviceStatus":null,"ShieldReasonId":null,"ShieldReason":null,"UpdateDatetime":"2017-12-04T14:15:04.47","MaintStartDate":null,"ProductionMonth":"2008-10-01T00:00:00","IsInstall":false,"IsOnline":false,"GpsX":0,"GpsY":0,"ID":94797,"CreateTime":"2017-11-02T15:37:08.157"}
     * CheckDate : 2018-07-27T18:31:30.72
     * Remark : null
     * IsPassed : true
     * CType : 4
     * UploadDate : 2018-07-27T18:31:30.717
     * LongitudeAndLatitude : 123.02105736006,41.146171689272
     * CheckDetails : [{"CheckId":47322,"StepId":12,"Step":{"CheckType":4,"CheckTerms":[],"StepName":"其他","StepDesc":"其他","Sort":405,"IsTakePhoto":false,"IsActive":true,"ID":12,"CreateTime":"2015-10-08T00:00:00"},"CheckDate":"2018-07-27T18:31:32.41","CType":"4","IsPassed":true,"Remark":null,"PhotoUrl":"","NFCCode":null,"ID":343487,"CreateTime":"2018-07-27T18:31:33.473"},{"CheckId":47322,"StepId":26,"Step":{"CheckType":4,"CheckTerms":[{"StepId":26,"TermName":"上行超速保护装置动作试验","TermCode":"7","Sort":4007,"TermDesc":"工作正常","IsNFC":false,"ID":68,"CreateTime":"2016-06-01T14:24:55.113"},{"StepId":26,"TermName":"轿厢意外移动保护装置动作试验","TermCode":"8","Sort":4008,"TermDesc":"工作正常","IsNFC":false,"ID":69,"CreateTime":"2016-06-01T14:28:46.933"},{"StepId":26,"TermName":"轿顶、轿厢架、轿门及其附件安装螺栓","TermCode":"9","Sort":4009,"TermDesc":"紧固","IsNFC":false,"ID":70,"CreateTime":"2016-06-01T14:29:58.063"},{"StepId":26,"TermName":"轿厢和对重/平衡重的导轨支架","TermCode":"10","Sort":4010,"TermDesc":"固定，无松动","IsNFC":false,"ID":71,"CreateTime":"2016-06-01T14:30:58.73"},{"StepId":26,"TermName":"轿厢和对重/平衡重的导轨","TermCode":"11","Sort":4013,"TermDesc":"清洁，压板牢固","IsNFC":false,"ID":74,"CreateTime":"2016-06-01T14:35:28.637"},{"StepId":26,"TermName":"轿厢内报警装置（对讲系统）","TermCode":"18","Sort":4018,"TermDesc":"轿厢内报警装置（对讲系统）","IsNFC":true,"ID":91,"CreateTime":"2018-07-24T13:53:25.577"}],"StepName":"年度轿厢维保(轿厢照明、风扇、开关、按钮、安全钳、安全装置、电气触点、轿门运行等工作正常)","StepDesc":"年度轿厢维保(轿厢照明、风扇、开关、按钮、安全装置、安全钳、电气触点、轿门运行等工作正常)","Sort":402,"IsTakePhoto":false,"IsActive":true,"ID":26,"CreateTime":"2016-06-01T14:27:31.783"},"CheckDate":"2018-07-27T18:31:39.223","CType":"4","IsPassed":true,"Remark":null,"PhotoUrl":"","NFCCode":"","ID":343488,"CreateTime":"2018-07-27T18:31:39.307"},{"CheckId":47322,"StepId":25,"Step":{"CheckType":4,"CheckTerms":[{"StepId":25,"TermName":"减速机润滑油","TermCode":"1","Sort":4001,"TermDesc":"按照制造单位要求适时更换，保证油质符合要求","IsNFC":false,"ID":62,"CreateTime":"2016-06-01T14:11:59.073"},{"StepId":25,"TermName":"控制柜接触器、继电器触点","TermCode":"2","Sort":4002,"TermDesc":"接触良好","IsNFC":false,"ID":63,"CreateTime":"2016-06-01T14:14:25.903"},{"StepId":25,"TermName":"制动器铁芯(柱塞)","TermCode":"3","Sort":4003,"TermDesc":"进行清洁、润滑、检查，磨损量不超过制造单位要求","IsNFC":false,"ID":64,"CreateTime":"2016-06-01T14:15:37.527"},{"StepId":25,"TermName":"制动器制动能力","TermCode":"4","Sort":4004,"TermDesc":"符合制造单位要求，保持有足够的制动力，必要时进行轿厢装载125%额定载重量的制动试验","IsNFC":false,"ID":65,"CreateTime":"2016-06-01T14:17:23.757"},{"StepId":25,"TermName":"导电贿赂绝缘性能测试","TermCode":"5","Sort":4006,"TermDesc":"符合标准","IsNFC":false,"ID":67,"CreateTime":"2016-06-01T14:23:04.893"},{"StepId":25,"TermName":"限速器安全钳联动试验","TermCode":"6","Sort":4017,"TermDesc":"工作正常","IsNFC":false,"ID":78,"CreateTime":"2016-06-01T14:52:24.63"},{"StepId":25,"TermName":"机房内制动器","TermCode":"21","Sort":4021,"TermDesc":"机房内制动器","IsNFC":true,"ID":94,"CreateTime":"2018-07-24T13:57:22.96"}],"StepName":"年度机房维保(曳引机、制动器、编码器、限速器、控制柜、反绳轮等工作正常)","StepDesc":"年度机房维保(曳引机、制动器、编码器、限速器、控制柜、反绳轮等工作正常)","Sort":401,"IsTakePhoto":false,"IsActive":true,"ID":25,"CreateTime":"2016-06-01T14:10:09.987"},"CheckDate":"2018-07-27T18:31:41.79","CType":"4","IsPassed":true,"Remark":null,"PhotoUrl":"","NFCCode":"","ID":343489,"CreateTime":"2018-07-27T18:31:41.86"},{"CheckId":47322,"StepId":28,"Step":{"CheckType":4,"CheckTerms":[{"StepId":28,"TermName":"缓冲器","TermCode":"17","Sort":4016,"TermDesc":"固定，无松动","IsNFC":false,"ID":77,"CreateTime":"2016-06-01T14:50:43.32"},{"StepId":28,"TermName":"轿底各安装螺栓","TermCode":"16","Sort":4016,"TermDesc":"紧固","IsNFC":false,"ID":90,"CreateTime":"2017-01-01T00:00:00"},{"StepId":28,"TermName":"地坑内急停装置","TermCode":"19","Sort":4019,"TermDesc":"地坑内急停装置","IsNFC":true,"ID":92,"CreateTime":"2018-07-24T13:54:00.567"}],"StepName":"年度底坑维保(环境清洁，照明、缓冲器、急停开关等工作正常)","StepDesc":"年度底坑维保(环境清洁，照明、缓冲器、急停开关等工作正常)","Sort":404,"IsTakePhoto":false,"IsActive":true,"ID":28,"CreateTime":"2016-06-01T14:49:31.51"},"CheckDate":"2018-07-27T18:31:41.913","CType":"4","IsPassed":true,"Remark":null,"PhotoUrl":"","NFCCode":"","ID":343490,"CreateTime":"2018-07-27T18:31:41.97"},{"CheckId":47322,"StepId":27,"Step":{"CheckType":4,"CheckTerms":[{"StepId":27,"TermName":"层门装置和地坎","TermCode":"13","Sort":4013,"TermDesc":"无影响正常使用的变形，各安装螺栓紧固","IsNFC":false,"ID":73,"CreateTime":"2016-06-01T14:34:49.55"},{"StepId":27,"TermName":"随行电缆","TermCode":"12","Sort":4012,"TermDesc":"无损伤","IsNFC":false,"ID":87,"CreateTime":"2017-01-01T00:00:00"},{"StepId":27,"TermName":"轿厢称重装置","TermCode":"14","Sort":4014,"TermDesc":"准确有效","IsNFC":false,"ID":88,"CreateTime":"2017-01-01T00:00:00"},{"StepId":27,"TermName":"安全钳钳座","TermCode":"15","Sort":4015,"TermDesc":"固定，无松动","IsNFC":false,"ID":89,"CreateTime":"2017-01-01T00:00:00"},{"StepId":27,"TermName":"轿顶处轿门防碰撞保护装置","TermCode":"20","Sort":4020,"TermDesc":"轿顶处轿门防碰撞保护装置","IsNFC":true,"ID":93,"CreateTime":"2018-07-24T13:55:54.147"}],"StepName":"年度层门维保(地坎清洁，层楼召唤、显示、自动关门、自动复位、门锁触点等工作正常)","StepDesc":"年度层门维保(地坎清洁，层楼召唤、显示、自动关门、自动复位、门锁触点等工作正常)","Sort":403,"IsTakePhoto":false,"IsActive":true,"ID":27,"CreateTime":"2016-06-01T14:33:45.827"},"CheckDate":"2018-07-27T18:31:42.007","CType":"4","IsPassed":true,"Remark":null,"PhotoUrl":"","NFCCode":"","ID":343491,"CreateTime":"2018-07-27T18:31:42.073"},{"CheckId":47322,"StepId":29,"Step":{"CheckType":4,"CheckTerms":[],"StepName":"电梯运行正常(要求:拍摄轿箱内的电梯标识牌)","StepDesc":"电梯运行正常(要求:拍摄轿箱内的电梯标识牌)","Sort":406,"IsTakePhoto":true,"IsActive":true,"ID":29,"CreateTime":"2016-12-16T14:12:44.21"},"CheckDate":"2018-07-27T18:31:42.15","CType":"4","IsPassed":true,"Remark":null,"PhotoUrl":"~/Upload/NFCCheck/day_180727/201807270631425227.jpg","NFCCode":null,"ID":343492,"CreateTime":"2018-07-27T18:31:42.207"}]
     * CheckUsers : null
     * ConfirmUser : null
     * ConfirmUserMobile : null
     * Sign : null
     * SignDateTime : null
     * Use_UserID : 0
     * Use_UserName : null
     * Use_Createdate : null
     * Use_PhotoUrl : null
     * IsNFC : true
     * ID : 47322
     * CreateTime : 2018-07-27T18:31:30.723
     */

    private int UserId;
    private UserBean User;
    private int LiftId;
    private LiftBean Lift;
    private String CheckDate;
    private Object Remark;
    private boolean IsPassed;
    private String CType;
    private String UploadDate;
    private String LongitudeAndLatitude;
    private Object CheckUsers;
    private Object ConfirmUser;
    private Object ConfirmUserMobile;
    private Object Sign;
    private Object SignDateTime;
    private int Use_UserID;
    private String Use_UserName;
    private Object Use_Createdate;
    private String Use_PhotoUrl;
    private boolean IsNFC;
    private int ID;
    private String CreateTime;
    private List<CheckDetailsBean> CheckDetails;

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

    public LiftBean getLift() {
        return Lift;
    }

    public void setLift(LiftBean Lift) {
        this.Lift = Lift;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String CheckDate) {
        this.CheckDate = CheckDate;
    }

    public Object getRemark() {
        return Remark;
    }

    public void setRemark(Object Remark) {
        this.Remark = Remark;
    }

    public boolean isIsPassed() {
        return IsPassed;
    }

    public void setIsPassed(boolean IsPassed) {
        this.IsPassed = IsPassed;
    }

    public String getCType() {
        return CType;
    }

    public void setCType(String CType) {
        this.CType = CType;
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

    public Object getCheckUsers() {
        return CheckUsers;
    }

    public void setCheckUsers(Object CheckUsers) {
        this.CheckUsers = CheckUsers;
    }

    public Object getConfirmUser() {
        return ConfirmUser;
    }

    public void setConfirmUser(Object ConfirmUser) {
        this.ConfirmUser = ConfirmUser;
    }

    public Object getConfirmUserMobile() {
        return ConfirmUserMobile;
    }

    public void setConfirmUserMobile(Object ConfirmUserMobile) {
        this.ConfirmUserMobile = ConfirmUserMobile;
    }

    public Object getSign() {
        return Sign;
    }

    public void setSign(Object Sign) {
        this.Sign = Sign;
    }

    public Object getSignDateTime() {
        return SignDateTime;
    }

    public void setSignDateTime(Object SignDateTime) {
        this.SignDateTime = SignDateTime;
    }

    public int getUse_UserID() {
        return Use_UserID;
    }

    public void setUse_UserID(int Use_UserID) {
        this.Use_UserID = Use_UserID;
    }

    public String getUse_UserName() {
        return Use_UserName;
    }

    public void setUse_UserName(String Use_UserName) {
        this.Use_UserName = Use_UserName;
    }

    public Object getUse_Createdate() {
        return Use_Createdate;
    }

    public void setUse_Createdate(Object Use_Createdate) {
        this.Use_Createdate = Use_Createdate;
    }

    public String getUse_PhotoUrl() {
        return Use_PhotoUrl;
    }

    public void setUse_PhotoUrl(String Use_PhotoUrl) {
        this.Use_PhotoUrl = Use_PhotoUrl;
    }

    public boolean isIsNFC() {
        return IsNFC;
    }

    public void setIsNFC(boolean IsNFC) {
        this.IsNFC = IsNFC;
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

    public List<CheckDetailsBean> getCheckDetails() {
        return CheckDetails;
    }

    public void setCheckDetails(List<CheckDetailsBean> CheckDetails) {
        this.CheckDetails = CheckDetails;
    }

    public static class UserBean {
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
         * Password : E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E
         * Job : null
         * Phone : admin
         * Mobile : 18640100982
         * CheckMobile : null
         * Email : null
         * Gender : 男
         * CertificateNum : null
         * Sort : null
         * Remark : null
         * ActivationState : 1
         * AgreeDatatime : 2017-01-11T13:19:01.66
         * IsLookAgreement : true
         * UpdateDate : 2018-04-17T16:32:44.953
         * RoleName : null
         * NewPassword : null
         * ConfirmPassword : null
         * UserDataAddressIds : null
         * UserDataAddress : null
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
    }

    public static class LiftBean {
        /**
         * LiftSiteId : 40
         * LiftSiteDict : null
         * LiftTypeId : null
         * LiftTypeDict : null
         * LiftStatusId : 81
         * LiftStatusDict : null
         * LiftMaintenanceTypeId : null
         * LiftMaintenanceTypeDict : null
         * UseDepartmentId : 10307
         * UseDepartment : {"RoleGroupId":6,"RoleGroup":null,"AddressId":null,"DeptName":"鞍山星光物业服务有限公司","DeptCode":"91210302319014842R","Phone":null,"DeptAddress":null,"BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2017-11-02T13:56:48.977","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":null,"ID":10307,"CreateTime":"2017-11-02T13:56:48.82"}
         * MaintenanceDepartmentId : 6505
         * MaintenanceDepartment : {"RoleGroupId":7,"RoleGroup":null,"AddressId":null,"DeptName":"鞍山市长征机电设备工程有限公司","DeptCode":"912103026837217460","Phone":"0412-2239935","DeptAddress":"辽宁省 鞍山市 铁东区 铁东三道街10栋21层267号","BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2017-06-29T13:06:56.977","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":null,"ID":6505,"CreateTime":"2017-06-29T13:06:55.807"}
         * YearInspectionDepartmentId : null
         * YearInspectionDepartment : null
         * RegisterDepartmentId : null
         * RegisterDepartment : null
         * MadeDepartmentId : 3846
         * MadeDepartment : null
         * UpdateUserId : 3922
         * UpdateUser : {"DeptId":1637,"Dept":null,"RoleId":1,"Role":null,"UseLifts":null,"MaintLifts":null,"Maint2Lifts":null,"Maint3Lifts":null,"QualityLifts":null,"UserName":"admin","CheckUserName":null,"LoginName":"admin","Password":"E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E","Job":null,"Phone":"admin","Mobile":"18640100982","CheckMobile":null,"Email":null,"Gender":"男","CertificateNum":null,"Sort":null,"Remark":null,"ActivationState":1,"AgreeDatatime":"2017-01-11T13:19:01.66","IsLookAgreement":true,"UpdateDate":"2018-04-17T16:32:44.953","RoleName":null,"NewPassword":null,"ConfirmPassword":null,"UserDataAddressIds":null,"UserDataAddress":null,"ID":3922,"CreateTime":"2015-09-14T10:21:22.517"}
         * AddressId : 49
         * Address : null
         * UseUsers : null
         * MaintUsers : null
         * Maint2Users : null
         * Maint3Users : null
         * QualityUsers : null
         * ListTask : null
         * ListCheck : []
         * ListYearInspection : null
         * ListPropertyCheck : null
         * LiftNum : 406213
         * DeviceNum : 405374
         * LiftOnline : null
         * ProblemDeviceNum : null
         * LiftProblem : null
         * CertificateNum : 30102103412008102735
         * MachineNum : C0062356
         * CustomNum : null
         * ManufacturingLicenseNumber : null
         * InstallationAddress : 时代服装商品批发城8#
         * AddressPath : 辽宁省 鞍山市 铁西区
         * Brand : null
         * Model : null
         * BaiduMapXY : 123.033299,41.15408
         * LongitudeAndLatitude : 123.02105736006,41.146171689272
         * BaiduMapZoom : 18
         * MaintenancePeriod : null
         * FloorNumber : null
         * StationNumber : null
         * GateNumber : null
         * RatedSpeed : null
         * Deadweight : null
         * InstallationDatetime : null
         * ManufactureDate : 2008-10-27T00:00:00
         * SignStatus : 1
         * SignInstall : 1
         * DeviceStatus : null
         * ShieldReasonId : null
         * ShieldReason : null
         * UpdateDatetime : 2017-12-04T14:15:04.47
         * MaintStartDate : null
         * ProductionMonth : 2008-10-01T00:00:00
         * IsInstall : false
         * IsOnline : false
         * GpsX : 0.0
         * GpsY : 0.0
         * ID : 94797
         * CreateTime : 2017-11-02T15:37:08.157
         */

        private int LiftSiteId;
        private Object LiftSiteDict;
        private Object LiftTypeId;
        private Object LiftTypeDict;
        private int LiftStatusId;
        private Object LiftStatusDict;
        private Object LiftMaintenanceTypeId;
        private Object LiftMaintenanceTypeDict;
        private int UseDepartmentId;
        private UseDepartmentBean UseDepartment;
        private int MaintenanceDepartmentId;
        private MaintenanceDepartmentBean MaintenanceDepartment;
        private Object YearInspectionDepartmentId;
        private Object YearInspectionDepartment;
        private Object RegisterDepartmentId;
        private Object RegisterDepartment;
        private int MadeDepartmentId;
        private Object MadeDepartment;
        private int UpdateUserId;
        private UpdateUserBean UpdateUser;
        private int AddressId;
        private Object Address;
        private Object UseUsers;
        private Object MaintUsers;
        private Object Maint2Users;
        private Object Maint3Users;
        private Object QualityUsers;
        private Object ListTask;
        private Object ListYearInspection;
        private Object ListPropertyCheck;
        private String LiftNum;
        private String DeviceNum;
        private Object LiftOnline;
        private Object ProblemDeviceNum;
        private Object LiftProblem;
        private String CertificateNum;
        private String MachineNum;
        private Object CustomNum;
        private Object ManufacturingLicenseNumber;
        private String InstallationAddress;
        private String AddressPath;
        private Object Brand;
        private Object Model;
        private String BaiduMapXY;
        private String LongitudeAndLatitude;
        private String BaiduMapZoom;
        private Object MaintenancePeriod;
        private Object FloorNumber;
        private Object StationNumber;
        private Object GateNumber;
        private Object RatedSpeed;
        private Object Deadweight;
        private Object InstallationDatetime;
        private String ManufactureDate;
        private int SignStatus;
        private int SignInstall;
        private Object DeviceStatus;
        private Object ShieldReasonId;
        private Object ShieldReason;
        private String UpdateDatetime;
        private Object MaintStartDate;
        private String ProductionMonth;
        private boolean IsInstall;
        private boolean IsOnline;
        private double GpsX;
        private double GpsY;
        private int ID;
        private String CreateTime;
        private List<?> ListCheck;

        public int getLiftSiteId() {
            return LiftSiteId;
        }

        public void setLiftSiteId(int LiftSiteId) {
            this.LiftSiteId = LiftSiteId;
        }

        public Object getLiftSiteDict() {
            return LiftSiteDict;
        }

        public void setLiftSiteDict(Object LiftSiteDict) {
            this.LiftSiteDict = LiftSiteDict;
        }

        public Object getLiftTypeId() {
            return LiftTypeId;
        }

        public void setLiftTypeId(Object LiftTypeId) {
            this.LiftTypeId = LiftTypeId;
        }

        public Object getLiftTypeDict() {
            return LiftTypeDict;
        }

        public void setLiftTypeDict(Object LiftTypeDict) {
            this.LiftTypeDict = LiftTypeDict;
        }

        public int getLiftStatusId() {
            return LiftStatusId;
        }

        public void setLiftStatusId(int LiftStatusId) {
            this.LiftStatusId = LiftStatusId;
        }

        public Object getLiftStatusDict() {
            return LiftStatusDict;
        }

        public void setLiftStatusDict(Object LiftStatusDict) {
            this.LiftStatusDict = LiftStatusDict;
        }

        public Object getLiftMaintenanceTypeId() {
            return LiftMaintenanceTypeId;
        }

        public void setLiftMaintenanceTypeId(Object LiftMaintenanceTypeId) {
            this.LiftMaintenanceTypeId = LiftMaintenanceTypeId;
        }

        public Object getLiftMaintenanceTypeDict() {
            return LiftMaintenanceTypeDict;
        }

        public void setLiftMaintenanceTypeDict(Object LiftMaintenanceTypeDict) {
            this.LiftMaintenanceTypeDict = LiftMaintenanceTypeDict;
        }

        public int getUseDepartmentId() {
            return UseDepartmentId;
        }

        public void setUseDepartmentId(int UseDepartmentId) {
            this.UseDepartmentId = UseDepartmentId;
        }

        public UseDepartmentBean getUseDepartment() {
            return UseDepartment;
        }

        public void setUseDepartment(UseDepartmentBean UseDepartment) {
            this.UseDepartment = UseDepartment;
        }

        public int getMaintenanceDepartmentId() {
            return MaintenanceDepartmentId;
        }

        public void setMaintenanceDepartmentId(int MaintenanceDepartmentId) {
            this.MaintenanceDepartmentId = MaintenanceDepartmentId;
        }

        public MaintenanceDepartmentBean getMaintenanceDepartment() {
            return MaintenanceDepartment;
        }

        public void setMaintenanceDepartment(MaintenanceDepartmentBean MaintenanceDepartment) {
            this.MaintenanceDepartment = MaintenanceDepartment;
        }

        public Object getYearInspectionDepartmentId() {
            return YearInspectionDepartmentId;
        }

        public void setYearInspectionDepartmentId(Object YearInspectionDepartmentId) {
            this.YearInspectionDepartmentId = YearInspectionDepartmentId;
        }

        public Object getYearInspectionDepartment() {
            return YearInspectionDepartment;
        }

        public void setYearInspectionDepartment(Object YearInspectionDepartment) {
            this.YearInspectionDepartment = YearInspectionDepartment;
        }

        public Object getRegisterDepartmentId() {
            return RegisterDepartmentId;
        }

        public void setRegisterDepartmentId(Object RegisterDepartmentId) {
            this.RegisterDepartmentId = RegisterDepartmentId;
        }

        public Object getRegisterDepartment() {
            return RegisterDepartment;
        }

        public void setRegisterDepartment(Object RegisterDepartment) {
            this.RegisterDepartment = RegisterDepartment;
        }

        public int getMadeDepartmentId() {
            return MadeDepartmentId;
        }

        public void setMadeDepartmentId(int MadeDepartmentId) {
            this.MadeDepartmentId = MadeDepartmentId;
        }

        public Object getMadeDepartment() {
            return MadeDepartment;
        }

        public void setMadeDepartment(Object MadeDepartment) {
            this.MadeDepartment = MadeDepartment;
        }

        public int getUpdateUserId() {
            return UpdateUserId;
        }

        public void setUpdateUserId(int UpdateUserId) {
            this.UpdateUserId = UpdateUserId;
        }

        public UpdateUserBean getUpdateUser() {
            return UpdateUser;
        }

        public void setUpdateUser(UpdateUserBean UpdateUser) {
            this.UpdateUser = UpdateUser;
        }

        public int getAddressId() {
            return AddressId;
        }

        public void setAddressId(int AddressId) {
            this.AddressId = AddressId;
        }

        public Object getAddress() {
            return Address;
        }

        public void setAddress(Object Address) {
            this.Address = Address;
        }

        public Object getUseUsers() {
            return UseUsers;
        }

        public void setUseUsers(Object UseUsers) {
            this.UseUsers = UseUsers;
        }

        public Object getMaintUsers() {
            return MaintUsers;
        }

        public void setMaintUsers(Object MaintUsers) {
            this.MaintUsers = MaintUsers;
        }

        public Object getMaint2Users() {
            return Maint2Users;
        }

        public void setMaint2Users(Object Maint2Users) {
            this.Maint2Users = Maint2Users;
        }

        public Object getMaint3Users() {
            return Maint3Users;
        }

        public void setMaint3Users(Object Maint3Users) {
            this.Maint3Users = Maint3Users;
        }

        public Object getQualityUsers() {
            return QualityUsers;
        }

        public void setQualityUsers(Object QualityUsers) {
            this.QualityUsers = QualityUsers;
        }

        public Object getListTask() {
            return ListTask;
        }

        public void setListTask(Object ListTask) {
            this.ListTask = ListTask;
        }

        public Object getListYearInspection() {
            return ListYearInspection;
        }

        public void setListYearInspection(Object ListYearInspection) {
            this.ListYearInspection = ListYearInspection;
        }

        public Object getListPropertyCheck() {
            return ListPropertyCheck;
        }

        public void setListPropertyCheck(Object ListPropertyCheck) {
            this.ListPropertyCheck = ListPropertyCheck;
        }

        public String getLiftNum() {
            return LiftNum;
        }

        public void setLiftNum(String LiftNum) {
            this.LiftNum = LiftNum;
        }

        public String getDeviceNum() {
            return DeviceNum;
        }

        public void setDeviceNum(String DeviceNum) {
            this.DeviceNum = DeviceNum;
        }

        public Object getLiftOnline() {
            return LiftOnline;
        }

        public void setLiftOnline(Object LiftOnline) {
            this.LiftOnline = LiftOnline;
        }

        public Object getProblemDeviceNum() {
            return ProblemDeviceNum;
        }

        public void setProblemDeviceNum(Object ProblemDeviceNum) {
            this.ProblemDeviceNum = ProblemDeviceNum;
        }

        public Object getLiftProblem() {
            return LiftProblem;
        }

        public void setLiftProblem(Object LiftProblem) {
            this.LiftProblem = LiftProblem;
        }

        public String getCertificateNum() {
            return CertificateNum;
        }

        public void setCertificateNum(String CertificateNum) {
            this.CertificateNum = CertificateNum;
        }

        public String getMachineNum() {
            return MachineNum;
        }

        public void setMachineNum(String MachineNum) {
            this.MachineNum = MachineNum;
        }

        public Object getCustomNum() {
            return CustomNum;
        }

        public void setCustomNum(Object CustomNum) {
            this.CustomNum = CustomNum;
        }

        public Object getManufacturingLicenseNumber() {
            return ManufacturingLicenseNumber;
        }

        public void setManufacturingLicenseNumber(Object ManufacturingLicenseNumber) {
            this.ManufacturingLicenseNumber = ManufacturingLicenseNumber;
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

        public Object getBrand() {
            return Brand;
        }

        public void setBrand(Object Brand) {
            this.Brand = Brand;
        }

        public Object getModel() {
            return Model;
        }

        public void setModel(Object Model) {
            this.Model = Model;
        }

        public String getBaiduMapXY() {
            return BaiduMapXY;
        }

        public void setBaiduMapXY(String BaiduMapXY) {
            this.BaiduMapXY = BaiduMapXY;
        }

        public String getLongitudeAndLatitude() {
            return LongitudeAndLatitude;
        }

        public void setLongitudeAndLatitude(String LongitudeAndLatitude) {
            this.LongitudeAndLatitude = LongitudeAndLatitude;
        }

        public String getBaiduMapZoom() {
            return BaiduMapZoom;
        }

        public void setBaiduMapZoom(String BaiduMapZoom) {
            this.BaiduMapZoom = BaiduMapZoom;
        }

        public Object getMaintenancePeriod() {
            return MaintenancePeriod;
        }

        public void setMaintenancePeriod(Object MaintenancePeriod) {
            this.MaintenancePeriod = MaintenancePeriod;
        }

        public Object getFloorNumber() {
            return FloorNumber;
        }

        public void setFloorNumber(Object FloorNumber) {
            this.FloorNumber = FloorNumber;
        }

        public Object getStationNumber() {
            return StationNumber;
        }

        public void setStationNumber(Object StationNumber) {
            this.StationNumber = StationNumber;
        }

        public Object getGateNumber() {
            return GateNumber;
        }

        public void setGateNumber(Object GateNumber) {
            this.GateNumber = GateNumber;
        }

        public Object getRatedSpeed() {
            return RatedSpeed;
        }

        public void setRatedSpeed(Object RatedSpeed) {
            this.RatedSpeed = RatedSpeed;
        }

        public Object getDeadweight() {
            return Deadweight;
        }

        public void setDeadweight(Object Deadweight) {
            this.Deadweight = Deadweight;
        }

        public Object getInstallationDatetime() {
            return InstallationDatetime;
        }

        public void setInstallationDatetime(Object InstallationDatetime) {
            this.InstallationDatetime = InstallationDatetime;
        }

        public String getManufactureDate() {
            return ManufactureDate;
        }

        public void setManufactureDate(String ManufactureDate) {
            this.ManufactureDate = ManufactureDate;
        }

        public int getSignStatus() {
            return SignStatus;
        }

        public void setSignStatus(int SignStatus) {
            this.SignStatus = SignStatus;
        }

        public int getSignInstall() {
            return SignInstall;
        }

        public void setSignInstall(int SignInstall) {
            this.SignInstall = SignInstall;
        }

        public Object getDeviceStatus() {
            return DeviceStatus;
        }

        public void setDeviceStatus(Object DeviceStatus) {
            this.DeviceStatus = DeviceStatus;
        }

        public Object getShieldReasonId() {
            return ShieldReasonId;
        }

        public void setShieldReasonId(Object ShieldReasonId) {
            this.ShieldReasonId = ShieldReasonId;
        }

        public Object getShieldReason() {
            return ShieldReason;
        }

        public void setShieldReason(Object ShieldReason) {
            this.ShieldReason = ShieldReason;
        }

        public String getUpdateDatetime() {
            return UpdateDatetime;
        }

        public void setUpdateDatetime(String UpdateDatetime) {
            this.UpdateDatetime = UpdateDatetime;
        }

        public Object getMaintStartDate() {
            return MaintStartDate;
        }

        public void setMaintStartDate(Object MaintStartDate) {
            this.MaintStartDate = MaintStartDate;
        }

        public String getProductionMonth() {
            return ProductionMonth;
        }

        public void setProductionMonth(String ProductionMonth) {
            this.ProductionMonth = ProductionMonth;
        }

        public boolean isIsInstall() {
            return IsInstall;
        }

        public void setIsInstall(boolean IsInstall) {
            this.IsInstall = IsInstall;
        }

        public boolean isIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(boolean IsOnline) {
            this.IsOnline = IsOnline;
        }

        public double getGpsX() {
            return GpsX;
        }

        public void setGpsX(double GpsX) {
            this.GpsX = GpsX;
        }

        public double getGpsY() {
            return GpsY;
        }

        public void setGpsY(double GpsY) {
            this.GpsY = GpsY;
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

        public List<?> getListCheck() {
            return ListCheck;
        }

        public void setListCheck(List<?> ListCheck) {
            this.ListCheck = ListCheck;
        }

        public static class UseDepartmentBean {
            /**
             * RoleGroupId : 6
             * RoleGroup : null
             * AddressId : null
             * DeptName : 鞍山星光物业服务有限公司
             * DeptCode : 91210302319014842R
             * Phone : null
             * DeptAddress : null
             * BaiduMapXY : null
             * LongitudeAndLatitude : null
             * Remark : null
             * Sort : 0
             * UpdateDate : 2017-11-02T13:56:48.977
             * DeptDataAddressIds : null
             * DeptDataAddress : null
             * DeptUsers : null
             * ID : 10307
             * CreateTime : 2017-11-02T13:56:48.82
             */

            private int RoleGroupId;
            private Object RoleGroup;
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
            private Object DeptUsers;
            private int ID;
            private String CreateTime;

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

            public Object getDeptUsers() {
                return DeptUsers;
            }

            public void setDeptUsers(Object DeptUsers) {
                this.DeptUsers = DeptUsers;
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

        public static class MaintenanceDepartmentBean {
            /**
             * RoleGroupId : 7
             * RoleGroup : null
             * AddressId : null
             * DeptName : 鞍山市长征机电设备工程有限公司
             * DeptCode : 912103026837217460
             * Phone : 0412-2239935
             * DeptAddress : 辽宁省 鞍山市 铁东区 铁东三道街10栋21层267号
             * BaiduMapXY : null
             * LongitudeAndLatitude : null
             * Remark : null
             * Sort : 0
             * UpdateDate : 2017-06-29T13:06:56.977
             * DeptDataAddressIds : null
             * DeptDataAddress : null
             * DeptUsers : null
             * ID : 6505
             * CreateTime : 2017-06-29T13:06:55.807
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
            private Object DeptUsers;
            private int ID;
            private String CreateTime;

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

            public Object getDeptUsers() {
                return DeptUsers;
            }

            public void setDeptUsers(Object DeptUsers) {
                this.DeptUsers = DeptUsers;
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

        public static class UpdateUserBean {
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
             * Password : E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E
             * Job : null
             * Phone : admin
             * Mobile : 18640100982
             * CheckMobile : null
             * Email : null
             * Gender : 男
             * CertificateNum : null
             * Sort : null
             * Remark : null
             * ActivationState : 1
             * AgreeDatatime : 2017-01-11T13:19:01.66
             * IsLookAgreement : true
             * UpdateDate : 2018-04-17T16:32:44.953
             * RoleName : null
             * NewPassword : null
             * ConfirmPassword : null
             * UserDataAddressIds : null
             * UserDataAddress : null
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
        }
    }

    public static class CheckDetailsBean {
        /**
         * CheckId : 47322
         * StepId : 12
         * Step : {"CheckType":4,"CheckTerms":[],"StepName":"其他","StepDesc":"其他","Sort":405,"IsTakePhoto":false,"IsActive":true,"ID":12,"CreateTime":"2015-10-08T00:00:00"}
         * CheckDate : 2018-07-27T18:31:32.41
         * CType : 4
         * IsPassed : true
         * Remark : null
         * PhotoUrl :
         * NFCCode : null
         * ID : 343487
         * CreateTime : 2018-07-27T18:31:33.473
         */

        private int CheckId;
        private int StepId;
        private StepBean Step;
        private String CheckDate;
        private String CType;
        private boolean IsPassed;
        private Object Remark;
        private String PhotoUrl;
        private Object NFCCode;
        private int ID;
        private String CreateTime;

        public int getCheckId() {
            return CheckId;
        }

        public void setCheckId(int CheckId) {
            this.CheckId = CheckId;
        }

        public int getStepId() {
            return StepId;
        }

        public void setStepId(int StepId) {
            this.StepId = StepId;
        }

        public StepBean getStep() {
            return Step;
        }

        public void setStep(StepBean Step) {
            this.Step = Step;
        }

        public String getCheckDate() {
            return CheckDate;
        }

        public void setCheckDate(String CheckDate) {
            this.CheckDate = CheckDate;
        }

        public String getCType() {
            return CType;
        }

        public void setCType(String CType) {
            this.CType = CType;
        }

        public boolean isIsPassed() {
            return IsPassed;
        }

        public void setIsPassed(boolean IsPassed) {
            this.IsPassed = IsPassed;
        }

        public Object getRemark() {
            return Remark;
        }

        public void setRemark(Object Remark) {
            this.Remark = Remark;
        }

        public String getPhotoUrl() {
            return PhotoUrl;
        }

        public void setPhotoUrl(String PhotoUrl) {
            this.PhotoUrl = PhotoUrl;
        }

        public Object getNFCCode() {
            return NFCCode;
        }

        public void setNFCCode(Object NFCCode) {
            this.NFCCode = NFCCode;
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

        public static class StepBean {
            /**
             * CheckType : 4
             * CheckTerms : []
             * StepName : 其他
             * StepDesc : 其他
             * Sort : 405
             * IsTakePhoto : false
             * IsActive : true
             * ID : 12
             * CreateTime : 2015-10-08T00:00:00
             */

            private int CheckType;
            private String StepName;
            private String StepDesc;
            private int Sort;
            private boolean IsTakePhoto;
            private boolean IsActive;
            private int ID;
            private String CreateTime;
            private List<?> CheckTerms;

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

            public List<?> getCheckTerms() {
                return CheckTerms;
            }

            public void setCheckTerms(List<?> CheckTerms) {
                this.CheckTerms = CheckTerms;
            }
        }
    }
}
