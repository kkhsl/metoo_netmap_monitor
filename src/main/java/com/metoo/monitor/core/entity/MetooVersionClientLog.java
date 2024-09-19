package com.metoo.monitor.core.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 版本发布记录表
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("版本发布记录类")
public class MetooVersionClientLog extends IdEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端id对应客户端版本主键
     */
    private Long unitId;

    /**
     * 历史版本id
     */
    @ApiModelProperty("版本编码")
    private Long versionId;

    /**
     * 历史版本号
     */
    @ApiModelProperty("版本号")
    private String version;

    /**
     * 历史版本操作人
     */
    @ApiModelProperty("版本操作人")
    private Long opId;
    /**
     * 历史版本操作人名称
     */
    @ApiModelProperty("版本操作人名称")
    private String opName;

    /**
     * 版本状态，1表示已发布，2表示待升级，3表示升级成功，4表示失败
     */
    @ApiModelProperty("版本状态，1表示已发布，2表示待升级，3表示升级成功，4表示失败")
    private Integer resultFlag;


    @ApiModelProperty("版本状态名称")
    private Integer resultFlagName;
    /**
     * 错误信息
     */
    @ApiModelProperty("错误信息")
    private String errorInfo;


}
