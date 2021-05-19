package com.djh.shanjupay.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 支付通道参数
 *
 * @author MyMrDiao
 * @date 2021/05/20
 */
@Data
@TableName("pay_channel_param")
public class PayChannelParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 配置名称
     */
    @TableField("CHANNEL_NAME")
    private String channelName;

    /**
     * 商户ID
     */
    @TableField("MERCHANT_ID")
    private Long merchantId;

    /**
     * 原始支付渠道编码
     */
    @TableField("PAY_CHANNEL")
    private String payChannel;

    /**
     * 支付参数
     */
    @TableField("PARAM")
    private String param;

    /**
     * 应用与支付渠道绑定ID
     */
    @TableField("APP_PLATFORM_CHANNEL_ID")
    private Long appPlatformChannelId;


}
