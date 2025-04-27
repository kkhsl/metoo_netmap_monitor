package com.metoo.monitor.core.service.impl;

import com.metoo.monitor.core.entity.GeneralLog;
import com.metoo.monitor.core.mapper.GeneralLogMapper;
import com.metoo.monitor.core.service.IGeneralLogService;
import com.sun.tools.javah.Gen;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GeneralLogServiceImpl implements IGeneralLogService {

    @Resource
    private GeneralLogMapper generalLogMapper;

    @Override
    public boolean save(GeneralLog generalLog) {

        try {
            this.generalLogMapper.save(generalLog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
