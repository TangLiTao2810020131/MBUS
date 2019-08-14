package com.ets.bus.parmSet.roomTypeParm.entity;

/**
 * 房间类型实体类
 */
public class RoomType
{
    private String id;//房间类型id
    private String type_num;//类型编号
    private String type_name;//类型名称
    private Integer del_status;//是否删除 0：否 1：是
    private Integer effect_status;//是否生效
    private Double warn_water;//报警水量
    private Double valve_water;//关阀水量
    private Double over_water;//最大透支水量
    private Double hoard_water;//最大囤积水量
    private Double buy_money;//购水单价
    private Double return_money;//退水单价
    private Integer add_cycle;//软件补水周期
    private String add_cycle_name;//软件补水周期名称
    private String create_time;//创建时间
    private String effect_time;//参数生效时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_num() {
        return type_num;
    }

    public void setType_num(String type_num) {
        this.type_num = type_num;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public Integer getDel_status() {
        return del_status;
    }

    public void setDel_status(Integer del_status) {
        this.del_status = del_status;
    }

    public Integer getEffect_status() {
        return effect_status;
    }

    public void setEffect_status(Integer effect_status) {
        this.effect_status = effect_status;
    }

    public Double getWarn_water() {
        return warn_water;
    }

    public void setWarn_water(Double warn_water) {
        this.warn_water = warn_water;
    }

    public Double getValve_water() {
        return valve_water;
    }

    public void setValve_water(Double valve_water) {
        this.valve_water = valve_water;
    }

    public Double getOver_water() {
        return over_water;
    }

    public void setOver_water(Double over_water) {
        this.over_water = over_water;
    }

    public Double getHoard_water() {
        return hoard_water;
    }

    public void setHoard_water(Double hoard_water) {
        this.hoard_water = hoard_water;
    }

    public Double getBuy_money() {
        return buy_money;
    }

    public void setBuy_money(Double buy_money) {
        this.buy_money = buy_money;
    }

    public Double getReturn_money() {
        return return_money;
    }

    public void setReturn_money(Double return_money) {
        this.return_money = return_money;
    }

    public Integer getAdd_cycle() {
        return add_cycle;
    }

    public void setAdd_cycle(Integer add_cycle) {
        this.add_cycle = add_cycle;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getEffect_time() {
        return effect_time;
    }

    public void setEffect_time(String effect_time) {
        this.effect_time = effect_time;
    }

    public String getAdd_cycle_name() {
        return add_cycle_name;
    }

    public void setAdd_cycle_name(String add_cycle_name) {
        this.add_cycle_name = add_cycle_name;
    }
}