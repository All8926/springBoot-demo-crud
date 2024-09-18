package com.example.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Slf4j
@Component
public class COSUtils {
    @Value("${tencent.cos.secretId}")
    private String secretId;
    @Value("${tencent.cos.secretKey}")
    private String secretKey;
    @Value("${tencent.cos.bucketName}")
    private String bucketName;
    private String folder = "/test2";
    @Value("${tencent.cos.region}")
    private String region;


    private  COSClient createCli() {
        log.info("secretId:{}", secretId);
        // 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 设置bucket的区域, COS地域的简称请参照 https://www.qcloud.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 生成cos客户端
        return new COSClient(cred, clientConfig);
    }


    public URL putInputStreamDemo(MultipartFile file) throws IOException {
        COSClient cosClient = createCli();

        String originalFilename = file.getOriginalFilename();
        String key = folder + UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        log.info("ket:{}", key);

        InputStream inputStream = file.getInputStream();

        ObjectMetadata objectMetadata = new ObjectMetadata();
        // 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
        // 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
        objectMetadata.setContentLength(file.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
        // 设置单链接限速（如有需要），不需要可忽略
//        putObjectRequest.setTrafficLimit(8*1024*1024);
        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult.getRequestId());
            URL url = cosClient.getObjectUrl(bucketName, key);
            log.info("url: {}", url);
            return url;
        } catch (CosServiceException e) {
            e.printStackTrace();
            return null;
        } catch (CosClientException e) {
            e.printStackTrace();
            return null;
        } finally {
            cosClient.shutdown();

        }
    }


}
