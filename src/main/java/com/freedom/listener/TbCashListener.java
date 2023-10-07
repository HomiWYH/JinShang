package com.freedom.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.freedom.domain.Code;
import com.freedom.domain.TbCash;
import com.freedom.domain.TbCompany;
import com.freedom.exception.BusinessException;
import com.freedom.service.TbCashService;

import java.util.ArrayList;
import java.util.Map;

public class TbCashListener extends AnalysisEventListener<TbCash> {
    //作为缓存
    private ArrayList<TbCash> list = new ArrayList<>();
    //缓存大小
    private final int LIST_MAX = 100;
    //操作数据库
    private TbCashService cashService;

    public TbCashListener(TbCashService cashService) {
        this.cashService = cashService;
    }

    //读取的每一行都分析一次
    @Override
    public void invoke(TbCash tbCash, AnalysisContext analysisContext) {
        list.add(tbCash);
        if (list.size() >= LIST_MAX) {
            //保存到数据库
            boolean flag = cashService.saveBatch(list);
            if (!flag) {
                throw new BusinessException(Code.BUSINESS_ERROR, "现金流文件格式有误，请修改后重新上传");
            }
            //清空缓存
            list = new ArrayList<>(LIST_MAX);
        }
    }

    //读完整个excel表格之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (list.size() > 0) {
            //将剩余数据保存到数据库
            boolean flag = cashService.saveBatch(list);
            if (!flag) {
                throw new BusinessException(Code.BUSINESS_ERROR, "现金流文件格式有误，请修改后重新上传");
            }
            //清空缓存
            list = new ArrayList<>(LIST_MAX);
        }
    }


}
