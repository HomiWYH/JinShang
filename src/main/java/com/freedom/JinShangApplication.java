package com.freedom;

import com.alibaba.excel.EasyExcel;
import com.freedom.domain.TbCash;
import com.freedom.listener.TbCashListener;
import com.freedom.service.TbCashService;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class JinShangApplication {

    public static void main(String[] args) {
        SpringApplication.run(JinShangApplication.class, args);
    }

}
