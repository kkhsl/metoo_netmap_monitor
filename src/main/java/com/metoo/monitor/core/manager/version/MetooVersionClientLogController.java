package com.metoo.monitor.core.manager.version;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.entity.version.MetooVersionClientLog;
import com.metoo.monitor.core.service.IMetooVersionClientLogService;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.ApiPageInfo;
import com.metoo.monitor.core.vo.Result;
import com.metoo.monitor.core.vo.version.MetooVersionClientLogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端版本管理日志 前端控制器
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Api(tags = "客户端版本管理日志")
@RestController
@RequestMapping("/monitor/versionClientLog")
public class MetooVersionClientLogController {

    @Autowired
    private IMetooVersionClientLogService logService;


    @PostMapping("/list")
    @ApiOperation(value = "查询客户端版本管理日志列表", notes = "查询客户端版本管理日志列表")
    @ApiImplicitParam(name = "logVo", value = "参数", dataType = "MetooVersionClientLogQueryVo")
    public Result list(@RequestBody MetooVersionClientLogQueryVo logVo) {
        Page<MetooVersionClientLog> page = logService.list(logVo);
        if (page.getResult().size() > 0) {
            return ResponseUtil.ok(new ApiPageInfo<MetooVersionClientLog>(page));
        }
        return ResponseUtil.ok();
    }

}
