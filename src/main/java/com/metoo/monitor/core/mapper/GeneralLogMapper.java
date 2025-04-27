package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.entity.GeneralLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GeneralLogMapper {

    int save(GeneralLog generalLog);
}
