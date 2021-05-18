package com.djh.shanjupay.merchant.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 应用dto
 *
 * @author MyMrDiao
 * @date 2021/05/19
 */
@Data
@ApiModel(value="AppDto", description="应用信息")
public class AppDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用id，新增时无需传入")
    private String appId;

    @ApiModelProperty(value = "商店名称")
    private String appName;

    @ApiModelProperty(value = "所属商户")
    private Long merchantId;

    @ApiModelProperty(value = "应用公钥(RSAWithSHA256)")
    private String publicKey;

    @ApiModelProperty(value = "授权回调地址，创建时可不填")
    private String notifyUrl;


}
