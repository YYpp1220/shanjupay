package com.djh.shanjupay.upload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.djh.shanjupay.common.constant.ShanjuPayConstant;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.upload.properties.AliOssProperty;
import com.djh.shanjupay.upload.properties.QiniuProperty;
import com.djh.shanjupay.upload.util.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

/**
 * 文件服务实现类
 *
 * @author MyMrDiao
 * @date 2021/05/13
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl implements ShanjuPayConstant {
    @Autowired
    private QiniuProperty qiniuProperty;

    @Autowired
    private AliOssProperty aliOssProperty;


    /**
     * qiniu oss上传
     *
     * @param file    spring mvc文件对象
     * @return {@link String}
     */
    public String qiniuOssUpload (MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!Arrays.asList(UPLOAD_FILE_TYPE).contains(suffix)) {
            log.error("上传图片格式有误！上传格式为{}", suffix);
            throw new BusinessException(CommonErrorCode.E_100109);
        }
        String fileName = UUID.randomUUID() + suffix;
        try {
            QiniuUtils.uploadQiniu(qiniuProperty.getAccessKey(), qiniuProperty.getSecretKey(), qiniuProperty.getBucket(), file.getBytes(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        }
        // 返回文件url
        return "http://" + qiniuProperty.getUrl() + fileName;
    }

    /**
     * ali oss上传
     *
     * @param file    spring mvc文件对象
     * @return {@link String}
     */
    public String aliOssUpload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String aliOssFileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!Arrays.asList(UPLOAD_FILE_TYPE).contains(aliOssFileSuffix)) {
            log.error("文件上传格式有误！{}", aliOssFileSuffix);
            throw new BusinessException(CommonErrorCode.E_100109);
        }
        String key = UUID.randomUUID() + aliOssFileSuffix;
        String path = aliOssProperty.getDir() + key;
        OSS ossClient = new OSSClientBuilder().build(aliOssProperty.getEndpoint(), aliOssProperty.getAccessKeyId(), aliOssProperty.getAccessKeySecret());
        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(aliOssProperty.getBucket(), path, new ByteArrayInputStream(file.getBytes()));
            ossClient.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.UNKNOWN);
        }
        return aliOssProperty.getOssDomain() + path;
    }
}
