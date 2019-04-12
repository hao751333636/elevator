package com.sinodom.elevator.bean.lift;

public class PhotoBean {

    /**
     * ID : 24
     * EquipmentId : 29
     * ResponceTime : null
     * FileNmae : xxxxx.jpg
     * LiftId : 155940
     * TaskId : null
     */

    private int ID;
    private int EquipmentId;
    private String ResponceTime;
    private String FileNmae;
    private int LiftId;
    private int TaskId;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        EquipmentId = equipmentId;
    }

    public String getResponceTime() {
        return ResponceTime;
    }

    public void setResponceTime(String responceTime) {
        ResponceTime = responceTime;
    }

    public String getFileNmae() {
        return FileNmae;
    }

    public void setFileNmae(String fileNmae) {
        FileNmae = fileNmae;
    }

    public int getLiftId() {
        return LiftId;
    }

    public void setLiftId(int liftId) {
        LiftId = liftId;
    }

    public int getTaskId() {
        return TaskId;
    }

    public void setTaskId(int taskId) {
        TaskId = taskId;
    }
}
