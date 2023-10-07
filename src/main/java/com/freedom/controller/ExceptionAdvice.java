package com.freedom.controller;

import com.freedom.domain.Code;
import com.freedom.domain.Result;
import com.freedom.exception.BusinessException;
import com.freedom.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException be) {
        return new Result(be.getCode(), null, be.getMessage());
    }

    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException se) {
        return new Result(se.getCode(), null, se.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public Result doExcelLoadAndDownload(IOException ioe) {
        return new Result(Code.IO_ERROR, null, "文件路径错误，请修改后上传");
    }

    @ExceptionHandler(Exception.class)
    public Result doOtherException(Exception ex) {
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        return new Result(Code.SYSTEM_UNKNOW_ERROR, null, "系统繁忙，请稍后再试！");
    }
}
