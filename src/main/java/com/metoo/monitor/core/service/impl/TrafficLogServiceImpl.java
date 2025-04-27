package com.metoo.monitor.core.service.impl;

import com.metoo.monitor.core.entity.GeneralLog;
import com.metoo.monitor.core.entity.TrafficLog;
import com.metoo.monitor.core.mapper.GeneralLogMapper;
import com.metoo.monitor.core.mapper.TrafficLogMapper;
import com.metoo.monitor.core.service.IGeneralLogService;
import com.metoo.monitor.core.service.ITrafficLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class TrafficLogServiceImpl implements ITrafficLogService {

    @Resource
    private TrafficLogMapper trafficLogMapper;

    @Override
    public boolean save(TrafficLog trafficLog) {

        try {
            this.trafficLogMapper.save(trafficLog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
