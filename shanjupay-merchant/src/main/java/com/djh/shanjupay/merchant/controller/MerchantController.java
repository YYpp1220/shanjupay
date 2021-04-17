package com.djh.shanjupay.merchant.controller;


import com.djh.shanjupay.merchant.dto.MerchantDto;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import com.djh.shanjupay.merchant.service.impl.MerchantServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2021-04-14
 */
@Slf4j
@Controller
@Api(value = "商户平台-商户相关", tags = "商户平台-商户相关")
public class MerchantController {
    public static final String SHANJUPAY_NAME = "商户";

    @Autowired
    private MerchantServiceImpl merchantService;

    @ApiOperation(value = "根据商户id查询")
    @ApiImplicitParam(name = "merchantId", value = "商户id", dataType = "Long", required = true)
    @GetMapping("/queryById")
    public ResponseEntity<MerchantDto> queryById (@RequestParam Long merchantId) {
        MerchantDto merchantDto = merchantService.queryById(merchantId);
        return ResponseEntity.ok(merchantDto);
    }
}
