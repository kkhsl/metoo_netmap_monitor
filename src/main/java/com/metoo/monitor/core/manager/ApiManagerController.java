package com.metoo.monitor.core.manager;

import com.alibaba.fastjson.JSONObject;
import com.metoo.monitor.core.entity.Accessory;
import com.metoo.monitor.core.entity.Application;
import com.metoo.monitor.core.service.IAccessoryService;
import com.metoo.monitor.core.service.IApplicationService;
import com.metoo.monitor.core.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class ApiManagerController {

    @Autowired
    private IApplicationService applicationService;
    @Autowired
    private IAccessoryService accessoryService;

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

}
