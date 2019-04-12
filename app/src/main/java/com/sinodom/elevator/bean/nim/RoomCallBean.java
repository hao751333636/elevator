package com.sinodom.elevator.bean.nim;

import java.io.Serializable;

/**
 * Created by GUO on 2017/12/19.
 * 投诉建议
 */

public class RoomCallBean implements Serializable {

    /**
     * UserName : 用户名
     * LoginName : 登录名
     * RoleName : 角色名
     * Mobile : 电话
     */

    private String UserName;
    private String LoginName;
    private String RoleName;
    private String Mobile;
    private String UserID;

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    private boolean IsSelect = false;

    public boolean isSelect() {
        return IsSelect;
    }

    public void setSelect(boolean select) {
        IsSelect = select;
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

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }
}
