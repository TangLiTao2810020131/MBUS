package com.ets.bus.socket.log.entity;

import java.util.Date;

/**
 * @author 宋晨
 * @create 2019/4/9
 * socket连接日志
 */
public class SocketLogBean {

    public SocketLogBean(){

    }
    public SocketLogBean(String id, Date currentTime){
        this.id = id;
        this.currentTime = currentTime;
    }

    /**
     * ID
     */
    private String id;

    /**
     * channel的ID
     */
    private String channelId;

    /**
     * 当前时间
     */
    private Date currentTime;

    /**
     * 备注
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
