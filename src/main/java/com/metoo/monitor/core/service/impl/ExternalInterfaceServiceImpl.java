package com.metoo.monitor.core.service.impl;

import cn.hutool.core.date.DateUtil;
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
        MetooVersionClient clientInfo= clientMapper.detailById(params.getUnitId());
        if(null==clientInfo){
            throw new BusiException("单位信息不存在");
        }
        return this.clientMapper.updateClientSurveyTime(params.getUnitId(), DateUtil.parseDateTime(params.getSurveyTime())) > 0;
    }
}
