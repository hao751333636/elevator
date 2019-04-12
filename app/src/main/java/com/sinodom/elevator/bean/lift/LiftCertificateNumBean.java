package com.sinodom.elevator.bean.lift;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GUO on 2017/11/29.
 */

public class LiftCertificateNumBean implements Serializable{

    /**
     * LiftSiteId : null
     * LiftSiteDict : null
     * LiftTypeId : 339
     * LiftTypeDict : null
     * LiftStatusId : 80
     * LiftStatusDict : null
     * LiftMaintenanceTypeId : 76
     * LiftMaintenanceTypeDict : null
     * UseDepartmentId : 18685
     * UseDepartment : {"RoleGroupId":6,"RoleGroup":null,"AddressId":null,"DeptName":"沈阳市世茂新纪元置业有限公司","DeptCode":null,"Phone":null,"DeptAddress":null,"BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2018-09-28T13:51:42","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":null,"ID":18685,"CreateTime":"2018-08-21T09:52:48"}
     * MaintenanceDepartmentId : 18688
     * MaintenanceDepartment : {"RoleGroupId":7,"RoleGroup":null,"AddressId":null,"DeptName":"电梯维保单位","DeptCode":"00001","Phone":null,"DeptAddress":null,"BaiduMapXY":null,"LongitudeAndLatitude":null,"Remark":null,"Sort":0,"UpdateDate":"2018-07-06T15:03:22.453","DeptDataAddressIds":null,"DeptDataAddress":null,"DeptUsers":null,"ID":18688,"CreateTime":"2018-07-06T15:03:22.387"}
     * YearInspectionDepartmentId : null
     * YearInspectionDepartment : null
     * RegisterDepartmentId : null
     * RegisterDepartment : null
     * MadeDepartmentId : null
     * MadeDepartment : null
     * UpdateUserId : 3922
     * UpdateUser : null
     * AddressId : 19
     * Address : {"ParentId":2,"ParentAddress":{"ParentId":1,"ParentAddress":null,"ChildAddresses":[],"DeptDataAddress":null,"UserDataAddress":null,"Code":"210100","Name":"沈阳市","Level":2,"Path":"辽宁省 沈阳市","IdPath":"219,1,2,","ID":2,"CreateTime":"2016-03-13T15:49:55.993"},"ChildAddresses":null,"DeptDataAddress":null,"UserDataAddress":null,"Code":"210102","Name":"沈河区","Level":3,"Path":"辽宁省 沈阳市 沈河区","IdPath":"219,1,2,19,","ID":19,"CreateTime":"2016-03-13T15:49:58.12"}
     * UseUsers : null
     * MaintUsers : null
     * Maint2Users : null
     * Maint3Users : null
     * QualityUsers : null
     * ListTask : null
     * ListCheck : null
     * ListYearInspection : []
     * ListPropertyCheck : null
     * LiftDetail : null
     * LiftNum : 090004
     * DeviceNum : null
     * LiftOnline : null
     * ProblemDeviceNum : null
     * LiftProblem : null
     * CertificateNum : 00009000080000700006
     * MachineNum : null
     * CustomNum : null
     * ManufacturingLicenseNumber : null
     * InstallationAddress : 华盾大数据研究院
     * AddressPath : 辽宁省 沈阳市 沈河区
     * Brand : null
     * Model : null
     * BaiduMapXY : 123.415405,41.756042
     * LongitudeAndLatitude : 123.40160735808,41.748039438718
     * BaiduMapZoom : 14
     * MaintenancePeriod : null
     * FloorNumber : null
     * StationNumber : null
     * GateNumber : null
     * RatedSpeed : null
     * Deadweight : null
     * InstallationDatetime : null
     * ManufactureDate : null
     * SignStatus : 1
     * SignInstall : 1
     * DeviceStatus : null
     * ShieldReasonId : null
     * ShieldReason : null
     * UpdateDatetime : 2018-10-10T13:05:34.117
     * MaintStartDate : null
     * ProductionMonth : 0001-01-01T00:00:00
     * IsInstall : false
     * IsOnline : false
     * GpsX : 41.756042
     * GpsY : 123.415405
     * ID : 155940
     * CreateTime : 2018-09-18T10:40:42.74
     */

    private Object LiftSiteId;
    private Object LiftSiteDict;
    private int LiftTypeId;
    private Object LiftTypeDict;
    private int LiftStatusId;
    private Object LiftStatusDict;
    private int LiftMaintenanceTypeId;
    private Object LiftMaintenanceTypeDict;
    private int UseDepartmentId;
    private UseDepartmentBean UseDepartment;
    private int MaintenanceDepartmentId;
    private MaintenanceDepartmentBean MaintenanceDepartment;
    private Object YearInspectionDepartmentId;
    private Object YearInspectionDepartment;
    private Object RegisterDepartmentId;
    private Object RegisterDepartment;
    private Object MadeDepartmentId;
    private Object MadeDepartment;
    private int UpdateUserId;
    private Object UpdateUser;
    private int AddressId;
    private AddressBean Address;
    private Object UseUsers;
    private Object MaintUsers;
    private Object Maint2Users;
    private Object Maint3Users;
    private Object QualityUsers;
    private Object ListTask;
    private Object ListCheck;
    private Object ListPropertyCheck;
    private Object LiftDetail;
    private String LiftNum;
    private Object DeviceNum;
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
    private Object ManufactureDate;
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
    private List<?> ListYearInspection;

    public Object getLiftSiteId() {
        return LiftSiteId;
    }

    public void setLiftSiteId(Object LiftSiteId) {
        this.LiftSiteId = LiftSiteId;
    }

    public Object getLiftSiteDict() {
        return LiftSiteDict;
    }

    public void setLiftSiteDict(Object LiftSiteDict) {
        this.LiftSiteDict = LiftSiteDict;
    }

    public int getLiftTypeId() {
        return LiftTypeId;
    }

    public void setLiftTypeId(int LiftTypeId) {
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

    public int getLiftMaintenanceTypeId() {
        return LiftMaintenanceTypeId;
    }

    public void setLiftMaintenanceTypeId(int LiftMaintenanceTypeId) {
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

    public Object getMadeDepartmentId() {
        return MadeDepartmentId;
    }

    public void setMadeDepartmentId(Object MadeDepartmentId) {
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

    public AddressBean getAddress() {
        return Address;
    }

    public void setAddress(AddressBean Address) {
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

    public Object getListPropertyCheck() {
        return ListPropertyCheck;
    }

    public void setListPropertyCheck(Object ListPropertyCheck) {
        this.ListPropertyCheck = ListPropertyCheck;
    }

    public Object getLiftDetail() {
        return LiftDetail;
    }

    public void setLiftDetail(Object LiftDetail) {
        this.LiftDetail = LiftDetail;
    }

    public String getLiftNum() {
        return LiftNum;
    }

    public void setLiftNum(String LiftNum) {
        this.LiftNum = LiftNum;
    }

    public Object getDeviceNum() {
        return DeviceNum;
    }

    public void setDeviceNum(Object DeviceNum) {
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

    public Object getManufactureDate() {
        return ManufactureDate;
    }

    public void setManufactureDate(Object ManufactureDate) {
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

    public List<?> getListYearInspection() {
        return ListYearInspection;
    }

    public void setListYearInspection(List<?> ListYearInspection) {
        this.ListYearInspection = ListYearInspection;
    }

    public static class UseDepartmentBean {
        /**
         * RoleGroupId : 6
         * RoleGroup : null
         * AddressId : null
         * DeptName : 沈阳市世茂新纪元置业有限公司
         * DeptCode : null
         * Phone : null
         * DeptAddress : null
         * BaiduMapXY : null
         * LongitudeAndLatitude : null
         * Remark : null
         * Sort : 0
         * UpdateDate : 2018-09-28T13:51:42
         * DeptDataAddressIds : null
         * DeptDataAddress : null
         * DeptUsers : null
         * ID : 18685
         * CreateTime : 2018-08-21T09:52:48
         */

        private int RoleGroupId;
        private Object RoleGroup;
        private Object AddressId;
        private String DeptName;
        private Object DeptCode;
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

        public Object getDeptCode() {
            return DeptCode;
        }

        public void setDeptCode(Object DeptCode) {
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
         * DeptName : 电梯维保单位
         * DeptCode : 00001
         * Phone : null
         * DeptAddress : null
         * BaiduMapXY : null
         * LongitudeAndLatitude : null
         * Remark : null
         * Sort : 0
         * UpdateDate : 2018-07-06T15:03:22.453
         * DeptDataAddressIds : null
         * DeptDataAddress : null
         * DeptUsers : null
         * ID : 18688
         * CreateTime : 2018-07-06T15:03:22.387
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

    public static class AddressBean {
        /**
         * ParentId : 2
         * ParentAddress : {"ParentId":1,"ParentAddress":null,"ChildAddresses":[],"DeptDataAddress":null,"UserDataAddress":null,"Code":"210100","Name":"沈阳市","Level":2,"Path":"辽宁省 沈阳市","IdPath":"219,1,2,","ID":2,"CreateTime":"2016-03-13T15:49:55.993"}
         * ChildAddresses : null
         * DeptDataAddress : null
         * UserDataAddress : null
         * Code : 210102
         * Name : 沈河区
         * Level : 3
         * Path : 辽宁省 沈阳市 沈河区
         * IdPath : 219,1,2,19,
         * ID : 19
         * CreateTime : 2016-03-13T15:49:58.12
         */

        private int ParentId;
        private ParentAddressBean ParentAddress;
        private Object ChildAddresses;
        private Object DeptDataAddress;
        private Object UserDataAddress;
        private String Code;
        private String Name;
        private int Level;
        private String Path;
        private String IdPath;
        private int ID;
        private String CreateTime;

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int ParentId) {
            this.ParentId = ParentId;
        }

        public ParentAddressBean getParentAddress() {
            return ParentAddress;
        }

        public void setParentAddress(ParentAddressBean ParentAddress) {
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

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
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

        public String getIdPath() {
            return IdPath;
        }

        public void setIdPath(String IdPath) {
            this.IdPath = IdPath;
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

        public static class ParentAddressBean {
            /**
             * ParentId : 1
             * ParentAddress : null
             * ChildAddresses : []
             * DeptDataAddress : null
             * UserDataAddress : null
             * Code : 210100
             * Name : 沈阳市
             * Level : 2
             * Path : 辽宁省 沈阳市
             * IdPath : 219,1,2,
             * ID : 2
             * CreateTime : 2016-03-13T15:49:55.993
             */

            private int ParentId;
            private Object ParentAddress;
            private Object DeptDataAddress;
            private Object UserDataAddress;
            private String Code;
            private String Name;
            private int Level;
            private String Path;
            private String IdPath;
            private int ID;
            private String CreateTime;
            private List<?> ChildAddresses;

            public int getParentId() {
                return ParentId;
            }

            public void setParentId(int ParentId) {
                this.ParentId = ParentId;
            }

            public Object getParentAddress() {
                return ParentAddress;
            }

            public void setParentAddress(Object ParentAddress) {
                this.ParentAddress = ParentAddress;
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

            public String getCode() {
                return Code;
            }

            public void setCode(String Code) {
                this.Code = Code;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
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

            public String getIdPath() {
                return IdPath;
            }

            public void setIdPath(String IdPath) {
                this.IdPath = IdPath;
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

            public List<?> getChildAddresses() {
                return ChildAddresses;
            }

            public void setChildAddresses(List<?> ChildAddresses) {
                this.ChildAddresses = ChildAddresses;
            }
        }
    }
}
