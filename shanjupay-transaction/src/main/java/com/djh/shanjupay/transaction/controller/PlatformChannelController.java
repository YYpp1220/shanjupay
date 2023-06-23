package com.djh.shanjupay.transaction.controller;


import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.util.StringUtil;
import com.djh.shanjupay.transaction.dto.PayChannelDto;
import com.djh.shanjupay.transaction.dto.PlatformChannelDto;
import com.djh.shanjupay.transaction.service.impl.PayChannelServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.djh.shanjupay.transaction.service.impl.PlatformChannelServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
@Slf4j
@RestController
@Api(value = "商户平台‐渠道和支付参数相关", tags = "商户平台‐渠道和支付参数相关")
public class PlatformChannelController {
    public static final String SHANJUPAY_NAME = "商户-渠道和支付参数";

    @Autowired
    private PlatformChannelServiceImpl platformChannelService;

    @Autowired
    private PayChannelServiceImpl payChannelService;

    @ApiOperation("获取平台服务类型")
    @ApiImplicitParam(name = "", value = "", dataType = "", required = false)
    @GetMapping("/getPlatformChannelList")
    public ResponseEntity<RestResponse<List<PlatformChannelDto>>> getPlatformChannelList () {
        return ResponseEntity.ok(RestResponse.success(platformChannelService.queryPlatformChannel()));
    }

    @ApiOperation("根据平台服务类型获取支付渠道列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platformChannelCode", value = "服务类型编码", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping("/platformChannel/{platformChannelCode}")
    public List<PayChannelDto> queryPayChannelByPlatformChannel(@PathVariable String platformChannelCode) {
        if (StringUtil.isBlank(platformChannelCode)) {
            throw new BusinessException(CommonErrorCode.E_100101);
        }
        return payChannelService.queryPayChannelByPlatformChannel(platformChannelCode);
    }
}
