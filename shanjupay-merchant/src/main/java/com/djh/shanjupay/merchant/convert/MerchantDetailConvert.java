package com.djh.shanjupay.merchant.convert;

import com.djh.shanjupay.common.interfaces.mapstruct.MapStruct;
import com.djh.shanjupay.merchant.dto.MerchantDto;
import com.djh.shanjupay.merchant.entity.Merchant;
import com.djh.shanjupay.merchant.vo.MerchantDetailVO;
import org.mapstruct.Mapper;

/**
 * 商人细节转换
 *
 * @author MyMrDiao
 * @date 2021/05/16
 */
@Mapper(componentModel = "spring")
public interface MerchantDetailConvert extends MapStruct<Merchant, MerchantDto, MerchantDetailVO> {
}
