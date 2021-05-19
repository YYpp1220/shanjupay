package com.djh.shanjupay.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 应用平台通道
 *
 * @author MyMrDiao
 * @date 2021/05/20
 */
@Data
@TableName("app_platform_channel")
public class AppPlatformChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 应用id
     */
    @TableField("APP_ID")
    private String appId;

    /**
     * 平台支付渠道
     */
    @TableField("PLATFORM_CHANNEL")
    private String platformChannel;


}
