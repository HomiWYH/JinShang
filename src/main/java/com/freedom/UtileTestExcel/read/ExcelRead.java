package com.freedom.UtileTestExcel.read;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.freedom.domain.TbCash;
import com.freedom.domain.TbCompany;
import org.junit.Test;


public class ExcelRead {

    @Test
    public void MulSheetRead() {
        String fileName = "MulSheetFill.xlsx";

        EasyExcel.read(fileName, TbCompany.class, new PageReadListener<TbCompany>(dalist -> {
            System.out.println(dalist);
        })).sheet(0).doRead();
        EasyExcel.read(fileName, TbCash.class, new PageReadListener<TbCash>(dalist -> {
            System.out.println(dalist);
        })).sheet(1).headRowNumber(2).doRead();
    }

}
