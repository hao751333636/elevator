package com.sinodom.elevator.bean.business;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/12/20.
 * 提交维保 储存提交数据
 */

public class CheckDetailPaperlessEntity implements Serializable {
    /// 维保明细ID
    public int CheckDetailId;
    /// 维保记录ID
    public int CheckId;
    /// 检查日期
    public String CheckDate;
    /// 维保类型
    public String CType;
    /// 上传日期
    public String UploadDate;
    /// 是否通过
    public boolean IsPassed;
    /// 备注
    public String Remark;
    /// 现场照片
    public String Photo;
    /// 定位
    public String LongitudeAndLatitude;
    /// 维保步骤ID
    public int StepId;
    /// 用户Id
    public int UserId;
    /// 部门Id
    public int DeptId;
    /// 电梯Id
    public int LiftId;
    public int ID;
    //标签
    public String NFCCode;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNFCCode() {
        return NFCCode;
    }

    public void setNFCCode(String NFCCode) {
        this.NFCCode = NFCCode;
    }

    public String getCType() {
        return CType;
    }

    public void setCType(String CType) {
        this.CType = CType;
    }

    public int getCheckDetailId() {
        return CheckDetailId;
    }

    public void setCheckDetailId(int checkDetailId) {
        CheckDetailId = checkDetailId;
    }

    public int getCheckId() {
        return CheckId;
    }

    public void setCheckId(int checkId) {
        CheckId = checkId;
    }

    public String getCheckDate() {
        return CheckDate;
    }

    public void setCheckDate(String checkDate) {
        CheckDate = checkDate;
    }

    public String getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(String uploadDate) {
        UploadDate = uploadDate;
    }

    public boolean isPassed() {
        return IsPassed;
    }

    public void setPassed(boolean passed) {
        IsPassed = passed;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getLongitudeAndLatitude() {
        return LongitudeAndLatitude;
    }

    public void setLongitudeAndLatitude(String longitudeAndLatitude) {
        LongitudeAndLatitude = longitudeAndLatitude;
    }

    public int getStepId() {
        return StepId;
    }

    public void setStepId(int stepId) {
        StepId = stepId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getDeptId() {
        return DeptId;
    }

    public void setDeptId(int deptId) {
        DeptId = deptId;
    }

    public int getLiftId() {
        return LiftId;
    }

    public void setLiftId(int liftId) {
        LiftId = liftId;
    }
}
