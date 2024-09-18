package com.metoo.monitor.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.metoo.monitor.core.entity.MetooArea;
import com.metoo.monitor.core.mapper.MetooAreaMapper;
import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.vo.MetooAreaVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 区域表 服务实现类
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-18
 */
@Service
@AllArgsConstructor
public class MetooAreaServiceImpl implements IMetooAreaService {

    private final MetooAreaMapper baseMapper;
    @Override
    public List<MetooArea> queryTree() {
        return this.baseMapper.queryList();
    }

    @Override
    public List<MetooAreaVo> queryUnitList(Long areaId) {
        return this.baseMapper.queryUnitList(areaId);
    }
}
