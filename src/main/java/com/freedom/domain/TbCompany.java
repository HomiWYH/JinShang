package com.freedom.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司信息
 *
 * @TableName tb_company
 */
@TableName(value = "tb_company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbCompany implements Serializable {
    /**
     * 公司编号
     */
    @ExcelProperty(value = "公司编号", index = 0)
    @TableId
    private Integer cid;

    /**
     * 公司名称
     */
    @ExcelProperty(value = "公司名称", index = 1)
    private String companyName;

    /**
     * 公司简介
     */
    @ExcelProperty(value = "公司简介", index = 2)
    private String information;

    @TableField(exist = false)
    @ExcelIgnore
    private static final long serialVersionUID = 1L;
}