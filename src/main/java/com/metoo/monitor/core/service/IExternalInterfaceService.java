package com.metoo.monitor.core.service;

import com.metoo.monitor.core.vo.external.SurveyTimeVo;

/**
 * 外部接口
 */
public interface IExternalInterfaceService {
    /**
     * 发送下发测绘任务时间
     * @param params
     * @return
     */
    boolean sendSurveyTime(SurveyTimeVo params);

}
