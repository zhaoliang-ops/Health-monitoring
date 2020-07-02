package org.gv_data.hmt.model;

import java.util.List;

/**
 * @author zhaoliang
 * @desc 健康监控系统
 * @时间 2019-10-29 7:42
 */
public class RespPageBean {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
