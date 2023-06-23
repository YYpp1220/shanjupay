package com.djh.shanjupay.transaction.convert;

import com.djh.shanjupay.common.interfaces.mapstruct.MapStruct;
import com.djh.shanjupay.transaction.dto.PayChannelParamDto;
import com.djh.shanjupay.transaction.entity.PayChannelParam;
import com.djh.shanjupay.transaction.vo.PayChannelParamVO;
import org.mapstruct.Mapper;

/**
 * 支付通道参数转换
 *
 * @author MyMrDiao
 * @date 2023/01/10
 */
@Mapper(componentModel = "spring")
public interface PayChannelParamConvert extends MapStruct<PayChannelParam, PayChannelParamDto, PayChannelParamVO> {
}
