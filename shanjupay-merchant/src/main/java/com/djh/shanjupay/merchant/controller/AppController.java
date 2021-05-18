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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@Api(value = "商户平台‐应用管理", tags = "商户平台‐应用相关", description = "商户平台‐应用相关")
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
}
