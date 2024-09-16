package com.metoo.monitor.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.metoo.monitor.core.dto.ApplicationDTO;
import com.metoo.monitor.core.entity.Application;
import com.metoo.monitor.core.mapper.ApplicationMapper;
import com.metoo.monitor.core.service.IApplicationService;
import com.metoo.monitor.core.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {

    @Resource
    private ApplicationMapper applicationMapper;

    @Override
    public Application selectObjById(Long id) {
        return this.applicationMapper.selectObjById(id);
    }

    @Override
    public List<Application> selectObjByMap(Map params) {
        return this.applicationMapper.selectObjByMap(params);
    }

    @Override
    public Page<Application> selectObjConditionQuery(ApplicationDTO instance) {
        if(instance == null){
            instance = new ApplicationDTO();
        }
        Page<Application> page = PageHelper.startPage(instance.getCurrentPage(), instance.getPageSize());
        this.applicationMapper.selectObjConditionQuery(instance);
        return page;
    }

    @Override
    public boolean save(Application instance) {
        if(instance.getId() == null || instance.getId().equals("")){
            instance.setAddTime(new Date());
            try {
                this.applicationMapper.save(instance);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else{
            try {
                this.applicationMapper.update(instance);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            this.applicationMapper.delete(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
