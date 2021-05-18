package com.djh.shanjupay.merchant.convert;

import com.djh.shanjupay.common.interfaces.mapstruct.MapStruct;
import com.djh.shanjupay.merchant.dto.AppDto;
import com.djh.shanjupay.merchant.entity.App;
import org.mapstruct.Mapper;

/**
 * 应用程序转换
 *
 * @author MyMrDiao
 * @date 2021/05/19
 */
@Mapper(componentModel = "spring")
public interface AppConvert extends MapStruct<App, AppDto, App> {
}
