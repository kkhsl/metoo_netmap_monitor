package com.metoo.monitor.core.enums;

/**
 * 客户端版本类型枚举类
 * @author zzy
 */
public enum VersionType {
    ADD(0,"增量版本"),
    ALL(1, "全量版本");

    private Integer code;

    private String value;

    private VersionType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(Integer code){
        for (VersionType type : VersionType.values()) {
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
