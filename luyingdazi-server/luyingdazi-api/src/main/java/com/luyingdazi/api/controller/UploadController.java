package com.luyingdazi.api.controller;

import com.aliyun.oss.OSS;
import com.luyingdazi.api.config.OssConfig;
import com.luyingdazi.common.exception.BizException;
import com.luyingdazi.common.result.Result;
import com.luyingdazi.common.result.ResultCode;
import com.luyingdazi.common.util.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 文件上传接口
 *
 * @author luyingdazi
 */
@Slf4j
@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final OSS ossClient;
    private final OssConfig ossConfig;

    /** 允许的图片类型 */
    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg", "image/png", "image/gif", "image/webp");

    /** 最大文件大小 5MB */
    private static final long MAX_SIZE = 5 * 1024 * 1024;

    /**
     * 上传单张图片
     */
    @PostMapping("/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        validateFile(file);
        String url = doUpload(file);
        return Result.success(url);
    }

    /**
     * 批量上传图片（最多9张）
     */
    @PostMapping("/images")
    public Result<List<String>> uploadImages(@RequestParam("files") MultipartFile[] files) {
        if (files.length > 9) {
            throw new BizException("最多上传9张图片");
        }

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            validateFile(file);
            urls.add(doUpload(file));
        }
        return Result.success(urls);
    }

    // ==================== 私有方法 ====================

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BizException("文件不能为空");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new BizException("文件大小不能超过5MB");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new BizException("只支持 JPG/PNG/GIF/WEBP 格式");
        }
    }

    private String doUpload(MultipartFile file) {
        try {
            // 生成文件路径: images/2026/06/20/uuid.jpg
            String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String suffix = getFileSuffix(file.getOriginalFilename());
            String objectKey = "images/" + datePath + "/" + UUID.randomUUID().toString().replace("-", "") + suffix;

            InputStream inputStream = file.getInputStream();
            ossClient.putObject(ossConfig.getBucketName(), objectKey, inputStream);

            // 数据库存永久地址；读取接口会按需生成短期签名 URL。
            String url = ossConfig.getUrlPrefix() + objectKey;
            log.info("用户{}上传图片: {}", UserContext.getUserId(), url);
            return url;
        } catch (Exception e) {
            log.error("OSS上传失败", e);
            throw new BizException(ResultCode.OSS_UPLOAD_FAIL);
        }
    }

    private String getFileSuffix(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ".jpg";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
