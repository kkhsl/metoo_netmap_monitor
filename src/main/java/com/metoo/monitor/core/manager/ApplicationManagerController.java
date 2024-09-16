package com.metoo.monitor.core.manager;

import com.github.pagehelper.Page;
import com.github.pagehelper.util.StringUtil;
import com.metoo.monitor.core.dto.ApplicationDTO;
import com.metoo.monitor.core.entity.Accessory;
import com.metoo.monitor.core.entity.Application;
import com.metoo.monitor.core.entity.Res;
import com.metoo.monitor.core.service.IAccessoryService;
import com.metoo.monitor.core.service.IApplicationService;
import com.metoo.monitor.core.utils.ByteSizeFormatter;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.utils.file.ApplicationUploadUtils;
import com.metoo.monitor.core.vo.PageInfo;
import com.metoo.monitor.core.vo.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/monitor/application")
@RestController
public class ApplicationManagerController {

    @Autowired
    private IApplicationService applicationService;
    @Autowired
    private IAccessoryService accessoryService;


    @PostMapping("/list")
    public Result list(@RequestBody ApplicationDTO dto){
        Page<Application> page = this.applicationService.selectObjConditionQuery(dto);
        if(page.getResult().size() > 0) {
            if(page.getResult().size() > 0){
                for (Application application : page.getResult()) {
                    if(application.getAccessoryId() != null){
                        Accessory accessory = this.accessoryService.selectObjById(application.getAccessoryId());
                        if(accessory != null){
                            String size = ByteSizeFormatter.formatBytes(accessory.getSize());
                            application.setSize(size);
                        }
                    }
                }
            }
            return ResponseUtil.ok(new PageInfo<Application>(page));
        }
        return ResponseUtil.ok();
    }

    @PostMapping("/list2")
    public Result list2(){
        Map params = new HashMap();
        params.put("orderBy", "addTime");
        params.put("orderType", "desc");
        List<Application> applicationList = this.applicationService.selectObjByMap(params);
        return ResponseUtil.ok(applicationList);
    }


    @ApiOperation("创建/更新")
    @PostMapping({"/save"})
    public Result save(@RequestBody Application instance) {
        if (StringUtil.isEmpty(instance.getVersion())) {
            return ResponseUtil.badArgument("版本号不能为空");
        }
        boolean flag = this.applicationService.save(instance);
        if(flag){
            return ResponseUtil.ok();
        }
        return ResponseUtil.error();
    }

//    @PostMapping("/upload")
    public Result handleFileUpload1(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseUtil.badArgument("请选择文件");
        }
        try {
            // 获取文件信息
            String originalFilename = file.getOriginalFilename();
            long fileSize = file.getSize();
            String fileExtension = getFileExtension(originalFilename);

            // 获取文件保存目录的绝对路径
            String uploadDir = Paths.get("").toAbsolutePath().toString()
                                        + File.separator
                                        + "files"
                                        + File.separator
                                        + System.currentTimeMillis();
            log.info(uploadDir);
            // 创建目录
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            String filePath = uploadDir + "/" + originalFilename;
            File dest = new File(filePath);
            file.transferTo(dest);

            Accessory accessory = new Accessory();
            accessory.setName(originalFilename);
            accessory.setExt(fileExtension);
            accessory.setSize(Long.valueOf(fileSize).intValue());
            accessory.setPath(uploadDir);
            this.accessoryService.save(accessory);
            Map result = new HashMap();
            result.put("accessoryId", accessory.getId());
            return ResponseUtil.ok("上传成功", result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseUtil.error("上传失败");
        }
    }

    @Autowired
    private ApplicationUploadUtils applicationUploadUtils;

    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        String uploadDir = ApplicationUploadUtils.getUploadDir();
        Result result = applicationUploadUtils.upload(file, uploadDir);
//        if(result.getCode() == 200){
//            Accessory accessory = ApplicationUploadUtils.recordFileInfo(file, uploadDir);
//            this.accessoryService.save(accessory);
//        }
        return result;
    }


    /**
     * 即支持直接下载，又支持传参
     * @param file
     * @return
     */
    @PostMapping("/upload/version/2")
    public Result handleFileUploadVersion2(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseUtil.badArgument("请选择文件");
        }
        try {
            // 获取文件信息
            String originalFilename = file.getOriginalFilename();
            long fileSize = file.getSize();
            String fileExtension = getFileExtension(originalFilename);

            // 获取文件保存目录的绝对路径
            String uploadDir = Paths.get("").toAbsolutePath().toString()
                    + File.separator
                    + "files"
                    + File.separator
                    + System.currentTimeMillis();
            log.info(uploadDir);
            // 创建目录
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            String filePath = uploadDir + "/" + originalFilename;
            File dest = new File(filePath);
            file.transferTo(dest);

            Accessory accessory = new Accessory();
            accessory.setName(originalFilename);
            accessory.setExt(fileExtension);
            accessory.setSize(Long.valueOf(fileSize).intValue());
            accessory.setPath(uploadDir);
            this.accessoryService.save(accessory);
            Map result = new HashMap();
            result.put("accessoryId", accessory.getId());
            return ResponseUtil.ok("上传成功", result);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseUtil.error("上传失败");
        }
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    @GetMapping("/download/{id}")
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

//
//    @GetMapping("/download")
//    public Object downloadFile(HttpServletResponse response) throws IOException {
//        Map params = new HashMap();
//        params.put("orderBy", "addTime");
//        params.put("orderType", "desc");
//        List<Application> applicationList = this.applicationService.selectObjByMap(params);
//        if(applicationList.size() > 0){
//            Application application = applicationList.get(0);
//            if(application != null){
//                Long accessoryId = application.getAccessoryId();
//                if(accessoryId != null || accessoryId.equals("")){
//                    Accessory accessory = this.accessoryService.selectObjById(accessoryId);
//                    if(accessory != null){
//                        String FILE_DIRECTORY = accessory.getPath();
//                        String fileName = accessory.getName();
//                        Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
//                        Resource resource = new UrlResource(filePath.toUri());
//                        try {
//                            boolean flag = this.download(resource.getInputStream(), fileName, response);
//                            if(flag){
//                                return ResponseUtil.ok("下载成功");
//                            }
//                            return ResponseUtil.error("下载失败");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return ResponseUtil.error("下载失败");
//                        }
//                    }
//                }
//            }
//        }
//        return ResponseUtil.badArgument("资源不存在");
//    }



    @GetMapping("/download")
    public ResponseEntity<Object> downloadFile(HttpServletResponse response) throws IOException {
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
                        // Simulate some JSON response after file download
                        // You can modify this part as per your actual JSON response structure
//                        Result result = new Result(0,"下载成功");
//                        return ResponseEntity.status(HttpStatus.OK)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .body(result);
                    }
                }
            }
        }
        Result result = new Result(-1,"下载失败");
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(result);
    }

    public static boolean download(InputStream in, String fileName, HttpServletResponse response) {
        try {
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            return true;
        } catch (IOException var9) {
            return false;
        }
    }

    @DeleteMapping({"/delete"})
    public Result delete(@RequestParam(required = false, value = "id") String id,
                         @RequestParam(required = false, value = "ids") String[] ids) {
        if(ids != null && ids.length > 0){
            for (String arg : ids) {
                boolean flag = this.applicationService.delete(Long.parseLong(arg));
                if(!flag){
                    return ResponseUtil.badArgument("删除失败");
                }
            }
        }else  if(id != null && !id.equals("")){
            boolean flag = this.applicationService.delete(Long.parseLong(id));
            if(flag){
                return ResponseUtil.ok();
            }else{
                return ResponseUtil.badArgument();
            }
        }
        return ResponseUtil.badArgument();
    }

}
