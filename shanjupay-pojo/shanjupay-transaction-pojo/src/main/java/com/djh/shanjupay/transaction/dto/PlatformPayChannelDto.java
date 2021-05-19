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
 * 平台支付通道dto
 *
 * @author MyMrDiao
 * @date 2021/05/20
 */
@Data
@ApiModel(value="PlatformPayChannelDto", description="")
public class PlatformPayChannelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "平台支付渠道编码")
    private String platformChannel;

    @ApiModelProperty(value = "原始支付渠道名称")
    private String payChannel;


}
