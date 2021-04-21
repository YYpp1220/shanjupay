package com.djh.shanjupay.merchant.service.impl;

import com.djh.shanjupay.merchant.entity.Staff;
import com.djh.shanjupay.merchant.mapper.StaffMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2021-04-14
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> {
    @Autowired(required = false)
    private StaffMapper staffMapper;
}
