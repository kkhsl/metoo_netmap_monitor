package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.entity.MetooVersionClient;
import com.metoo.monitor.core.vo.version.MetooVersionClientQueryVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 客户端版本管理 Mapper 接口
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-17
 */
@Mapper
public interface MetooVersionClientMapper {
    /**
     * 分页查询
     * @param queryVo
     * @return
     */
    List<MetooVersionClient> queryPagelist(MetooVersionClientQueryVo queryVo);

    /**
     * 保存数据
     * @param saveEntity
     * @return
     */
    boolean saveInfo(MetooVersionClient saveEntity);

    /**
     * 删除客户端版本
     * @param unitId
     * @return
     */
    boolean deleteById(Long unitId);

    /**
     * 获取详情
     * @param unitId
     * @return
     */
    MetooVersionClient detailById(Long unitId);
}
