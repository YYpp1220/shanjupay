package com.djh.shanjupay.transaction.controller;


import com.djh.shanjupay.transaction.service.impl.AppPlatformChannelServiceImpl;
import org.springframework.stereotype.Controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 说明了应用选择了平台中的哪些支付渠道 前端控制器
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
@Slf4j
@Controller
@Api(value = "", tags = "", description="")
public class AppPlatformChannelController {

    @Autowired
    private AppPlatformChannelServiceImpl appPlatformChannelService;
}
