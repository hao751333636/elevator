package com.sinodom.elevator.bean.repairrecord;

import java.util.List;

/**
 * Created by GUO on 2017/12/28.
 */

public class RepairRecordDetailBean {

    /**
     * UserId : 14066
     * User : {"DeptId":6667,"Dept":null,"RoleId":15,"Role":null,"UseLifts":null,"MaintLifts":null,"Maint2Lifts":null,"Maint3Lifts":null,"QualityLifts":null,"UserName":"张健","CheckUserName":null,"LoginName":"13674188620","Password":"E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E","Job":null,"Phone":null,"Mobile":"13674188620","CheckMobile":null,"Email":null,"Gender":null,"CertificateNum":null,"Sort":null,"Remark":null,"ActivationState":1,"AgreeDatatime":null,"IsLookAgreement":false,"UpdateDate":null,"RoleName":null,"NewPassword":null,"ConfirmPassword":null,"UserDataAddressIds":null,"UserDataAddress":null,"BusinessPermissionList":[],"ID":14066,"CreateTime":"2017-08-24T15:16:16.89"}
     * LiftId : 67256
     * Lift : {"LiftSiteId":40,"LiftSiteDict":null,"LiftTypeId":null,"LiftTypeDict":null,"LiftStatusId":81,"LiftStatusDict":null,"LiftMaintenanceTypeId":null,"LiftMaintenanceTypeDict":null,"UseDepartmentId":6666,"UseDepartment":null,"MaintenanceDepartmentId":6667,"MaintenanceDepartment":null,"YearInspectionDepartmentId":null,"YearInspectionDepartment":null,"RegisterDepartmentId":null,"RegisterDepartment":null,"MadeDepartmentId":1631,"MadeDepartment":null,"UpdateUserId":null,"UpdateUser":null,"AddressId":20,"Address":null,"UseUsers":null,"MaintUsers":null,"Maint2Users":null,"Maint3Users":null,"QualityUsers":null,"ListTask":null,"ListCheck":null,"ListYearInspection":null,"ListPropertyCheck":null,"LiftNum":"193668","DeviceNum":"193668","LiftOnline":null,"ProblemDeviceNum":null,"LiftProblem":null,"CertificateNum":"31102101002012100033","MachineNum":null,"CustomNum":null,"ManufacturingLicenseNumber":null,"InstallationAddress":"联合路三洋重工北区地块1#2","AddressPath":"辽宁省 沈阳市 大东区","Brand":null,"Model":null,"BaiduMapXY":null,"LongitudeAndLatitude":null,"BaiduMapZoom":null,"MaintenancePeriod":"2周","FloorNumber":null,"StationNumber":null,"GateNumber":null,"RatedSpeed":null,"Deadweight":null,"InstallationDatetime":null,"SignStatus":null,"SignInstall":null,"DeviceStatus":1,"ShieldReasonId":null,"ShieldReason":null,"UpdateDatetime":"2017-08-24T15:18:05.123","MaintStartDate":null,"ProductionMonth":"2012-10-01T00:00:00","IsInstall":false,"IsOnline":false,"GpsX":0,"GpsY":0,"ID":67256,"CreateTime":"2017-08-24T15:18:04.843"}
     * RepairPosition : 123.414697,41.75636
     * Remark : 测试
     * BeforePhoto : ~/Upload/Check/day_170831/201708310421371839.jpg
     * AfterPhoto : ~/Upload/Check/day_170831/201708310421375427.jpg
     * ID : 2
     * CreateTime : 2017-08-31T16:21:28.557
     */

    private int UserId;
    private UserBean User;
    private int LiftId;
    private LiftBean Lift;
    private String RepairPosition;
    private String Remark;
    private String BeforePhoto;
    private String AfterPhoto;
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

    public String getRepairPosition() {
        return RepairPosition;
    }

    public void setRepairPosition(String RepairPosition) {
        this.RepairPosition = RepairPosition;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getBeforePhoto() {
        return BeforePhoto;
    }

    public void setBeforePhoto(String BeforePhoto) {
        this.BeforePhoto = BeforePhoto;
    }

    public String getAfterPhoto() {
        return AfterPhoto;
    }

    public void setAfterPhoto(String AfterPhoto) {
        this.AfterPhoto = AfterPhoto;
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
         * DeptId : 6667
         * Dept : null
         * RoleId : 15
         * Role : null
         * UseLifts : null
         * MaintLifts : null
         * Maint2Lifts : null
         * Maint3Lifts : null
         * QualityLifts : null
         * UserName : 张健
         * CheckUserName : null
         * LoginName : 13674188620
         * Password : E1-0A-DC-39-49-BA-59-AB-BE-56-E0-57-F2-0F-88-3E
         * Job : null
         * Phone : null
         * Mobile : 13674188620
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
         * ID : 14066
         * CreateTime : 2017-08-24T15:16:16.89
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
         * UseDepartmentId : 6666
         * UseDepartment : null
         * MaintenanceDepartmentId : 6667
         * MaintenanceDepartment : null
         * YearInspectionDepartmentId : null
         * YearInspectionDepartment : null
         * RegisterDepartmentId : null
         * RegisterDepartment : null
         * MadeDepartmentId : 1631
         * MadeDepartment : null
         * UpdateUserId : null
         * UpdateUser : null
         * AddressId : 20
         * Address : null
         * UseUsers : null
         * MaintUsers : null
         * Maint2Users : null
         * Maint3Users : null
         * QualityUsers : null
         * ListTask : null
         * ListCheck : null
         * ListYearInspection : null
         * ListPropertyCheck : null
         * LiftNum : 193668
         * DeviceNum : 193668
         * LiftOnline : null
         * ProblemDeviceNum : null
         * LiftProblem : null
         * CertificateNum : 31102101002012100033
         * MachineNum : null
         * CustomNum : null
         * ManufacturingLicenseNumber : null
         * InstallationAddress : 联合路三洋重工北区地块1#2
         * AddressPath : 辽宁省 沈阳市 大东区
         * Brand : null
         * Model : null
         * BaiduMapXY : null
         * LongitudeAndLatitude : null
         * BaiduMapZoom : null
         * MaintenancePeriod : 2周
         * FloorNumber : null
         * StationNumber : null
         * GateNumber : null
         * RatedSpeed : null
         * Deadweight : null
         * InstallationDatetime : null
         * SignStatus : null
         * SignInstall : null
         * DeviceStatus : 1
         * ShieldReasonId : null
         * ShieldReason : null
         * UpdateDatetime : 2017-08-24T15:18:05.123
         * MaintStartDate : null
         * ProductionMonth : 2012-10-01T00:00:00
         * IsInstall : false
         * IsOnline : false
         * GpsX : 0
         * GpsY : 0
         * ID : 67256
         * CreateTime : 2017-08-24T15:18:04.843
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
        private Object UseDepartment;
        private int MaintenanceDepartmentId;
        private Object MaintenanceDepartment;
        private Object YearInspectionDepartmentId;
        private Object YearInspectionDepartment;
        private Object RegisterDepartmentId;
        private Object RegisterDepartment;
        private int MadeDepartmentId;
        private Object MadeDepartment;
        private Object UpdateUserId;
        private Object UpdateUser;
        private int AddressId;
        private Object Address;
        private Object UseUsers;
        private Object MaintUsers;
        private Object Maint2Users;
        private Object Maint3Users;
        private Object QualityUsers;
        private Object ListTask;
        private Object ListCheck;
        private Object ListYearInspection;
        private Object ListPropertyCheck;
        private String LiftNum;
        private String DeviceNum;
        private Object LiftOnline;
        private Object ProblemDeviceNum;
        private Object LiftProblem;
        private String CertificateNum;
        private Object MachineNum;
        private Object CustomNum;
        private Object ManufacturingLicenseNumber;
        private String InstallationAddress;
        private String AddressPath;
        private Object Brand;
        private Object Model;
        private Object BaiduMapXY;
        private String LongitudeAndLatitude;
        private Object BaiduMapZoom;
        private String MaintenancePeriod;
        private Object FloorNumber;
        private Object StationNumber;
        private Object GateNumber;
        private Object RatedSpeed;
        private Object Deadweight;
        private Object InstallationDatetime;
        private Object SignStatus;
        private Object SignInstall;
        private int DeviceStatus;
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

        public Object getUseDepartment() {
            return UseDepartment;
        }

        public void setUseDepartment(Object UseDepartment) {
            this.UseDepartment = UseDepartment;
        }

        public int getMaintenanceDepartmentId() {
            return MaintenanceDepartmentId;
        }

        public void setMaintenanceDepartmentId(int MaintenanceDepartmentId) {
            this.MaintenanceDepartmentId = MaintenanceDepartmentId;
        }

        public Object getMaintenanceDepartment() {
            return MaintenanceDepartment;
        }

        public void setMaintenanceDepartment(Object MaintenanceDepartment) {
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

        public Object getUpdateUserId() {
            return UpdateUserId;
        }

        public void setUpdateUserId(Object UpdateUserId) {
            this.UpdateUserId = UpdateUserId;
        }

        public Object getUpdateUser() {
            return UpdateUser;
        }

        public void setUpdateUser(Object UpdateUser) {
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

        public Object getListCheck() {
            return ListCheck;
        }

        public void setListCheck(Object ListCheck) {
            this.ListCheck = ListCheck;
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

        public Object getMachineNum() {
            return MachineNum;
        }

        public void setMachineNum(Object MachineNum) {
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

        public Object getBaiduMapXY() {
            return BaiduMapXY;
        }

        public void setBaiduMapXY(Object BaiduMapXY) {
            this.BaiduMapXY = BaiduMapXY;
        }

        public String getLongitudeAndLatitude() {
            return LongitudeAndLatitude;
        }

        public void setLongitudeAndLatitude(String LongitudeAndLatitude) {
            this.LongitudeAndLatitude = LongitudeAndLatitude;
        }

        public Object getBaiduMapZoom() {
            return BaiduMapZoom;
        }

        public void setBaiduMapZoom(Object BaiduMapZoom) {
            this.BaiduMapZoom = BaiduMapZoom;
        }

        public String getMaintenancePeriod() {
            return MaintenancePeriod;
        }

        public void setMaintenancePeriod(String MaintenancePeriod) {
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

        public Object getSignStatus() {
            return SignStatus;
        }

        public void setSignStatus(Object SignStatus) {
            this.SignStatus = SignStatus;
        }

        public Object getSignInstall() {
            return SignInstall;
        }

        public void setSignInstall(Object SignInstall) {
            this.SignInstall = SignInstall;
        }

        public int getDeviceStatus() {
            return DeviceStatus;
        }

        public void setDeviceStatus(int DeviceStatus) {
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
    }
}
