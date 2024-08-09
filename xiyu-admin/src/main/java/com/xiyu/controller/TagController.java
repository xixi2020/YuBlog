package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.AddTagDto;
import com.xiyu.domain.dto.EditTagDto;
import com.xiyu.domain.dto.TagListDto;
import com.xiyu.domain.entity.Tag;
import com.xiyu.service.TagService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        tagService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 通过id查询tag
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }

    /**
     * 修改标签
     * @param editTagDto
     * @return
     */
    @PutMapping
    public ResponseResult editTag(@RequestBody EditTagDto editTagDto){
        Tag tag = BeanCopyUtils.copyBean(editTagDto, Tag.class);
        tagService.updateById(tag);
        return ResponseResult.okResult();

    }




}
