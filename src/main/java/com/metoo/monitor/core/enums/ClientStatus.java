package com.metoo.monitor.core.enums;

/**
 * 客户端状态枚举类
 * @author zzy
 */
public enum ClientStatus {
    OFFLINE("0","离线"),
    ONLINE("1", "在线");

    private String code;

    private String value;

    private ClientStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code){
        for (ClientStatus type : ClientStatus.values()) {
            if (type.getCode().equals(code)) {
                return type.getValue();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
