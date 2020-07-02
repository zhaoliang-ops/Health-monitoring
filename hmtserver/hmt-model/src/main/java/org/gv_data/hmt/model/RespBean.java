package org.gv_data.hmt.model;

/**
 * @author zhaoliang
 * @desc 健康监控系统
 * @时间 2019-09-20 8:39
 */
public class RespBean {
    private Integer status;
    private String msg;
    private Object obj;

    public static org.gv_data.hmt.model.RespBean build() {
        return new org.gv_data.hmt.model.RespBean();
    }

    public static org.gv_data.hmt.model.RespBean ok(String msg) {
        return new org.gv_data.hmt.model.RespBean(200, msg, null);
    }

    public static org.gv_data.hmt.model.RespBean ok(String msg, Object obj) {
        return new org.gv_data.hmt.model.RespBean(200, msg, obj);
    }

    public static org.gv_data.hmt.model.RespBean error(String msg) {
        return new org.gv_data.hmt.model.RespBean(500, msg, null);
    }

    public static org.gv_data.hmt.model.RespBean error(String msg, Object obj) {
        return new org.gv_data.hmt.model.RespBean(500, msg, obj);
    }

    private RespBean() {
    }

    private RespBean(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public Integer getStatus() {
        return status;
    }

    public org.gv_data.hmt.model.RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public org.gv_data.hmt.model.RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public org.gv_data.hmt.model.RespBean setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}
