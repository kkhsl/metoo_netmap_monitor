package com.metoo.monitor.core.manager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.metoo.monitor.core.dto.LicenseDto;
import com.metoo.monitor.core.entity.Accessory;
import com.metoo.monitor.core.entity.Application;
import com.metoo.monitor.core.entity.License;
import com.metoo.monitor.core.service.IAccessoryService;
import com.metoo.monitor.core.service.IApplicationService;
import com.metoo.monitor.core.service.ILicenseService;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.ApiPageInfo;
import com.metoo.monitor.core.vo.PageInfo;
import com.metoo.monitor.core.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

@RequestMapping("/api/license")
@RestController
public class ApiLicenseManagerController {


    @Autowired
    private ILicenseService licenseService;

    @PostMapping("/list")
    public Object list(@RequestBody(required=false) LicenseDto dto){
        if(dto == null){
            dto = new LicenseDto();
        }
        Page<License> page = this.licenseService.selectObjConditionQuery(dto);
        if(page.getResult().size() > 0){
            return ResponseUtil.ok(new ApiPageInfo<License>(page));
        }
        return  ResponseUtil.ok();
    }
}
