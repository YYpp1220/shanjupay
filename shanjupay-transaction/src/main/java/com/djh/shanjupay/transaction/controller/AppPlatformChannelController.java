package com.djh.shanjupay.transaction.controller;


import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.transaction.service.impl.AppPlatformChannelServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 说明了应用选择了平台中的哪些支付渠道 前端控制器
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
@Slf4j
@RestController
@Api(value = "商户平台-应用与服务类型关系", tags = "商户平台-应用与服务类型关系")
public class AppPlatformChannelController {
    public static final String SHANJUPAY_NAME = "商户-应用与服务类型关系";

    @Autowired
    private AppPlatformChannelServiceImpl appPlatformChannelService;

    @ApiOperation("绑定服务类型")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "应用id",name = "appId",dataType = "string",paramType = "path"),
            @ApiImplicitParam(value = "服务类型code",name = "platformChannelCodes",dataType = "string",paramType = "query")
    })
    @PostMapping("/bindPlatformForApp")
    public ResponseEntity<RestResponse<Boolean>> bindPlatformForApp (@RequestParam("appId") String appId, @RequestParam("platformChannelCodes") String platformChannelCodes) {
        if (StringUtils.isEmpty(appId) && StringUtils.isEmpty(platformChannelCodes)) {
            throw new BusinessException(CommonErrorCode.E_100101);
        }
        try {
            appPlatformChannelService.bindPlatformChannelForApp(appId, platformChannelCodes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(RestResponse.success(true));
    }
}
