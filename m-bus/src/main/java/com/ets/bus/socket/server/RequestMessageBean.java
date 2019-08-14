package com.ets.bus.socket.server;

/**
 * @author 姚轶文
 * @create 2019- 04-07 17:49
 */
public class RequestMessageBean {

    private int head_data = ConstantValue.HEAD_DATA;  //消息头开始， 十六进制 0e

    private int contentLength;  // 数据长度

    private byte[] content;  //数据内容


    public int getHead_data() {
        return head_data;
    }

    public void setHead_data(int head_data) {
        this.head_data = head_data;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
