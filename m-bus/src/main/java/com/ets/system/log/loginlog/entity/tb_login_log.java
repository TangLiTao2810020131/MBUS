package com.ets.system.log.loginlog.entity;

/**
 * @author 吴浩
 * @create 2019-01-08 20:15
 */
public class tb_login_log {

    private String id;
    private String ipaddress;
    private String logintime;
    private String username;
    private String region; //省份
    private String city; //城市
    private String isp; //网络供应商
    private String ostype; //操作系统
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIpaddress() {
        return ipaddress;
    }
    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }
    public String getLogintime() {
        return logintime;
    }
    public void setLogintime(String logintime) {
        this.logintime = logintime;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRegion() {
        return region;
    }
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getIsp() {
        return isp;
    }
    public void setIsp(String isp) {
        this.isp = isp;
    }
    public String getOstype() {
        return ostype;
    }
    public void setOstype(String ostype) {
        this.ostype = ostype;
    }



}
