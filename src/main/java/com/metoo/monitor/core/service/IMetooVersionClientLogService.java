package com.metoo.monitor.core.service;

import com.github.pagehelper.Page;
import com.metoo.monitor.core.entity.version.MetooVersionClientLog;
import com.metoo.monitor.core.vo.version.MetooVersionClientLogQueryVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 版本发布记录表 服务类
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Service
public interface IMetooVersionClientLogService  {
    /**
     * 分页查询版本发布日志
     * @param logVo
     * @return
     */
    Page<MetooVersionClientLog> list(MetooVersionClientLogQueryVo logVo);
}
