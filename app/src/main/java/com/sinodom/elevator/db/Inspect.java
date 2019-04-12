package com.sinodom.elevator.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 电梯检验离线
 */
@Entity
public class Inspect {
    @Id
    private Long id;
    private int InspectId;
    private int TypeID;
    private String TypeName;
    private int StepId;
    private String StepName;
    private boolean IsPassed;
    private String Remark;
    private String PhotoUrl;
    private String VideoPath;
    private String MapX;
    private String MapY;
    private boolean IsPhoto;
    private String Local;
    @Generated(hash = 517726730)
    public Inspect(Long id, int InspectId, int TypeID, String TypeName, int StepId,
            String StepName, boolean IsPassed, String Remark, String PhotoUrl,
            String VideoPath, String MapX, String MapY, boolean IsPhoto,
            String Local) {
        this.id = id;
        this.InspectId = InspectId;
        this.TypeID = TypeID;
        this.TypeName = TypeName;
        this.StepId = StepId;
        this.StepName = StepName;
        this.IsPassed = IsPassed;
        this.Remark = Remark;
        this.PhotoUrl = PhotoUrl;
        this.VideoPath = VideoPath;
        this.MapX = MapX;
        this.MapY = MapY;
        this.IsPhoto = IsPhoto;
        this.Local = Local;
    }
    @Generated(hash = 1505544833)
    public Inspect() {
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
    public String getRemark() {
        return this.Remark;
    }
    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
    public String getPhotoUrl() {
        return this.PhotoUrl;
    }
    public void setPhotoUrl(String PhotoUrl) {
        this.PhotoUrl = PhotoUrl;
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
    public String getLocal() {
        return this.Local;
    }
    public void setLocal(String Local) {
        this.Local = Local;
    }
}
