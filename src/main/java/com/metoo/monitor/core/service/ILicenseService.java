package com.metoo.monitor.core.service;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.dto.LicenseDto;
import com.metoo.monitor.core.entity.License;

import java.util.List;
import java.util.Map;

/**
 * @author HKK
 * @version 1.0
 * @date 2024-05-29 15:21
 */
public interface ILicenseService {

    Page<License> selectObjConditionQuery(LicenseDto instance);

    List<License> selectObjByMap(Map params);

    boolean save(License instance);

    boolean delete(Long id);
}
