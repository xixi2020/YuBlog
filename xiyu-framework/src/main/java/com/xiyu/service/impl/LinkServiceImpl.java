package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.constants.SystemConstants;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.entity.Link;
import com.xiyu.mapper.LinkMapper;
import com.xiyu.service.LinkService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {
    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> linkQueryWrapper = new LambdaQueryWrapper<>();
        linkQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(linkQueryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyList(linkList, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}
