package com.metoo.monitor.core.entity.version;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户端版本管理
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MetooVersionClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("单位编码")
    private Long unitId;


    @ApiModelProperty("客户端名称")
    private String unitName;

    @ApiModelProperty("所属区域编码")
    private Long areaId;

    @ApiModelProperty("当前版本编码")
    private Long curVersionId;

    /**
     * 当前版本号
     */
    @ApiModelProperty("当前版本号")
    private String curVersion;

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

    /**
     * 客户端状态：0表示离线，1表示在线
     */
    @ApiModelProperty("客户端状态：0表示离线，1表示在线")
    private Integer clientStatus;

    /**
     * 删除标记：0表示正常，1表示删除
     */
    private Integer deleteStatus;
    /**
     * 版本状态：0表示已完成，1表示未完成
     */
    private Integer versionStatus;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建人名称
     */
    private String createName;

    /**
     * 创建时间
     */
    private LocalDateTime addTime;

    /**
     * 更新人
     */
    private Long updateBy;

    /**
     * 更新人名称
     */
    private String updateName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
