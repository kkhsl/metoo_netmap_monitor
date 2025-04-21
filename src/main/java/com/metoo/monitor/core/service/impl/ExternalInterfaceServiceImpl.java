package com.metoo.monitor.core.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.metoo.monitor.core.entity.MetooVersionClient;
import com.metoo.monitor.core.exception.BusiException;
import com.metoo.monitor.core.mapper.MetooVersionClientMapper;
import com.metoo.monitor.core.service.IExternalInterfaceService;
import com.metoo.monitor.core.vo.external.SurveyTimeVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 外部接口实现类
 * @author zzy
 * @version 1.0
 * @date 2025/4/16 16:40
 */
@Service
@AllArgsConstructor
@Slf4j
public class ExternalInterfaceServiceImpl implements IExternalInterfaceService {
    private final MetooVersionClientMapper clientMapper;
    /**
     * 更新客户端测绘时间
     * @param params
     * @return
     */
    @Override
    public boolean sendSurveyTime(SurveyTimeVo params) {
        if (null == params || CollUtil.isEmpty(params.getUnitId())) {
            throw new BusiException("单位编码不能为空");
        }
        if (StrUtil.isEmpty(params.getSurveyTime())) {
            throw new BusiException("测绘时间不能为空");
        }
        if (CollUtil.isNotEmpty(params.getUnitId())) {
            params.getUnitId().forEach(o -> {
                MetooVersionClient clientInfo = clientMapper.detailById(o);
                if (null == clientInfo) {
                    log.error("{}单位信息不存在",o);
                }
                this.clientMapper.updateClientSurveyTime(o, DateUtil.parseDateTime(params.getSurveyTime()));
            });
        }
        return true;
    }
}
