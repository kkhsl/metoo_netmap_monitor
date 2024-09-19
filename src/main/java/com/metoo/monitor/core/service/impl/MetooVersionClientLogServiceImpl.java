package com.metoo.monitor.core.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.metoo.monitor.core.entity.MetooVersionClientLog;
import com.metoo.monitor.core.mapper.MetooVersionClientLogMapper;
import com.metoo.monitor.core.service.IMetooVersionClientLogService;
import com.metoo.monitor.core.vo.version.MetooVersionClientLogQueryVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 版本发布记录表 服务实现类
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Service
@AllArgsConstructor
public class MetooVersionClientLogServiceImpl implements IMetooVersionClientLogService {
    private final MetooVersionClientLogMapper baseMapper;
    @Override
    public Page<MetooVersionClientLog> list(MetooVersionClientLogQueryVo logVo) {
        Page<MetooVersionClientLog> page = PageHelper.startPage(logVo.getCurrentPage(), logVo.getPageSize());
        this.baseMapper.list(logVo);
        return page;
    }
}
