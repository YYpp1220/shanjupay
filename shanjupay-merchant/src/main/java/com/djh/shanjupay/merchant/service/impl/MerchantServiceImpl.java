package com.djh.shanjupay.merchant.service.impl;

import cn.hutool.json.JSONUtil;
import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.merchant.dto.MerchantDto;
import com.djh.shanjupay.merchant.entity.Merchant;
import com.djh.shanjupay.merchant.mapper.MerchantMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djh.shanjupay.merchant.vo.MerchantRegisterVO;
import com.djh.shanjupay.sms.client.SmsClient;
import com.djh.shanjupay.sms.entity.VerificationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2021-04-14
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> {
    @Autowired(required = false)
    private MerchantMapper merchantMapper;

    @Autowired(required = false)
    private SmsClient smsClient;

    /**
     * 查询通过id
     *
     * @param merchantId 商户id
     * @return {@link MerchantDto}
     */
    public MerchantDto queryById (Long merchantId) {
        Merchant merchant = merchantMapper.selectById(merchantId);
        return JSONUtil.toBean(JSONUtil.toJsonStr(merchant), MerchantDto.class);
    }

    /**
     * 获取短信验证码
     *
     * @param phone 电话
     * @return {@link RestResponse<String>}
     */
    public RestResponse<VerificationInfo> getSmsCode(String phone) {
        if (StringUtils.isEmpty(phone)) {
            throw new BusinessException(CommonErrorCode.E_200230);
        }
        Map<String, Object> phoneSmsCodeMap = new HashMap<>(16);
        phoneSmsCodeMap.put("mobile", phone);
        RestResponse<VerificationInfo> verificationInfo = null;
        try {
            verificationInfo = smsClient.generateVerificationInfo("sms", phoneSmsCodeMap, 300);
        } catch (Exception e) {
            log.error("feign调用出错！{}", e.getMessage());
            throw new BusinessException(CommonErrorCode.CUSTOM);
        }
        return verificationInfo;
    }

    /**
     * 校验验证码
     *
     * @param verifyKey  验证关键
     * @param verifyCode 验证代码
     * @return {@link RestResponse<Boolean>}
     */
    public RestResponse<Boolean> checkVerifyCode (String verifyKey, String verifyCode) {
        RestResponse<Boolean> verifyCodeFlag = new RestResponse<>();
        try {
            verifyCodeFlag = smsClient.verify("sms", verifyKey, verifyCode);
        } catch (Exception e) {
            log.error("feign调用出错！{}", e.getMessage());
            throw new BusinessException(CommonErrorCode.E_100102);
        }
        if (!verifyCodeFlag.getResult()) {
            throw new BusinessException(CommonErrorCode.E_100102);
        }
        return verifyCodeFlag;
    }

    /**
     * 保存的商户
     *
     * @param merchantRegisterVO 商人登记签证官
     * @return {@link RestResponse<MerchantDto>}
     */
    public RestResponse<MerchantDto> saveMerchant(MerchantRegisterVO merchantRegisterVO) {
        return null;
    }
}
