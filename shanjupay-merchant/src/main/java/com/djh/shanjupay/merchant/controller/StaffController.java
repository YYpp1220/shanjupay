package com.djh.shanjupay.merchant.controller;


import org.springframework.stereotype.Controller;
import com.djh.shanjupay.merchant.service.impl.StaffServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2021-04-14
 */
@Slf4j
@RestController
@Api(value = "", tags = "", description="")
public class StaffController {

    @Autowired
    private StaffServiceImpl staffService;
}
