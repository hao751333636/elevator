package com.sinodom.elevator.bean.nim;

import java.io.Serializable;
import java.util.List;

public class RoomBean implements Serializable{

    /**
     * roomId : testRoom1536215083830
     * creator : 10000031
     * roomName : testRoom1536215083830
     * userCount : 2
     * userList : [{"WYID":"10000031","name":"???","ucode":"","iscreate":true},{"WYID":"esl_18524481721","name":"???","ucode":"20180906","iscreate":false}]
     */

    private String roomId;
    private String creator;
    private String roomName;
    private String userCount;
    private int type;
    private List<UserListBean> userList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }

    public List<UserListBean> getUserList() {
        return userList;
    }

    public void setUserList(List<UserListBean> userList) {
        this.userList = userList;
    }

    public static class UserListBean implements Serializable{
        /**
         * WYID : 10000031
         * name : ???
         * ucode :
         * iscreate : true
         */

        private String WYID;
        private String name;
        private String ucode;
        private String userID;
        private boolean iscreate;

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getWYID() {
            return WYID;
        }

        public void setWYID(String WYID) {
            this.WYID = WYID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUcode() {
            return ucode;
        }

        public void setUcode(String ucode) {
            this.ucode = ucode;
        }

        public boolean isIscreate() {
            return iscreate;
        }

        public void setIscreate(boolean iscreate) {
            this.iscreate = iscreate;
        }
    }
}
