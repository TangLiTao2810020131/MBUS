package com.ets.bus.systemMgt.operationLog.entity;

public class mb_operation_log {
    private String id;
    private String workerName;
    private String moduleName;
    private String operaContent;
    private String operaTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOperaContent() {
        return operaContent;
    }

    public void setOperaContent(String operaContent) {
        this.operaContent = operaContent;
    }

    public String getOperaTime() {
        return operaTime;
    }

    public void setOperaTime(String operaTime) {
        this.operaTime = operaTime;
    }
}
