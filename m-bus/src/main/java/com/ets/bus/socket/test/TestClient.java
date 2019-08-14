package com.ets.bus.socket.test;

import com.ets.bus.socket.entity.ConcentratorProtocolBean;
import com.ets.bus.socket.server.ConstantValue;
import com.ets.bus.socket.test.entity.LiveTestBean;
import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


/**
 * @author 姚轶文
 * @create 2019- 04-09 15:44
 */
public class TestClient implements Runnable{

    public  Socket socket;
    public  DataOutputStream out;
    public  InputStream in;

    private Thread tRecv = new Thread(new RecvThread());
    private Thread tKeep = new Thread(new KeepThread());

    public void run() {
        connect();
        tRecv.start();
        tKeep.start();
    }

    public void connect() {
        CreateSocket createSocket = new CreateSocket();

        socket = createSocket.getSocket();

        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private  class RecvThread implements Runnable {
        public void run() {
            try {
                System.out.println("==============开始接收数据===============");
                while (true) {
                    byte[] b = new byte[1024];
                    int r = in.read(b);
                    if (r > -1) {
                        String str = new String(b);
                        System.out.println(str);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private class KeepThread implements Runnable {
        public void run() {
            try {
                System.out.println("=====================开始发送心跳包==============");
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    LiveTestBean liveTest = new LiveTestBean();
                    //心跳内容
                    byte[] content = new byte[8];
                    content[0] = liveTest.getSignalIntensity();
                    content[1] = liveTest.getVersion();
                    content[2] = liveTest.getPingNum();
                    content[3] = liveTest.getPongNum();
                    for (int i=0; i<liveTest.getSpare().length; i++){
                        content[4+i] = liveTest.getSpare()[i];
                    }
                    //包长
                    int contentLength = ConstantValue.BASE_LENGTH + content.length;
                    int sum = ConstantValue.PROTOCOL_START + ConstantValue.FUNCODE_LIVE + contentLength;
                    for (byte item:liveTest.getDeviceId()) {
                        sum += item;
                    }
                    for (byte item:content) {
                        sum += item;
                    }
                    byte[] checkArr = intToByteArray(sum);
                    byte[] contentLengthArr = intToByteArray(contentLength);

                    ConcentratorProtocolBean msg = new ConcentratorProtocolBean();
                    msg.setFunctionCode(ConstantValue.FUNCODE_LIVE);
                    msg.setDeviceId(liveTest.getDeviceId());
                    msg.setContent(content);
                    //msg.setCheck(checkArr[3]);

                    out.writeByte(msg.getHead());
                    out.writeByte(msg.getFunctionCode());
                    out.write(msg.getDeviceId());
                    out.writeByte(contentLengthArr[2]);
                    out.writeByte(contentLengthArr[3]);
                    out.write(msg.getContent());
                    out.writeByte(msg.getCheck());
                    out.writeByte(msg.getEnd());

                    System.out.println("发送心跳数据包内容："+ new Gson().toJson(msg));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

}
