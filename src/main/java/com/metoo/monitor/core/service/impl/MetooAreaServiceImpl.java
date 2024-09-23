package com.metoo.monitor.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.metoo.monitor.core.entity.MetooArea;
import com.metoo.monitor.core.exception.BusiException;
import com.metoo.monitor.core.mapper.MetooAreaMapper;
import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.vo.MetooAreaSyncVo;
import com.metoo.monitor.core.vo.MetooAreaVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 区域表 服务实现类
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-18
 */
@Service
@Slf4j
@AllArgsConstructor
public class MetooAreaServiceImpl implements IMetooAreaService {

    private final MetooAreaMapper baseMapper;

    /**
     * 树的根ID
     **/
    private final static Long ROOT_ID = 0L;

    @Override
    public List<MetooArea> queryTree() {
        return this.baseMapper.queryList();
    }

    @Override
    public List<MetooAreaVo> queryUnitList(Long areaId) {
        return this.baseMapper.queryUnitList(areaId);
    }

    @Override
    public MetooArea queryById(Long areaId) {
        return this.baseMapper.queryById(areaId);
    }

    @Override
    public List<Tree<Long>> allTree() {
        //查询区域列表
        List<MetooArea> areaList = this.baseMapper.queryAll();

        //组装成树形结构
        List<Tree<Long>> treeList = beanConvertTreeNode(areaList);

        if (CollectionUtil.isEmpty(treeList)) {
            throw new BusiException("未初始化区域信息");
        }
        return treeList;
    }

    /**
     * 保存单位信息-同步接口
     *
     * @param areaInfo
     * @return
     */
    @Override
    public boolean syncSave(MetooAreaSyncVo areaInfo) {
        // 判断区域编码是否存在
        if (areaInfo.getId() != null) {
            MetooArea area = this.baseMapper.queryById(areaInfo.getId());
            if (area == null) {
                //不存在则新增
                return this.baseMapper.saveInfo(Convert.convert(MetooArea.class, areaInfo)) > 0;
            } else {
                //存在则更新操作
                return this.baseMapper.updateInfo(Convert.convert(MetooArea.class, areaInfo)) > 0;

            }
        } else {
            throw new BusiException("同步参数不正确");
        }
    }

    /**
     * 批量保存接口
     * @param areaInfos
     * @return
     */
    @Override
    public boolean syncBatchArea(List<MetooAreaSyncVo> areaInfos) {
        if(CollectionUtil.isNotEmpty(areaInfos)){
            areaInfos.forEach(this::syncSave);
        }
        return true;
    }

    /**
     * 区域实体转树节点列表
     *
     * @param areaList
     * @return
     */
    private List<Tree<Long>> beanConvertTreeNode(List<MetooArea> areaList) {
        List<TreeNode<Long>> collect = areaList.stream().map(itemDO -> {
            Map<String, Object> map = new HashMap<>(8);
            TreeNode<Long> treeNode = new TreeNode<Long>().setId(itemDO.getId())
                    .setName(itemDO.getName())
                    .setParentId(itemDO.getParentId() == null ? ROOT_ID : itemDO.getParentId())
                    .setExtra(map);
            return treeNode;
        }).collect(Collectors.toList());
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setChildrenKey("children");
        //转换器
        List<Tree<Long>> treeNodes = TreeUtil.build(collect, ROOT_ID, treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getName());
                });
        return treeNodes;
    }
}
