package com.sinodom.elevator.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 电梯检验分类离线
 */
@Entity
public class InspectItem {
    @Id
    private Long id;
    private int InspectId;
    private int StepId;
    private String StepName;
    private boolean IsPassed;
    private String PhotoUrl;
    private String Remark;
    private int TypeID;
    private String TypeName;
    private String VideoPath;
    private String MapX;
    private String MapY;
    private boolean IsPhoto;
    private int UserId;
    private String Local;
    private String UserName;
    private String CreateTime;
    private String TemplateAttributeJson;
    @Generated(hash = 2015712685)
    public InspectItem(Long id, int InspectId, int StepId, String StepName,
            boolean IsPassed, String PhotoUrl, String Remark, int TypeID,
            String TypeName, String VideoPath, String MapX, String MapY,
            boolean IsPhoto, int UserId, String Local, String UserName,
            String CreateTime, String TemplateAttributeJson) {
        this.id = id;
        this.InspectId = InspectId;
        this.StepId = StepId;
        this.StepName = StepName;
        this.IsPassed = IsPassed;
        this.PhotoUrl = PhotoUrl;
        this.Remark = Remark;
        this.TypeID = TypeID;
        this.TypeName = TypeName;
        this.VideoPath = VideoPath;
        this.MapX = MapX;
        this.MapY = MapY;
        this.IsPhoto = IsPhoto;
        this.UserId = UserId;
        this.Local = Local;
        this.UserName = UserName;
        this.CreateTime = CreateTime;
        this.TemplateAttributeJson = TemplateAttributeJson;
    }
    @Generated(hash = 71777420)
    public InspectItem() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getInspectId() {
        return this.InspectId;
    }
    public void setInspectId(int InspectId) {
        this.InspectId = InspectId;
    }
    public int getStepId() {
        return this.StepId;
    }
    public void setStepId(int StepId) {
        this.StepId = StepId;
    }
    public String getStepName() {
        return this.StepName;
    }
    public void setStepName(String StepName) {
        this.StepName = StepName;
    }
    public boolean getIsPassed() {
        return this.IsPassed;
    }
    public void setIsPassed(boolean IsPassed) {
        this.IsPassed = IsPassed;
    }
    public String getPhotoUrl() {
        return this.PhotoUrl;
    }
    public void setPhotoUrl(String PhotoUrl) {
        this.PhotoUrl = PhotoUrl;
    }
    public String getRemark() {
        return this.Remark;
    }
    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
    public int getTypeID() {
        return this.TypeID;
    }
    public void setTypeID(int TypeID) {
        this.TypeID = TypeID;
    }
    public String getTypeName() {
        return this.TypeName;
    }
    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }
    public String getVideoPath() {
        return this.VideoPath;
    }
    public void setVideoPath(String VideoPath) {
        this.VideoPath = VideoPath;
    }
    public String getMapX() {
        return this.MapX;
    }
    public void setMapX(String MapX) {
        this.MapX = MapX;
    }
    public String getMapY() {
        return this.MapY;
    }
    public void setMapY(String MapY) {
        this.MapY = MapY;
    }
    public boolean getIsPhoto() {
        return this.IsPhoto;
    }
    public void setIsPhoto(boolean IsPhoto) {
        this.IsPhoto = IsPhoto;
    }
    public int getUserId() {
        return this.UserId;
    }
    public void setUserId(int UserId) {
        this.UserId = UserId;
    }
    public String getLocal() {
        return this.Local;
    }
    public void setLocal(String Local) {
        this.Local = Local;
    }
    public String getUserName() {
        return this.UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    public String getCreateTime() {
        return this.CreateTime;
    }
    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
    public String getTemplateAttributeJson() {
        return this.TemplateAttributeJson;
    }
    public void setTemplateAttributeJson(String TemplateAttributeJson) {
        this.TemplateAttributeJson = TemplateAttributeJson;
    }
}
