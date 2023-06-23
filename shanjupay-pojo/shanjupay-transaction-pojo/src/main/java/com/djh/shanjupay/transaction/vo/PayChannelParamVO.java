package com.djh.shanjupay.transaction.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 支付通道参数签证官
 *
 * @author MyMrDiao
 * @date 2023/01/10
 */
@ApiModel(value = "PayChannelParamVO", description = "商户原始支付渠道参数配置")
@Data
public class PayChannelParamVO implements Serializable {
    @ApiModelProperty("参数配置id,新增时无需")
    private Long id;

    @ApiModelProperty("应用的appId,是业务id")
    @NotNull(message = "应用的appId不能为空")
    private String appId;

    @ApiModelProperty("应用绑定的服务类型对应的code")
    @NotNull(message = "应用绑定的服务类型对应的code不能为空")
    private String platformChannelCode;

    @ApiModelProperty("参数配置名称")
    private String channelName;

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("原始支付渠道编码")
    @NotNull(message = "原始支付渠道编码不能为空")
    private String payChannel;

    @ApiModelProperty("原始支付渠道参数配置内容，json格式")
    private String param;

    @ApiModelProperty("应用绑定的服务类型Id")
    private Long appPlatformChannelId;
}
