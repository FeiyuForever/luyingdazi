package com.luyingdazi.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页响应封装
 *
 * @author luyingdazi
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 总记录数 */
    private long total;

    /** 当前页码 */
    private int pageNum;

    /** 每页大小 */
    private int pageSize;

    /** 总页数 */
    private int pages;

    /** 数据列表 */
    private List<T> list;

    public static <T> PageResult<T> of(List<T> list, long total, int pageNum, int pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setList(list);
        result.setTotal(total);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setPages((int) Math.ceil((double) total / pageSize));
        return result;
    }

    public static <T> PageResult<T> empty(int pageNum, int pageSize) {
        PageResult<T> result = new PageResult<>();
        result.setList(Collections.emptyList());
        result.setTotal(0);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setPages(0);
        return result;
    }
}
