package com.metoo.monitor.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.metoo.monitor.core.entity.MetooVersionClientLog;
import com.metoo.monitor.core.enums.VersionLogStatus;
import com.metoo.monitor.core.mapper.MetooVersionClientLogMapper;
import com.metoo.monitor.core.service.IMetooVersionClientLogService;
import com.metoo.monitor.core.vo.version.MetooVersionClientAppBatchVo;
import com.metoo.monitor.core.vo.version.MetooVersionClientLogQueryVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 保存日志
     * @param logEntity
     * @return
     */
    @Override
    public boolean saveLog(MetooVersionClientLog logEntity) {
        logEntity.setAddTime(DateUtil.date());
        // 默认为已发布状态
        logEntity.setVersionStatus(VersionLogStatus.PUBLISH.getCode());
        return this.baseMapper.save(logEntity)>0;
    }

    /**
     * 查询已发布的版本数据
     * @param unitId
     * @return
     */
    @Override
    public List<MetooVersionClientLog> queryUpdateVersion(Long unitId) {
        return this.baseMapper.queryUpdateVersion(unitId);
    }

    /***
     * 最近的版本
     * @param unitId
     * @return
     */
    @Override
    public MetooVersionClientLog lastVersion(Long unitId) {
        return this.baseMapper.lastInfo(unitId);
    }

    /**
     * 更新版本日志状态
     * @param logEntity
     * @return
     */
    @Override
    public boolean updateLogStatus(MetooVersionClientLog logEntity) {
        return this.baseMapper.updateLogStatus(logEntity)>0;
    }

    /**
     * 批量发布
     * @param batchVo
     * @return
     */
    @Override
    public boolean batchPublish(List<MetooVersionClientLog> batchVo) {
        return this.baseMapper.batchInsert(batchVo)>0;
    }

    /**
     * 批量删除之前已发布的版本
     * @param vo
     * @return
     */
    @Override
    public boolean batchUpdate(MetooVersionClientAppBatchVo vo) {
        return this.baseMapper.batchUpdate(vo.getUnitIds(),vo.getAppVersion())>0;
    }

    /**
     * 用于升级版本处理，更新低版本为更新失败，并备注忽略更新
     * @param unitId
     * @param versionId
     */
    @Override
    public void beforeInfoUpdate(Long unitId, Long versionId) {
        List<MetooVersionClientLog> infoList= this.baseMapper.beforeList(unitId,versionId);
        if(CollUtil.isNotEmpty(infoList)){
            infoList.forEach(o->{
                o.setVersionStatus(VersionLogStatus.CANCEL.getCode());
                o.setErrorInfo("忽略更新");
                this.baseMapper.updateLogStatus(o);
            });
        }
    }

    /**
     * 用于回退版本处理，更新其他版本为更新失败，并备注忽略更新
     * @param unitId
     * @param versionId
     */
    @Override
    public void  notVersionUpdate(Long unitId, Long versionId,String errorMsg) {
        List<MetooVersionClientLog> infoList=this.baseMapper.notVersionList(unitId,versionId);
        if(CollUtil.isNotEmpty(infoList)){
            infoList.forEach(o->{
                o.setVersionStatus(VersionLogStatus.CANCEL.getCode());
                o.setErrorInfo(errorMsg);
                this.baseMapper.updateLogStatus(o);
            });
        }
    }
}
