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
 * 现金流表
 *
 * @TableName tb_cash
 */
@TableName(value = "tb_cash")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TbCash implements Serializable {
    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Long sid;

    /**
     * 项目名称
     */
    @ExcelProperty(value = "项目名称", index = 0)
    private String item;

    /**
     * 行次
     */
    @ExcelProperty(value = "行次", index = 1)
    private Long num;

    /**
     * 金额
     */
    @ExcelProperty(value = "金额", index = 2)
    private Double amount;

    /**
     * 公司编号
     */
    @ExcelProperty(value = "公司编号", index = 3)
    private Integer cid;

    @TableField(exist = false)
    @ExcelIgnore
    private static final long serialVersionUID = 1L;
}