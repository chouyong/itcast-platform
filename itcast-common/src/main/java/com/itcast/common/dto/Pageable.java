package com.itcast.common.dto;

import java.util.List;

/**
 * 可分页数据集合对象
 *
 * @param <T> 数据记录对象
 */
public class Pageable<T> {

    // 当前页号
    private long pageNum;

    // 单页记录数
    private long pageSize;

    // 总页数
    private long pageTotal;

    // 数据记录集
    private List<T> records;

    /**
     * 构造方法
     */
    public Pageable() {
    }

    /**
     * 构造方法
     *
     * @param records 数据记录集
     */
    public Pageable(List<T> records) {
        this.pageNum = 1;
        this.pageSize = 1;
        this.pageTotal = 1;
        this.records = records;
    }

    /**
     * 构造方法
     *
     * @param pageNum   当前页号
     * @param pageSize  单页记录数
     * @param pageTotal 总页数
     * @param records   数据记录集
     */
    public Pageable(long pageNum, long pageSize, long pageTotal, List<T> records) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.records = records;
    }

    /**
     * 是否为单页对象
     *
     * @return boolean 是否单页
     */
    public boolean single() {
        return (this.pageTotal == 1);
    }

    /**
     * 是否为空对象
     *
     * @return boolean 是否为空
     */
    public boolean empty() {
        return (this.records == null || this.records.isEmpty());
    }

    public long getPageNum() {
        return pageNum;
    }

    public void setPageNum(long pageNum) {
        this.pageNum = pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(long pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
