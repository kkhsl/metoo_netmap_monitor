package com.metoo.monitor.core.service;

import com.metoo.monitor.core.entity.MetooArea;
import com.metoo.monitor.core.vo.MetooAreaVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 区域表 服务类
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-18
 */
@Service
public interface IMetooAreaService {

    List<MetooArea> queryTree();

    /**
     * 根据区域信息获取下来的单位
     * @param areaId
     * @return
     */
    List<MetooAreaVo> queryUnitList(Long areaId);
}
