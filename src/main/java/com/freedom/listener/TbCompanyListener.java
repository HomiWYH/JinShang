package com.freedom.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.freedom.domain.Code;
import com.freedom.domain.TbCash;
import com.freedom.domain.TbCompany;
import com.freedom.exception.BusinessException;
import com.freedom.service.TbCompanyService;

import java.util.ArrayList;

public class TbCompanyListener extends AnalysisEventListener<TbCompany> {
    //作为缓存
    private ArrayList<TbCompany> list = new ArrayList<>();
    //缓存大小
    private final int LIST_MAX = 100;
    private TbCompanyService companyService;

    public TbCompanyListener(TbCompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public void invoke(TbCompany tbCompany, AnalysisContext analysisContext) {
        list.add(tbCompany);
        if (list.size() >= LIST_MAX) {
            //保存到数据库
            boolean flag = companyService.saveBatch(list);
            if (!flag) {
                throw new BusinessException(Code.BUSINESS_ERROR, "公司信息文件格式有误，请修改后重新上传");
            }

            //清空缓存
            list = new ArrayList<>(LIST_MAX);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (list.size() > 0) {
            //将剩余数据保存到数据库
            boolean flag = companyService.saveBatch(list);
            if (!flag) {
                throw new BusinessException(Code.BUSINESS_ERROR, "公司信息文件格式有误，请修改后重新上传");
            }
            //清空缓存
            list = new ArrayList<>(LIST_MAX);
        }
    }
}
