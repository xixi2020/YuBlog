package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.AddTagDto;
import com.xiyu.domain.dto.EditTagDto;
import com.xiyu.domain.dto.TagListDto;
import com.xiyu.domain.entity.Tag;
import com.xiyu.service.TagService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.PageVo;
import com.xiyu.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 分页查询tag标签，可以通过名字和描述查询
     * @param pageNum
     * @param pageSize
     * @param tagListDto
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum, pageSize, tagListDto);
    }

    /**
     * 添加标签
     * @param tagDto
     * @return
     */
    @PostMapping
    public ResponseResult addTag(@RequestBody AddTagDto tagDto){
        Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    /**
     * 删除标签
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    /**
     * 通过id查询tag
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getLableById(@PathVariable(value = "id")Long id){
        return tagService.getLableById(id);
    }

    /**
     * 修改标签:通过id
     * @param tagVo
     * @return
     */
    @PutMapping
    public ResponseResult updateById(@RequestBody TagVo tagVo){
        return tagService.myUpdateById(tagVo);

    }

    //写博文：新增文章标签
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }



}
