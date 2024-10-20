package com.metoo.monitor.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 客户端版本管理
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("客户端版本管理类")
public class MetooVersionClient extends IdEntity {

    /**
     * 单位编码
     */
    private Long unitId;


    /**
     * 客户端名称
     */
    private String unitName;

    /**
     * 所属区域编码
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;


}
