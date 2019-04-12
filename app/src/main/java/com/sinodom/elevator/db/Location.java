package com.sinodom.elevator.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by GUO on 2018/1/5.
 * 心跳-位置
 */
@Entity
public class Location {
    @Id
    private Long id;
    private int UserId;
    private String Guid;
    private String BaiduMapX;
    private String BaiduMapY;
    private String UpdateDate;
    private String LongitudeAndLatitude;
    @Generated(hash = 1668419107)
    public Location(Long id, int UserId, String Guid, String BaiduMapX,
            String BaiduMapY, String UpdateDate, String LongitudeAndLatitude) {
        this.id = id;
        this.UserId = UserId;
        this.Guid = Guid;
        this.BaiduMapX = BaiduMapX;
        this.BaiduMapY = BaiduMapY;
        this.UpdateDate = UpdateDate;
        this.LongitudeAndLatitude = LongitudeAndLatitude;
    }
    @Generated(hash = 375979639)
    public Location() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getUserId() {
        return this.UserId;
    }
    public void setUserId(int UserId) {
        this.UserId = UserId;
    }
    public String getBaiduMapX() {
        return this.BaiduMapX;
    }
    public void setBaiduMapX(String BaiduMapX) {
        this.BaiduMapX = BaiduMapX;
    }
    public String getBaiduMapY() {
        return this.BaiduMapY;
    }
    public void setBaiduMapY(String BaiduMapY) {
        this.BaiduMapY = BaiduMapY;
    }
    public String getUpdateDate() {
        return this.UpdateDate;
    }
    public void setUpdateDate(String UpdateDate) {
        this.UpdateDate = UpdateDate;
    }
    public String getLongitudeAndLatitude() {
        return this.LongitudeAndLatitude;
    }
    public void setLongitudeAndLatitude(String LongitudeAndLatitude) {
        this.LongitudeAndLatitude = LongitudeAndLatitude;
    }
    public String getGuid() {
        return this.Guid;
    }
    public void setGuid(String Guid) {
        this.Guid = Guid;
    }
}
