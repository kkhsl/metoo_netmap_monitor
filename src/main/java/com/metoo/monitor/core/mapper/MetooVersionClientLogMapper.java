package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.entity.MetooVersionClientLog;
import com.metoo.monitor.core.vo.version.MetooVersionClientLogQueryVo;
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
    boolean save(MetooVersionClientLog log);
}
