package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.dto.ApplicationDTO;
import com.metoo.monitor.core.entity.Application;
import com.metoo.monitor.core.vo.Result;

import java.util.List;
import java.util.Map;

public interface ApplicationMapper {

    Application selectObjById(Long id);

    List<Application> selectObjByMap(Map params);

    List<Application>  selectObjConditionQuery(ApplicationDTO dto);

    int save(Application instance);

    int update(Application instance);

    int delete(Long id);

    List<Application> queryList(String version);
}
