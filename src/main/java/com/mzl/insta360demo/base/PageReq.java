package com.mzl.insta360demo.base;

import lombok.Data;

/**
 * @ClassName: PageReq
 * @Description: 分页请求体
 * @Author: mzl
 * @CreateDate: 2023/12/4 13:43
 * @Version: 1.0
 */
@Data
public class PageReq {

    /**
     * 当前页
     */
    private Integer pageNumber = 1;

    /**
     * 页大小
     */
    private Integer pageSize = 10;

}