package com.djh.shanjupay.transaction.convert;

import com.djh.shanjupay.common.interfaces.mapstruct.MapStruct;
import com.djh.shanjupay.transaction.dto.PlatformChannelDto;
import com.djh.shanjupay.transaction.entity.PlatformChannel;
import com.djh.shanjupay.transaction.vo.PlatformChannelVO;
import org.mapstruct.Mapper;

/**
 * 平台转换频道
 *
 * @author MyMrDiao
 * @date 2021/05/22
 */
@Mapper(componentModel = "spring")
public interface PlatformChannelConvert extends MapStruct<PlatformChannel, PlatformChannelDto, PlatformChannelVO> {
}
