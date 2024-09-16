package com.metoo.monitor.core.manager;

import com.alibaba.fastjson.JSONObject;
import com.metoo.monitor.core.entity.Accessory;
import com.metoo.monitor.core.entity.Application;
import com.metoo.monitor.core.service.IAccessoryService;
import com.metoo.monitor.core.service.IApplicationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/nrsm/traffic")
@RestController
public class AccessoryManagerController {

    @Autowired
    private IAccessoryService accessoryService;

    @ApiOperation("版本信息")
    @GetMapping("/data/{traffic}")
    public String version(@PathVariable String traffic){
        Accessory accessory = new Accessory();
        accessory.setName(traffic);
        int i = this.accessoryService.save(accessory);
        return "ok";
    }

}
