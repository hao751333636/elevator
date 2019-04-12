package com.sinodom.elevator.socket;

import android.util.Log;

import com.sinodom.elevator.util.HexUtils;
import com.sinodom.elevator.util.TextUtil;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.net.PortUnreachableException;

public class MinaTCPConnectorHandler extends IoHandlerAdapter {

    private MinaCallBack mCallBack;
    private String mString = "";

    public MinaTCPConnectorHandler(MinaCallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * 当session被建立是触发
     *
     * @param session
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String msg = message.toString();
        super.messageReceived(session, message);
        Log.e("MinaConnectorHandler", "messageReceived-al-" + msg);
        String[] string1 = msg.split(" ");
        if ((msg.startsWith("AA") && string1.length == 36) || (msg.startsWith("BB") || msg.startsWith("E1") || msg.startsWith("E2") || msg.startsWith("E3"))
                && HexUtils.checkCode(msg).equals(string1[string1.length - 1])) {
            mString = "";
        } else {
            if (!TextUtil.isEmpty(mString)) {
                msg = mString + " " + msg;
            }
        }
        String[] string = msg.split(" ");
        if (msg.startsWith("AA")) {
            Log.e("MinaConnectorHandler", "messageReceived-aa-" + msg);
            if (string.length == 36) {
                mCallBack.messageReceivedAA(msg);
                mString = "";
            } else {
                mString = msg;
            }
        } else if (msg.startsWith("BB")) {
            Log.e("MinaConnectorHandler", "messageReceived-bb-" + msg);
            if (HexUtils.checkCode(msg).equals(string[string.length - 1])) {
                mCallBack.messageReceivedBB(msg);
                mString = "";
            } else {
                mString = msg;
            }
        } else if (msg.startsWith("E1")) {
            Log.e("MinaConnectorHandler", "messageReceived-e1-" + msg);
            if (HexUtils.checkCode(msg).equals(string[string.length - 1])) {
                mCallBack.messageReceivedE1(msg);
                mString = "";
            } else {
                mString = msg;
            }
        } else if (msg.startsWith("E2")) {
            Log.e("MinaConnectorHandler", "messageReceived-e2-" + msg);
            if (HexUtils.checkCode(msg).equals(string[string.length - 1])) {
                mCallBack.messageReceivedE2(msg);
                mString = "";
            } else {
                mString = msg;
            }
        } else if (msg.startsWith("E3")) {
            Log.e("MinaConnectorHandler", "messageReceived-e3-" + msg);
            if (HexUtils.checkCode(msg).equals(string[string.length - 1])) {
                mCallBack.messageReceivedE3(msg);
                mString = "";
            } else {
                mString = msg;
            }
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        Log.e("MinaConnectorHandler", "网络层关闭session连接,停止网络服务");
        super.sessionClosed(session);
        MinaConnector.isCon = false;
        mCallBack.sessionClosed(false);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        Log.e("MinaConnectorHandler", "检测到正在向网关发送字符串：" + message);
        super.messageSent(session, message);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        Log.e("MinaConnectorHandler", "网络层已开启session");
        super.sessionOpened(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable exception) throws Exception {
        super.exceptionCaught(session, exception);
        if (exception instanceof PortUnreachableException) {
            MinaConnector.isCon = false;
            mCallBack.sessionClosed(false);
            Log.e("MinaConnectorHandler", "网络故障！！！！！！！！！");
        }
        Log.e("MinaConnectorHandler", "session出现异常", exception);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus idle) throws Exception {
        super.sessionIdle(session, idle);
    }
}
