package com.djh.shanjupay.upload.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里oss属性配置文件
 *
 * @author MrMyHui
 * @date 2021/05/13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ali.oss")
public class AliOssProperty {
    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

    private String bucket;

    private String ossDomain;

    private String dir;
}
