package com.sinodom.elevator.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by GUO on 2017/12/6.
 * 电梯救援按钮状态显示判断
 */
@Entity
public class StatusAction {
    @Id
    private Long id;
    private int StatusId;
    private String StatusName;
    private String ActionName;
    private String ActionDesc;
    private String UserType;
    private int Argument;
    private int ID;
    private String CreateTime;
    @Generated(hash = 216545123)
    public StatusAction(Long id, int StatusId, String StatusName, String ActionName,
            String ActionDesc, String UserType, int Argument, int ID,
            String CreateTime) {
        this.id = id;
        this.StatusId = StatusId;
        this.StatusName = StatusName;
        this.ActionName = ActionName;
        this.ActionDesc = ActionDesc;
        this.UserType = UserType;
        this.Argument = Argument;
        this.ID = ID;
        this.CreateTime = CreateTime;
    }
    @Generated(hash = 1912324643)
    public StatusAction() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getStatusId() {
        return this.StatusId;
    }
    public void setStatusId(int StatusId) {
        this.StatusId = StatusId;
    }
    public String getStatusName() {
        return this.StatusName;
    }
    public void setStatusName(String StatusName) {
        this.StatusName = StatusName;
    }
    public String getActionName() {
        return this.ActionName;
    }
    public void setActionName(String ActionName) {
        this.ActionName = ActionName;
    }
    public String getActionDesc() {
        return this.ActionDesc;
    }
    public void setActionDesc(String ActionDesc) {
        this.ActionDesc = ActionDesc;
    }
    public String getUserType() {
        return this.UserType;
    }
    public void setUserType(String UserType) {
        this.UserType = UserType;
    }
    public int getArgument() {
        return this.Argument;
    }
    public void setArgument(int Argument) {
        this.Argument = Argument;
    }
    public int getID() {
        return this.ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getCreateTime() {
        return this.CreateTime;
    }
    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}
