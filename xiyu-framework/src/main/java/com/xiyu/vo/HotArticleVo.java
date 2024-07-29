package com.xiyu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 和前端接口联调时规定的接口，建议从entity复制避免出错
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HotArticleVo {
    private Long id;
    //标题
    private String title;

    //访问量
    private Long viewCount;
}
