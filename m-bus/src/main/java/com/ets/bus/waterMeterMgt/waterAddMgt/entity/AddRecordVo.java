package com.ets.bus.waterMeterMgt.waterAddMgt.entity;

import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.RecordBaseVo;
import com.ets.bus.waterMeterMgt.waterPurchaseMgt.entity.WaterMeterInfoVo;
import com.google.gson.annotations.Expose;


/**
 * @author 宋晨
 * @create 2019/4/2
 * 补水信息Vo
 */
public class AddRecordVo extends RecordBaseVo {

    /**
     * 流水号
     */
    private String serialNumber;

    /**
     * 补水量（吨）
     */
    private Double addWater;

    /**
     * 操作金额
     */
    private Double operatMoney;

    /**
     * 类型（0:房间补水，1:楼层补水，2：按导入补水,3：换房补水）
     */
    private Integer type;

    /**
     * 房间信息
     */
    @Expose(serialize = false, deserialize = false)
    private WaterMeterInfoVo waterMeterInfoVo;


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getAddWater() {
        return addWater;
    }

    public void setAddWater(Double addWater) {
        this.addWater = addWater;
    }

    public Double getOperatMoney() {
        return operatMoney;
    }

    public void setOperatMoney(Double operatMoney) {
        this.operatMoney = operatMoney;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public WaterMeterInfoVo getWaterMeterInfoVo() {
        return waterMeterInfoVo;
    }

    public void setWaterMeterInfoVo(WaterMeterInfoVo waterMeterInfoVo) {
        this.waterMeterInfoVo = waterMeterInfoVo;
    }
}
