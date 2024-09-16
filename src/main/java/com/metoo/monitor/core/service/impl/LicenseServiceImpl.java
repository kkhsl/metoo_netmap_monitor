package com.metoo.monitor.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.metoo.monitor.core.dto.LicenseDto;
import com.metoo.monitor.core.entity.License;
import com.metoo.monitor.core.mapper.LicenseMapper;
import com.metoo.monitor.core.service.ILicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author HKK
 * @version 1.0
 * @date 2024-05-29 15:24
 */
@Service
@Transactional
public class LicenseServiceImpl implements ILicenseService {

    @Autowired
    private LicenseMapper licenseMapper;

    @Override
    public Page<License> selectObjConditionQuery(LicenseDto instance) {
        if(instance == null){
            instance = new LicenseDto();
        }
        Page<License> page = PageHelper.startPage(instance.getCurrentPage(), instance.getPageSize());
        this.licenseMapper.selectObjConditionQuery(instance);
        return page;
    }

    @Override
    public List<License> selectObjByMap(Map params) {
        return this.licenseMapper.selectObjByMap(params);
    }

    @Override
    public boolean save(License instance) {
        try {
            instance.setAddTime(new Date());
            this.licenseMapper.save(instance);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            this.licenseMapper.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
