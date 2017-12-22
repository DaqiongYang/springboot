package com.github.xuybin.springboot.model;

/**
 * 包含分页的数据 返回响应包装泛型
 * Created by xuybin on 2016/7/10.
 * @param <T>  返回的结果数据类型
 */
public class Paginator<T> {
    /*页号*/
    int pageIndex;
    /*每页数据含有的数据条数*/
    int pageSize;
    /*总页数*/
    int totalPages;

    /*总数据量*/
    int totalCount;

    /*返回的结果*/
    T data;

    public Paginator(T data) {
        this.data = data;
    }

    public Paginator(int pageIndex, int pageSize, int totalPages, int totalCount, T data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        this.data = data;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Paginator{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", data=" + data +
                '}';
    }
}
