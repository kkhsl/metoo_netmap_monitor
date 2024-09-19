package com.metoo.monitor.core.enums;

/**
 * 客户端版本状态枚举类
 * @author zzy
 */
public enum VersionStatus {
    NORMAL("0","完成"),
    ABNORMAL("1", "未完成");

    private String code;

    private String value;

    private VersionStatus(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(String code){
        for (VersionStatus type : VersionStatus.values()) {
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
