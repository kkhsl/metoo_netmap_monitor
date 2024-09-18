package com.metoo.monitor.core.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MetooArea implements Serializable {

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
