package com.djh.shanjupay.transaction.controller;


import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.util.SecurityUtil;
import com.djh.shanjupay.transaction.vo.PayChannelParamVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.djh.shanjupay.transaction.service.impl.PayChannelParamServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 某商户针对某一种原始支付渠道的配置参数 前端控制器
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
@Slf4j
@RestController
@Api(value = "商户平台‐原始支付渠道的配置参数", tags = "商户平台‐原始支付渠道的配置参数")
public class PayChannelParamController {

    @Autowired
    private PayChannelParamServiceImpl payChannelParamService;

    @ApiOperation(value = "商户配置支付渠道参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payChannelParam", value = "商户配置支付渠道参数", required = true, dataType = "PayChannelParamVO", paramType = "body")
    })
    @PostMapping("payChannelParams")
    public ResponseEntity<Object> createPayChannelParam(@RequestBody @Validated PayChannelParamVO payChannelParamVO, BindingResult result) {
        if (BusinessException.paramVerificationEx(result)) {
            return ResponseEntity.ok(RestResponse.success(result));
        }
        Long merchantId = SecurityUtil.getMerchantId();
        payChannelParamVO.setMerchantId(merchantId);
        payChannelParamService.savePayChannelParam(payChannelParamVO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
