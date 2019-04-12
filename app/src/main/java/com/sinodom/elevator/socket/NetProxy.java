package com.sinodom.elevator.socket;

import android.app.Application;

public class NetProxy extends Application {

    private static String host;
    private static int port;

    public static INetConnector netConnector = null;
    private static boolean result = false;
    private static MinaCallBack mCallBack;

    private static INetConnector getNetConnector(MinaCallBack callBack) {
        if (netConnector == null) {
            netConnector = new MinaConnector(callBack);
            host = "192.168.4.1";
            port = 5000;
//            host = "192.168.1.29";
//            port = 50002;
        }
        return netConnector;
    }

    /**
     * 连接真实网关
     *
     * @return
     */
    public static boolean getConnect(MinaCallBack callBack) {
        mCallBack = callBack;
        netConnector = getNetConnector(callBack);
        result = netConnector.connectServer(host, port);
        return result;
    }

    public static void send(String msg) {
        if (netConnector != null) {
            netConnector.write(msg);
        }
    }

    private static INetConnector reNetConnect() {
        if (!netConnector.isConnect()) {
            netConnector = getNetConnector(mCallBack);
        }
        return netConnector;
    }

    /**
     * 停止网络服务
     */
    public static void stopNetConnect() {
        if (netConnector != null) {
            netConnector.disconnect();
            netConnector = null;
        }
    }
}
