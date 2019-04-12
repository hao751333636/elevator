package com.sinodom.elevator.bean.business;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 安卓 on 2017/12/19.
 */

public class ElevatorInformationBean implements Serializable {

    /**
     * LiftSiteId : 28
     * LiftSiteDict : null
     * LiftTypeId : 22
     * LiftTypeDict : null
     * LiftStatusId : 81
     * LiftStatusDict : null
     * LiftMaintenanceTypeId : 78
     * LiftMaintenanceTypeDict : null
     * UseDepartmentId : 1109
     * UseDepartment : null
     * MaintenanceDepartmentId : 5400
     * MaintenanceDepartment : null
     * YearInspectionDepartmentId : null
     * YearInspectionDepartment : null
     * RegisterDepartmentId : null
     * RegisterDepartment : null
     * MadeDepartmentId : null
     * MadeDepartment : null
     * UpdateUserId : 3922
     * UpdateUser : null
     * AddressId : 19
     * Address : null
     * UseUsers : null
     * MaintUsers : null
     * Maint2Users : null
     * Maint3Users : null
     * QualityUsers : null
     * ListTask : null
     * ListCheck : [{"UserId":3689,"User":null,"LiftId":10517,"CheckDate":"2017-12-18T14: 20: 00","IsPassed":true,"Remark":"","UploadDate":"2017-12-18T14: 20: 41.067","LongitudeAndLatitude":"123.415086,             41.756241","CheckDetails":null,"ConfirmUser":null,"ConfirmUserMobile":null,"Sign":null,"SignDateTime":null,"ID":20220,"CreateTime":"2017-12-18T14: 20: 41.1"}]
     * ListYearInspection : null
     * ListPropertyCheck : [null]
     * LiftNum : 110211
     * DeviceNum : 110211
     * LiftOnline : null
     * ProblemDeviceNum : null
     * LiftProblem : null
     * CertificateNum : 30102101002007040024
     * MachineNum : null
     * CustomNum : null
     * ManufacturingLicenseNumber : null
     * InstallationAddress : 青年大街106号一号楼1#
     * AddressPath : 辽宁省沈阳市沈河区
     * Brand : 沈阳博林特电梯集团股份有限公司
     * Model : null
     * BaiduMapXY : 123.438340,         41.801373
     * LongitudeAndLatitude : 123.42565949833,         41.793049173525
     * BaiduMapZoom : 19
     * MaintenancePeriod : 2周
     * FloorNumber : 4
     * StationNumber : null
     * GateNumber : null
     * RatedSpeed : null
     * Deadweight : null
     * InstallationDatetime : 2016-01-04T00: 00: 00
     * SignStatus : null
     * SignInstall : 1
     * DeviceStatus : 1
     * ShieldReasonId : null
     * ShieldReason : null
     * UpdateDatetime : 2016-03-22T11: 07: 56.163
     * MaintStartDate : 2016-01-01T00: 00: 00
     * ProductionMonth : 2007-04-01T00: 00: 00
     * IsInstall : false
     * IsOnline : false
     * GpsX : 123.43834
     * GpsY : 41.801373
     * ID : 10517
     * CreateTime : 2016-01-04T09: 39: 47.527
     */

    private int LiftSiteId;
    private Object LiftSiteDict;
    private int LiftTypeId;
    private Object LiftTypeDict;
    private int LiftStatusId;
    private Object LiftStatusDict;
    private int LiftMaintenanceTypeId;
    private Object LiftMaintenanceTypeDict;
    private int UseDepartmentId;
    private Object UseDepartment;
    private int MaintenanceDepartmentId;
    private Object MaintenanceDepartment;
    private Object YearInspectionDepartmentId;
    private Object YearInspectionDepartment;
    private Object RegisterDepartmentId;
    private Object RegisterDepartment;
    private Object MadeDepartmentId;
    private Object MadeDepartment;
    private int UpdateUserId;
    private Object UpdateUser;
    private int AddressId;
    private Object Address;
    private Object UseUsers;
    private Object MaintUsers;
    private Object Maint2Users;
    private Object Maint3Users;
    private Object QualityUsers;
    private Object ListTask;
    private Object ListYearInspection;
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
    private String Brand;
    private Object Model;
    private String BaiduMapXY;
    private String LongitudeAndLatitude;
    private String BaiduMapZoom;
    private String MaintenancePeriod;
    private Object FloorNumber;
    private Object StationNumber;
    private String GateNumber;
    private Object RatedSpeed;
    private Object Deadweight;
    private String InstallationDatetime;
    private Object SignStatus;
    private int SignInstall;
    private int DeviceStatus;
    private Object ShieldReasonId;
    private Object ShieldReason;
    private String UpdateDatetime;
    private String MaintStartDate;
    private String ProductionMonth;
    private boolean IsInstall;
    private boolean IsOnline;
    private double GpsX;
    private double GpsY;
    private int ID;
    private String CreateTime;
    private List<ListCheckBean> ListCheck;
//    private List<Null> ListPropertyCheck;

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

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
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

    public String getGateNumber() {
        return GateNumber;
    }

    public void setGateNumber(String GateNumber) {
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

    public String getInstallationDatetime() {
        return InstallationDatetime;
    }

    public void setInstallationDatetime(String InstallationDatetime) {
        this.InstallationDatetime = InstallationDatetime;
    }

    public Object getSignStatus() {
        return SignStatus;
    }

    public void setSignStatus(Object SignStatus) {
        this.SignStatus = SignStatus;
    }

    public int getSignInstall() {
        return SignInstall;
    }

    public void setSignInstall(int SignInstall) {
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

    public String getMaintStartDate() {
        return MaintStartDate;
    }

    public void setMaintStartDate(String MaintStartDate) {
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

    public List<ListCheckBean> getListCheck() {
        return ListCheck;
    }

    public void setListCheck(List<ListCheckBean> ListCheck) {
        this.ListCheck = ListCheck;
    }

//    public List<Null> getListPropertyCheck() {
//        return ListPropertyCheck;
//    }
//
//    public void setListPropertyCheck(List<Null> ListPropertyCheck) {
//        this.ListPropertyCheck = ListPropertyCheck;
//    }

    public static class ListCheckBean {
        /**
         * UserId : 3689
         * User : null
         * LiftId : 10517
         * CheckDate : 2017-12-18T14: 20: 00
         * IsPassed : true
         * Remark :
         * UploadDate : 2017-12-18T14: 20: 41.067
         * LongitudeAndLatitude : 123.415086,             41.756241
         * CheckDetails : null
         * ConfirmUser : null
         * ConfirmUserMobile : null
         * Sign : null
         * SignDateTime : null
         * ID : 20220
         * CreateTime : 2017-12-18T14: 20: 41.1
         */

        private int UserId;
        private Object User;
        private int LiftId;
        private String CheckDate;
        private boolean IsPassed;
        private String Remark;
        private String UploadDate;
        private String LongitudeAndLatitude;
        private Object CheckDetails;
        private Object ConfirmUser;
        private Object ConfirmUserMobile;
        private Object Sign;
        private Object SignDateTime;
        private int ID;
        private String CreateTime;
        private String CType;

        public String getCType() {
            return CType;
        }

        public void setCType(String CType) {
            this.CType = CType;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int UserId) {
            this.UserId = UserId;
        }

        public Object getUser() {
            return User;
        }

        public void setUser(Object User) {
            this.User = User;
        }

        public int getLiftId() {
            return LiftId;
        }

        public void setLiftId(int LiftId) {
            this.LiftId = LiftId;
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

        public Object getCheckDetails() {
            return CheckDetails;
        }

        public void setCheckDetails(Object CheckDetails) {
            this.CheckDetails = CheckDetails;
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
