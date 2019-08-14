package com.ets.bus.systemMgt.concentratorMgt.entity;

/**
 * @author 宋晨
 * @create 2019/4/22
 * 上报周期
 * 集中器上传数据频率
 * 前一个字节表示小时
 * 后一个字节表示分钟，分钟必须是5的倍数（0/5/10/15…），HEX码）
 * 0x00xx00表示保持不变（即不改变之前设置的数据上报周期）
 */
public class ReportCycleVo {

    /**
     * 小时
     */
    private Integer hour;

    /**
     * 分钟
     */
    private Integer minute;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }
}
