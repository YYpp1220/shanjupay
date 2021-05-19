package com.djh.shanjupay.transaction.dto;

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
 * 支付通道参数dto
 *
 * @author MyMrDiao
 * @date 2021/05/20
 */
@Data
@ApiModel(value="PayChannelParamDto", description="某商户针对某一种原始支付渠道的配置参数")
public class PayChannelParamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "配置名称")
    private String channelName;

    @ApiModelProperty(value = "商户ID")
    private Long merchantId;

    @ApiModelProperty(value = "原始支付渠道编码")
    private String payChannel;

    @ApiModelProperty(value = "支付参数")
    private String param;

    @ApiModelProperty(value = "应用与支付渠道绑定ID")
    private Long appPlatformChannelId;


}
