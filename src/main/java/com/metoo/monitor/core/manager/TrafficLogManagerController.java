package com.metoo.monitor.core.manager;

import com.metoo.monitor.core.entity.Accessory;
import com.metoo.monitor.core.entity.GeneralLog;
import com.metoo.monitor.core.entity.TrafficLog;
import com.metoo.monitor.core.service.IAccessoryService;
import com.metoo.monitor.core.service.IGeneralLogService;
import com.metoo.monitor.core.service.ITrafficLogService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RequestMapping("/api/nrsm/traffic")
@RestController
public class TrafficLogManagerController {

    @Autowired
    private ITrafficLogService trafficLogService;

    @ApiOperation("版本信息")
    @GetMapping("/data/{traffic}")
    public String version(@PathVariable String traffic){

//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        TrafficLog trafficLog = new TrafficLog();
        trafficLog.setAddTime(new Date());
        trafficLog.setData(traffic);
        boolean i = this.trafficLogService.save(trafficLog);
        return "ok";
    }

}
