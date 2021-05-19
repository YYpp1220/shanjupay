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
 * 应用平台通道dto
 *
 * @author MyMrDiao
 * @date 2021/05/20
 */
@Data
@ApiModel(value="AppPlatformChannelDto", description="说明了应用选择了平台中的哪些支付渠道")
public class AppPlatformChannelDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "应用id")
    private String appId;

    @ApiModelProperty(value = "平台支付渠道")
    private String platformChannel;


}
