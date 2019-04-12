package com.sinodom.elevator.socket;

public interface MinaCallBack {
    public void messageReceivedAA(String msg);
    public void messageReceivedBB(String msg);
    public void messageReceivedE1(String msg);
    public void messageReceivedE2(String msg);
    public void messageReceivedE3(String msg);
    public void sessionClosed(boolean isCon);
}
