package com.metoo.monitor.core.manager.external;

import com.metoo.monitor.core.service.IExternalInterfaceService;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.Result;
import com.metoo.monitor.core.vo.external.SurveyTimeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 外部接口
 * @author zzy
 * @version 1.0
 * @date 2025/4/16 15:48
 */
@Api(tags = "外部接口管理")
@RestController
@AllArgsConstructor
@RequestMapping("/external")
@Slf4j
public class ExternalInterfaceController {
    private final IExternalInterfaceService externalInterfaceService;
    /**
     * 发送下发测绘任务时间
     * @param params
     * @return
     */
    @PostMapping("/sendSurveyTime")
    @ApiOperation(value = "发送下发测绘任务时间", notes = "发送下发测绘任务时间")
    public Result sendSurveyTime(@RequestBody SurveyTimeVo params){
        try {
            externalInterfaceService.sendSurveyTime(params);
            return ResponseUtil.ok("下发任务成功");
        } catch (Exception e) {
            log.error("下发任务失败：{}", e);
            return ResponseUtil.fail("下发任务失败");
        }
    }
}
