package com.djh.shanjupay.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.djh.shanjupay.transaction.dto.PayChannelDto;
import com.djh.shanjupay.transaction.entity.PayChannel;
import com.djh.shanjupay.transaction.entity.PlatformChannel;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
public interface PayChannelMapper extends BaseMapper<PayChannel> {
    /**
     * 根据平台服务类型获取原始支付渠道
     *
     * @param platformChannelCode 平台渠道代码
     * @return {@link List < PlatformChannel >}
     */
    public List<PayChannelDto> selectPayChannelByPlatformChannel(@Param("platformChannelCode") String platformChannelCode);
}
