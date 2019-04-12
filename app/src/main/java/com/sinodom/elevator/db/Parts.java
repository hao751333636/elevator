package com.sinodom.elevator.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 配件管理离线
 */
@Entity
public class Parts {
    @Id
    private Long id;
    private String Manufacturer;
    private String Brand;
    private String Model;
    private String InstallationTime;
    private String PartsTypeId;
    private String ProductName;
    private String LiftId;
    private String photoPath;
    @Generated(hash = 366253976)
    public Parts(Long id, String Manufacturer, String Brand, String Model,
            String InstallationTime, String PartsTypeId, String ProductName,
            String LiftId, String photoPath) {
        this.id = id;
        this.Manufacturer = Manufacturer;
        this.Brand = Brand;
        this.Model = Model;
        this.InstallationTime = InstallationTime;
        this.PartsTypeId = PartsTypeId;
        this.ProductName = ProductName;
        this.LiftId = LiftId;
        this.photoPath = photoPath;
    }
    @Generated(hash = 1817985636)
    public Parts() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getManufacturer() {
        return this.Manufacturer;
    }
    public void setManufacturer(String Manufacturer) {
        this.Manufacturer = Manufacturer;
    }
    public String getBrand() {
        return this.Brand;
    }
    public void setBrand(String Brand) {
        this.Brand = Brand;
    }
    public String getModel() {
        return this.Model;
    }
    public void setModel(String Model) {
        this.Model = Model;
    }
    public String getInstallationTime() {
        return this.InstallationTime;
    }
    public void setInstallationTime(String InstallationTime) {
        this.InstallationTime = InstallationTime;
    }
    public String getPartsTypeId() {
        return this.PartsTypeId;
    }
    public void setPartsTypeId(String PartsTypeId) {
        this.PartsTypeId = PartsTypeId;
    }
    public String getProductName() {
        return this.ProductName;
    }
    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }
    public String getLiftId() {
        return this.LiftId;
    }
    public void setLiftId(String LiftId) {
        this.LiftId = LiftId;
    }
    public String getPhotoPath() {
        return this.photoPath;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
