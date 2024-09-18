package com.metoo.monitor.core.manager.version;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.entity.version.MetooVersionClient;
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
 * 客户端版本管理 前端控制器
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Api(tags = "客户端版本管理")
@RestController
@RequestMapping("/monitor/versionClient")
public class MetooVersionClientController {

    @Autowired
    private IMetooVersionClientService metooVersionClientService;


    /**
     * 获取区域树
     */
    @ApiOperation(value = "获取区域树", notes = "获取区域树")
    @GetMapping("/tree")
    public Result tree() {
        return ResponseUtil.ok(metooVersionClientService.tree());
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询客户端版本管理列表", notes = "查询客户端版本管理列表")
    @ApiImplicitParam(name = "versionClientVo", value = "参数", dataType = "MetooVersionClientQueryVo")
    public Result list(@RequestBody MetooVersionClientQueryVo versionClientVo) {
        Page<MetooVersionClient> page = metooVersionClientService.list(versionClientVo);
//        if (page.getResult().size() > 0) {
//            return ResponseUtil.ok(new ApiPageInfo<MetooVersionClient>(page));
//        }
        return ResponseUtil.ok(new ApiPageInfo<MetooVersionClient>(page));
    }
    @PostMapping("/save")
    @ApiOperation(value = "新增客户端版本", notes = "新增客户端版本")
    @ApiImplicitParam(name = "versionClientVo", value = "参数", dataType = "MetooVersionClientVo")
    public Result save(@RequestBody @Validated MetooVersionClientVo versionClientVo) {
        metooVersionClientService.save(versionClientVo);
        return ResponseUtil.ok();
    }
    @GetMapping("/detail/{unitId}")
    @ApiOperation(value = "查询客户端版本详情", notes = "查询客户端版本详情")
    @ApiImplicitParam(name = "unitId", value = "客户端id", dataType = "Long")
    public Result detail(@PathVariable Long unitId) {
        return ResponseUtil.ok(metooVersionClientService.detail(unitId));
    }
    @DeleteMapping("/deleteById/{unitId}")
    @ApiOperation(value = "删除客户端版本", notes = "删除客户端版本")
    @ApiImplicitParam(name = "unitId", value = "客户端id", dataType = "Long")
    public Result deleteById(@PathVariable Long unitId) {
        metooVersionClientService.deleteById(unitId);
        return ResponseUtil.ok();
    }

    @PostMapping("/publish")
    @ApiOperation(value = "发布客户端版本", notes = "发布客户端版本")
    @ApiImplicitParam(name = "appVo", value = "参数", dataType = "MetooVersionClientAppVo")
    public Result publish(@RequestBody @Validated MetooVersionClientAppVo appVo) {
        metooVersionClientService.publish(appVo);
        return ResponseUtil.ok();
    }
    @PostMapping("/detectUpdate")
    @ApiOperation(value = "客户端版本检测更新", notes = "客户端版本检测更新")
    @ApiImplicitParam(name = "curVo", value = "参数", dataType = "MetooVersionClientVo")
    public Result detectUpdate(@RequestBody @Validated MetooVersionClientVo curVo) {
        return ResponseUtil.ok(metooVersionClientService.detectUpdate(curVo));
    }

}
