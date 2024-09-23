package com.metoo.monitor.core.mapper;

import com.metoo.monitor.core.entity.MetooArea;
import com.metoo.monitor.core.vo.MetooAreaVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * <p>
 * 区域表 Mapper 接口
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-18
 */
@Mapper
public interface MetooAreaMapper {
    /**
     * 查询区域目录
     * @return
     */
    List<MetooArea> queryList();

    /**
     * 根据区域编码获取单位列表
     * @param areaId
     * @return
     */
    List<MetooAreaVo> queryUnitList(Long areaId);

    /**
     * 查询区域信息
     * @param areaId
     * @return
     */
    MetooArea queryById(Long areaId);
    /**
     * 查询所有数据
     * @return
     */
    List<MetooArea> queryAll();

    /**
     * 保存区域信息
     * @param vo
     * @return
     */
    int saveInfo(MetooArea vo);

    /**
     * 更新区域信息
     * @param vo
     * @return
     */
    int updateInfo(MetooArea vo);
}
