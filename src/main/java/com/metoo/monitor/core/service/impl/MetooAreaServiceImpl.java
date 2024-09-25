package com.metoo.monitor.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.metoo.monitor.core.entity.MetooArea;
import com.metoo.monitor.core.exception.BusiException;
import com.metoo.monitor.core.mapper.MetooAreaMapper;
import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.vo.MetooAreaSyncVo;
import com.metoo.monitor.core.vo.MetooAreaVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        // 单位信息同步
        boolean flag = StrUtil.isNotEmpty(areaInfo.getUnitId()) && StrUtil.isNotEmpty(areaInfo.getArea())
                && StrUtil.isNotEmpty(areaInfo.getUnit()) && StrUtil.isNotEmpty(areaInfo.getCity());
        if (flag) {
            MetooArea area = this.baseMapper.queryById(Long.parseLong(areaInfo.getUnitId()));
            // 先根据path路径查询所属的路径信息：city+area
            String path = areaInfo.getCity() + StrUtil.SLASH + areaInfo.getArea() + StrUtil.SLASH;
            List<MetooArea> findAreaList = this.baseMapper.queryAreaByPath(path);
            if (CollectionUtil.isNotEmpty(findAreaList)) {
                MetooArea saveEntity = Convert.convert(MetooArea.class, areaInfo);
                saveEntity.setName(areaInfo.getUnit());
                saveEntity.setParentId(findAreaList.get(0).getId());
                if (area == null) {
                    //不存在则新增
                    this.baseMapper.saveInfo(saveEntity);
                } else {
                    //存在则更新操作
                    this.baseMapper.updateInfo(saveEntity);
                }
            } else {
                // 目录信息保存
                Long parentId = saveParenInfo(areaInfo.getCity(), areaInfo.getArea());
                MetooArea saveAll = Convert.convert(MetooArea.class, areaInfo);
                saveAll.setName(areaInfo.getUnit());
                saveAll.setParentId(parentId);
                //新增叶子节点
                this.baseMapper.saveInfo(saveAll);
            }
        } else {
            // 目录信息
            saveParenInfo(areaInfo.getCity(), areaInfo.getArea());

        }
        return true;
    }

    /**
     * 保存目录结构信息
     *
     * @param city
     * @param area
     */
    public Long saveParenInfo(String city, String area) {
        Long parentId = 0L;
        if (StrUtil.isEmpty(area) && StrUtil.isNotEmpty(city)) {
            //根目录信息
            String path = city + StrUtil.SLASH;
            List<MetooArea> pathArea = this.baseMapper.queryAreaByPath(path);
            if (CollectionUtil.isEmpty(pathArea)) {
                // 不存在上级目录
                MetooArea topArea = new MetooArea();
                topArea.setName(city);
                this.baseMapper.saveInfo(topArea);
            }
        } else if (StrUtil.isNotEmpty(area) && StrUtil.isNotEmpty(city)) {
            // 先根据path路径查询所属的路径信息：city+area
            String parentPath = city + StrUtil.SLASH + area + StrUtil.SLASH;
            List<MetooArea> findAreaList = this.baseMapper.queryAreaByPath(parentPath);
            if (CollectionUtil.isEmpty(findAreaList)) {
                // 不存在目录,需要新增目录
                MetooArea parentArea = new MetooArea();
                parentArea.setName(area);
                String path = city + StrUtil.SLASH;
                List<MetooArea> pathArea = this.baseMapper.queryAreaByPath(path);
                if (CollectionUtil.isNotEmpty(pathArea)) {
                    // 存在上级目录
                    parentArea.setParentId(parentArea.getId());
                } else {
                    // 不存在上级目录
                    MetooArea topArea = new MetooArea();
                    topArea.setName(city);
                    this.baseMapper.saveInfo(topArea);
                    parentArea.setParentId(topArea.getId());
                }
                this.baseMapper.saveInfo(parentArea);
                parentId = parentArea.getId();
            }
        }
        return parentId;
    }

    /**
     * 批量保存接口
     *
     * @param areaInfos
     * @return
     */
    @Override
    public boolean syncBatchArea(List<MetooAreaSyncVo> areaInfos) {
        if (CollectionUtil.isNotEmpty(areaInfos)) {
            for (MetooAreaSyncVo areaInfo : areaInfos) {
                try {
                    syncSave(areaInfo);
                } catch (Exception e) {
                    log.error("批量保存失败：{}", JSON.toJSONString(areaInfo));
                }
            }
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
            if (null == itemDO.getParentId()) {
                return new TreeNode<Long>().setId(itemDO.getId())
                        .setName(itemDO.getName())
                        .setParentId(ROOT_ID)
                        .setExtra(map);
            } else {
                return new TreeNode<Long>().setId(itemDO.getId())
                        .setName(itemDO.getName())
                        .setParentId(itemDO.getParentId())
                        .setExtra(map);
            }
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
