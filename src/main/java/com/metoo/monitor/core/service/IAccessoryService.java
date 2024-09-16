package com.metoo.monitor.core.service;

import com.metoo.monitor.core.entity.Accessory;

import java.util.List;
import java.util.Map;

public interface IAccessoryService {

    Accessory selectObjById(Long id);

    int save(Accessory instance);

    int update(Accessory instance);

    int delete(Long id);

    List<Accessory> query(Map params);

    List<Accessory> selectObjByMap(Map params);
}
