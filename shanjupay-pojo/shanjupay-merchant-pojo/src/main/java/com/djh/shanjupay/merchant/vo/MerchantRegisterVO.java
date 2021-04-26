package com.djh.shanjupay.merchant.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotNull(message = "手机号不能为空！")
    @Pattern(regexp = "^(?:(?:|00)86)?1(?:(?:3[d])|(?:4[5-7|9])|(?:5[0-3|5-9])|(?:6[5-7])|(?:7[0-8])|(?:8[d])|(?:9[1|8|9]))d{8}$", message = "手机号有误，请重新输入！")
    private String mobile;

    @ApiModelProperty("商户用户名")
    @NotNull(message = "联系人名称不能为空")
    @Length(min = 2, max = 6, message = "联系人名称两到六个字符，最多六个字符！")
    @Pattern(regexp = "^(?:[u4e00-u9fa5-Za-z0-9]{2,6})$", message = "联系人昵称限制：联系人名称最多六个字符！")
    private String username;

    @ApiModelProperty("商户密码")
    @NotNull(message = "密码不能为空！")
    @Length(min = 6, max = 16, message = "密码为6到16位大小写英文，数字，特殊符号！")
    @Pattern(regexp = "^S*(?=S{6,16})(?=S*d)(?=S*[A-Z])(?=S*[a-z])(?=S*[!@#$%^&*? ])S*$", message = "密码为6到16位大小写英文，数字，特殊符号！至少包含1个大写字母，1个小写字母，1个数字，1个特殊字符。")
    private String password;

    @ApiModelProperty("验证码的key")
    private String verifyKey;

    @ApiModelProperty("验证码")
    private String verifyCode;
}
