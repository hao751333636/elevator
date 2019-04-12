package com.sinodom.elevator.bean.examine;

/**
 * 父级数据bean
 * Created by jethro on 2017/5/25.
 */

public class GroupBean {

    private String title ; //部门名称


    /**
     * @param title 部门名称
     */
    public GroupBean(String title) {
        this.title = title;
    }

    //获取部门名称
    public String getTitle() {
        return title;
    }

    //设置部门名称
    public void setTitle(String title) {
        this.title = title;
    }
}
