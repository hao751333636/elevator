package com.sinodom.elevator.bean;

/**
 * Created by 安卓 on 2017/11/3.
 */

public class ResponBean {

    /**
     * Success : true
     * Code : 1
     * Message : 操作成功
     * Data : 16:05:44
     * Count : 0
     */

    private boolean Success;
    private String Code;
    private String Message;
    private String Data;
    private int Count;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }
}
