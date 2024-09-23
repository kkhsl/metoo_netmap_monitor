package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.dto.ApplicationDTO;
import com.metoo.monitor.core.entity.Application;
import com.metoo.monitor.core.vo.Result;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询版本升级列表（跨版本情况）
     * @param startId
     * @param endId
     * @return
     */
    List<Application> queryUpdateVersions(@Param("startId") Long startId,@Param("endId") Long endId);
    /**
     * 根据版本号查询版本信息
     * @param version
     * @return
     */
    Application queryVersionByName(String version);
}
