package com.sinodom.elevator.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 无纸化电梯维保
 */
@Entity
public class PaperlessMaintenance {
    @Id
    private Long id;
    private String Json;
    @Generated(hash = 1966538906)
    public PaperlessMaintenance(Long id, String Json) {
        this.id = id;
        this.Json = Json;
    }
    @Generated(hash = 285944824)
    public PaperlessMaintenance() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJson() {
        return this.Json;
    }
    public void setJson(String Json) {
        this.Json = Json;
    }
}
