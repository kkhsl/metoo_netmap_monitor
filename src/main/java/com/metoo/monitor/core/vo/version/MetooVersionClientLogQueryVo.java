package com.metoo.monitor.core.vo.version;

import com.metoo.monitor.core.dto.PageDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 客户端版本管理日志
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Data
public class MetooVersionClientLogQueryVo extends PageDto<MetooVersionClientLogQueryVo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("客户端编码")
    private Long unitId;

    @ApiModelProperty("版本状态，1表示已发布，2表示待升级，3表示升级成功，4表示失败")
    private Integer resultFlag;


}
