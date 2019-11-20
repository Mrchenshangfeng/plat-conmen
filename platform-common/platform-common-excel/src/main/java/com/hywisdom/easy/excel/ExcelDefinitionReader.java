package com.hywisdom.easy.excel;

import java.util.Map;

import com.hywisdom.easy.excel.config.ExcelDefinition;

/**
 * Excel定义接口
 * @author wangxz
 *
 */
public interface ExcelDefinitionReader {
	/**
	 * 获取 ExcelDefinition注册信息
	 * @return
	 */
	Map<String, ExcelDefinition> getRegistry();
}
