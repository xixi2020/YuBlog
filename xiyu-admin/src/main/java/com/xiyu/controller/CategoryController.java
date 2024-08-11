package com.xiyu.controller;

import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.CategoryDto;
import com.xiyu.domain.entity.Category;
import com.xiyu.service.CategoryService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.vo.AdminCategoryVo;
import com.xiyu.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 写博文查询文章接口
     * @return
     */
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        List<AdminCategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }

    /**
     * 分页查询分类列表
     */
    @GetMapping("/list")
    public ResponseResult list(Category category, Integer pageNum, Integer pageSize){
        PageVo pageVos = categoryService.selectCategoryPage(category, pageNum, pageSize);
        return ResponseResult.okResult(pageVos);
    }

    /**
     * 新增分类
     * @param categoryDto
     * @return
     */
    @PostMapping
    public ResponseResult add(@RequestBody CategoryDto categoryDto){
        Category category = BeanCopyUtils.copyBean(categoryDto, Category.class);
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable(value = "id") Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    /**
     * 修改分类
     * @param category
     * @return
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Category category){
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }
}