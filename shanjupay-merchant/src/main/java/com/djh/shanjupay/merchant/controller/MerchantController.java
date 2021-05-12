package com.djh.shanjupay.merchant.controller;


import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.util.BuilderUtils;
import com.djh.shanjupay.merchant.convert.MerchantConvert;
import com.djh.shanjupay.merchant.dto.MerchantDto;
import com.djh.shanjupay.merchant.vo.MerchantRegisterVO;
import com.djh.shanjupay.sms.entity.VerificationInfo;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.djh.shanjupay.merchant.service.impl.MerchantServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
@Api(value = "商户平台-商户相关", tags = "商户平台-商户相关")
public class MerchantController {
    public static final String SHANJUPAY_NAME = "商户";

    @Autowired
    private MerchantServiceImpl merchantService;

    @Autowired
    private MerchantConvert merchantConvert;

    private static  BuilderUtils<RestResponse<MerchantRegisterVO>> responseBuilderUtils = BuilderUtils.of(RestResponse::new);
    private static  RestResponse<MerchantRegisterVO> restResponse = new RestResponse<>();

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

    @ApiOperation("注册商户")
    @ApiImplicitParam(name = "merchantRegisterVO", value = "注册信息", required = true, dataType = "MerchantRegisterVO", paramType = "body")
    @PostMapping("/register")
    public ResponseEntity<RestResponse<MerchantRegisterVO>> saveMerchant (@RequestBody @Valid MerchantRegisterVO merchantRegisterVO) {
        //BuilderUtils<RestResponse<MerchantRegisterVO>> responseBuilderUtils = BuilderUtils.of(RestResponse::new);
        //RestResponse<MerchantRegisterVO> restResponse = new RestResponse<>();
        if (StringUtils.isEmpty(merchantRegisterVO)) {
            throw new BusinessException(CommonErrorCode.E_200201);
        }
        if (StringUtils.isEmpty(merchantRegisterVO.getVerifyKey()) || StringUtils.isEmpty(merchantRegisterVO.getVerifyCode())) {
            throw new BusinessException(CommonErrorCode.E_100103);
        }
        merchantService.checkVerifyCode(merchantRegisterVO.getVerifyKey(), merchantRegisterVO.getVerifyCode());
        MerchantDto merchantDto = merchantConvert.voToDto(merchantRegisterVO);
        MerchantRegisterVO merchantRegisterVoResponse = merchantService.saveMerchant(merchantDto);
        if (!StringUtils.isEmpty(merchantRegisterVoResponse)) {
            restResponse = responseBuilderUtils.with(RestResponse::setCode, 200).with(RestResponse::setResult, merchantRegisterVoResponse).build();
        }
        return ResponseEntity.ok(restResponse);
    }
}
