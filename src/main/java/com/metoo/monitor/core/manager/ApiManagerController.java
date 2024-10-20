package com.metoo.monitor.core.manager;

import com.alibaba.fastjson.JSONObject;
import com.metoo.monitor.core.entity.Accessory;
import com.metoo.monitor.core.entity.Application;
import com.metoo.monitor.core.service.IAccessoryService;
import com.metoo.monitor.core.service.IApplicationService;
import com.metoo.monitor.core.service.IMetooAreaService;
import com.metoo.monitor.core.service.IMetooVersionClientService;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.vo.MetooAreaSyncVo;
import com.metoo.monitor.core.vo.Result;
import com.metoo.monitor.core.vo.version.MetooVersionClientUpdateVo;
import com.metoo.monitor.core.vo.version.MetooVersionClientVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

@RequestMapping("/api/soft/version")
@Slf4j
@RestController
public class ApiManagerController {

    @Autowired
    private IApplicationService applicationService;
    @Autowired
    private IAccessoryService accessoryService;
    @Autowired
    private  IMetooVersionClientService metooVersionClientService;

    @Autowired
    private IMetooAreaService metooAreaService;
    @ApiOperation("版本信息")
    @GetMapping
    public String version(){
        Map map = new HashMap();
        map.put("code", 0);
        String protocol = "http";
        Integer port = 8930;
        String host = "127.0.0.1";
        String path = "/api/soft/version/download";

//        StringBuilder url = new StringBuilder();
//        url.append(protocol).append(host).append(port).append(path);
//        map.put("url", url.toString());
        map.put("url", "");
        try {
            URI uri = new URI(protocol, null, host, port, path, null, null);
            map.put("url", uri.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            map.put("code", -1);
        }

        map.put("version", "");
        try {
            Map params = new HashMap();
            params.put("orderBy", "addTime");
            params.put("orderType", "desc");
            List<Application> applicationList = this.applicationService.selectObjByMap(params);
            if(applicationList.size() > 0){
                map.put("version", applicationList.get(0).getVersion());
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
        }

        return JSONObject.toJSONString(map);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile() throws IOException {
        Map params = new HashMap();
        params.put("orderBy", "addTime");
        params.put("orderType", "desc");
        List<Application> applicationList = this.applicationService.selectObjByMap(params);
        if(applicationList.size() > 0){
            Application application = applicationList.get(0);
            if(application != null){
                Long accessoryId = application.getAccessoryId();
                if(accessoryId != null || accessoryId.equals("")){
                    Accessory accessory = this.accessoryService.selectObjById(accessoryId);
                    if(accessory != null){

                        // Load file as Resource
                        String FILE_DIRECTORY = accessory.getPath();
                        String fileName = accessory.getName();
                        Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
                        Resource resource = new UrlResource(filePath.toUri());

                        // Check if file exists
                        if (!resource.exists()) {
                            throw new RuntimeException("File not found: " + fileName);
                        }

                        // Set content disposition
                        String contentType = Files.probeContentType(filePath);
                        // 设置响应头
                        HttpHeaders headers = new HttpHeaders();

                        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=myfile.zip");
                        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

//                        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//                        headers.add(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);

                        return ResponseEntity.ok()
                                .headers(headers)
                                .body(resource);
                    }
                }
            }
        }

        return null;
    }


    @GetMapping("/download2")
    public ResponseEntity<Object> downloadFile(HttpServletRequest request) throws IOException {
        System.out.println(request.getScheme());
        Map params = new HashMap();
        params.put("orderBy", "addTime");
        params.put("orderType", "desc");
        List<Application> applicationList = this.applicationService.selectObjByMap(params);
        if(applicationList.size() > 0){
            Application application = applicationList.get(0);
            if(application != null){
                Long accessoryId = application.getAccessoryId();
                if(accessoryId != null || accessoryId.equals("")){
                    Accessory accessory = this.accessoryService.selectObjById(accessoryId);
                    if(accessory != null){
                        // Load file as Resource
                        String FILE_DIRECTORY = accessory.getPath();
                        String fileName = accessory.getName();
                        Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
                        Resource resource = new UrlResource(filePath.toUri());
                        // Determine file's content type
                        String contentType = Files.probeContentType(filePath);

                        // Set Content-Disposition header
                        HttpHeaders headers = new HttpHeaders();
                        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

                        // Set Content-Type header
                        headers.add(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);

                        // Return response entity with file as attachment
                        ResponseEntity<Object> responseEntity = ResponseEntity.ok()
                                .headers(headers)
                                .body(resource);
                        return responseEntity;

                    }
                }
            }
        }
//        Result result = new Result(-1,"下载失败");
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
        return null;
    }

    @ApiOperation("版本信息")
    @GetMapping("all")
    public String monitor(){
        Map map = new HashMap();
        map.put("code", 0);
        map.put("version", new ArrayList());
        try {
            Map params = new HashMap();
            params.put("orderBy", "addTime");
            params.put("orderType", "desc");
            List<Application> applicationList = this.applicationService.selectObjByMap(params);
            if(applicationList.size() > 0){
                map.put("version", applicationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
        }
        return JSONObject.toJSONString(map);
    }
    @PostMapping("/detectUpdate")
    @ApiOperation(value = "客户端版本检测更新", notes = "客户端版本检测更新")
    public Result detectUpdate(@RequestBody @Validated MetooVersionClientVo curVo) {
        return ResponseUtil.ok(metooVersionClientService.detectUpdate(curVo));
    }

    @PostMapping("/refreshUpdate")
    @ApiOperation(value = "客户端版本更新后同步更新到版本管理", notes = "客户端版本更新后同步更新到版本管理")
    public Result updateVersionFromClient(@RequestBody @Validated MetooVersionClientUpdateVo curVo) {
        try {
            metooVersionClientService.updateVersionFromClient(curVo);
            return ResponseUtil.ok();
        } catch (Exception e) {
            log.error("客户端版本更新后同步更新到版本管理出现问题：{}", e);
            return ResponseUtil.fail(e.getMessage());
        }
    }


    @GetMapping("/downloadVersion/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable(name = "id") String id) throws IOException {
        Application application = this.applicationService.selectObjById(Long.parseLong(id));
        if(application != null){
            Long accessoryId = application.getAccessoryId();
            if(accessoryId != null || accessoryId.equals("")){
                Accessory accessory = this.accessoryService.selectObjById(accessoryId);
                if(accessory != null){
                    // Load file as Resource
                    String FILE_DIRECTORY = accessory.getPath();
                    String fileName = accessory.getName();
                    Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
                    Resource resource = new UrlResource(filePath.toUri());

                    // Check if file exists
                    if (!resource.exists()) {
                        throw new RuntimeException("File not found: " + fileName);
                    }

                    // Set content disposition
                    String contentType = Files.probeContentType(filePath);
                    HttpHeaders headers = new HttpHeaders();
                    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
                    headers.add(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : MediaType.APPLICATION_OCTET_STREAM_VALUE);

                    return ResponseEntity.ok()
                            .headers(headers)
                            .body(resource);
                }
            }
        }
        return null;
    }

    @PostMapping("/syncArea")
    @ApiOperation(value = "同步新增区域信息-单个新增", notes = "同步新增-外部接口调用同步-单个新增")
    public Result syncSave(@RequestBody MetooAreaSyncVo areaInfo) {
        try {
            metooAreaService.syncSave(areaInfo);
            return ResponseUtil.ok();
        }catch (Exception ex){
            log.error("同步新增区域信息-单个新增出现错误：{}",ex);
            return ResponseUtil.fail(ex.getMessage());
        }
    }

    @PostMapping("/syncBatchArea")
    @ApiOperation(value = "同步新增区域信息-批量更新", notes = "同步新增-外部接口调用同步-批量更新")
    public Result syncBatchArea(@RequestBody List<MetooAreaSyncVo> areaInfos) {
        try {
            metooAreaService.syncBatchArea(areaInfos);
            return ResponseUtil.ok();
        }catch (Exception ex){
            log.error("同步新增区域信息-批量更新出现错误：{}",ex);
            return ResponseUtil.fail(ex.getMessage());
        }
    }
    @GetMapping("/syncUnit")
    @ApiOperation(value = "同步单位信息", notes = "同步单位信息")
    @ApiImplicitParam(name = "syncType", value = "同步类型：1表示全量，0表示增量", dataType = "String")
    public Result syncUnit(String syncType) {
        try {
            metooVersionClientService.syncUnit(syncType);
            return ResponseUtil.ok();
        } catch (Exception e) {
            log.error("同步单位信息出现问题：{}", e);
            return ResponseUtil.fail(e.getMessage());
        }
    }
}
