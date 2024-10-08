package com.metoo.monitor.core.service.impl;

import com.metoo.monitor.core.entity.Accessory;
import com.metoo.monitor.core.mapper.AccessoryMapper;
import com.metoo.monitor.core.service.IAccessoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AccessoryServiceImpl implements IAccessoryService {

    @Autowired
    private AccessoryMapper accessoryMapper;

    @Override
    public Accessory selectObjById(Long id) {
        return this.accessoryMapper.selectObjById(id);
    }

    @Override
    public int save(Accessory instance) {
        instance.setAddTime(new Date());
        return this.accessoryMapper.save(instance);
    }

    @Override
    public int update(Accessory instance) {
        return this.accessoryMapper.update(instance);
    }

    @Override
    public int delete(Long id) {
        return this.accessoryMapper.delete(id);
    }

    @Override
    public List<Accessory> query(Map params) {
        return this.accessoryMapper.query(params);
    }

    @Override
    public List<Accessory> selectObjByMap(Map params) {
        return this.accessoryMapper.selectObjByMap(params);
    }
}
