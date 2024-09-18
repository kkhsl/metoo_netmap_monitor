package com.metoo.monitor.core.vo.version;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 客户端版本管理详情
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Data
public class MetooVersionClientDetailVo extends MetooVersionClientVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("所属区域名称")
    private String areaName;

    @ApiModelProperty("版本说明")
    private String versionRemark;

    @ApiModelProperty("版本状态0表示已完成，1表示未完成")
    private String versionStatus;

    @ApiModelProperty("版本状态名称")
    private String versionStatusName;

    @ApiModelProperty("客户端状态0表示离线，1表示在线")
    private String clientStatus;

    @ApiModelProperty("客户端状态名称")
    private String clientStatusName;
}
