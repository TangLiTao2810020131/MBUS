package com.ets.bus.socket.test;

/**
 * @author 姚轶文
 * @create 2019- 04-09 18:15
 */
public class ThreadClient {


    public static void main(String[] args) {
        try {
        for(int i=0 ; i<2 ; i ++)
        {

            Thread testClient = new Thread(new TestClient());
            testClient.start();

            System.out.println("启动线程:"+i);
            Thread.sleep(500);
        }

            Thread.currentThread().wait();
        } catch (InterruptedException e) {
        }
    }
}
