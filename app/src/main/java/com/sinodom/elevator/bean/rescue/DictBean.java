package com.sinodom.elevator.bean.rescue;

/**
 * Created by GUO on 2017/12/6.
 * 字典(故障原因、解救方法、误报原因)
 */

public class DictBean {
    private String ID;
    private String FullPath;
    private String DictName;

    public String getDictName() {
        return DictName;
    }

    public void setDictName(String dictName) {
        DictName = dictName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFullPath() {
        return FullPath;
    }

    public void setFullPath(String fullPath) {
        FullPath = fullPath;
    }
}
