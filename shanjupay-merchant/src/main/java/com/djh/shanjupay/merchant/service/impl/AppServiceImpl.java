package com.djh.shanjupay.merchant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.common.util.RandomUuidUtil;
import com.djh.shanjupay.merchant.convert.AppConvert;
import com.djh.shanjupay.merchant.dto.AppDto;
import com.djh.shanjupay.merchant.entity.App;
import com.djh.shanjupay.merchant.entity.Merchant;
import com.djh.shanjupay.merchant.mapper.AppMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.djh.shanjupay.merchant.mapper.MerchantMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

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
public class AppServiceImpl extends ServiceImpl<AppMapper, App> {
    @Autowired(required = false)
    private AppMapper appMapper;

    @Autowired(required = false)
    private MerchantMapper merchantMapper;

    @Autowired(required = false)
    private AppConvert appConvert;

    public AppDto createApp (Long merchantId, AppDto appDto) {
        //校验商户是否通过资质审核
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (StringUtils.isEmpty(merchant)) {
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        if (!"2".equals(merchant.getAuditStatus())) {
            throw new BusinessException(CommonErrorCode.E_200003);
        }
        if(isExistAppName(appDto.getAppName())){
            throw new BusinessException(CommonErrorCode.E_200004);
        }
        //保存应用信息
        appDto.setAppId(RandomUuidUtil.getUuid());
        appDto.setMerchantId(merchant.getId());
        App app = appConvert.dtoToEntity(appDto);
        appMapper.insert(app);
        return appConvert.entityToDto(app);
    }

    /**
     * 校验应用名是否已被使用
     *
     * @param appName 应用程序名称
     * @return {@link Boolean}
     */
    public Boolean isExistAppName (String appName) {
        Integer count = appMapper.selectCount(new QueryWrapper<App>().lambda().eq(App::getAppName, appName));
        return count > 0;
    }
}
