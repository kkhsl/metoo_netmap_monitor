package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.entity.GeneralLog;
import com.metoo.monitor.core.entity.TrafficLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TrafficLogMapper {

    int save(TrafficLog trafficLog);
}
