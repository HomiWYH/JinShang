package com.freedom.mapper;

import com.freedom.domain.TbCompany;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ASUS
* @description 针对表【tb_company(公司信息)】的数据库操作Mapper
* @createDate 2023-09-20 14:23:41
* @Entity com.freedom.domain.TbCompany
*/
@Mapper
public interface TbCompanyMapper extends BaseMapper<TbCompany> {

}




