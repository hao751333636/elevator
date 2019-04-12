package com.sinodom.elevator.bean.elevator;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/10/31.
 */

public class ExamineDataBean implements Serializable {


    /**
     * TypeID : 1
     * TypeName : 自动器检验
     * StepName : 开始检验
     * IsPhoto : true
     * IsVideo : false
     * Sort : 1
     * ID : 1
     * CreateTime : 2017-10-25T00:00:00
     */

    private int TypeID;
    private String TypeName;
    private String StepName;
    private boolean IsPhoto;
    private boolean IsVideo;
    private int Sort;
    private int ID;
    private String CreateTime;

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int TypeID) {
        this.TypeID = TypeID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getStepName() {
        return StepName;
    }

    public void setStepName(String StepName) {
        this.StepName = StepName;
    }

    public boolean isIsPhoto() {
        return IsPhoto;
    }

    public void setIsPhoto(boolean IsPhoto) {
        this.IsPhoto = IsPhoto;
    }

    public boolean isIsVideo() {
        return IsVideo;
    }

    public void setIsVideo(boolean IsVideo) {
        this.IsVideo = IsVideo;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int Sort) {
        this.Sort = Sort;
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
