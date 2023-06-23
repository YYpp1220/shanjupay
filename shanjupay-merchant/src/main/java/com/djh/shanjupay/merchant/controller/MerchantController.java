package com.djh.shanjupay.merchant.controller;


import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.util.BuilderUtils;
import com.djh.shanjupay.common.util.SecurityUtil;
import com.djh.shanjupay.limit.client.annotation.Limit;
import com.djh.shanjupay.merchant.convert.MerchantConvert;
import com.djh.shanjupay.merchant.convert.MerchantDetailConvert;
import com.djh.shanjupay.merchant.dto.MerchantDto;
import com.djh.shanjupay.merchant.vo.MerchantDetailVO;
import com.djh.shanjupay.merchant.vo.MerchantRegisterVO;
import com.djh.shanjupay.sms.entity.VerificationInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @Autowired
    private MerchantDetailConvert merchantDetailConvert;

    private static  BuilderUtils<RestResponse> responseBuilderUtils = BuilderUtils.of(RestResponse::new);
    private static  RestResponse restResponse = new RestResponse<>();

    /**
     * 根据商户id查询
     *
     * @param merchantId 商人id
     * @return {@link ResponseEntity<RestResponse<MerchantDto>>}
     */
    @ApiOperation(value = "根据商户id查询")
    @ApiImplicitParam(name = "merchantId", value = "商户id", dataType = "Long", required = true)
    @GetMapping("/queryById")
    public ResponseEntity<RestResponse<MerchantDto>> queryMerchantById (@RequestParam("merchantId") Long merchantId) {
        MerchantDto merchantDto = merchantService.queryMerchantById(merchantId);
        restResponse = responseBuilderUtils.with(RestResponse::setCode, 200).with(RestResponse::setResult, merchantDto).build();
        return ResponseEntity.ok(restResponse);
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
    @Limit(period = 60, count = 5, fallbackImpl = "fixedWindow")
    public ResponseEntity<RestResponse<VerificationInfo>> getSmsCode (@RequestParam("phone") String phone) {
        log.info("获取短信验证码开始，手机号{}", phone);
        RestResponse<VerificationInfo> restResponse = merchantService.getSmsCode(phone);
        return ResponseEntity.ok(restResponse);
    }

    /**
     * 注册商户
     *
     * @param merchantRegisterVO 商人登记签证官
     * @return {@link ResponseEntity<RestResponse<MerchantRegisterVO>>}
     */
    @ApiOperation("注册商户")
    @ApiImplicitParam(name = "merchantRegisterVO", value = "注册信息", required = true, dataType = "MerchantRegisterVO", paramType = "body")
    @PostMapping("/register")
    @Limit(period = 60, count = 5, fallbackImpl = "fixedWindow")
    public ResponseEntity<RestResponse<MerchantRegisterVO>> saveMerchant (@RequestBody @Valid MerchantRegisterVO merchantRegisterVO) {
        if (StringUtils.isEmpty(merchantRegisterVO)) {
            throw new BusinessException(CommonErrorCode.E_100108);
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

    /**
     * 商户资质申请
     *
     * @param merchantInfo 商家信息
     */
    @ApiOperation("商户资质申请")
    @ApiImplicitParams({ @ApiImplicitParam(name = "merchantInfo", value = "商户认证资料", required = true, dataType = "MerchantDetailVO", paramType = "body") })
    @PostMapping("/save")
    public void saveMerchant(@RequestBody MerchantDetailVO merchantInfo) {
        Long merchantId = SecurityUtil.getMerchantId();
        if (StringUtils.isEmpty(merchantId) || StringUtils.isEmpty(merchantInfo)) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        MerchantDto merchantDto = merchantDetailConvert.voToDto(merchantInfo);
        merchantService.applyMerchant(merchantId, merchantDto);
    }
}
