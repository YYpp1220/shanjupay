package com.djh.shanjupay.transaction.service.impl;

import com.djh.shanjupay.common.enumerate.CommonErrorCode;
import com.djh.shanjupay.common.exception.BusinessException;
import com.djh.shanjupay.transaction.convert.PlatformChannelConvert;
import com.djh.shanjupay.transaction.dto.PlatformChannelDto;
import com.djh.shanjupay.transaction.entity.PlatformChannel;
import com.djh.shanjupay.transaction.mapper.PlatformChannelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2021-05-20
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class PlatformChannelServiceImpl extends ServiceImpl<PlatformChannelMapper, PlatformChannel> {
    @Autowired
    private PlatformChannelMapper platformChannelMapper;

    @Autowired
    private PlatformChannelConvert platformChannelConvert;

    /**
     * 获取平台服务类型
     *
     * @return {@link List<PlatformChannelDto>}
     */
    public List<PlatformChannelDto> queryPlatformChannel () {
        List<PlatformChannel> platformChannels = platformChannelMapper.selectList(null);
        if (StringUtils.isEmpty(platformChannels)) {
            throw new BusinessException(CommonErrorCode.E_100104);
        }
        return platformChannelConvert.entityListToDtoList(platformChannels);
    }
}
