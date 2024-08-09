package com.xiyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.TabListDto;
import com.xiyu.domain.dto.TagListDto;
import com.xiyu.domain.entity.Tag;
import com.xiyu.vo.PageVo;
import com.xiyu.vo.TagVo;

import java.util.List;

public interface TagService extends IService<Tag> {
    /**
     * 查询标签列表
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    /**
     * 新增标签
     * @param tagListDto
     * @return
     */
    ResponseResult addTag(TabListDto tagListDto);

    /**
     * 删除标签
     * @param id
     * @return
     */
    ResponseResult deleteTag(Long id);

    /**
     * 修改标签-①根据标签的id来查询标签
     * @param id
     * @return
     */
    ResponseResult getLableById(Long id);

    /**
     * 修改标签-②根据标签的id来修改标签
     * @param tagVo
     * @return
     */
    ResponseResult myUpdateById(TagVo tagVo);

    /**
     * 写博文-查询文章标签的接口
     * @return
     */
    List<TagVo> listAllTag();
}
