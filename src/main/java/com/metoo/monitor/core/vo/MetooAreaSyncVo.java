package com.metoo.monitor.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * <p>
 * 区域信息vo
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@ApiModel("区域信息Vo")
@Data
public class MetooAreaSyncVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("区域名称")
    private String area;

    @ApiModelProperty("单位名称")
    private String unit;

    @ApiModelProperty("地市名称")
    private String city;

    @ApiModelProperty("单位编码")
    private String unitId;
}
