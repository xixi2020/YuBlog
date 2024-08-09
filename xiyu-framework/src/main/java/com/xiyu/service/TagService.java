package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.TagListDto;
import com.xiyu.domain.entity.Tag;
import com.xiyu.vo.PageVo;

public interface TagService extends IService<Tag> {
    //查询标签列表
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);
}
