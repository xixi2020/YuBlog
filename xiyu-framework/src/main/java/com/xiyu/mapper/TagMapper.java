package com.xiyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiyu.domain.entity.Tag;
import io.lettuce.core.dynamic.annotation.Param;

public interface TagMapper extends BaseMapper<Tag>{
    //删除标签，逻辑删除
    int myUpdateById(@Param("id")Long id, @Param("flag") int flag);
}