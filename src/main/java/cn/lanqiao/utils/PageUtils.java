package cn.lanqiao.utils;
//分页工具类

import lombok.Data;

import java.util.List;

@Data
public class PageUtils<T> {
    private long pageIndex;//当前页面
    private long pageSize;//每页显示多少条数据
    private long totalCount;//总条数
    private long pageCount;//总页数
    private List<T> records;//每页的数据集合
    private long numberStart;//开始的页码数字
    private long numberEnd;//结束的页码数字

    public PageUtils() {
    }

    public PageUtils(long pageIndex, long pageSize, long totalCount, List<T> records) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.records = records;
        //计算总页数
        this.pageCount = (totalCount % pageSize == 0 ? (totalCount / pageSize) : (totalCount / pageSize + 1) );
        //数学算法：
        //给页码序号赋值，一共显示10个页面(动态页面)
        if (this.pageCount <= 10) {
            this.numberStart = 1;
            this.numberEnd = pageCount;
        } else {
            this.numberStart = pageIndex - 4;
            this.numberEnd = pageIndex + 5;
            if (numberStart < 1) {
                this.numberStart = 1;
                this.numberEnd = 10;
            } else if (numberEnd > pageCount) {
                this.numberEnd =pageCount;
                this.numberStart = pageCount - 9;
            }
        }
    }
}
