package com.metoo.monitor.core.manager;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.entity.version.MetooVersionClient;
import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.service.IMetooVersionClientService;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.ApiPageInfo;
import com.metoo.monitor.core.vo.Result;
import com.metoo.monitor.core.vo.version.MetooVersionClientAppVo;
import com.metoo.monitor.core.vo.version.MetooVersionClientQueryVo;
import com.metoo.monitor.core.vo.version.MetooVersionClientVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 区域信息管理 前端控制器
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Api(tags = "区域信息")
@RestController
@RequestMapping("/monitor/areaInfo")
public class MetooAreaController {

    @Autowired
    private IMetooAreaService metooAreaService;


    @GetMapping("/queryUnitList/{areaId}")
    @ApiOperation(value = "根据区域编码获取单位列表", notes = "根据区域编码获取单位列表")
    @ApiImplicitParam(name = "areaId", value = "区域编码", dataType = "Long")
    public Result queryUnitList(@PathVariable Long areaId) {
        return ResponseUtil.ok(metooAreaService.queryUnitList(areaId));
    }

}
