package com.djh.shanjupay.upload.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件服务实现类
 *
 * @author MyMrDiao
 * @date 2021/05/13
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FileServiceImpl {

    public String upload (byte[] bytes, String fileName) {
        return null;
    }
}
