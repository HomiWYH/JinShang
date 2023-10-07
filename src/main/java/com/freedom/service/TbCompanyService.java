package com.freedom.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.freedom.domain.TbCompany;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author ASUS
 * @description 针对表【tb_company(公司信息)】的数据库操作Service
 * @createDate 2023-09-20 14:23:41
 */
public interface TbCompanyService extends IService<TbCompany> {
    public IPage<TbCompany> getPage(int currentPage, int pageSize, TbCompany company);

    public IPage<TbCompany> getPage(int currentPage, int pageSize);
}
