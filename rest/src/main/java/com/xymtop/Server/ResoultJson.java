package com.xymtop.Server;

/**
 * @ClassName : ResoultJson
 * @Description : 返回的数据状态
 * @Author : 肖叶茂
 * @Date: 2022/12/12  17:44
 */
public class ResoultJson<T> {
    private  int code;
    private  T data;
    private  String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResoultJson(int code) {
        this.code = code;
    }

    public ResoultJson(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResoultJson(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResoultJson{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public ResoultJson() {
    }
}
