package com.metoo.monitor.core.service;

import cn.hutool.core.lang.tree.Tree;
import com.metoo.monitor.core.entity.MetooArea;
import com.metoo.monitor.core.vo.MetooAreaSyncVo;
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
public interface IMetooAreaService {

    List<MetooArea> queryTree();

    /**
     * 根据区域信息获取下来的单位
     * @param areaId
     * @return
     */
    List<MetooAreaVo> queryUnitList(Long areaId);

    /**
     * 查询区域名称
     * @param areaId
     * @return
     */
    MetooArea queryById(Long areaId);

    /**
     * 所有节点树
     * @return
     */
    List<Tree<Long>> allTree();

    /**
     * 同步保存接口
     * @param areaInfo
     * @return
     */
    boolean syncSave(MetooAreaSyncVo areaInfo);
}
