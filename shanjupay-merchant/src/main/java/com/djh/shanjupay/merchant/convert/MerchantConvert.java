package com.djh.shanjupay.merchant.convert;

import com.djh.shanjupay.common.interfaces.mapstruct.MapStruct;
import com.djh.shanjupay.merchant.dto.MerchantDto;
import com.djh.shanjupay.merchant.entity.Merchant;
import com.djh.shanjupay.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 商人把
 *
 * @author MyMrDiao
 * @date 2021/04/24
 */
@Mapper(componentModel = "spring")
public interface MerchantConvert extends MapStruct<Merchant, MerchantDto, MerchantRegisterVO> {
}
