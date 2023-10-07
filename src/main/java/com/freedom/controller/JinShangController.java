package com.freedom.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.freedom.domain.Code;
import com.freedom.domain.Result;
import com.freedom.domain.TbCash;
import com.freedom.domain.TbCompany;
import com.freedom.exception.BusinessException;
import com.freedom.exception.SystemException;
import com.freedom.listener.TbCashListener;
import com.freedom.listener.TbCompanyListener;
import com.freedom.service.TbCashService;
import com.freedom.service.TbCompanyService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jinshang")
public class JinShangController {
    @Resource
    private TbCashService cashService;

    @Resource
    private TbCompanyService companyService;


    /**
     * 下载数据库内所有的内容到excel
     *
     * @param response
     * @throws IOException
     */
    /*@GetMapping("/download")
    public Result download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        List<TbCompany> companies = companyService.list();
        List<TbCash> cashes = cashService.list();

        String templateFileName = "模板.xlsx";


        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet1 = EasyExcel.writerSheet("公司信息").build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet("现金流量表").build();

            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(companies, fillConfig, writeSheet1);
            excelWriter.fill(cashes, fillConfig, writeSheet2);
            excelWriter.finish();
        }
        return new Result(Code.DOWNLOAD_OK, true, "文件下载完成");
    }*/

    /**
     * 下载模板
     *
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/download")
    public Result download(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        TbCompany company = new TbCompany(1001, "大力集团", "美女多，帅哥多。");
        List<TbCompany> companies = new ArrayList<>();
        companies.add(company);

        TbCash cash = new TbCash(1L, "大力", 1L, 111.11, 1001);
        List<TbCash> cashes = new ArrayList<>();
        cashes.add(cash);

        String templateFileName = "模板2.xlsx";


        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet1 = EasyExcel.writerSheet("公司信息").build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet("现金流量表").build();

            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(companies, fillConfig, writeSheet1);
            excelWriter.fill(cashes, fillConfig, writeSheet2);
            excelWriter.finish();
        }
        return new Result(Code.DOWNLOAD_OK, true, "文件下载完成");
    }

    /**
     * 根据公司编号（cid）下载excel表格
     *
     * @param response
     * @param cid
     * @throws IOException
     */
    @GetMapping("/download/{cid}")
    public Result downloadByCid(HttpServletResponse response, @PathVariable int cid) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        TbCompany company = companyService.getById(cid);
        List<TbCompany> companies = new ArrayList<>();
        companies.add(company);
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("cid", cid);
        List<TbCash> cashes = cashService.list(qw);

        String templateFileName = "模板.xlsx";
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).withTemplate(templateFileName).build()) {
            WriteSheet writeSheet1 = EasyExcel.writerSheet("公司信息").build();
            WriteSheet writeSheet2 = EasyExcel.writerSheet("现金流量表").build();

            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
            excelWriter.fill(companies, fillConfig, writeSheet1);
            excelWriter.fill(cashes, fillConfig, writeSheet2);
            excelWriter.finish();
        }
        return new Result(Code.DOWNLOAD_OK, true, "文件下载完成");
    }

    /**
     * 上传exccel表格
     *
     * @param file
     * @param response
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file, HttpServletResponse response) throws IOException {
        try {
            EasyExcel.read(file.getInputStream(), TbCompany.class, new TbCompanyListener(companyService)).sheet("公司信息").doRead();
            EasyExcel.read(file.getInputStream(), TbCash.class, new TbCashListener(cashService)).sheet("现金流量表").headRowNumber(2).doRead();
        } catch (Exception e) {
            throw new SystemException(Code.UPLOAD_ERROR, "文件格式错误，请审查后提交");
        }
        return new Result(Code.UPLOAD_OK, true, "文件上传成功");
    }

    /**
     * 打印公司信息数据库的所有内容
     *
     * @return
     */
    @GetMapping
    public Result getAll() {
        List<TbCompany> list = companyService.list();
        Integer code = list != null ? Code.GET_OK : Code.GET_ERROR;
        String message = list != null ? "" : "数据库查询失败，请重试";
        return new Result(code, list, message);
    }

    /**
     * 获取所有现金流表信息
     */
    @GetMapping("/information/{cid}")
    public Result getAllCash(@PathVariable Integer cid) {
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("cid", cid);
        List<TbCash> list = cashService.list(qw);
        Integer code = list != null ? Code.GET_OK : Code.GET_ERROR;
        String message = list != null ? "" : "数据库查询失败，请重试";
        return new Result(code, list, message);
    }

    /**
     * 根据公司编号（cid）删除某一条公司以及其表单信息的数据
     *
     * @param cid
     */
    @DeleteMapping("{cid}")
    public Result DeleteById(@PathVariable int cid) {
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("cid", cid);
        boolean cashRemove = cashService.remove(qw);
        boolean companyRemove = companyService.removeById(cid);
        System.out.println(cashRemove + "    " + companyRemove);
        if (cashRemove || companyRemove) {
            return new Result(Code.DELETE_OK, true, "删除成功");
        } else {
            return new Result(Code.DELETE_ERROR, false, "删除失败");
        }
    }
    /**
     * 根据公司编号（cid）获取其公司信息
     *
     * @param cid
     * @return
     */
    @GetMapping("{cid}")
    public Result getByCid(@PathVariable int cid) {
        TbCompany company = companyService.getById(cid);
        Integer code = company != null ? Code.GET_OK : Code.GET_ERROR;
        String message = company != null ? "" : "数据查询失败，请重试！";
        return new Result(code, company, message);
    }



    /**
     * 对公司信息进行修改
     *
     * @param company
     */
    @PutMapping
    public Result update(@RequestBody TbCompany company) {
        boolean flag = companyService.updateById(company);
        return new Result(flag ? Code.UPDATE_OK : Code.UPDATE_ERROR, flag, flag ? "" : "数据编辑失败，请重试!");
    }

    /**
     * 分页显示
     * @param currentPage
     * @param pageSize
     * @return
     */
    /*@GetMapping("{currentPage}/{pageSize}")
    public IPage<TbCompany> getPage(@PathVariable int currentPage, @PathVariable int pageSize) {
        IPage<TbCompany> page = companyService.getPage(currentPage, pageSize);
        //如果当前页码值大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = companyService.getPage((int) page.getPages(), pageSize);
        }
        return page;
    }*/

    /**
     * 分页显示，并且包括查询功能
     *
     * @param currentPage 当前页
     * @param pageSize    每页显示数量
     * @param company     要查询的公司信息，模糊查询
     * @return
     */
    @GetMapping("{currentPage}/{pageSize}")
    public Result getPage(@PathVariable int currentPage, @PathVariable int pageSize, TbCompany company) {
        IPage<TbCompany> page = companyService.getPage(currentPage, pageSize, company);
        //如果当前页码值大于了总页码值，那么重新执行查询操作，使用最大页码值作为当前页码值
        if (currentPage > page.getPages()) {
            page = companyService.getPage((int) page.getPages(), pageSize, company);
        }
        return new Result(Code.GET_OK, page);
    }

    /**
     * 用于添加信息
     *
     * @param company
     */
    @PostMapping
    public Result insert(@RequestBody TbCompany company) {
        boolean flag = companyService.save(company);
        return new Result(flag ? Code.SAVE_OK : Code.SAVE_ERROR, flag, flag ? "" : "数据添加失败，请重试!");
    }
}
