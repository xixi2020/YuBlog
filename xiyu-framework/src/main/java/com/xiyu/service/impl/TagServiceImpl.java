package com.xiyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiyu.domain.ResponseResult;
import com.xiyu.domain.dto.TabListDto;
import com.xiyu.domain.dto.TagListDto;
import com.xiyu.domain.entity.LoginUser;
import com.xiyu.domain.entity.Tag;
import com.xiyu.mapper.TagMapper;
import com.xiyu.service.TagService;
import com.xiyu.utils.BeanCopyUtils;
import com.xiyu.utils.SecurityUtils;
import com.xiyu.vo.PageVo;
import com.xiyu.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagMapper tagMapper;
    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        //通过标签名模糊查询:如果没有标签名就不查
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()), Tag::getName, tagListDto.getName());
        //通过标签备注模糊查询
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()), Tag::getRemark, tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    //主要是从后台收到对应的新增消息后返回转成返回给前端的格式
    @Override
    public ResponseResult addTag(TabListDto tagListDto) {
        Tag tag = new Tag();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        tag.setCreateBy(loginUser.getUser().getId());

        //日期转换
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            //将日期转换为指定的字符串
            String strNow = dateFormat.format(date);
            //字符转成Date类型
            Date parseDate = dateFormat.parse(strNow);
            tag.setCreateTime(parseDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());

        tagMapper.insert(tag);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        Tag tag = tagMapper.selectById(id);
        if (tag != null){
            // 把 def_flag=1 为逻辑删除
            int flag = 1;
            tagMapper.myUpdateById(id, flag);
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLableById(Long id) {
        Tag tag = tagMapper.selectById(id);
        // 封装成vo响应给前端
        TagVo tagVoData = BeanCopyUtils.copyBean(tag,TagVo.class);
        return ResponseResult.okResult(tagVoData);
    }

    //主要是时间的转换：因为是从后台前端传来的
    @Override
    public ResponseResult myUpdateById(TagVo tagVo) {
        Tag tag = new Tag();
        // 获取更新时间、更新人
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 更新人的id
        tag.setUpdateBy(loginUser.getUser().getId());

        // 创建时间、更新时间
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 获取当前时间
            Date now = new Date();
            //将当前时间格式化为指定格式的字符串
            String strNow = dateFormat.format(now);
            //将字符串转换为Date类型
            Date date = dateFormat.parse(strNow);
            //最终得到的就是创建时间
            tag.setUpdateTime(date);
        } catch (ParseException e){
            e.printStackTrace();
        }

        //修改标签id、标签名、标签的描述信息
        tag.setId(tagVo.getId());
        tag.setName(tagVo.getName());
        tag.setRemark(tagVo.getRemark());

        tagMapper.updateById(tag);
        return ResponseResult.okResult();

    }

    //提供给新增文章的接口，返回转为前端实体类的对象
    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getName);
        List<Tag> tagList = list(queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyList(tagList, TagVo.class);
        return tagVos;
    }
}
