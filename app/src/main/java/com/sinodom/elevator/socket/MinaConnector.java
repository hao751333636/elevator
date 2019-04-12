package com.sinodom.elevator.socket;


import android.util.Log;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.util.HexUtils;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;

public class MinaConnector implements INetConnector {

    private static IoSession session;
    public static boolean isCon = false;
    private static NioSocketConnector connectortcp;
    private MinaCallBack mCallBack;

    public MinaConnector(MinaCallBack callBack) {
        mCallBack = callBack;
    }

    public boolean connectServer(String host, int port) {
        isCon = this.startTcpConnect(host, port);
        return isCon;
    }

    public void disconnect() {
        if (connectortcp != null) {
            connectortcp.dispose();
        }
        if (session != null) {
            session.close(true);
        }
        isCon = false;
    }

    public boolean isConnect() {
        return isCon;
    }

    public void write(String msg) {
        // TODO Auto-generated method stub
        if (session != null) {
            Logger.i("Mina", "msg : " + msg);
            session.write(msg);
        }
    }

    private void sendData() {
        try {// 这是一个测试网络是否可达的报文，重要
            session.write("test host and port is usalbe!");
            Thread.sleep(1000);
            isCon = true;
        } catch (Exception e) {
            Logger.e("MinaConnector", e.getMessage());
            isCon = false;
        }
    }

    private boolean startTcpConnect(String host, int port) {
        isCon = false;
        connectortcp = new NioSocketConnector();
        // 设定服务器端的消息处理器:一个SamplMinaServerHandler对象, 添加一个业务处理器
        connectortcp.setHandler(new MinaTCPConnectorHandler(mCallBack));
        connectortcp.setConnectTimeoutMillis(3000);
        connectortcp.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ByteArrayCodecFactory()));
        connectortcp.getSessionConfig().setReceiveBufferSize(1024);
        connectortcp.getSessionConfig().setThroughputCalculationInterval(1);
        // 连结到服务器:
        ConnectFuture cf = connectortcp.connect(new InetSocketAddress(host, port));

        try {
            // 这是一个测试网络是否可达的报文，重要
            cf.awaitUninterruptibly();
            session = cf.getSession();
            isCon = true;
        } catch (Exception e) {
            Logger.e("MinaConnector----", "connect Exception ");
            isCon = false;
        }

        return isCon;
    }

    //编码
    public class ByteArrayEncoder extends ProtocolEncoderAdapter {

        @Override
        public void encode(IoSession session, Object message,
                           ProtocolEncoderOutput out) {
            String cmd = (String) message;
            String[] cmds = cmd.split(" ");
            byte[] aaa = new byte[cmds.length];
            int i = 0;
            for (String b : cmds) {
                if (b.equals("FF")) {
                    aaa[i++] = -1;
                } else {
                    aaa[i++] = (byte) Integer.parseInt(b, 16);
                }
            }
            out.write(IoBuffer.wrap(aaa));
            out.flush();
        }
    }

    //解码
    public class ByteArrayDecoder extends CumulativeProtocolDecoder {
        @Override
        public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
                throws Exception {
            int limit = in.limit();
            byte[] bytes = new byte[limit];
            in.get(bytes);
            out.write(HexUtils.byte2HexStr(bytes));
            Log.e("TAG", "decode-" + HexUtils.byte2HexStr(bytes));
        }

        @Override
        protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
            int limit = in.limit();
            byte[] bytes = new byte[limit];
            in.get(bytes);
            out.write(HexUtils.byte2HexStr(bytes));
            Log.e("TAG", "doDecode-" + HexUtils.byte2HexStr(bytes));
            return false;
        }
    }

    //工厂
    public class ByteArrayCodecFactory implements ProtocolCodecFactory {
        private ByteArrayDecoder decoder;
        private ByteArrayEncoder encoder;

        public ByteArrayCodecFactory() {
            encoder = new ByteArrayEncoder();
            decoder = new ByteArrayDecoder();
        }

        @Override
        public ProtocolDecoder getDecoder(IoSession session) throws Exception {
            return decoder;
        }

        @Override
        public ProtocolEncoder getEncoder(IoSession session) throws Exception {
            return encoder;
        }
    }

}
