package com.rocky.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.rocky.dto.FileInfo;
import com.rocky.dto.UploadInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/upload")
public class UploadController {
    @Value("${upload.path}")
    private String uploadPath ;

    @Value("${upload.url}")
    private String uploadUrl;

    @PostMapping
    public String upload(@RequestBody UploadInfo uploadInfo){
        String name = uploadInfo.getName();
        name = System.currentTimeMillis() + "_" + name;
        String base64 = uploadInfo.getBase64();
        String[] strArray = StrUtil.splitToArray(base64, "base64,");
        byte[] bytes = Base64.decode(strArray[1]);
        String path = uploadPath + name;
        FileUtil.writeBytes(bytes, path);

        return  uploadUrl + "images/" + name;
    }

    @GetMapping
    public List<FileInfo> upload(){
        List<String> fileList = FileUtil.listFileNames(uploadPath);
        List<FileInfo> fileInfos = fileList.stream().map(s -> {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setName(s);
            fileInfo.setUrl(uploadUrl + "images/" + s);
            return fileInfo;
        }).collect(Collectors.toList());
        return  fileInfos;
    }
}
