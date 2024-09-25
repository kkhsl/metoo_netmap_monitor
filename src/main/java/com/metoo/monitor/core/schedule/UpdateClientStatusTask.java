package com.metoo.monitor.core.schedule;

import cn.hutool.core.date.DateTime;
import com.metoo.monitor.core.entity.MetooVersionClient;
import com.metoo.monitor.core.enums.ClientStatus;
import com.metoo.monitor.core.service.IMetooVersionClientService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

/**
 * 定时任务更新客户端状态
 *
 * @author zzy
 * @version 1.0
 * @date 2024/9/20 17:02
 */
@Component
@Slf4j
public class UpdateClientStatusTask {
    @Autowired
    private IMetooVersionClientService versionClientService;

    /**
     * 更新客户端状态时任务
     */
    @Scheduled(fixedRate=60*60*1000)
    public void UpdateClientStatusTask() {
        log.info("====================================更新客户端状态时任务开始执行==========================");
        // 查询所有的客户端数据
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateTime.now());
        // 将时间减少一个小时
        calendar.add(Calendar.HOUR, -1);
        List<MetooVersionClient> allClients = versionClientService.queryAllList();
        if (CollectionUtils.isNotEmpty(allClients)) {
            allClients.forEach(client -> {
                if (null != client.getUpdateTime() && client.getUpdateTime().after(calendar.getTime())) {
                    //最近更新时间在12小时之内，说明客户端在线了
                    versionClientService.updateClientStatus(client.getUnitId(), ClientStatus.ONLINE.getCode());
                } else {
                    // 更新客户端状态为离线
                    versionClientService.updateClientStatus(client.getUnitId(), ClientStatus.OFFLINE.getCode());
                }
            });
        }
        log.info("====================================更新客户端状态定时任务结束==========================");
    }


}
