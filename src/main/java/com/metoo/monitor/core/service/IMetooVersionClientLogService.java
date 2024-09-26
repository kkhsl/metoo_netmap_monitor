package com.metoo.monitor.core.service;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.entity.MetooVersionClientLog;
import com.metoo.monitor.core.vo.version.MetooVersionClientAppBatchVo;
import com.metoo.monitor.core.vo.version.MetooVersionClientLogQueryVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 版本发布记录表 服务类
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
public interface IMetooVersionClientLogService  {
    /**
     * 分页查询版本发布日志
     * @param logVo
     * @return
     */
    Page<MetooVersionClientLog> list(MetooVersionClientLogQueryVo logVo);

    /**
     * 保存版本日志
     * @param logEntity
     * @return
     */
    boolean saveLog(MetooVersionClientLog logEntity);

    /**
     * 查询已发布的版本数据
     * @param unitId
     * @return
     */
    List<MetooVersionClientLog> queryUpdateVersion(Long unitId);

    /**
     * 最新成功的版本
     * @param unitId
     * @return
     */
    MetooVersionClientLog lastVersion(Long unitId);

    /**
     * 更新状态
     * @param logEntity
     * @return
     */
    boolean updateLogStatus(MetooVersionClientLog logEntity);

    /**
     * 批量发布
     * @param batchVos
     * @return
     */
    boolean batchPublish(List<MetooVersionClientLog> batchVos);

    /**
     * 批量删除之前已发布的版本日志
     * @param vo
     * @return
     */
    boolean batchUpdate(MetooVersionClientAppBatchVo vo);
}
