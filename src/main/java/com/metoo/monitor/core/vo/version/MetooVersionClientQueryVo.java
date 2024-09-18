package com.metoo.monitor.core.vo.version;

import com.metoo.monitor.core.dto.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 客户端版本管理
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Data
public class MetooVersionClientQueryVo extends PageDto<MetooVersionClientQueryVo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("客户端名称")
    private String unitName;

    @ApiModelProperty("所属区域编码")
    private Long areaId;


}
