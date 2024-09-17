package com.example.controller;

import com.example.pojo.Result;
import com.example.utils.COSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.net.URL;

@RestController
@Slf4j
public class UploadController {

    @Autowired
    private COSUtils cosUtils;

    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("image: {}",image);
        URL url = cosUtils.putInputStreamDemo(image);
        return Result.success(url);
    }
}
