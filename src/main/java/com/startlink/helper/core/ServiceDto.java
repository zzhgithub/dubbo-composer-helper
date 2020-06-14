package com.startlink.helper.core;

import java.io.Serializable;

public class ServiceDto implements Serializable {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 引入语句
     */
    private String requireString;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRequireString() {
        return requireString;
    }

    public void setRequireString(String requireString) {
        this.requireString = requireString;
    }
}
