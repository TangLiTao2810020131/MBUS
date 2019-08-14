package com.ets.bus.socket.test;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 姚轶文
 * @create 2019- 04-11 9:33
 */
public class CreateSocket {

    public static final String IP_ADDR = "127.0.0.1";//服务器地址
    public static final int PORT = 8899;//服务器端口号

    public Socket socket ;

    public Socket getSocket() {
        try {
            socket = new Socket(IP_ADDR, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return socket;
    }
}
