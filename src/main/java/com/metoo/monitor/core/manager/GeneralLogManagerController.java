package com.metoo.monitor.core.manager;

import com.metoo.monitor.core.entity.Accessory;
import com.metoo.monitor.core.entity.GeneralLog;
import com.metoo.monitor.core.service.IGeneralLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequestMapping("/api/general/log")
@RestController
public class GeneralLogManagerController {

    @Autowired
    private IGeneralLogService generalLogService;

    @ApiOperation("版本信息")
    @GetMapping("/data/{data}")
    public String version(@PathVariable String data){
        GeneralLog log = new GeneralLog();
        log.setAddTime(new Date());
        log.setData(data);
        boolean i = this.generalLogService.save(log);
        return "ok";
    }
}
