package com.poorpaper.v7center;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class V7CenterApplicationTests {

    @Autowired
    private FastFileStorageClient client;

    @Test
    void contextLoads() {
    }

    @Test
    public void uploadTest() throws FileNotFoundException {
        File file = new File("H:\\zhx95\\DUTS-TE\\DUTS-TE-Image\\ILSVRC2012_test_00000034.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        StorePath storePath = client.uploadImageAndCrtThumbImage(fileInputStream, file.length(), "jpg", null);
        System.out.println(storePath.getPath());
        System.out.println(storePath.getFullPath());
        System.out.println(storePath.getGroup());
    }

    @Test
    public void deleteTest() {
        client.deleteFile("/group1/M00/00/00/wKg4gWKcykmAWKXJAAB0o1iajGE405.jpg");
        System.out.println("ok");
    }
}
