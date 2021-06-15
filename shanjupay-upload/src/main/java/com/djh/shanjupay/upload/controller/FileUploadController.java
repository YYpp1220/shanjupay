package com.djh.shanjupay.upload.controller;

import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.util.BuilderUtils;
import com.djh.shanjupay.upload.service.impl.FileServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件上传控制器
 *
 * @author MyMrDiao
 * @date 2021/05/13
 */
@Slf4j
@RestController
@Api(value = "聚宇支付-文件上传", tags = "聚宇支付-文件上传")
public class FileUploadController {
    public static final String SHANJUPAY_NAME = "文件上传";
    @Autowired
    private FileServiceImpl fileService;

    private static BuilderUtils<RestResponse> responseBuilderUtils = BuilderUtils.of(RestResponse::new);
    private static RestResponse restResponse = new RestResponse<>();

    /**
     * qiniu 上传
     *
     * @param file 文件
     * @return {@link ResponseEntity<RestResponse<String>>}
     */
    @ApiOperation("用户资质证件上传")
    @ApiImplicitParam(name = "file", value = "文件流", dataType = "MultipartFile", required = true)
    @PostMapping("/qiniuUpload")
    public ResponseEntity<RestResponse<String>> qiniuUpload (@RequestParam("file") MultipartFile file) {
        log.info("文件上传开始！文件名称{}", file.getOriginalFilename());
        if (StringUtils.isEmpty(file)) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        String fileUrl;
        try {
            fileUrl = fileService.qiniuOssUpload(file);
            restResponse = responseBuilderUtils.with(RestResponse::setCode, 200).with(RestResponse::setResult, fileUrl).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        }
        return ResponseEntity.ok(restResponse);
    }

    /**
     * 阿里云上传
     *
     * @param file 文件
     * @return {@link ResponseEntity<RestResponse<String>>}
     */
    @ApiOperation("用户资质证件上传")
    @ApiImplicitParam(name = "file", value = "文件流", dataType = "MultipartFile", required = true)
    @PostMapping("/aliUpload")
    public ResponseEntity<RestResponse<String>> aliUpload (@RequestParam("file") MultipartFile file) {
        log.info("文件上传开始！文件名称{}", file.getOriginalFilename());
        if (StringUtils.isEmpty(file)) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        String fileUrl;
        try {
            fileUrl = fileService.aliOssUpload(file);
            restResponse = responseBuilderUtils.with(RestResponse::setCode, 200).with(RestResponse::setResult, fileUrl).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        }
        return ResponseEntity.ok(restResponse);
    }
}
