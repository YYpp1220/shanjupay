package com.djh.shanjupay.merchant.controller;


import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.util.SecurityUtil;
import com.djh.shanjupay.merchant.dto.AppDto;
import com.djh.shanjupay.merchant.entity.App;
import com.djh.shanjupay.merchant.service.impl.AppServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2021-04-14
 */
@Slf4j
@RestController
@Api(value = "商户平台‐应用管理", tags = "商户平台‐应用相关")
public class AppController {
    public static final String SHANJUPAY_NAME = "应用";

    @Autowired
    private AppServiceImpl appService;

    /**
     * 创建应用程序
     *
     * @param appDto 应用dto
     * @return {@link ResponseEntity<RestResponse>}
     */
    @ApiOperation("商户创建应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "app", value = "应用信息", required = true, dataType = "AppDTO", paramType = "body")
    })
    @PostMapping("/createApp")
    public ResponseEntity<RestResponse> createApp (@RequestBody AppDto appDto) {
        if (StringUtils.isEmpty(appDto)) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        Long merchantId = SecurityUtil.getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            throw new BusinessException(CommonErrorCode.E_NO_AUTHORITY);
        }
        AppDto app = appService.createApp(merchantId, appDto);
        return ResponseEntity.ok(RestResponse.success(app));
    }

    /**
     * 查询商户下的应用列表
     *
     * @return {@link ResponseEntity<RestResponse<List<AppDto>>>}
     */
    @ApiOperation("查询商户下的应用列表")
    @GetMapping("/queryAppList")
    public ResponseEntity<RestResponse<List<AppDto>>> queryAppList () {
        Long merchantId = SecurityUtil.getMerchantId();
        if (StringUtils.isEmpty(merchantId)) {
            throw new BusinessException(CommonErrorCode.E_NO_AUTHORITY);
        }
        List<AppDto> appDtoList = appService.queryAppByMerchantId(merchantId);
        return ResponseEntity.ok(RestResponse.success(appDtoList));
    }

    /**
     * 根据appId获取应用的详细信息
     *
     * @param appId 应用程序id
     * @return {@link ResponseEntity<RestResponse<AppDto>>}
     */
    @ApiOperation("根据appId获取应用的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "商户应用id", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/queryApp/{appId}")
    public ResponseEntity<RestResponse<AppDto>> queryAppById (@PathVariable String appId) {
        if (StringUtils.isEmpty(appId)) {
            throw new BusinessException(CommonErrorCode.E_110006);
        }
        AppDto appDto = appService.getAppById(appId);
        return ResponseEntity.ok(RestResponse.success(appDto));
    }
}
