package com.djh.shanjupay.merchant.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 商户注册信息
 *
 * @author MyMrDiao
 * @date 2021/04/24
 */
@Data
@ApiModel(value = "MerchantRegisterVO", description = "商户注册信息")
public class MerchantRegisterVO implements Serializable {
    @ApiModelProperty("商户手机号")
    private String mobile;

    @ApiModelProperty("商户用户名")
    private String username;

    @ApiModelProperty("商户密码")
    private String password;

    @ApiModelProperty("验证码的key")
    private String verifyKey;

    @ApiModelProperty("验证码")
    private String verifyCode;
}
