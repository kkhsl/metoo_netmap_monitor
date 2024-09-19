package com.metoo.monitor.core.vo.version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metoo.monitor.core.enums.ClientStatus;
import com.metoo.monitor.core.enums.VersionStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户端版本管理详情
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
public class MetooVersionClientDetailVo extends MetooVersionClientVo {

    @ApiModelProperty("所属区域名称")
    private String areaName;

    @ApiModelProperty("版本说明")
    private String versionRemark;

    @ApiModelProperty("版本状态0表示已完成，1表示未完成")
    private String versionStatus;

    @ApiModelProperty("版本状态名称")
    private String versionStatusName;

    @ApiModelProperty("客户端状态0表示离线，1表示在线")
    private String clientStatus;

    @ApiModelProperty("客户端状态名称")
    private String clientStatusName;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String updateTime;

    public String getVersionStatusName() {
        return VersionStatus.getValueByCode(this.versionStatus);
    }

    public String getClientStatusName() {
        return ClientStatus.getValueByCode(this.clientStatus);
    }
}
