package com.cy.pj.common.pojo;

import lombok.Setter;

import java.io.Serializable;

/**借助此对象封装控制层响应到客户端的数据*/
@Setter
public class JsonResult implements Serializable {
    private static final long serialVersionUID = -8722122492343039602L;
    /**响应状态码*/
    private Integer state=1;//1 表示ok,0表示error
    /**响应状态码对应的具体信息*/
    private String message="ok";
    /**响应数据(一般是查询时获取到的正确数据)*/
    private Object data;
    public JsonResult(){}
    public JsonResult(String message){
        this.message=message;
    }

    public JsonResult(Object data){
        this.data=data;
    }

    public JsonResult(Throwable e){
        this.state=0;
        this.message=e.getMessage();
    }

    public Integer getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
