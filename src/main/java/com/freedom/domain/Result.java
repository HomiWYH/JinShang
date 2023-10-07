package com.freedom.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;//存储状态码
    private Object data;//存储数据
    private String msg;//存储程序执行后的信息，（成功或者报错信息）

    public Result(Integer code, Object data) {
        this.data = data;
        this.code = code;
    }
}
