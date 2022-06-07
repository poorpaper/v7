package com.poorpaper.v7center.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.poorpaper.commons.pojo.ResultBean;
import com.poorpaper.v7center.pojo.WangEditorResultBean;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 处理文件相关操作
 */

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FastFileStorageClient client;

    @Value("${image.server}")
    private String imageServer;


    @PostMapping("upload")
    public ResultBean<String> upload(MultipartFile file) {
        // 获取文件后缀 **.**
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        // 使用client上传到fastDFS
        try {
            StorePath storePath = client.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), extName, null);
//            String fullPath = storePath.getFullPath();
            StringBuilder stringBuilder = new StringBuilder(imageServer).append(storePath.getFullPath());
//            System.out.println(stringBuilder.toString());
            return new ResultBean<>("200", stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            //TODO 把日志框架整合进来
            return new ResultBean<>("500", "当前服务器繁忙，请稍后再试");
        }
    }

    @PostMapping("batchUpload")
    public WangEditorResultBean batchUpload(MultipartFile[] files) {
        String[] data = new String[files.length];
        try {
            for (int i = 0; i < files.length; i++) {
                String originalFilename = files[i].getOriginalFilename();
                String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                StorePath storePath = client.uploadImageAndCrtThumbImage(files[i].getInputStream(), files[i].getSize(), extName, null);
                StringBuilder stringBuilder = new StringBuilder(imageServer).append(storePath.getFullPath());
                data[i] = stringBuilder.toString();
            }
            return new WangEditorResultBean("0", data);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO 把日志框架整合进来
            return new WangEditorResultBean("1", null);
        }
    }
}
