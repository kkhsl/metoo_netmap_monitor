package com.metoo.monitor.core.enums;

/**
 * 客户端历史版本状态枚举类
 * @author zzy
 * 版本状态，1表示已发布，2表示待升级，3表示发布成功，4表示失败，5表示已取消
 */
public enum VersionLogStatus {
    PUBLISH(1,"已发布"),
    UPLOAD(2, "待升级"),
    FINISH(3, "发布成功"),
    ERROR(4, "失败"),
    CANCEL(5, "已取消");

    private Integer code;

    private String value;

    private VersionLogStatus(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getValueByCode(Integer code){
        for (VersionLogStatus type : VersionLogStatus.values()) {
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
