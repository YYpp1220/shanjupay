package com.djh.shanjupay.merchant.controller;


import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.merchant.dto.MerchantDto;
import com.djh.shanjupay.sms.entity.VerificationInfo;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.djh.shanjupay.merchant.service.impl.MerchantServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2021-04-14
 */
@Slf4j
@Controller
@Api(value = "商户平台-商户相关", tags = "商户平台-商户相关")
public class MerchantController {
    public static final String SHANJUPAY_NAME = "商户";

    @Autowired
    private MerchantServiceImpl merchantService;

    @ApiOperation(value = "根据商户id查询")
    @ApiImplicitParam(name = "merchantId", value = "商户id", dataType = "Long", required = true)
    @GetMapping("/queryById")
    public ResponseEntity<MerchantDto> queryById (@RequestParam("merchantId") Long merchantId) {
        MerchantDto merchantDto = merchantService.queryById(merchantId);
        return ResponseEntity.ok(merchantDto);
    }

    /**
     * 获取短信验证码
     *
     * @param phone 电话
     * @return {@link ResponseEntity<RestResponse<String>>}
     */
    @ApiOperation("获取手机验证码")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String", paramType = "query")
    @GetMapping("/getSmsCode")
    public ResponseEntity<RestResponse<VerificationInfo>> getSmsCode (@RequestParam("phone") String phone) {
        log.info("获取短信验证码开始，手机号{}", phone);
        RestResponse<VerificationInfo> restResponse = merchantService.getSmsCode(phone);
        return ResponseEntity.ok(restResponse);
    }
}
