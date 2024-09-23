package com.metoo.monitor.core.service;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.dto.ApplicationDTO;
import com.metoo.monitor.core.entity.Application;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IApplicationService {

    Application selectObjById(Long id);

    List<Application> selectObjByMap(Map params);

    Page<Application> selectObjConditionQuery(ApplicationDTO dto);

    boolean save(Application instance);

    boolean delete(Long id);

    /**
     * 根据版本名称查询版本列表
     * @param version
     * @return
     */
    List<Application> queryList(String version);

    /**
     * 查询版本升级列表（跨版本情况）
     * @param startId
     * @param endId
     * @return
     */
    List<Application> queryUpdateVersions( Long startId,Long endId);

    /**
     * 根据版本号查询版本信息
     * @param version
     * @return
     */
    Application queryVersionByName(String version);

}
