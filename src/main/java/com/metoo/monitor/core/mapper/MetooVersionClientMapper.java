package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.entity.MetooVersionClient;
import com.metoo.monitor.core.vo.version.MetooVersionClientQueryVo;
import org.apache.ibatis.annotations.Param;
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
    int saveInfo(MetooVersionClient saveEntity);

    /**
     * 删除客户端版本
     * @param unitId
     * @return
     */
    int deleteById(Long unitId);

    /**
     * 获取详情
     * @param unitId
     * @return
     */
    MetooVersionClient detailById(Long unitId);

    /**
     * 更新信息
     * @param updateInfo
     * @return
     */
    int updateAppInfoAndStatus(MetooVersionClient updateInfo);

    /**
     * 客户端更新当前版本状态
     * @param updateInfo
     * @return
     */
    int updateAppInfoAndStatusFromClient(MetooVersionClient updateInfo);

    /**
     * 查询所有客户端列表
     * @return
     */
    List<MetooVersionClient> queryAllList();

    /**
     * 更新客户端状态
     * @param unitId
     * @return
     */
    int updateClientStatus(@Param("unitId")Long unitId, @Param( "clientStatus")Integer clientStatus);

    /**
     * 更新客户端版本信息-名称和区域编码
     * @param updateInfo
     * @return
     */
    int updateClientByNameAndAreaId(MetooVersionClient updateInfo);
}
