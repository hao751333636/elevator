package com.sinodom.elevator;

public class Constants {

    private Constants() {
    }

    public static class Url {
        public static final String LOGIN = "api/LoginInfo/Login";//登录
//        public static final String InspectList = "api/Inspect/GetInspectList";//检验列表
    }

    public static class PushType {
        public static final String COMPLAINT = "complaint";       //投诉建议
        public static final String NOTICE = "notice";             //通知通告
        public static final String RESCUE0 = "sound0";           //救援任务0
        public static final String RESCUE1 = "sound1";           //救援任务1
        public static final String RESCUE2 = "sound2";           //救援任务2
        public static final String RESCUE3 = "sound3";           //救援任务3
        public static final String VIDEO = "video";           //救援任务3
        public static final String PHOTO = "Photo";           //图片
    }

    public static class Config {
        public static final int OS = 1;
        public static final String DB_NAME = "elevator_db";
        public static final String SP_NAME = "elevator_sp";
        public static final int SHORT_DISPLAY_TIME = 1000;
        public static final int LOAD_DISPLAY_TIME = 2000;
        public static final boolean DEFAULT_ONLY_WIFI = true;
        public static final int MAX_TAKE_PHOTO = 6;
        public static final String UNKNOWN_ERR = "未知错误";
    }

    public static class AppType {
        public static final String ELEVATOR = "Elevator";
        public static final String MAINTENANCE = "Maintenance";
    }

    public static class ResProtocol {
        public static final String HTTP = "http://";
        public static final String FILE = "file://";
        public static final String DRAWABLE = "drawable://";
        public static final String CONTENT = "content://";
        public static final String ASSETS = "assets://";
    }

    public static class PathSet {
        public static final String DATA_PATH = "elevator/";
        public static final String TEMP_DATA_PATH = "elevator/tempdata/";
        public static final String VOLLEY_CACHE_PATH = "elevator/volley/";
        public static final String SAVE_IMAGE_PATH = "elevator/images/";
    }

    public static class Code {
        public static final int OPEN_GALLERY = 101;
        public static final int OPEN_CAMERA = 102;
        public static final int OPEN_CROPIMAGE = 103;
        public static final int GO_RESCUE = 106;
        public static final int RESCUE_OK = 107;
        public static final int GO_SCORE = 108;
        public static final int SCORE_OK = 109;
        public static final int GO_WBAZTS = 110;
        public static final int WBAZTS_OK = 111;
    }

    public static class STATUS {
        public static final int SUCCESS = 1;
        public static final int FAILURE = 0;
    }

    public static class Action {
        public static final String LOGIN = "com.sinodom.elevator.login";
        public static final String LOGOUT = "com.sinodom.elevator.logout";
        public static final String FILE = "com.sinodom.elevator.file";
        public static final String NOTIFICATION = "com.sinodom.elevator.notification";
    }

    public static class PushPrefix {
        public static final String ALIAS = "A_";
        public static final String ALLUSER = "ALLUSER";
        public static final String TAGS = "T_";
    }
}