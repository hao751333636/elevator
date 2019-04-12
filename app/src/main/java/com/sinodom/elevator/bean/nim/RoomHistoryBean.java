package com.sinodom.elevator.bean.nim;

import java.io.Serializable;

public class RoomHistoryBean implements Serializable{

    /**
     * RoomID : 10001
     * CreateTime : 2018-09-14T09:42:04.627
     * EndTime : null
     * UserName : 王处长
     * LoginName : 13050586898
     * Phone : 13050586898
     */

    private String RoomID;
    private String CreateTime;
    private String EndTime;
    private String UserName;
    private String LoginName;
    private String Phone;
    private String RoleName;
    private int State;
    private String FromUserName;
    private String FromLoginName;
    private String FromPhone;
    private String FromRoleName;

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getFromLoginName() {
        return FromLoginName;
    }

    public void setFromLoginName(String fromLoginName) {
        FromLoginName = fromLoginName;
    }

    public String getFromPhone() {
        return FromPhone;
    }

    public void setFromPhone(String fromPhone) {
        FromPhone = fromPhone;
    }

    public String getFromRoleName() {
        return FromRoleName;
    }

    public void setFromRoleName(String fromRoleName) {
        FromRoleName = fromRoleName;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
}
