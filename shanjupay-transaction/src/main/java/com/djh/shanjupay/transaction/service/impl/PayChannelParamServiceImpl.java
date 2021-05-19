package com.djh.shanjupay.transaction.service.impl;

import com.djh.shanjupay.transaction.entity.PayChannelParam;
import com.djh.shanjupay.transaction.mapper.PayChannelParamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 某商户针对某一种原始支付渠道的配置参数 服务实现类
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PayChannelParamServiceImpl extends ServiceImpl<PayChannelParamMapper, PayChannelParam> {
    @Autowired
    private PayChannelParamMapper payChannelParamMapper;
}
