package com.metoo.monitor.core.schedule;

import com.metoo.monitor.core.service.IMetooVersionClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务更新客户端区域信息
 *
 * @author zzy
 * @version 1.0
 * @date 2024/10/18 15:02
 */
@Component
@Slf4j
public class SyncClientUnitInfoTask {
    @Autowired
    private IMetooVersionClientService versionClientService;
    @Value("${client.unit.sync.switch}")
    private boolean clientUnitSyncSwitch;

    /**
     * 更新客户端状态时任务
     */
    @Scheduled(fixedRate = 8 * 60 * 60 * 1000)
    public void SyncClientUnitInfoTask() {
        if (clientUnitSyncSwitch) {
            log.info("====================================定时任务更新客户端区域信息任务开始执行==========================");
            try {
                versionClientService.syncUnit("0");
            } catch (Exception ex) {
                log.error("定时任务更新客户端区域信息任务出现错误：{}", ex);
            }
            log.info("====================================定时任务更新客户端区域信息任务结束==========================");
        }
    }


}
