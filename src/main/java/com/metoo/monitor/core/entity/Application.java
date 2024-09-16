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

}
