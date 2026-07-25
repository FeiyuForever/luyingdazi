package com.luyingdazi.api.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.luyingdazi.api.config.OssConfig;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    private final Map<String, OSS> regionalClients = new ConcurrentHashMap<>();

    public String toAccessibleUrl(String url) {
        if (url == null || url.isBlank()) {
            return url;
        }

        URI uri;
        try {
            uri = URI.create(url);
        } catch (IllegalArgumentException exception) {
            return url;
        }

        String host = uri.getHost();
        String expectedHostPrefix = ossConfig.getBucketName() + ".";
        if (host == null || !host.startsWith(expectedHostPrefix)
                || !host.endsWith(".aliyuncs.com")) {
            return url;
        }

        String objectKey = uri.getRawPath();
        if (objectKey != null && objectKey.startsWith("/")) {
            objectKey = objectKey.substring(1);
        }
        objectKey = URLDecoder.decode(objectKey == null ? "" : objectKey,
                StandardCharsets.UTF_8);
        if (objectKey.isBlank()) {
            return url;
        }

        Date expiration = new Date(System.currentTimeMillis() + URL_TTL_MILLIS);
        String sourceEndpoint = host.substring(expectedHostPrefix.length());
        OSS signingClient = sourceEndpoint.equals(ossConfig.getEndpoint())
                ? ossClient
                : regionalClients.computeIfAbsent(sourceEndpoint,
                        endpoint -> new OSSClientBuilder().build(
                                endpoint,
                                ossConfig.getAccessKeyId(),
                                ossConfig.getAccessKeySecret()));
        String signedUrl = signingClient.generatePresignedUrl(
                ossConfig.getBucketName(), objectKey, expiration).toString();
        return signedUrl.startsWith("http://")
                ? "https://" + signedUrl.substring("http://".length())
                : signedUrl;
    }

    public List<String> toAccessibleUrls(List<String> urls) {
        if (urls == null || urls.isEmpty()) {
            return urls;
        }
        return urls.stream().map(this::toAccessibleUrl).toList();
    }

    @PreDestroy
    void closeRegionalClients() {
        regionalClients.values().forEach(OSS::shutdown);
    }
}
