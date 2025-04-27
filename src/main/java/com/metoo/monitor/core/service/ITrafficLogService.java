package com.metoo.monitor.core.service;

import com.metoo.monitor.core.entity.GeneralLog;
import com.metoo.monitor.core.entity.TrafficLog;

public interface ITrafficLogService {

    boolean save(TrafficLog trafficLog);
}
