package com.metoo.monitor.core.enums;

/**
 * 客户端状态枚举类
 * @author zzy
 */
public enum ClientStatus {
    OFFLINE(0,"离线"),
    ONLINE(1, "在线");

    private Integer code;

    private String value;

    private ClientStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(Integer code){
        for (ClientStatus type : ClientStatus.values()) {
            if (type.getCode().equals(code)) {
                return type.getValue();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
