package com.sinodom.elevator.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by HYD on 2017/10/30.
 * 用户
 */
@Entity
public class Session {
    @Id
    private Long id;
    private int UserID;
    private int RoleId;
    private String LoginName;
    private String UserName;
    private int DeptId;
    private String RoleCode;
    @Generated(hash = 166440392)
    public Session(Long id, int UserID, int RoleId, String LoginName,
            String UserName, int DeptId, String RoleCode) {
        this.id = id;
        this.UserID = UserID;
        this.RoleId = RoleId;
        this.LoginName = LoginName;
        this.UserName = UserName;
        this.DeptId = DeptId;
        this.RoleCode = RoleCode;
    }
    @Generated(hash = 1317889643)
    public Session() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUserID() {
        return this.UserID;
    }
    public void setUserID(int UserID) {
        this.UserID = UserID;
    }
    public String getLoginName() {
        return this.LoginName;
    }
    public void setLoginName(String LoginName) {
        this.LoginName = LoginName;
    }
    public String getUserName() {
        return this.UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    public int getDeptId() {
        return this.DeptId;
    }
    public void setDeptId(int DeptId) {
        this.DeptId = DeptId;
    }
    public String getRoleCode() {
        return this.RoleCode;
    }
    public void setRoleCode(String RoleCode) {
        this.RoleCode = RoleCode;
    }
    public int getRoleId() {
        return this.RoleId;
    }
    public void setRoleId(int RoleId) {
        this.RoleId = RoleId;
    }
}
