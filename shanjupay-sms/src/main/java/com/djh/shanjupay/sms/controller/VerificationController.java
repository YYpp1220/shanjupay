package com.djh.shanjupay.sms.controller;


import com.djh.shanjupay.sms.common.domain.RestResponse;
import com.djh.shanjupay.sms.dto.VerificationInfo;
import com.djh.shanjupay.sms.service.VerificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 验证控制器
 *
 * @author MyMrDiao
 * @date 2021/04/20
 */
@Api(value = "验证码服务接口")
@RestController
public class VerificationController {

    @Autowired
    private VerificationService verificationService;


    /**
     * 生成验证信息
     *
     * @param name          的名字
     * @param payload       有效载荷
     * @param effectiveTime 有效的时间
     * @return {@link RestResponse<VerificationInfo>}
     */
    @ApiOperation(value="生成验证信息", notes="生成验证信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "业务名称", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "payload", value = "业务携带参数，如手机号 ，邮箱", required = true, paramType="body"),
            @ApiImplicitParam(name = "effectiveTime", value = "验证信息有效期(秒)", required = false, dataType = "String", paramType="query")
    })
    @PostMapping(value = "/generate")
    public RestResponse<VerificationInfo> generateVerificationInfo(@RequestParam("name")String name,
                                                                   @RequestBody Map<String,Object> payload,
                                                                   @RequestParam("effectiveTime")int effectiveTime){
        VerificationInfo verificationInfo = verificationService.generateVerificationInfo(name, payload, effectiveTime);
        return RestResponse.success(verificationInfo);
    }


    /**
     * 验证
     *
     * @param name             的名字
     * @param verificationKey  验证关键
     * @param verificationCode 验证码
     * @return {@link RestResponse<Boolean>}
     */
    @ApiOperation(value="校验", notes="校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "业务名称", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "verificationKey", value = "验证key", required = true, dataType = "String", paramType="query"),
            @ApiImplicitParam(name = "verificationCode", value = "验证码", required = true, dataType = "String", paramType="query")
    })
    @PostMapping(value = "/verify")
    public RestResponse<Boolean> verify(String name, String verificationKey, String verificationCode){
        Boolean isSuccess = verificationService.verify(name,verificationKey,verificationCode);
        return RestResponse.success(isSuccess);
    }


}
