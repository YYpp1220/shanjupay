package com.djh.shanjupay.merchant.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@ApiModel(value="StaffDto", description="")
public class StaffDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商户ID")
    private Long merchantId;

    @ApiModelProperty(value = "姓名")
    private String fullName;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "用户名(关联统一用户)")
    private String username;

    @ApiModelProperty(value = "手机号(关联统一用户)")
    private String mobile;

    @ApiModelProperty(value = "员工所属门店")
    private Long storeId;

    @ApiModelProperty(value = "最后一次登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty(value = "0表示禁用，1表示启用")
    private Boolean staffStatus;


}
