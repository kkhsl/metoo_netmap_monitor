package com.metoo.monitor.core.manager;

import com.metoo.monitor.core.utils.file.UploadVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RequestMapping("/test")
@RestController
public class TestConstroller {

    @GetMapping("getUserDir")
   public void getUserDir(){
       String rootDirectory = System.getProperty("user.dir");
       Path uploadPath = Paths.get(rootDirectory, "uploads");
       log.info("打印项目根目录: UploadPath {}", uploadPath.toString());

       String workingDirectory = Paths.get("").toAbsolutePath().toString();
       log.info("打印项目根目录: workingDirectory {}", workingDirectory);

       ApplicationHome home = new ApplicationHome(UploadVersion.class);
       File jarDir = home.getSource().getParentFile();
       log.info("打印项目根目录: workingDirectory {}", jarDir.getAbsolutePath());
   }

   @PostMapping("/getInfo")
   public String getFileInfo(@RequestParam("file") MultipartFile file){
       // 获取文件名
       String fileName = file.getOriginalFilename();

       // 获取文件大小
       long fileSize = file.getSize();

       // 获取文件内容类型
       String contentType = file.getContentType();

       // 输出文件信息
       System.out.println("File Name: " + fileName);
       System.out.println("File Size: " + fileSize + " bytes");
       System.out.println("Content Type: " + contentType);

       // 你可以在这里处理文件，如保存到磁盘
       // file.transferTo(new File("path/to/save/" + fileName));
       return "File uploaded successfully!";
   }

}
