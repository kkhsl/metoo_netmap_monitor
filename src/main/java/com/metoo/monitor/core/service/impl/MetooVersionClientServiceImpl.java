package com.metoo.monitor.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.metoo.monitor.core.entity.*;
import com.metoo.monitor.core.enums.ClientStatus;
import com.metoo.monitor.core.enums.VersionLogStatus;
import com.metoo.monitor.core.enums.VersionStatus;
import com.metoo.monitor.core.enums.VersionType;
import com.metoo.monitor.core.exception.BusiException;
import com.metoo.monitor.core.mapper.MetooVersionClientMapper;
import com.metoo.monitor.core.service.IApplicationService;
import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.service.IMetooVersionClientLogService;
import com.metoo.monitor.core.service.IMetooVersionClientService;
import com.metoo.monitor.core.utils.ShiroUserHolder;
import com.metoo.monitor.core.utils.VersionUtils;
import com.metoo.monitor.core.vo.version.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
@Slf4j
public class MetooVersionClientServiceImpl implements IMetooVersionClientService {

    private final IMetooAreaService areaService;
    private final MetooVersionClientMapper clientMapper;
    private final IApplicationService applicationService;
    private final IMetooVersionClientLogService clientLogService;
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
     *
     * @param versionClientVo
     * @return
     */
    @Override
    public Page<MetooVersionClient> list(MetooVersionClientQueryVo versionClientVo) {
        Page<MetooVersionClient> page = PageHelper.startPage(versionClientVo.getCurrentPage(), versionClientVo.getPageSize());
        clientMapper.queryPagelist(versionClientVo);
        return page;
    }

    /**
     * 保存客户端版本
     *
     * @param versionClientVo
     * @return
     */
    @Override
    public boolean save(MetooVersionClientVo versionClientVo) {
        if (null == versionClientVo.getUnitId()) {
            throw new BusiException("单位编码不能为空");
        }
        // 判断客户端是否存在
        MetooVersionClient oldInfo = clientMapper.detailById(versionClientVo.getUnitId());
        if (null != oldInfo) {
            throw new BusiException("客户端已存在");
        }
        // 当前用用户信息
        User currentUser = ShiroUserHolder.currentUser();
        // 保存客户端信息
        MetooVersionClient saveEntity = Convert.convert(MetooVersionClient.class, versionClientVo);
        saveEntity.setVersionStatus(VersionStatus.NORMAL.getCode());
        saveEntity.setClientStatus(ClientStatus.ONLINE.getCode());
        saveEntity.setAddTime(DateUtil.date());
        //saveEntity.setCreateBy(currentUser.getId());
        //saveEntity.setCreateName(currentUser.getUsername());
        return clientMapper.saveInfo(saveEntity) > 0;
    }

    @Override
    public MetooVersionClientDetailVo detail(Long unitId) {
        MetooVersionClient client = clientMapper.detailById(unitId);
        //  当前版本说明
        Application curVersion = applicationService.selectObjById(client.getCurVersionId());

        MetooVersionClientDetailVo result = Convert.convert(MetooVersionClientDetailVo.class, client);
        // 版本说明
        if (curVersion != null) {
            result.setVersionRemark(curVersion.getDesc());
        }
        // 区域名称
        MetooArea areaInfo = areaService.queryById(client.getAreaId());
        if (null != areaInfo) {
            result.setAreaName(areaInfo.getName());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publish(MetooVersionClientAppVo appVo) {
        if (null == appVo.getUnitId()) {
            throw new BusiException("单位编码不能为空");
        }
        if (null == appVo.getAppVersionId()) {
            throw new BusiException("指定版本不能为空");
        }
        // 发布逻辑
        Application application = applicationService.selectObjById(appVo.getAppVersionId());
        // 如果是增量版本，则需判断版本号是不是大于最新的版本号
        if (null != application && VersionType.ADD.getCode().equals(application.getVersion())) {
            // 获取最新的版本信息
            MetooVersionClientLog lastInfo = clientLogService.lastVersion(appVo.getUnitId());
            if (VersionLogStatus.PUBLISH.getCode().equals(lastInfo.getVersionStatus()) ||
                    VersionLogStatus.UPLOAD.getCode().equals(lastInfo.getVersionStatus())) {
                throw new BusiException("当前存在待升级版本");
            }

        }
        // 如果是全量版本，则直接发布
        // 当前用用户信息
        User currentUser = ShiroUserHolder.currentUser();
        // 新增版本记录，为待发布状态
        MetooVersionClientLog logEntity = MetooVersionClientLog.builder().build();
        logEntity.setUnitId(appVo.getUnitId());
        logEntity.setVersionId(appVo.getAppVersionId());
        logEntity.setVersion(appVo.getAppVersion());
        //logEntity.setOpId(currentUser.getId());
        //logEntity.setOpName(currentUser.getUsername());
        clientLogService.saveLog(logEntity);
        //更新客户段版本记录为未完成状态、指定版本信息
        MetooVersionClient updateInfo = Convert.convert(MetooVersionClient.class, appVo);
        updateInfo.setVersionStatus(VersionStatus.ABNORMAL.getCode());
        return clientMapper.updateAppInfoAndStatus(updateInfo) > 0;
    }

    /**
     * 批量发布
     *
     * @param appVos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchPublish(MetooVersionClientAppBatchVo appVos) {
        if (CollectionUtil.isEmpty(appVos.getUnitIds())) {
            throw new BusiException("单位编码不能为空");
        }
        if (null == appVos.getAppVersionId()) {
            throw new BusiException("指定版本不能为空");
        }
        // 当前用用户信息
        User currentUser = ShiroUserHolder.currentUser();
        // 全网发布前端未传版本号的情况
        if (StrUtil.isEmpty(appVos.getAppVersion())) {
            //根据版本号获取版本编码
            Application application = applicationService.selectObjById(appVos.getAppVersionId());
            if (null != application) {
                appVos.setAppVersion(application.getVersion());
            }
        }
        // 新增版本记录，为待发布状态
        List<MetooVersionClientLog> logs = CollectionUtil.newArrayList();
        appVos.getUnitIds().forEach(o -> {
            MetooVersionClientLog logEntity = MetooVersionClientLog.builder().build();
            logEntity.setUnitId(o);
            logEntity.setVersionId(appVos.getAppVersionId());
            logEntity.setVersion(appVos.getAppVersion());
            // logEntity.setOpId(currentUser.getId());
            // logEntity.setOpName(currentUser.getUsername());
            logs.add(logEntity);
            //更新客户段版本记录为未完成状态、指定版本信息
            MetooVersionClient updateInfo = Convert.convert(MetooVersionClient.class, logEntity);
            updateInfo.setAppVersionId(appVos.getAppVersionId());
            updateInfo.setAppVersion(appVos.getAppVersion());
            updateInfo.setVersionStatus(VersionStatus.ABNORMAL.getCode());
            clientMapper.updateAppInfoAndStatus(updateInfo);
        });
        //批量删除已发布的版本信息
        clientLogService.batchUpdate(appVos);
        // 批量插入新版本信息
        clientLogService.batchPublish(logs);
        return true;
    }

    /**
     * 检测更新逻辑
     *
     * @param curVo
     * @return
     */
    @Override
    public MetooVersionClientAppUpdateVo detectUpdate(MetooVersionClientVo curVo) {
        if (StrUtil.isNotEmpty(curVo.getCurVersion())) {
            //根据版本号获取版本编码
            Application application = applicationService.queryVersionByName(curVo.getCurVersion());
            if (null != application) {
                curVo.setCurVersionId(application.getId());
            } else {
                log.error("当前版本信息不存在：{}", curVo.getCurVersion());
                //默认当前版本id为1
                curVo.setCurVersionId(1L);
            }
        }
        // 每次检测时，需更新版本信息及最新更新时间
        ThreadUtil.execAsync(() -> {
            updateVersionAndTime(curVo.getCurVersionId(), curVo.getCurVersion(), curVo.getUnitId());
        });
        // 检测更新逻辑
        // 查询当前客户端是否存在已发布的版本数据
        List<MetooVersionClientLog> versionList = clientLogService.queryUpdateVersion(curVo.getUnitId());
        if (CollectionUtil.isNotEmpty(versionList)) {
            MetooVersionClientLog lastInfo = versionList.get(0);
            // 版本是否存在跨版本的情况，如果是增量版本需要一个个升级操作
            List<Application> appList = applicationService.queryUpdateVersions(curVo.getCurVersionId(), lastInfo.getVersionId());
            if (CollectionUtil.isNotEmpty(appList)) {
                if (appList.size() == 1) {
                    // 只有一个版本升级
                    return MetooVersionClientAppUpdateVo.builder().unitId(curVo.getUnitId()).appVersionId(appList.get(0).getId()).appVersion(appList.get(0).getVersion()).build();
                } else {
                    //多个版本情况，需倒序升级
                    Application app = appList.get(0);
                    if (VersionType.ALL.getCode().equals(app.getVersionType())) {
                        // 如果最新为全量版本则只更新当前版本
                        return MetooVersionClientAppUpdateVo.builder().unitId(curVo.getUnitId()).appVersionId(app.getId()).appVersion(app.getVersion()).build();
                    } else {
                        //多个版本情况，需倒序升级
                        return getLastVersionByUpdate(appList, curVo.getCurVersion(), curVo.getUnitId());
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取倒序去最新的版本
     *
     * @param appList
     * @param curVersion
     * @return
     */
    private MetooVersionClientAppUpdateVo getLastVersionByUpdate(List<Application> appList, String curVersion, Long unitId) {
        for (int i = appList.size() - 1; i >= 0; i--) {
            Application tempApp = appList.get(i);
            if (!curVersion.equals(tempApp.getVersion())) {
                // 和当前版本不一致，才推送
                return MetooVersionClientAppUpdateVo.builder().unitId(unitId).appVersionId(tempApp.getId()).appVersion(tempApp.getVersion()).build();
            }
        }
        return null;
    }

    /**
     * 删除客户端版本
     *
     * @param unitId
     * @return
     */
    @Override
    public boolean deleteById(Long unitId) {
        if (null == unitId) {
            throw new BusiException("单位编码不能为空");
        }
        // 是否能删除逻辑判断
        MetooVersionClient info = clientMapper.detailById(unitId);
        //只有离线状态可以删除
        if (null == info) {
            throw new BusiException("客户端信息不存在");
        }
        if (info.getClientStatus().equals(ClientStatus.ONLINE.getCode())) {
            throw new BusiException("当前客户端状态不允许删除");
        }
        return clientMapper.deleteById(unitId) > 0;
    }

    /**
     * 更新客户端版本后更新状态
     *
     * @param clientVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateVersionFromClient(MetooVersionClientUpdateVo clientVo) {
        if (StrUtil.isNotEmpty(clientVo.getCurVersion())) {
            //根据版本号获取版本编码
            Application application = applicationService.queryVersionByName(clientVo.getCurVersion());
            if (null != application) {
                clientVo.setCurVersionId(application.getId());
            } else {
                log.error("当前版本信息不存在：{}", clientVo.getCurVersion());
            }
        }
        MetooVersionClient updateInfo = Convert.convert(MetooVersionClient.class, clientVo);
        MetooVersionClientLog logEntity = Convert.convert(MetooVersionClientLog.class, clientVo);
        // 根据单位id查询现在版本信息
        MetooVersionClient lastInfo = clientMapper.detailById(clientVo.getUnitId());
        // 更加当前版本号更新
        logEntity.setVersion(clientVo.getCurVersion());
        if (null != lastInfo) {
            if (null == lastInfo.getAppVersion()) {
                //默认状态
                //升级成功
                logEntity.setVersionStatus(VersionLogStatus.FINISH.getCode());
                updateInfo.setVersionStatus(VersionStatus.NORMAL.getCode());
            } else {
                // 存在升级版本
                if (!lastInfo.getAppVersion().equals(clientVo.getCurVersion())) {
                    updateInfo.setVersionStatus(VersionStatus.ABNORMAL.getCode());
                    //升级失败
                    logEntity.setVersionStatus(VersionLogStatus.ERROR.getCode());
                } else {
                    updateInfo.setVersionStatus(VersionStatus.NORMAL.getCode());
                    //升级成功
                    logEntity.setVersionStatus(VersionLogStatus.FINISH.getCode());
                }
            }
            clientMapper.updateAppInfoAndStatusFromClient(updateInfo);
        }
        //更新日志状态
        clientLogService.updateLogStatus(logEntity);
        return true;
    }

    /**
     * 查询客户端状态
     *
     * @return
     */
    @Override
    public List<MetooVersionClient> queryAllList() {
        return this.clientMapper.queryAllList();
    }

    /**
     * 更新客户端状态
     *
     * @return
     */
    @Override
    public boolean updateClientStatus(Long unitId, Integer clientStatus) {
        return this.clientMapper.updateClientStatus(unitId, clientStatus) > 0;
    }

    /**
     * 全网发布客户端版本
     *
     * @param allVo
     */
    @Override
    public void allPublish(MetooVersionClientAppBatchVo allVo) {
        if (null == allVo.getAppVersionId()) {
            throw new BusiException("指定版本不能为空");
        }
        ThreadUtil.execAsync(() -> {
            //获取所有单位信息
            List<MetooVersionClient> allList = this.queryAllList();
            List<List<MetooVersionClient>> newList = Lists.partition(allList, 150);
            for (List<MetooVersionClient> subList : newList) {
                allVo.setUnitIds(subList.stream().map(MetooVersionClient::getUnitId).collect(Collectors.toList()));
                try {
                    this.batchPublish(allVo);
                } catch (Exception ex) {
                    log.error("全网发布客户端版本出错：{}", ex);
                }
            }
        });

    }

    /**
     * 同步单位信息
     * 1表示：全量，0或null:表示增量
     *
     * @param syncType
     */
    @Override
    public void syncUnit(String syncType) {
        Map params = new HashMap();
        params.put("orderBy", "addTime");
        params.put("orderType", "asc");
        List<Application> applicationList = this.applicationService.selectObjByMap(params);
        Long id = 1L;
        String version = "1.0.0";
        if (CollUtil.isNotEmpty(applicationList)) {
            // 默认第一个
            version = applicationList.get(0).getVersion();
            id = applicationList.get(0).getId();
        }
        List<MetooArea> allAreaList = new ArrayList<>();
        // 全量同步
        if (StrUtil.isNotEmpty(syncType) && ("1").equals(syncType)) {
            // 获取所有的区域
            allAreaList = areaService.queryUpdateArea(null, null);

        } else {
            // 增量更新，取当前时间前一天间隔
            allAreaList = areaService.queryUpdateArea(DateUtil.offsetDay(new Date(), -1).toString(), DateUtil.now());

        }
        // 分5个列表
        List<List<MetooArea>> splitLists = VersionUtils.splitList(allAreaList, 5);
        for (int i = 0; i < splitLists.size(); i++) {
            int finalI = i;
            String finalVersion = version;
            Long finalId = id;
            ThreadUtil.execAsync(() -> {
                // 扫描执行
                this.updateOrInitVersionInfo(splitLists.get(finalI), finalId, finalVersion);
            });
        }
    }

    /**
     * 更新或新增版本信息
     *
     * @param areaList
     */
    public void updateOrInitVersionInfo(List<MetooArea> areaList, Long id, String version) {
        if (CollUtil.isNotEmpty(areaList)) {
            areaList.forEach(temp -> {
                try {
                    // 先去客户端版本管理查询单位是否存在
                    MetooVersionClient client = this.clientMapper.detailById(temp.getUnitId());
                    if (null == client) {
                        //如果不存在，则进行新增操作
                        MetooVersionClientVo saveEntity = Convert.convert(MetooVersionClientVo.class, temp);
                        saveEntity.setUnitName(temp.getName());
                        saveEntity.setAreaId(temp.getId());
                        saveEntity.setCurVersion(version);
                        saveEntity.setCurVersionId(id);
                        this.save(saveEntity);
                    } else {
                        //更新区域名称及区域编码
                        client.setAreaId(temp.getId());
                        client.setUnitName(temp.getName());
                        this.clientMapper.updateClientByNameAndAreaId(client);
                    }
                }catch (Exception ex){
                    log.error("更新或新增版本信息出错了：{}",ex);
                }
            });
        }
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

    /**
     * 更新客户端版本信息到服务器（每隔一个小时）
     *
     * @param curVersion
     * @param unitId
     */
    public void updateVersionAndTime(Long curVersionId, String curVersion, Long unitId) {
        MetooVersionClient updateInfo = new MetooVersionClient();
        updateInfo.setUnitId(unitId);
        updateInfo.setCurVersionId(curVersionId);
        updateInfo.setCurVersion(curVersion);
        // 根据单位id查询现在版本信息
        MetooVersionClient lastInfo = clientMapper.detailById(unitId);
        if (null != lastInfo) {
            if (null == lastInfo.getAppVersion()) {
                //默认状态
                updateInfo.setVersionStatus(VersionStatus.NORMAL.getCode());
            } else {
                // 存在升级版本
                if (!lastInfo.getAppVersion().equals(curVersion)) {
                    updateInfo.setVersionStatus(VersionStatus.ABNORMAL.getCode());
                } else {
                    updateInfo.setVersionStatus(VersionStatus.NORMAL.getCode());
                }
            }
            clientMapper.updateAppInfoAndStatusFromClient(updateInfo);
        }
    }
}
