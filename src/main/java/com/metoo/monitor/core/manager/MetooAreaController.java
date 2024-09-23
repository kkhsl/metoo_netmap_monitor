package com.metoo.monitor.core.manager;

import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.MetooAreaSyncVo;
import com.metoo.monitor.core.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 区域信息管理 前端控制器
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Api(tags = "区域信息")
@Slf4j
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
    @GetMapping("/allTree")
    @ApiOperation(value = "获取所有节点的树", notes = "获取所有节点的树")
    public Result allTree() {
        return ResponseUtil.ok(metooAreaService.allTree());
    }

    @PostMapping("/sync/save")
    @ApiOperation(value = "同步新增区域信息-单个新增", notes = "同步新增-外部接口调用同步-单个新增")
    public Result syncSave(@RequestBody MetooAreaSyncVo areaInfo) {
        try {
            metooAreaService.syncSave(areaInfo);
            return ResponseUtil.ok();
        }catch (Exception ex){
            log.error("同步新增区域信息-单个新增出现错误：{}",ex);
            return ResponseUtil.fail(ex.getMessage());
        }
    }
}
