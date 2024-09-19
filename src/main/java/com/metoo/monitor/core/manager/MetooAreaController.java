package com.metoo.monitor.core.manager;

import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
