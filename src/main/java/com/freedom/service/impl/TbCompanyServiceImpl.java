package com.freedom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freedom.service.TbCompanyService;
import com.freedom.domain.TbCompany;
import com.freedom.mapper.TbCompanyMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ASUS
 * @description 针对表【tb_company(公司信息)】的数据库操作Service实现
 * @createDate 2023-09-20 14:23:41
 */
@Service
public class TbCompanyServiceImpl extends ServiceImpl<TbCompanyMapper, TbCompany>
        implements TbCompanyService {
    @Resource
    private TbCompanyMapper companyMapper;

    @Override
    public IPage<TbCompany> getPage(int currentPage, int pageSize, TbCompany company) {
        /*LambdaQueryWrapper<TbCompany> lqw = new LambdaQueryWrapper<TbCompany>();
        lqw.like(TbCompany::getCid, company.getCid());
        lqw.like(Strings.isNotEmpty(company.getCompanyName()), TbCompany::getCompanyName, company.getCompanyName());*/
        QueryWrapper<TbCompany> qw = new QueryWrapper<>();
        qw.like(company.getCid() != null, "cid", company.getCid());
        qw.like(Strings.isNotEmpty(company.getCompanyName()), "companyName", company.getCompanyName());
        IPage page = new Page(currentPage, pageSize);
        companyMapper.selectPage(page, qw);
        return page;
    }

    @Override
    public IPage<TbCompany> getPage(int currentPage, int pageSize) {
        IPage page = new Page(currentPage, pageSize);
        companyMapper.selectPage(page, null);
        return page;
    }
}




