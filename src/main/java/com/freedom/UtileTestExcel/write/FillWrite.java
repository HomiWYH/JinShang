package com.freedom.UtileTestExcel.write;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.freedom.domain.TbCash;
import com.freedom.domain.TbCompany;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FillWrite {



    @Test
    public void mulSheetFill() {
        String fileName = "MulSheetFill.xlsx";

        String templateFileName = "模板.xlsx";

        TbCash cash1 = new TbCash(1L, "撒打算放", 1L, 2123.21, 201);
        TbCash cash2 = new TbCash(2L, "斯卡哈空间大", 2L, 132123.2, 201);
        TbCash cash3 = new TbCash(3L, "环保局部分", 3L, 132123.2, 201);
        TbCash cash4 = new TbCash(4L, "分发大", 4L, 132123.2, 202);
        TbCash cash5 = new TbCash(5L, "能看见你大", 5L, 132123.2, 202);
        List<TbCash> cashList = new ArrayList<>();
        cashList.add(cash1);
        cashList.add(cash2);
        cashList.add(cash3);
        cashList.add(cash4);
        cashList.add(cash5);

        TbCompany company1 = new TbCompany(201, "郑州有限公司", "事不大科技的把控记得把垃圾");
        TbCompany company2 = new TbCompany(202, "杭州有限公司", "安居客第八课就打不开");
        List<TbCompany> companyList = new ArrayList<>();
        companyList.add(company1);
        companyList.add(company2);

        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet1 = EasyExcel.writerSheet(0).build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet(1).build();
            // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
            // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
            // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
            // 如果数据量大 list不是最后一行 参照下一个
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(companyList, fillConfig, writeSheet1);
            excelWriter.fill(cashList, fillConfig, writeSheet2);
            excelWriter.finish();
        }


    }
}
