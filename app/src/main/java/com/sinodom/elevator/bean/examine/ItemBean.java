package com.sinodom.elevator.bean.examine;

/**
 * 姓名数据bean
 * Created by jethro on 2017/5/25.
 */

public class ItemBean {

    //姓名
    private String content ;

    public ItemBean(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
