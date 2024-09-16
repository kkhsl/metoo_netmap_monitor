package com.metoo.monitor.core.mapper;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.dto.LicenseDto;
import com.metoo.monitor.core.entity.License;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author HKK
 * @version 1.0
 * @date 2024-05-29 15:24
 */
@Mapper
public interface LicenseMapper {

    List<License> selectObjConditionQuery(LicenseDto instance);

    List<License> selectObjByMap(Map params);

    int save(License instance);

    int delete(Long id);
}
