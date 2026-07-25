package com.luyingdazi.api.service;

import com.aliyun.oss.OSS;
import com.luyingdazi.api.config.OssConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * 把数据库中保存的 OSS 永久地址转换为短期签名地址。
 *
 * <p>Bucket 保持私有，旧图片和新图片都可在小程序中安全访问。
 */
@Service
@RequiredArgsConstructor
public class OssUrlService {

    private static final long URL_TTL_MILLIS = 6 * 60 * 60 * 1000L;

    private final OSS ossClient;
    private final OssConfig ossConfig;

    public String toAccessibleUrl(String url) {
        if (url == null || url.isBlank()) {
            return url;
        }

        String prefix = ossConfig.getUrlPrefix();
        if (!url.startsWith(prefix)) {
            return url;
        }

        String objectKey = url.substring(prefix.length());
        int queryIndex = objectKey.indexOf('?');
        if (queryIndex >= 0) {
            objectKey = objectKey.substring(0, queryIndex);
        }
        objectKey = URLDecoder.decode(objectKey, StandardCharsets.UTF_8);
        if (objectKey.isBlank()) {
            return url;
        }

        Date expiration = new Date(System.currentTimeMillis() + URL_TTL_MILLIS);
        return ossClient.generatePresignedUrl(
                ossConfig.getBucketName(), objectKey, expiration).toString();
    }

    public List<String> toAccessibleUrls(List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return urls;
        }
        return urls.stream().map(this::toAccessibleUrl).toList();
    }
}
