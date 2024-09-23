package com.metoo.monitor.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application extends IdEntity{

    private String version;

    private String desc;

    private String size;

    private Long accessoryId;
    /**
     * 版本类型 0表示增量版本，1表示全量版本
     */
    private Integer versionType;

}
