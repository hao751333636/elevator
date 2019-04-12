package com.sinodom.elevator.bean.nim;

import java.io.Serializable;

public class RoomHistoryDetailBean implements Serializable{

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

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String roomID) {
        RoomID = roomID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
