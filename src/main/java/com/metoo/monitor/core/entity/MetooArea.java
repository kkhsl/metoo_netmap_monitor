package com.metoo.monitor.core.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 区域表
 * </p>
 *
 * @author codeGenerator
 * @since 2024-09-18
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("区域类")
public class MetooArea   {

    private static final long serialVersionUID = 1L;
    /**
     * 区域编码
     */
    private Long id;
    /**
     * 区域名称
     */
    private String name;

    /**
     * 父级节点
     */
    private Long parentId;

    /**
     * 单位id
     */
    private Long unitId;


}
