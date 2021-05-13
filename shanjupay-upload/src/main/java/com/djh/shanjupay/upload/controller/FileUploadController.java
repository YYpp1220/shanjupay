package com.djh.shanjupay.upload.controller;

import com.djh.shanjupay.upload.service.impl.FileServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传控制器
 *
 * @author MyMrDiao
 * @date 2021/05/13
 */
@Slf4j
@RestController
public class FileUploadController {
    public static final String SHANJUPAY_NAME = "文件上传";
    @Autowired
    private FileServiceImpl fileService;

    @ApiOperation("用户资质证件上传")
    @ApiImplicitParam(name = "file", value = "文件流", dataType = "MultipartFile", required = true)
    @PostMapping("/qiniuUpload")
    public String qiniuUpload (@RequestParam("file") MultipartFile file) {
        return null;
    }
}
