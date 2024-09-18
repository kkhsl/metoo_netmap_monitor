package com.metoo.monitor.core.service;

import cn.hutool.core.lang.tree.Tree;
import com.github.pagehelper.Page;
import com.metoo.monitor.core.entity.version.MetooVersionClient;
import com.metoo.monitor.core.vo.version.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 客户端版本管理 服务类
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Service
public interface IMetooVersionClientService  {
    /**
     * 区域目录树
     * @return
     */
    List<Tree<Long>> tree();

    /**
     * 分页查询客户端
     * @param versionClientVo
     * @return
     */
    Page<MetooVersionClient> list(MetooVersionClientQueryVo versionClientVo);

    /**
     * 新增客户端版本管理
     * @param versionClientVo
     * @return
     */
    boolean save(MetooVersionClientVo versionClientVo);

    /**
     * 查询客户端版本详情
     * @param unitId
     * @return
     */
    MetooVersionClientDetailVo detail(Long unitId);

    /**
     * 发布指定版本
     * @param appVo
     * @return
     */
    boolean publish(MetooVersionClientAppVo appVo);

    /**
     * 检查更新
     * @param curVo
     * @return
     */
    List<MetooVersionClientAppUpdateVo> detectUpdate(MetooVersionClientVo curVo);

    /**
     * 删除客户端版本详情
     * @param unitId
     * @return
     */
    boolean deleteById(Long unitId);
}
