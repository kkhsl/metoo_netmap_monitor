package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.entity.MetooVersionClientLog;
import com.metoo.monitor.core.vo.version.MetooVersionClientLogQueryVo;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 版本发布记录表 Mapper 接口
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Mapper
public interface MetooVersionClientLogMapper  {
    /**
     * 获取发布日志记录列表
     * @param logVo
     * @return
     */
    List<MetooVersionClientLog> list(MetooVersionClientLogQueryVo logVo);

    /**
     * 保存数据
     * @param log
     * @return
     */
    int save(MetooVersionClientLog log);

    /**
     * 查询已发布数据
     * @param unitId
     * @return
     */
    List<MetooVersionClientLog> queryUpdateVersion(Long unitId);

    /**
     * 获取最新的版本信息
     * @param unitId
     * @return
     */
    MetooVersionClientLog lastInfo(Long unitId);

    /**
     * 更新发布状态
     * @param log
     * @return
     */
    int updateLogStatus(MetooVersionClientLog log);

    /**
     * 批量发布
     * @param logs
     * @return
     */
    int batchInsert(List<MetooVersionClientLog> logs);

    /**
     * 批量更新
     * @param ids
     * @param version
     * @return
     */
    int batchUpdate(@Param("ids") List<Long> ids,@Param("version") String version);
}
