package com.metoo.monitor.core.vo.external;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 测绘下发任务信息
 * @author zzy
 * @version 1.0
 * @date 2025/4/16 15:55
 */
@ApiModel("测绘下发任务信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyTimeVo {
    @ApiModelProperty("单位编码")
    private Long unitId;

    @ApiModelProperty("测绘时间")
    private String surveyTime;

}
