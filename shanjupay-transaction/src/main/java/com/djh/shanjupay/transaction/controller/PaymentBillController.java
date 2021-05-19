package com.djh.shanjupay.transaction.controller;


import org.springframework.stereotype.Controller;
import com.djh.shanjupay.transaction.service.impl.PaymentBillServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
@Slf4j
@Controller
@Api(value = "", tags = "", description="")
public class PaymentBillController {

    @Autowired
    private PaymentBillServiceImpl paymentBillService;
}
