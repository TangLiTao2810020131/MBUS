package com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity;

/**
 * @author 宋晨
 * @create 2019/4/29
 * 集中器在线情况
 */
public class OnlineSituationVo {

    public OnlineSituationVo(){

    }

    public OnlineSituationVo(String name){
        this.name=name;
    }

    /**
     * 在线个数
     */
    private Integer num;

    /**
     * 名称在线/离线
     */
    private String name;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
