package com.metoo.monitor.core.enums;

/**
 * 客户端版本状态枚举类
 * @author zzy
 */
public enum VersionStatus {
    NORMAL(0,"完成"),
    ABNORMAL(1, "未完成");

    private Integer code;

    private String value;

    private VersionStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(Integer code){
        for (VersionStatus type : VersionStatus.values()) {
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
