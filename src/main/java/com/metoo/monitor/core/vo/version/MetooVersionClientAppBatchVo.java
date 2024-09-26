package com.metoo.monitor.core.vo.version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * <p>
 * 客户端版本管理-指定版本
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@ApiModel("客户端版本批量发布")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetooVersionClientAppBatchVo {

    @ApiModelProperty("单位编码集合")
    private List<Long> unitIds;

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
