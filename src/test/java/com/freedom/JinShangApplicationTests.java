package com.freedom;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.freedom.domain.TbCash;
import com.freedom.domain.TbCompany;
import com.freedom.listener.TbCashListener;
import com.freedom.listener.TbCompanyListener;
import com.freedom.service.TbCashService;
import com.freedom.service.TbCompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class JinShangApplicationTests {

    @Resource
    private TbCashService cashService;
    @Resource
    private TbCompanyService companyService;

    @Test
    public void getAll() {
        QueryWrapper qw = new QueryWrapper<>();
        //得到所有公司编号
        qw.select("cid");
        List<Integer> list = companyService.listObjs(qw);
        //根据公司编号锁定数据编号
        QueryWrapper qw1 = new QueryWrapper<>();
        qw1.in("cid", list);

        List<TbCash> list1 = cashService.list(qw1);
        System.out.println(list1);
    }

    @Test
    public void getByCid() {
        int cid = 1;
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("cid", cid);
        List<TbCash> list = cashService.list(qw);
        System.out.println(list);
    }


    @Test
    public void simpleRead() {
        String fileName = "MulSheetFill.xlsx";

        /*EasyExcel.read(fileName, TbCompany.class, new PageReadListener<TbCompany>(dalist -> {
            System.out.println(dalist);
        })).sheet(0).doRead();
        EasyExcel.read(fileName, TbCash.class, new PageReadListener<TbCash>(dalist -> {
            System.out.println(dalist);
        })).sheet(1).headRowNumber(2).doRead();*/

        EasyExcel.read(fileName, TbCompany.class, new TbCompanyListener(companyService)).sheet(0).doRead();
        EasyExcel.read(fileName, TbCash.class, new TbCashListener(cashService)).sheet(1).headRowNumber(2).doRead();
    }

    @Test
    public void Read() {
        String fileName = "山西凝固力新型材料有限公司.xlsx";

        EasyExcel.read(fileName,TbCompany.class, new TbCompanyListener(companyService)).sheet("公司信息").doRead();
        EasyExcel.read(fileName, TbCash.class, new TbCashListener(cashService)).sheet("现金流量表").headRowNumber(2).doRead();
    }

    @Test
    public void Write() {
        String fileName = "山西省凝固力的财务报表.xlsx";
        String templateFileName = "模板.xlsx";

        List<TbCompany> companies = companyService.list();
        List<TbCash> cashes = cashService.list();

        try (ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet1 = EasyExcel.writerSheet("公司信息").build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet("现金流量表").build();
            // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。默认 是false，会直接使用下一行，如果没有则创建。
            // forceNewRow 如果设置了true,有个缺点 就是他会把所有的数据都放到内存了，所以慎用
            // 简单的说 如果你的模板有list,且list不是最后一行，下面还有数据需要填充 就必须设置 forceNewRow=true 但是这个就会把所有数据放到内存 会很耗内存
            // 如果数据量大 list不是最后一行 参照下一个
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(companies, fillConfig, writeSheet1);
            excelWriter.fill(cashes, fillConfig, writeSheet2);
            excelWriter.finish();
        }
    }

    @Test
    public void delete(){
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("cid", 1001);
        cashService.remove(qw);
        companyService.removeById(1001);
    }
}
