package com.sinodom.elevator.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Nfc绑定
 */
@Entity
public class NfcBind {
    @Id
    private Long id;
    private String Json;
    @Generated(hash = 1203485510)
    public NfcBind(Long id, String Json) {
        this.id = id;
        this.Json = Json;
    }
    @Generated(hash = 1201222668)
    public NfcBind() {
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
