package com.metoo.monitor.core.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 区域信息vo
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Data
public class MetooAreaVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("单位编码")
    private Long id;

    @ApiModelProperty("单位名称")
    private String name;

}
