package com.djh.shanjupay.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.util.CheckObjectIsNullUtils;
import com.djh.shanjupay.transaction.convert.PayChannelParamConvert;
import com.djh.shanjupay.transaction.dto.PayChannelParamDto;
import com.djh.shanjupay.transaction.entity.AppPlatformChannel;
import com.djh.shanjupay.transaction.entity.PayChannelParam;
import com.djh.shanjupay.transaction.mapper.AppPlatformChannelMapper;
import com.djh.shanjupay.transaction.mapper.PayChannelParamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djh.shanjupay.transaction.vo.PayChannelParamVO;
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

    @Autowired
    private AppPlatformChannelMapper appPlatformChannelMapper;

    @Autowired
    private PayChannelParamConvert payChannelParamConvert;

    /**
     * 保存支付渠道参数
     *
     * @param payChannelParamVO 支付渠道参数
     */
    public void savePayChannelParam(PayChannelParamVO payChannelParamVO) {
        // 根据appid和服务类型查询应用与服务类型绑定id
        Long appPlatformChannelId = queryIdByAppPlatformChannel(payChannelParamVO.getAppId(), payChannelParamVO.getPlatformChannelCode());
        if (appPlatformChannelId == null) {
            // 应用未绑定该服务类型不可进行支付渠道参数配置
            throw new BusinessException(CommonErrorCode.E_300010);
        }
        // 根据应用与服务类型绑定id和支付渠道查询参数信息
        PayChannelParam payChannelParam = payChannelParamMapper.selectOne(new LambdaQueryWrapper<PayChannelParam>()
                .eq(PayChannelParam::getAppPlatformChannelId, appPlatformChannelId)
                .eq(PayChannelParam::getPayChannel, payChannelParamVO.getPayChannel()));
        // 更新已有配置
        if (CheckObjectIsNullUtils.objCheckIsNull(payChannelParam, "")) {
            payChannelParam.setChannelName(payChannelParamVO.getChannelName());
            payChannelParam.setParam(payChannelParamVO.getParam());
            payChannelParamMapper.updateById(payChannelParam);
        } else {
            // 添加新配置
            PayChannelParam payChannelParamNew = payChannelParamConvert.voToEntity(payChannelParamVO);
            payChannelParamNew.setId(null);
            // 应用与服务类型绑定id
            payChannelParamNew.setAppPlatformChannelId(appPlatformChannelId);
            payChannelParamMapper.insert(payChannelParamNew);
        }
    }

    /**
     * 根据appid和服务类型查询应用与服务类型绑定id
     *
     * @param appId               应用程序id
     * @param platformChannelCode 平台渠道代码
     * @return {@link Long}
     */
    private Long queryIdByAppPlatformChannel(String appId, String platformChannelCode) {
        // 根据appid和服务类型查询应用与服务类型绑定id
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper
                .selectOne(new LambdaQueryWrapper<AppPlatformChannel>()
                        .eq(AppPlatformChannel::getAppId, appId)
                        .eq(AppPlatformChannel::getPlatformChannel, platformChannelCode));
        if (!CheckObjectIsNullUtils.objCheckIsNull(appPlatformChannel, "")) {
            return appPlatformChannel.getId();
        }
        return null;
    }
}
