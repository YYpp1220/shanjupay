package com.djh.shanjupay.transaction.service.impl;

import com.djh.shanjupay.transaction.entity.AppPlatformChannel;
import com.djh.shanjupay.transaction.mapper.AppPlatformChannelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 说明了应用选择了平台中的哪些支付渠道 服务实现类
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AppPlatformChannelServiceImpl extends ServiceImpl<AppPlatformChannelMapper, AppPlatformChannel> {
    @Autowired
    private AppPlatformChannelMapper appPlatformChannelMapper;
}
