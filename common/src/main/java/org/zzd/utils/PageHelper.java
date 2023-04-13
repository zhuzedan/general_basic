package org.zzd.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装分页类
 * @author :zzd
 * @date : 2023-01-12 1:48
 */
@Data
@NoArgsConstructor
public class PageHelper<T> {
    //总条数
    private Long  totalCount;
    //总页数
    private Long totalPage;
    //当前页数
    private Long pageNum;
    //页面大小
    private Long pageSize;
    //返回数据
    private List<T> data;

    /**
     * 分页
     */
    public PageHelper(IPage<T> page) {
        this.data = page.getRecords();
        this.totalCount = page.getTotal();
    }

    public PageHelper(Long totalCount, List<T> data) {
        this.totalCount = totalCount;
        this.data = data;
    }

    public PageHelper(Long totalCount, Long totalPage, Long pageNum, Long pageSize, List<T> data) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.data = data;
    }
    public static <T> PageHelper<T> restPage(IPage<T> pageResult) {
        PageHelper<T> result = new PageHelper<>();
        result.setPageNum(pageResult.getCurrent());
        result.setPageSize(pageResult.getSize());
        result.setTotalCount(pageResult.getTotal());
        result.setTotalPage(pageResult.getTotal()%pageResult.getSize()==0?pageResult.getTotal()/pageResult.getSize():pageResult.getTotal()/pageResult.getSize()+1);
        result.setData(pageResult.getRecords());
        return result;
    }
}
