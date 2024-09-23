package com.metoo.monitor.core.vo.version;

import com.metoo.monitor.core.vo.ApiPageInfo;
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
@ApiModel("客户端版本分页查询管理")
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MetooVersionClientQueryVo extends ApiPageInfo<MetooVersionClientQueryVo> {

    @ApiModelProperty("客户端名称")
    private String unitName;

    @ApiModelProperty("所属区域编码")
    private Long areaId;
    /**
     * 客户端状态：0表示离线，1表示在线
     */
    @ApiModelProperty("客户端状态：0表示离线，1表示在线")
    private Integer clientStatus;
    /**
     * 版本状态：0表示已完成，1表示未完成
     */
    @ApiModelProperty("版本状态：0表示已完成，1表示未完成")
    private Integer versionStatus;

}
