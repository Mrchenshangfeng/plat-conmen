package com.hywisdom.easy.excel.parsing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.hywisdom.easy.excel.config.ExcelConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hywisdom.easy.excel.ExcelDefinitionReader;
import com.hywisdom.easy.excel.config.ExcelDefinition;
import com.hywisdom.easy.excel.config.FieldValue;
import com.hywisdom.easy.excel.exception.ExcelException;
import com.hywisdom.easy.util.ReflectUtil;

/**
 * Excel导出实现类
 * @author wangxz
 *
 */
public class ExcelExport extends AbstractExcelResolver{
	private ExcelConfig config = null;
	private ExcelDefinition excelDefinition = null;
	private Sheet sheet= null;
	private Workbook workbook = new SXSSFWorkbook();	
	private Row titleRow = null;;
	
	public ExcelExport(ExcelDefinitionReader definitionReader) {
		super(definitionReader);
	}
	
	public ExcelConfig getConfig() {
		return config;
	}

	public Row getTitleRow() {
		return titleRow;
	}

	public void setTitleRow(Row titleRow) {
		this.titleRow = titleRow;
	}

	public void setConfig(ExcelConfig config) {
		this.config = config;
	}
	
	public ExcelDefinition getExcelDefinition() {
		return excelDefinition;
	}

	public void setExcelDefinition(ExcelDefinition excelDefinition) {
		this.excelDefinition = excelDefinition;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	/**
	 * 创建导出Excel,如果集合没有数据,返回null
	 * @param id	 ExcelXML配置Bean的ID
	 * @param beans  ExcelXML配置的bean集合
	 * @param header Excel头信息(在标题之前)
	 * @param fields 指定导出的字段
	 * @return
	 * @throws Exception
	 *//*
	public ExcelExportResult createExcel(String id, List<?> beans,ExcelHeader header,List<String> fields) throws Exception{
		return this.createExcel(id, null, beans, header, fields);
	}*/
	
	public Workbook createExcel(ExcelConfig config) throws Exception{
		this.config = config;
		Set<Entry<String, List<?>>> entrySet = config.getDataMap().entrySet();
		//根据excel模板初始化workbook
		initWorkBookByTemplate();
		String flag = "";
		for (Entry<String, List<?>> entry : entrySet) {
			//从注册信息中获取Bean信息
			excelDefinition = definitionReader.getRegistry().get(entry.getKey());
			if(excelDefinition==null){
				throw new ExcelException("没有找到 ["+entry.getKey()+"] 的配置信息");
			}
			String type = excelDefinition.getType();
			if(type!=null){
				flag+=(type+";");
			}else{
				flag+="0;";
			}
			appendSheet(excelDefinition,entry.getValue());
		}
		setExcelFlag(workbook, flag);
		return workbook;
	}
	
	private Workbook initWorkBookByTemplate() throws InvalidFormatException, IOException{
		if(this.getConfig().getTemplatePath() != null){
			File file = new File(this.getConfig().getTemplatePath());
			if(file.exists()){
				workbook = WorkbookFactory.create(file);
			}
		}
		return workbook;
	}
	
	/**
	 * 创建导出Excel,如果集合没有数据,返回null
	 * @param id	 ExcelXML配置Bean的ID
	 * @param beans  ExcelXML配置的bean集合
	 * @param header Excel头信息(在标题之前)
	 * @return
	 * @throws Exception
	 */
	public Workbook createExcel(String id, List<?> beans,ExcelHeader header,List<String> fields) throws Exception{
		if(CollectionUtils.isNotEmpty(beans)){
			//从注册信息中获取Bean信息
			ExcelDefinition excelDefinition = definitionReader.getRegistry().get(id);
			if(excelDefinition==null){
				throw new ExcelException("没有找到 ["+id+"] 的配置信息");
			}
			String type = excelDefinition.getType();
			if(type!=null){
				setExcelFlag(workbook, type);
			}
			//实际传入的bean类型
			Class<?> realClass = beans.get(0).getClass();
			//传入的类型是excel配置class的类型,或者是它的子类,直接进行生成
			if(realClass==excelDefinition.getClazz() || TypeUtils.isAssignable(excelDefinition.getClazz(),realClass)){
				//导出指定字段的标题不是null,动态创建,Excel定义
				excelDefinition = dynamicCreateExcelDefinition(excelDefinition,fields);
			}
			//传入的类型是excel配置class的类型的父类,那么进行向上转型,只获取配置中父类存在的属性
			else if(TypeUtils.isAssignable(realClass,excelDefinition.getClazz())){
				excelDefinition = extractSuperClassFields(excelDefinition, fields, realClass);
			}else{
				//判断传入的集合与配置文件中的类型拥有共同的父类,如果有则向上转型
				Object superClass = ReflectUtil.getEqSuperClass(realClass, excelDefinition.getClazz());
				if(superClass!=Object.class){
					excelDefinition = extractSuperClassFields(excelDefinition, fields, realClass);
				}else{
					throw new ExcelException("传入的参数类型是:"+beans.get(0).getClass().getName()
							+"但是 配置文件的类型是: "+excelDefinition.getClazz().getName()+",参数既不是父类,也不是其相同父类下的子类,无法完成转换");
				}
				
			}
			workbook = appendSheet(excelDefinition,beans);
		}
		return workbook;
	}

	/**
	 * 创建导出Excel,如果集合没有数据,返回null
	 * @param id	 ExcelXML配置Bean的ID
	 * @param beans  ExcelXML配置的bean集合
	 * @param header Excel头信息(在标题之前)
	 * @param fields 指定导出的字段
	 * @return
	 * @throws Exception
	 */
	public Workbook createExcel(String id, List<?> beans,ExcelHeader header,List<String> fields,Map<String, Object> customMap) throws Exception{
		if(CollectionUtils.isNotEmpty(beans)){
			//从注册信息中获取Bean信息
			ExcelDefinition excelDefinition = definitionReader.getRegistry().get(id);
			if(excelDefinition==null){
				throw new ExcelException("没有找到 ["+id+"] 的配置信息");
			}
			String type = excelDefinition.getType();
			if(type!=null){
				setExcelFlag(workbook, type);
			}
			//实际传入的bean类型
			Class<?> realClass = beans.get(0).getClass();
			//传入的类型是excel配置class的类型,或者是它的子类,直接进行生成
			if(realClass==excelDefinition.getClazz() || TypeUtils.isAssignable(excelDefinition.getClazz(),realClass)){
				//导出指定字段的标题不是null,动态创建,Excel定义
				excelDefinition = dynamicCreateExcelDefinition(excelDefinition,fields);
			}
			//传入的类型是excel配置class的类型的父类,那么进行向上转型,只获取配置中父类存在的属性
			else if(TypeUtils.isAssignable(realClass,excelDefinition.getClazz())){
				excelDefinition = extractSuperClassFields(excelDefinition, fields, realClass);
			}else{
				//判断传入的集合与配置文件中的类型拥有共同的父类,如果有则向上转型
				Object superClass = ReflectUtil.getEqSuperClass(realClass, excelDefinition.getClazz());
				if(superClass!=Object.class){
					excelDefinition = extractSuperClassFields(excelDefinition, fields, realClass);
				}else{
					throw new ExcelException("传入的参数类型是:"+beans.get(0).getClass().getName()
							+"但是 配置文件的类型是: "+excelDefinition.getClazz().getName()+",参数既不是父类,也不是其相同父类下的子类,无法完成转换");
				}
				
			}
			workbook = appendSheet(excelDefinition,beans,customMap);
		}
		return workbook;
	}
	
	/**
	 * 创建Excel,模板信息
	 * @param id	 ExcelXML配置Bean的ID
	 * @param header Excel头信息(在标题之前)
	 * @param fields 指定导出的字段
	 * @return
	 * @throws Exception
	 */
	public Workbook createExcelTemplate(String id,ExcelHeader header,List<String> fields) throws Exception{
		//从注册信息中获取Bean信息
		ExcelDefinition excelDefinition = definitionReader.getRegistry().get(id);
		if(excelDefinition==null){
			throw new ExcelException("没有找到 ["+id+"] 的配置信息");
		}
		String type = excelDefinition.getType();
		if(type!=null){
			setExcelFlag(workbook, type);
		}
		excelDefinition.setHeader(header);
		excelDefinition = dynamicCreateExcelDefinition(excelDefinition,fields);
		return appendSheet(excelDefinition, null);
	}
	
	/**
	 * 创建Excel,模板信息
	 * @param id	 ExcelXML配置Bean的ID
	 * @param header Excel头信息(在标题之前)
	 * @param fields 指定导出的字段
	 * @return
	 * @throws Exception
	 */
	public Workbook createExcelTemplate(String id,ExcelHeader header,List<String> fields,Map<String, Object> customMap) throws Exception{
		//从注册信息中获取Bean信息
		ExcelDefinition excelDefinition = definitionReader.getRegistry().get(id);
		if(excelDefinition==null){
			throw new ExcelException("没有找到 ["+id+"] 的配置信息");
		}
		String type = excelDefinition.getType();
		if(type!=null){
			setExcelFlag(workbook, type);
		}
		excelDefinition.setHeader(header);
		excelDefinition = dynamicCreateExcelDefinition(excelDefinition,fields);
		return appendSheet(excelDefinition, null,customMap);
	}
	
	//抽取父类拥用的字段,同时从它的基础只上在进行筛选指定的字段
	private ExcelDefinition extractSuperClassFields(ExcelDefinition excelDefinition,List<String> fields,Class<?> realClass){
		//抽取出父类所拥有的字段
		List<String> fieldNames = ReflectUtil.getFieldNames(realClass);
		excelDefinition = dynamicCreateExcelDefinition(excelDefinition, fieldNames);
		//抽取指定的字段
		//导出指定字段的标题不是null,动态创建,Excel定义
		excelDefinition = dynamicCreateExcelDefinition(excelDefinition,fields);
		return excelDefinition;
	}
	
	/**
	 * 动态创建ExcelDefinition
	 * @param excelDefinition 原来的ExcelDefinition
	 * @param fields 
	 * @return
	 */
	private ExcelDefinition dynamicCreateExcelDefinition(ExcelDefinition excelDefinition, List<String> fields) {
		if(CollectionUtils.isNotEmpty(fields)){
			ExcelDefinition newDef = new ExcelDefinition();
			ReflectUtil.copyProps(excelDefinition, newDef,"fieldValues");
			List<FieldValue> oldValues = excelDefinition.getFieldValues();
			List<FieldValue> newValues = new ArrayList<FieldValue>(oldValues.size());
			//按照顺序,进行添加
			for(String name:fields){
				for(FieldValue field:oldValues){
					String fieldName = field.getName();
					if(fieldName.equals(name)){
						newValues.add(field);
						break;
					}
				}
			}
			newDef.setFieldValues(newValues);
			return newDef;
		}
		return excelDefinition;
		
	}

	/*protected ExcelExportResult doCreateExcel(ExcelDefinition excelDefinition, List<?> beans,ExcelHeader header) throws Exception {
		return this.doCreateExcel(excelDefinition, beans);
	}*/

	protected Workbook appendSheet(ExcelDefinition excelDefinition, List<?> beans) throws Exception {
		if(excelDefinition.getSheetname()!=null){
			sheet = workbook.createSheet(excelDefinition.getSheetname());
		}else{
			sheet = workbook.createSheet();
		}
		//创建标题之前,调用buildHeader方法,完成其他数据创建的一些信息
		if(excelDefinition.getHeader()!=null){
			excelDefinition.getHeader().buildHeader(sheet,excelDefinition,beans);
		}
		
		titleRow = createTitle(excelDefinition,sheet,workbook);
		//如果listBean不为空,创建数据行
		if(beans!=null){
			createRows(excelDefinition, sheet, beans,workbook,titleRow);
		}
		return workbook;
	}
	
	protected Workbook appendSheet(ExcelDefinition excelDefinition, List<?> beans,Map<String, Object> customMap) throws Exception {
		if(excelDefinition.getSheetname()!=null){
			sheet = workbook.createSheet(excelDefinition.getSheetname());
		}else{
			sheet = workbook.createSheet();
		}
		//创建标题之前,调用buildHeader方法,完成其他数据创建的一些信息
		if(excelDefinition.getHeader()!=null){
			excelDefinition.getHeader().buildHeader(sheet,excelDefinition,beans);
		}
		
		titleRow = createTitle(excelDefinition,sheet,workbook,customMap);
		//如果listBean不为空,创建数据行
		if(beans!=null){
			createRows(excelDefinition, sheet, beans,workbook,titleRow);
		}
		return workbook;
	}
	
	/**
	 * 创建Excel标题
	 * @param excelDefinition
	 * @param sheet
	 * @return 标题行
	 */
	protected Row createTitle(ExcelDefinition excelDefinition,Sheet sheet,Workbook workbook) {
		//标题索引号
		int titleIndex = sheet.getPhysicalNumberOfRows();
		Row titleRow = sheet.createRow(titleIndex);
		List<FieldValue> fieldValues = excelDefinition.getFieldValues();
		for(int i=0;i<fieldValues.size();i++){
			FieldValue fieldValue = fieldValues.get(i);
			//设置单元格宽度
			if(fieldValue.getColumnWidth() !=null){
				sheet.setColumnWidth(i, fieldValue.getColumnWidth());
			}
			//如果默认的宽度不为空,使用默认的宽度
			else if(excelDefinition.getDefaultColumnWidth()!=null){
				sheet.setColumnWidth(i, excelDefinition.getDefaultColumnWidth());
			}
			//设置单元格列是否隐藏
			if(fieldValue.isHidden()){
				sheet.setColumnHidden(i, fieldValue.isHidden());
			}
			Cell cell = titleRow.createCell(i);
			if(excelDefinition.getEnableStyle()){
				if(fieldValue.getAlign()!=null || fieldValue.getTitleBgColor()!=null || fieldValue.getTitleFountColor() !=null || excelDefinition.getDefaultAlign()!=null){
					cell.setCellStyle(workbook.createCellStyle());
					//设置cell 对齐方式
					setAlignStyle(fieldValue, workbook, cell,excelDefinition);
					//设置标题背景色
					setTitleBgColorStyle(fieldValue, workbook, cell);
					//设置标题字体色
					setTitleFountColorStyle(fieldValue, workbook, cell);
				}
			}
			setCellValue(cell,fieldValue.getTitle());
		}
		return titleRow;
	}
	
	/**
	 * 创建Excel标题
	 * @param excelDefinition
	 * @param sheet
	 * @param customMap 自定义Map
	 * @return 标题行
	 */
	protected Row createTitle(ExcelDefinition excelDefinition,Sheet sheet,Workbook workbook,Map<String, Object> customMap) {
		//标题索引号
		int titleIndex = sheet.getPhysicalNumberOfRows();
		Row titleRow = sheet.createRow(titleIndex);
		List<FieldValue> fieldValues = excelDefinition.getFieldValues();
		for(int i=0;i<fieldValues.size();i++){
			FieldValue fieldValue = fieldValues.get(i);
			//设置单元格宽度
			if(fieldValue.getColumnWidth() !=null){
				sheet.setColumnWidth(i, fieldValue.getColumnWidth());
			}
			//如果默认的宽度不为空,使用默认的宽度
			else if(excelDefinition.getDefaultColumnWidth()!=null){
				sheet.setColumnWidth(i, excelDefinition.getDefaultColumnWidth());
			}
			//设置单元格列是否隐藏
			if(fieldValue.isHidden()){
				sheet.setColumnHidden(i, fieldValue.isHidden());
			}
			//设置单元格下拉（支持自定义）
			if(StringUtils.isNoneBlank(fieldValue.getSelectValue())){
				String[] explicitListValues = null;
				//如果有配置的是key，从自定义Map 中获取，否则直接获取配置的值
				if(null != customMap && null != customMap.get(fieldValue.getSelectValue())){
					try {
						explicitListValues = (String[]) customMap.get(fieldValue.getSelectValue());
					} catch (Exception e) {
						throw new ExcelException("传入的自定义Map数据转换失败");
					}
				}else{
					explicitListValues = fieldValue.getSelectValue().split("[,;]");
				}
				DataValidationHelper helper = sheet.getDataValidationHelper();
				//CellRangeAddressList(firstRow, lastRow, firstCol, lastCol)设置行列范围  
				CellRangeAddressList addressList = new CellRangeAddressList(1, 1000, i, i);  
				//设置下拉框数据  
				DataValidationConstraint constraint = helper.createExplicitListConstraint(explicitListValues);   
				DataValidation dataValidation = helper.createValidation(constraint, addressList);  
				//处理Excel兼容性问题  
				if(dataValidation instanceof XSSFDataValidation) {
					dataValidation.setSuppressDropDownArrow(true);
					dataValidation.setShowErrorBox(true);
				}else {
					dataValidation.setSuppressDropDownArrow(false);  
				}
				sheet.addValidationData(dataValidation);
			}
			
			Cell cell = titleRow.createCell(i);
			if(excelDefinition.getEnableStyle()){
				if(fieldValue.getAlign()!=null || fieldValue.getTitleBgColor()!=null || fieldValue.getTitleFountColor() !=null || excelDefinition.getDefaultAlign()!=null){
					cell.setCellStyle(workbook.createCellStyle());
					//设置cell 对齐方式
					setAlignStyle(fieldValue, workbook, cell,excelDefinition);
					//设置标题背景色
					setTitleBgColorStyle(fieldValue, workbook, cell);
					//设置标题字体色
					setTitleFountColorStyle(fieldValue, workbook, cell);
				}
			}
			setCellValue(cell,fieldValue.getTitle());
		}
		return titleRow;
	}
	
	/**
	 * 创建行
	 * @param excelDefinition
	 * @param sheet
	 * @param beans
	 * @param workbook
	 * @param titleIndex
	 * @throws Exception
	 */
	public void createRows(ExcelDefinition excelDefinition,Sheet sheet,List<?> beans,Workbook workbook,Row titleRow) throws Exception{
		int num = sheet.getPhysicalNumberOfRows();
		int startRow = num ;
		for(int i=0;i<beans.size();i++){
			Row row = sheet.createRow(i+num);
			createRow(excelDefinition,row,beans.get(i),workbook,sheet,titleRow,startRow++);
		}
	}
	
	
	/**
	 * 创建行
	 * @param excelDefinition
	 * @param row
	 * @param bean
	 * @param workbook
	 * @param sheet
	 * @param titleRow
	 * @param rowNum
	 * @throws Exception
	 */
	protected void createRow(ExcelDefinition excelDefinition, Row row, Object bean,Workbook workbook,Sheet sheet,Row titleRow,int rowNum) throws Exception {
		List<FieldValue> fieldValues = excelDefinition.getFieldValues();
		for(int i=0;i<fieldValues.size();i++){
			FieldValue fieldValue = fieldValues.get(i);
			String name = fieldValue.getName();
			Object value = ReflectUtil.getProperty(bean, name);
			//从解析器获取值
			Object val = convert(bean,value, fieldValue, Type.EXPORT,rowNum);
			Cell cell = row.createCell(i);
			//cell样式是否与标题一致,如果一致,找到对应的标题样式进行设置
			if(excelDefinition.getEnableStyle()){
				if(fieldValue.isUniformStyle()){
					//获取标题行
					//获取对应的标题行样式
					Cell titleCell = titleRow.getCell(i);
					CellStyle cellStyle = titleCell.getCellStyle();
					cell.setCellStyle(cellStyle);
				}
			}
			setCellValue(cell, val);
		}
	}
	
	//设置cell 对齐方式
	private void setAlignStyle(FieldValue fieldValue,Workbook workbook,Cell cell,ExcelDefinition excelDefinition){
		if(fieldValue.getAlign()!=null){
			CellStyle cellStyle = cell.getCellStyle();
			cellStyle.setAlignment(fieldValue.getAlign());
			cell.setCellStyle(cellStyle);
		}else if(excelDefinition.getDefaultAlign()!=null){
			CellStyle cellStyle = cell.getCellStyle();
			cellStyle.setAlignment(excelDefinition.getDefaultAlign());
			cell.setCellStyle(cellStyle);
		}
	}
	
	//设置cell 背景色方式
	private void setTitleBgColorStyle(FieldValue fieldValue,Workbook workbook,Cell cell){
		if(fieldValue.getTitleBgColor()!=null){
			CellStyle cellStyle = cell.getCellStyle();
			cellStyle.setFillForegroundColor(fieldValue.getTitleBgColor());
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}
	}
	
	//设置cell 字体颜色
	private void setTitleFountColorStyle(FieldValue fieldValue,Workbook workbook,Cell cell){
		if(fieldValue.getTitleFountColor()!=null){
			CellStyle cellStyle = cell.getCellStyle();
			Font font = workbook.createFont();
			font.setColor(fieldValue.getTitleFountColor());
			cellStyle.setFont(font);
		}
	}
	
	/**
	 * 设置Excel标记
	 * @param workbook
	 * @param type
	 */
	private void setExcelFlag(Workbook workbook, String type){
		if(workbook instanceof XSSFWorkbook){
			System.out.println("is XSSFWorkbook");
			CoreProperties cp = ((XSSFWorkbook)workbook).getProperties().getCoreProperties();
			cp.setKeywords(type);
		}else if(workbook instanceof HSSFWorkbook){
			((HSSFWorkbook)workbook).getSummaryInformation().setKeywords(type);
			System.out.println("is HSSFWorkbook");
		}else if(workbook instanceof SXSSFWorkbook){
			((SXSSFWorkbook)workbook).getXSSFWorkbook().getProperties().getCoreProperties().setKeywords(type);
			System.out.println("is SXSSFWorkbook");
		}
	}
	
}
