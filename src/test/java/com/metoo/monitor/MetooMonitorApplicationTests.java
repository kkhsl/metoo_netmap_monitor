package com.metoo.monitor;

import com.metoo.monitor.core.utils.file.UploadVersion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@SpringBootTest
class MetooMonitorApplicationTests {

    @Test
    void contextLoads() {
    }



    @Test
    public void testUpload_validFile() throws IOException {
//        // 创建一个模拟的 MultipartFile 对象
//          MultipartFile mockFile = Mockito.mock(MultipartFile.class);
//
//        // 配置模拟对象的行为
//        Mockito.when(mockFile.getOriginalFilename()).thenReturn("testfile.txt");

        // 创建模拟 MultipartFile 对象
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);

        // 配置模拟的文件名
        Mockito.when(mockFile.getOriginalFilename()).thenReturn("update/.zip");

        // 配置模拟的文件内容
        Mockito.when(mockFile.getInputStream()).thenReturn(new ByteArrayInputStream("file content".getBytes()));

        // 配置模拟的文件大小
        Mockito.when(mockFile.getSize()).thenReturn(1002L); // 文件大小为 12 字节

        // 使用模拟对象进行测试
        String filename = mockFile.getOriginalFilename();

        System.out.println(filename);

        boolean flag = UploadVersion.upload(mockFile);
        System.out.println(flag);
//
//        // 调用要测试的方法
//        boolean result = FileUploadService.upload(file);
//
//        // 验证结果
//        assertTrue(result);
//        verify(file).getOriginalFilename();
//        verify(file).getSize();
    }

}
