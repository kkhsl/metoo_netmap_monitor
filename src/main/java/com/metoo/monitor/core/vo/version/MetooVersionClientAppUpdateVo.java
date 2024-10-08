package com.metoo.monitor.core.vo.version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 客户端版本管理-指定版本
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@ApiModel("客户端版本管理")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetooVersionClientAppUpdateVo {

    @ApiModelProperty("单位编码")
    private Long unitId;

    /**
     * 指定安装的版本编码
     */
    @ApiModelProperty("指定安装的版本编码")
    private Long appVersionId;

    /**
     * 指定安装的版本
     */
    @ApiModelProperty("指定安装的版本")
    private String appVersion;
}
