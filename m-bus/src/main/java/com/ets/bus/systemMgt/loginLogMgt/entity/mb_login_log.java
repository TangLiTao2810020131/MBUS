package com.ets.bus.systemMgt.loginLogMgt.entity;

public class mb_login_log {
    private String id;
    private String workerAccount;
    private String workerName;
    private String ipaddress;
    private String loginTime;
    private String loginState;//登录状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkerAccount() {
        return workerAccount;
    }

    public void setWorkerAccount(String workerAccount) {
        this.workerAccount = workerAccount;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginState() {
        return loginState;
    }

    public void setLoginState(String loginState) {
        this.loginState = loginState;
    }
}
