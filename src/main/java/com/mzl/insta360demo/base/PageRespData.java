package com.mzl.insta360demo.base;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PageRespData
 * @Description: 分页响应体
 * @Author: mzl
 * @CreateDate: 2023/12/4 13:44
 * @Version: 1.0
 */
@Data
public class PageRespData<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    private int currPage;

    /**
     * 每页记录数
     */
    private int pageSize;

    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 列表数据
     */
    private List<T> list;

    public PageRespData(List<T> list, int currPage, int pageSize, long totalCount, int totalPage) {
        this.list = list;
        this.currPage = currPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }

}