package com.metoo.monitor.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.metoo.monitor.core.entity.MetooArea;
import com.metoo.monitor.core.entity.version.MetooVersionClient;
import com.metoo.monitor.core.exception.BusiException;
import com.metoo.monitor.core.mapper.version.MetooVersionClientMapper;
import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.service.IMetooVersionClientService;
import com.metoo.monitor.core.vo.version.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 客户端版本管理 服务实现类
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Service
@AllArgsConstructor
public class MetooVersionClientServiceImpl  implements IMetooVersionClientService {

    private final IMetooAreaService areaService;
    private final MetooVersionClientMapper baseMapper;

    /**
     * 树的根ID
     **/
    private final static Long ROOT_ID = 0L;

    @Override
    public List<Tree<Long>> tree() {
        //查询区域列表
        List<MetooArea> areaList = areaService.queryTree();

        //组装成树形结构
        List<Tree<Long>> treeList = beanConvertTreeNode(areaList);

        if (CollectionUtil.isEmpty(treeList)) {
            throw new BusiException("未初始化区域信息");
        }
        return treeList;
    }

    /**
     * 分页查询客户端版本信息
     * @param versionClientVo
     * @return
     */
    @Override
    public Page<MetooVersionClient> list(MetooVersionClientQueryVo versionClientVo) {
        Page<MetooVersionClient> page = PageHelper.startPage(versionClientVo.getCurrentPage(), versionClientVo.getPageSize());
        this.baseMapper.list(versionClientVo);
        return page;
    }

    /**
     * 保存客户端版本
     * @param versionClientVo
     * @return
     */
    @Override
    public boolean save(MetooVersionClientVo versionClientVo) {
        // TODO: 2024/9/18 判断客户端是否存在
        // 保存客户端信息
        MetooVersionClient saveEntity= Convert.convert(MetooVersionClient.class,versionClientVo);
        saveEntity.setClientStatus(0);
        return this.baseMapper.save(saveEntity);
    }

    @Override
    public MetooVersionClientDetailVo detail(Long unitId)
    {
        return null;
    }

    @Override
    public boolean publish(MetooVersionClientAppVo appVo) {
        return false;
    }

    @Override
    public List<MetooVersionClientAppUpdateVo> detectUpdate(MetooVersionClientVo curVo) {
        return null;
    }

    /**
     * 删除客户端版本
     * @param unitId
     * @return
     */
    @Override
    public boolean deleteById(Long unitId) {
        return this.baseMapper.deleteById(unitId);
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
