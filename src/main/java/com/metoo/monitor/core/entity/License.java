package com.metoo.monitor.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class License {

    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date addTime;// 添加时间

    @ApiModelProperty("申请码,系统唯一序列号")
    private String systemSN;

    private String applicantCode;

    @ApiModelProperty("授权码")
    private String license;

    @ApiModelProperty("开始时间")
    private Long startTime;

    @ApiModelProperty("结束时间")
    private Long endTime;

    @ApiModelProperty("License版本号")
    private String licenseVersion;

    @ApiModelProperty("License类型 0：试用版 1，授权版 2：终身版")
    private String type;

    @ApiModelProperty("License状态  0：未过期 1:未授权 2：已过期")
    private Integer status;

    @ApiModelProperty("SN码来源 0：同一来源 1：不同来源 不同来源则不允许使用")
    private Integer from;

    //#################################
    private String unit_id;

    private String version;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区/县")
    private String area;

    @ApiModelProperty("单位名称")
    private String unit;

}
