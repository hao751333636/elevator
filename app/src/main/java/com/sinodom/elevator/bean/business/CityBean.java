package com.sinodom.elevator.bean.business;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/12/4.
 * 报警仪联网 查询 城市
 */

public class CityBean implements Serializable {

    /**
     * ParentId : 1
     * ID : 2
     * Name : 沈阳市
     */

    private int ParentId;
    private int ID;
    private String Name;

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int ParentId) {
        this.ParentId = ParentId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
