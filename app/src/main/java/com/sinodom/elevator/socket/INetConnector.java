package com.sinodom.elevator.socket;

public interface INetConnector {
	
	
	public boolean connectServer(String host, int port);
	
	public void disconnect();
	
	public void write(String msg);
	
	public boolean isConnect();
}
