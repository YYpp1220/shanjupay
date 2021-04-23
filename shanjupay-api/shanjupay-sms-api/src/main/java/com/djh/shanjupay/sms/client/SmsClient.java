package com.djh.shanjupay.sms.client;

import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.sms.entity.VerificationInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 短信客户端
 *
 * @author MrMyHui
 * @date 2021/04/22
 */
@FeignClient("sj-sms")
public interface SmsClient {
    /**
     * 生成验证信息
     *
     * @param name          的名字
     * @param payload       有效载荷
     * @param effectiveTime 有效的时间
     * @return {@link RestResponse<VerificationInfo>}
     */
    @PostMapping(value = "/sms/generate")
    public RestResponse<VerificationInfo> generateVerificationInfo(@RequestParam("name")String name,
                                                                   @RequestBody @RequestParam Map<String,Object> payload,
                                                                   @RequestParam(value = "effectiveTime", required = false)int effectiveTime);

    /**
     * 验证
     *
     * @param name             的名字
     * @param verificationKey  验证关键
     * @param verificationCode 验证码
     * @return {@link RestResponse<Boolean>}
     */
    @PostMapping(value = "/sms/verify")
    public RestResponse<Boolean> verify(@RequestParam String name,
                                        @RequestParam String verificationKey,
                                        @RequestParam String verificationCode);
}
