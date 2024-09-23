package com.metoo.monitor.core.vo.version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户端版本管理
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@ApiModel("客户端版本更新信息Vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetooVersionClientUpdateVo {

    @ApiModelProperty("单位编码")
    private Long unitId;

    @ApiModelProperty("当前版本编码")
    private Long curVersionId;

    @ApiModelProperty("当前版本号")
    private String curVersion;

    @ApiModelProperty("错误信息")
    private String errorInfo;
    /**
     * 版本状态，1表示已发布，2表示待升级，3表示升级成功，4表示失败
     */
    @ApiModelProperty("版本状态，1表示已发布，2表示待升级，3表示升级成功，4表示失败")
    private Integer versionStatus;
}
