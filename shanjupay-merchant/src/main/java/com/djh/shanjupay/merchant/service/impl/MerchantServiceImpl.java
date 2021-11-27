package com.djh.shanjupay.merchant.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.djh.shanjupay.common.domain.RestResponse;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.interfaces.limit.Limit;
import com.djh.shanjupay.merchant.convert.MerchantConvert;
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

import javax.validation.Valid;
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

    @Autowired(required = false)
    private MerchantConvert merchantConvert;

    /**
     * 查询通过id
     *
     * @param merchantId 商户id
     * @return {@link MerchantDto}
     */
    public MerchantDto queryMerchantById (Long merchantId) {
        Merchant merchant = merchantMapper.selectById(merchantId);
        return JSONUtil.toBean(JSONUtil.toJsonStr(merchant), MerchantDto.class);
    }

    /**
     * 获取短信验证码
     *
     * @param phone 电话
     * @return {@link RestResponse<String>}
     */
    @Limit
    public RestResponse<VerificationInfo> getSmsCode(String phone) throws BusinessException {
        if (StringUtils.isEmpty(phone)) {
            throw new BusinessException(CommonErrorCode.E_100112);
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
    public void checkVerifyCode (String verifyKey, String verifyCode) throws BusinessException {
        RestResponse<Boolean> verifyCodeFlag = new RestResponse<>();
        try {
            verifyCodeFlag = smsClient.verify("sms", verifyKey, verifyCode);
        } catch (Exception e) {
            log.error("feign调用出错！{}", e.getMessage());
            throw new BusinessException(CommonErrorCode.E_999915);
        }
        if (!verifyCodeFlag.getResult()) {
            throw new BusinessException(CommonErrorCode.E_100102);
        }
    }

    /**
     * 保存的商户
     *
     * @param merchantDto 商人登记签证官
     * @return {@link RestResponse<MerchantDto>}
     */
    public MerchantRegisterVO saveMerchant(@Valid MerchantDto merchantDto) throws BusinessException {
        // 验证手机号码唯一性
        LambdaQueryWrapper<Merchant> queryWrapper = new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile, merchantDto.getMobile());
        Integer mobileCount = merchantMapper.selectCount(queryWrapper);
        // 存在及抛出异常
        if (mobileCount > 0) {
            throw new BusinessException(CommonErrorCode.E_100113);
        }
        //将dto转成entity
        Merchant merchant = merchantConvert.dtoToEntity(merchantDto);
        //设置审核状态0‐未申请,1‐已申请待审核,2‐审核通过,3‐审核拒绝
        merchant.setAuditStatus("0");
        //保存商户信息
        int insertId = merchantMapper.insert(merchant);
        if (StringUtils.isEmpty(insertId)) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        //将entity转成 dto
        return merchantConvert.entityToVo(merchant);
    }

    /**
     * 资质申请
     *
     * @param merchantId  商人id
     * @param merchantDto 资质申请信息
     */
    public void applyMerchant (Long merchantId, MerchantDto merchantDto) {
        // 判断校验前端参数是否存在
        if (StringUtils.isEmpty(merchantDto) || StringUtils.isEmpty(merchantId)) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        // 根据id查询对应商户
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (StringUtils.isEmpty(merchant)) {
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        // mapstruct对象转换
        Merchant merchantUpdate = merchantConvert.dtoToEntity(merchantDto);
        // 必要信息从获取的数据填入，前端可能存在篡改参数
        merchantUpdate.setId(merchant.getId());
        merchantUpdate.setMobile(merchant.getMobile());
        merchantUpdate.setAuditStatus("1");
        merchantUpdate.setTenantId(merchant.getTenantId());
        merchantMapper.updateById(merchantUpdate);
    }
}
