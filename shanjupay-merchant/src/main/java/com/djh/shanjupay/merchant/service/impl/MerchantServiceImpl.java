package com.djh.shanjupay.merchant.service.impl;

import cn.hutool.json.JSONUtil;
import com.djh.shanjupay.merchant.dto.MerchantDto;
import com.djh.shanjupay.merchant.entity.Merchant;
import com.djh.shanjupay.merchant.mapper.MerchantMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private MerchantMapper merchantMapper;

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
}
