package com.djh.shanjupay.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.transaction.entity.AppPlatformChannel;
import com.djh.shanjupay.transaction.mapper.AppPlatformChannelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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

    /**
     * 为app绑定平台服务类型
     *
     * @param appId                应用程序id
     * @param platformChannelCodes 平台服务类型列表
     */
    public void bindPlatformChannelForApp (String appId, String platformChannelCodes) {
        //根据appId和平台服务类型code查询app_platform_channel
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new QueryWrapper<AppPlatformChannel>()
                .lambda()
                .eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCodes));
        if (StringUtils.isEmpty(appPlatformChannel)) {
            throw new BusinessException(CommonErrorCode.E_100104);
        }
        //如果没有绑定则绑定
        appPlatformChannel = new AppPlatformChannel();
        appPlatformChannel.setAppId(appId);
        appPlatformChannel.setPlatformChannel(platformChannelCodes);
        int appPlatformChannelNum = appPlatformChannelMapper.insert(appPlatformChannel);
        if (appPlatformChannelNum <= 0) {
            throw new BusinessException(CommonErrorCode.E_100116);
        }
    }
}
