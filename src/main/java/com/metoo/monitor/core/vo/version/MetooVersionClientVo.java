package com.metoo.monitor.core.vo.version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 客户端版本管理
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@ApiModel("客户端版本管理")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MetooVersionClientVo  {

    @ApiModelProperty("单位编码")
    private Long unitId;


    @ApiModelProperty("客户端名称")
    private String unitName;

    @ApiModelProperty("所属区域编码")
    private Long areaId;

    @ApiModelProperty("当前版本编码")
    private Long curVersionId;

    @ApiModelProperty("当前版本号")
    private String curVersion;

}
