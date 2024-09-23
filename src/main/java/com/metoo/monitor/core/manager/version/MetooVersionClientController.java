package com.metoo.monitor.core.manager.version;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.entity.MetooVersionClient;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@AllArgsConstructor
@RequestMapping("/monitor/versionClient")
public class MetooVersionClientController {

    private final IMetooVersionClientService metooVersionClientService;


    /**
     * 获取区域树
     */
    @ApiOperation(value = "获取区域树", notes = "获取区域树")
    @GetMapping("/tree")
    public Result tree() {
        return ResponseUtil.ok(metooVersionClientService.tree());
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询客户端版本管理列表-分页", notes = "查询客户端版本管理列表-分页")
    public Result list(@RequestBody MetooVersionClientQueryVo versionClientVo) {
        Page<MetooVersionClient> page = metooVersionClientService.list(versionClientVo);
        return ResponseUtil.ok(new ApiPageInfo<MetooVersionClient>(page));
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增客户端版本", notes = "新增客户端版本")
    public Result save(@RequestBody @Validated MetooVersionClientVo versionClientVo) {
        try {
            boolean success = metooVersionClientService.save(versionClientVo);
            if (success) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.error();
            }
        } catch (Exception e) {
            log.error("新增客户端版本出现问题：{}", e);
            return ResponseUtil.fail(e.getMessage());
        }
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
        try {
            boolean success = metooVersionClientService.deleteById(unitId);
            if (success) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail("删除客户端版本不成功");
            }
        } catch (Exception e) {
            log.error("删除客户端版本出现问题：{}", e);
            return ResponseUtil.fail(e.getMessage());
        }
    }

    @PostMapping("/publish")
    @ApiOperation(value = "发布客户端版本", notes = "发布客户端版本")
    public Result publish(@RequestBody @Validated MetooVersionClientAppVo appVo) {
        try {
            boolean success = metooVersionClientService.publish(appVo);
            if (success) {
                return ResponseUtil.ok();
            } else {
                return ResponseUtil.fail("发布客户端版本本不成功");
            }
        } catch (Exception e) {
            log.error("发布客户端版本出现问题：{}", e);
            return ResponseUtil.fail(e.getMessage());
        }
    }

}
