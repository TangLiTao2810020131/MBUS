package com.ets.bus.parmSet.readWriteCardparm.entity;

public class ReadWriteCardParm {
    private String id;
    private String version_num;//版本号
    private String base_address;//基础信息区扇区地址
    private String money_address;//金额信息区扇区地址
    private String record_address;//记录信息区扇区地址
    private String watermete_address;//水表信息区扇区地址
    private String extend_address;//扩展信息区扇区地址
    private String purse_address;//小钱包信息区扇区地址
    private String use_crc;//是否使用CRC校验
    private String card_pass_mode;//用户的卡密码模式
    private String retention_one;//保留值1
    private String retention_two;//保留值2
    private String del_status;// '是否删除（0：否，1：是）'
    private String create_time;//创建时间
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion_num() {
        return version_num;
    }

    public void setVersion_num(String version_num) {
        this.version_num = version_num;
    }

    public String getBase_address() {
        return base_address;
    }

    public void setBase_address(String base_address) {
        this.base_address = base_address;
    }

    public String getMoney_address() {
        return money_address;
    }

    public void setMoney_address(String money_address) {
        this.money_address = money_address;
    }

    public String getRecord_address() {
        return record_address;
    }

    public void setRecord_address(String record_address) {
        this.record_address = record_address;
    }

    public String getWatermete_address() {
        return watermete_address;
    }

    public void setWatermete_address(String watermete_address) {
        this.watermete_address = watermete_address;
    }

    public String getExtend_address() {
        return extend_address;
    }

    public void setExtend_address(String extend_address) {
        this.extend_address = extend_address;
    }

    public String getPurse_address() {
        return purse_address;
    }

    public void setPurse_address(String purse_address) {
        this.purse_address = purse_address;
    }

    public String getUse_crc() {
        return use_crc;
    }

    public void setUse_crc(String use_crc) {
        this.use_crc = use_crc;
    }

    public String getCard_pass_mode() {
        return card_pass_mode;
    }

    public void setCard_pass_mode(String card_pass_mode) {
        this.card_pass_mode = card_pass_mode;
    }

    public String getRetention_one() {
        return retention_one;
    }

    public void setRetention_one(String retention_one) {
        this.retention_one = retention_one;
    }

    public String getRetention_two() {
        return retention_two;
    }

    public void setRetention_two(String retention_two) {
        this.retention_two = retention_two;
    }

    public String getDel_status() {
        return del_status;
    }

    public void setDel_status(String del_status) {
        this.del_status = del_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }


}
