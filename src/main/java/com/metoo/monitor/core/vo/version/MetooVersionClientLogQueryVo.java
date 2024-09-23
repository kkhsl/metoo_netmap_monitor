package com.metoo.monitor.core.vo.version;

import com.metoo.monitor.core.vo.ApiPageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 客户端版本管理日志
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@ApiModel("客户端版本管理日志")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MetooVersionClientLogQueryVo extends ApiPageInfo<MetooVersionClientLogQueryVo>  {

    @ApiModelProperty("客户端编码")
    private Long unitId;

    @ApiModelProperty("版本状态，1表示已发布，2表示待升级，3表示升级成功，4表示失败")
    private Integer versionStatus;


}
