package com.djh.shanjupay.upload.properties;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 七牛云属性配置文件
 *
 * @author MrMyHui
 * @date 2021/05/13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "qiniu.oss")
public class QiniuProperty {
    private String qiniuUrl;

    private String accessKey;

    private String secretKey;

    private String bucket;
}
