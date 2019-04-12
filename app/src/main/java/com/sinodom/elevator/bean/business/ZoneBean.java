package com.sinodom.elevator.bean.business;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/12/5.
 * 报警仪联网 查询 区
 */

public class ZoneBean implements Serializable {

    /**
     * ParentId : 2
     * ParentAddress : null
     * ChildAddresses : null
     * DeptDataAddress : null
     * UserDataAddress : null
     * Code : 210115
     * Name : 法库县
     * Level : 3
     * Path : 辽宁省 沈阳市 法库县
     * ID : 32
     * CreateTime : 2016-03-13T15:49:58.12
     */

    private int ParentId;
//    private Object ParentAddress;
//    private Object ChildAddresses;
//    private Object DeptDataAddress;
//    private Object UserDataAddress;
    private String Code;
    private String Name;
    private int Level;
    private String Path;
    private int ID;
    private String CreateTime;

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

//    public Object getParentAddress() {
//        return ParentAddress;
//    }
//
//    public void setParentAddress(Object ParentAddress) {
//        this.ParentAddress = ParentAddress;
//    }
//
//    public Object getChildAddresses() {
//        return ChildAddresses;
//    }
//
//    public void setChildAddresses(Object ChildAddresses) {
//        this.ChildAddresses = ChildAddresses;
//    }
//
//    public Object getDeptDataAddress() {
//        return DeptDataAddress;
//    }
//
//    public void setDeptDataAddress(Object DeptDataAddress) {
//        this.DeptDataAddress = DeptDataAddress;
//    }
//
//    public Object getUserDataAddress() {
//        return UserDataAddress;
//    }
//
//    public void setUserDataAddress(Object UserDataAddress) {
//        this.UserDataAddress = UserDataAddress;
//    }

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
