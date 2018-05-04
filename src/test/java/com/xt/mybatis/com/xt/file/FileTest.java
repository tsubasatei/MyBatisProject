package com.xt.mybatis.com.xt.file;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;

import java.io.File;

/**
 * Created with xt.
 * Date: 2018/5/4
 * Time: 15:39
 * Description: 测试类上传文件
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileTest {

    @Value("${web.upload-path}")
    private String path;

    /** 文件上传测试 */
    @Test
    public void uploadTest() throws Exception {
        File f = new File("D:/pic.png");
        FileCopyUtils.copy(f, new File(path+"/1.png"));
    }

    /**遍历此路径下的所有文件*/
    @Test
    public void listFilesTest() {
        File file = new File(path);
        for(File f : file.listFiles()) {
            System.out.println("fileName : "+f.getName());
        }
    }
}
