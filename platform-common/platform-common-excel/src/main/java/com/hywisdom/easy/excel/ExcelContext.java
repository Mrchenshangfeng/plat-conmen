package com.hywisdom.easy.excel;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hywisdom.easy.excel.config.ExcelConfig;
import com.hywisdom.easy.excel.exception.ExcelException;
import com.hywisdom.easy.excel.parsing.ExcelExport;
import com.hywisdom.easy.excel.parsing.ExcelImport;
import com.hywisdom.easy.excel.result.ExcelExportResult;
import com.hywisdom.easy.excel.result.ExcelImportResult;
import com.hywisdom.easy.excel.xml.XMLExcelDefinitionReader;
import com.hywisdom.easy.util.ReflectUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.hywisdom.easy.excel.config.ExcelDefinition;
import com.hywisdom.easy.excel.config.FieldValue;
import com.hywisdom.easy.excel.parsing.ExcelHeader;

/**
 * Excel上下文支持,只需指定location配置文件路径,即可使用
 * @author wangxz
 *
 */
public class ExcelContext  {
	
	private ExcelDefinitionReader definitionReader;
	
	/** 用于缓存Excel配置 */
	private Map<String,List<FieldValue>> fieldValueMap = new HashMap<String, List<FieldValue>>();
	
	/**导出*/
	private ExcelExport excelExport;
	/**导入*/
	private ExcelImport excelImport;
	/**xml文件路径*/
	private String configPath;
	
	
	/**
	 * @param location 配置文件类路径
	 */
	private ExcelContext(String location) {
		try {
			this.configPath = location;
			//这里默认使用XML ExcelContent,如果有自己的需求需要自行修改
			definitionReader = new XMLExcelDefinitionReader(location);
			excelExport = new ExcelExport(definitionReader);
			excelImport = new ExcelImport(definitionReader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param location 配置文件类路径,添加自定义Map
	 */
	private ExcelContext(String location,Map<String, Object> formatMap) {
		try {
			this.configPath = location;
			//这里默认使用XML ExcelContent,如果有自己的需求需要自行修改
			definitionReader = new XMLExcelDefinitionReader(location,formatMap);
			excelExport = new ExcelExport(definitionReader);
			excelImport = new ExcelImport(definitionReader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 创建Excel
	 * @param id 配置ID
	 * @param beans 配置class对应的List
	 * @return Workbook
	 * @throws Exception 
	 */
	public Workbook createExcel(String id, List<?> beans) throws Exception {
		return createExcel(id, beans, null, null);
	}
	
	public ExcelExportResult doExport(ExcelConfig config) throws Exception{
		Workbook workbook = excelExport.createExcel(config);
		OutputStream ops = new FileOutputStream(config.getOutPath());
		workbook.write(ops);
		ops.close();
		return new ExcelExportResult(config.getOutPath(), workbook);
	}
	
	public Workbook createExcel(ExcelConfig config) throws Exception {
		return excelExport.createExcel(config);
	}
	
	public void append(List<?> beans) throws Exception{
		excelExport.createRows(excelExport.getExcelDefinition(), excelExport.getSheet(), beans, excelExport.getWorkbook(), excelExport.getTitleRow());
	}
	/**
	 * 创建Excel部分信息
	 * @param id 配置ID
	 * @param beans 配置class对应的List
	 * @return Workbook
	 * @throws Exception 
	 */
	public Workbook createExcelForPart(String id, List<?> beans) throws Exception {
		return createExcelForPart(id, beans, null, null);
	}
	
	public Workbook createExcelForPart(ExcelConfig config) throws Exception {
		return excelExport.createExcel(config);
	}
	
	/**
	 * 创建Excel
	 * @param id 配置ID
	 * @param beans 配置class对应的List
	 * @param header 导出之前,在标题前面做出一些额外的操作，比如增加文档描述等,可以为null
	 * @return Workbook
	 * @throws Exception 
	 */
	public Workbook createExcel(String id, List<?> beans,ExcelHeader header) throws Exception {
		return createExcel(id, beans, header, null);
	}
	
	/**
	 * 创建Excel部分信息
	 * @param id 配置ID
	 * @param beans 配置class对应的List
	 * @param header 导出之前,在标题前面做出一些额外的操作，比如增加文档描述等,可以为null
	 * @return Workbook
	 * @throws Exception 
	 */
	public Workbook createExcelForPart(String id, List<?> beans,ExcelHeader header) throws Exception {
		return createExcelForPart(id, beans, header, null);
	}
	/**
	 * 创建Excel
	 * @param id 配置ID
	 * @param beans 配置class对应的List
	 * @param header 导出之前,在标题前面做出一些额外的操作,比如增加文档描述等,可以为null
	 * @param fields 指定Excel导出的字段(bean对应的字段名称),可以为null
	 * @return Workbook
	 * @throws Exception 
	 */
	public Workbook createExcel(String id,  List<?> beans,ExcelHeader header,List<String> fields) throws Exception {
		
		return excelExport.createExcel(id, beans,header,fields);
	}
	
	/**
	 * 创建Excel
	 * @param id 配置ID
	 * @param beans 配置class对应的List
	 * @param header 导出之前,在标题前面做出一些额外的操作,比如增加文档描述等,可以为null
	 * @param fields 指定Excel导出的字段(bean对应的字段名称),可以为null
	 * @param customMap 自定义Map
	 * @return Workbook
	 * @throws Exception 
	 */
	public Workbook createExcelCustom(String id,List<?> beans,ExcelHeader header,List<String> fields,Map<String, Object> customMap) throws Exception {
		
		return excelExport.createExcel(id, beans,header,fields,customMap);
	}

	/**
	 * 创建Excel部分信息
	 * @param id 配置ID
	 * @param beans 配置class对应的List
	 * @param header 导出之前,在标题前面做出一些额外的操作,比如增加文档描述等,可以为null
	 * @param fields 指定Excel导出的字段(bean对应的字段名称),可以为null
	 * @return Workbook
	 * @throws Exception 
	 */
	public Workbook createExcelForPart(String id, List<?> beans,ExcelHeader header,List<String> fields) throws Exception {
		return excelExport.createExcel(id, beans,header,fields);
	}
	
	/**
	 * 创建Excel,模板信息
	 * @param id	 ExcelXML配置Bean的ID
	 * @param header Excel头信息(在标题之前)
	 * @param fields 指定导出的字段
	 * @param customMap 自定义值
	 * @return
	 * @throws Exception
	 */
	public Workbook createExcelTemplate(String id,ExcelHeader header,List<String> fields,Map<String, Object> customMap) throws Exception{
		return excelExport.createExcelTemplate(id, header,fields,customMap);
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
		return excelExport.createExcelTemplate(id, header,fields);
	}
	
	/***
	 * 读取Excel信息
	 * @param id 配置ID
	 * @param excelStream Excel文件流
	 * @return ExcelImportResult
	 * @throws Exception 
	 */
	public ExcelImportResult readExcel(String id, InputStream excelStream) throws Exception {
		return excelImport.readExcel(id,excelStream,false);
	}
	
	/***
	 * 读取Excel信息
	 * @param id 配置ID
	 * @param excelStream Excel文件流
	 * @param multivalidate 是否逐条校验，默认单行出错立即抛出ExcelException，为true时为批量校验,可通过ExcelImportResult.hasErrors,和getErrors获取具体错误信息
	 * @return ExcelImportResult
	 * @throws Exception 
	 */
	public ExcelImportResult readExcel(String id, InputStream excelStream,boolean multivalidate) throws Exception {
		return excelImport.readExcel(id,excelStream,multivalidate);
	}
	
	/***
	 * 读取Excel信息
	 * @param id 配置ID
	 * @param excelStream Excel文件流
	 * @param multivalidate 是否逐条校验，默认单行出错立即抛出ExcelException，为true时为批量校验,可通过ExcelImportResult.hasErrors,和getErrors获取具体错误信息
	 * @return ExcelImportResult
	 * @throws Exception 
	 */
	public ExcelImportResult readExcel(String id, Workbook workbook,boolean multivalidate) throws Exception {
		return excelImport.readExcel(id,workbook,multivalidate);
	}
	
	public List<ExcelImportResult> doImport(String [] excelIds, String path,boolean multivalidate) throws Exception{
		File file = new File(path);
		InputStream io = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(io);
		List<ExcelImportResult> list = new ArrayList<ExcelImportResult>();
		for (String id : excelIds) {
			list.add(this.readExcel(id, workbook, multivalidate));
		}
		io.close();
		return list;
	}
	
	public List<ExcelImportResult> doImport(String [] excelIds, String path) throws Exception{
		return this.doImport(excelIds, path, false);
	}
	
	public ExcelImportResult doImport(String excelId, String path) throws Exception{
		return this.doImport(excelId, path, false);
	}
	
	public ExcelImportResult doImport(String excelId, String path,boolean multivalidate) throws Exception{
		File file = new File(path);
		InputStream io = new FileInputStream(file);
		ExcelImportResult readExcel = this.readExcel(excelId, io, multivalidate);
		return readExcel;
	}
	
	/**
	 * 获取Excel 配置文件中的字段
	 * @param key
	 * @return
	 */
	public List<FieldValue> getFieldValues(String key){
		List<FieldValue> list = fieldValueMap.get(key);
		if(list == null){
			ExcelDefinition def = definitionReader.getRegistry().get(key);
			if(def == null){
				throw new ExcelException("没有找到["+key+"]的配置信息");
			}
			//使用copy方式,避免使用者修改原生的配置信息
			List<FieldValue> fieldValues = def.getFieldValues();
			list = new ArrayList<FieldValue>(fieldValues.size());
			for(FieldValue fieldValue:fieldValues){
				FieldValue val = new FieldValue();
				ReflectUtil.copyProps(fieldValue, val);
				list.add(val);
			}
			fieldValueMap.put(key, list);
		}
		return list;
	}
	
	private static String writeXML(List<ExcelDefinition> list){
		String path = System.getProperty("java.io.tmpdir");
		path += (UUID.randomUUID()+".xml");
		try {
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
			OutputFormat format=OutputFormat.createPrettyPrint();  
			format.setIndent(true);
			XMLWriter xw = new XMLWriter(out,format);
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("excels");
			for(ExcelDefinition ed : list){
				Element e = root.addElement("excel");
				e.addAttribute("id", ed.getId());
				e.addAttribute("class", ed.getClassName());
				e.addAttribute("enableStyle", String.valueOf(ed.getEnableStyle()));
				e.addAttribute("sheetIndex", String.valueOf(ed.getSheetIndex()));
				e.addAttribute("titleIndex", String.valueOf(ed.getTitleIndex()));
				if(ed.getDefaultAlign()!=null)
					e.addAttribute("defaultAlign", String.valueOf(ed.getDefaultAlign()));
				if(ed.getDefaultColumnWidth()!=null)
					e.addAttribute("defaultColumnWidth", String.valueOf(ed.getDefaultColumnWidth()));
				if(ed.getSheetname()!=null){
					e.addAttribute("sheetname", ed.getSheetname());
				}
				if(ed.getType()!=null){
					e.addAttribute("type", ed.getType());
				}
				for (FieldValue fv : ed.getFieldValues()) {
					Element c = e.addElement("field");
					c.addAttribute("name", fv.getName());
					c.addAttribute("title", fv.getTitle());
					c.addAttribute("isNull", String.valueOf(fv.isNull()));
					c.addAttribute("uniformStyle", String.valueOf(fv.isUniformStyle()));
					
					if(fv.getPattern()!= null)
						c.addAttribute("pattern", fv.getPattern());
					if(fv.getFormat()!= null)
						c.addAttribute("format", fv.getFormat());
					if(fv.getCellValueConverterName()!= null)
						c.addAttribute("cellValueConverterName", fv.getCellValueConverterName());
					if(fv.getRegex()!= null)
						c.addAttribute("regex", fv.getRegex());
					if(fv.getRegexErrMsg()!= null)
						c.addAttribute("regexErrMsg", fv.getRegexErrMsg());
					if(fv.getColumnWidth()!= null)
						c.addAttribute("columnWidth", String.valueOf(fv.getColumnWidth()));
					if(fv.getAlign()!= null)
						c.addAttribute("align", String.valueOf(fv.getAlign()));
					if(fv.getTitleBgColor()!= null)
						c.addAttribute("titleBgColor", String.valueOf(fv.getTitleBgColor()));
					if(fv.getTitleFountColor()!= null)
						c.addAttribute("titleFountColor", String.valueOf(fv.getTitleFountColor()));
					if(fv.getDecimalFormatPattern()!=null)
						c.addAttribute("decimalFormatPattern", fv.getDecimalFormatPattern());
					if(fv.getDefaultValue()!=null)
						c.addAttribute("defaultValue", fv.getDefaultValue());
				}
			}
			xw.write(doc);
			xw.flush();
			xw.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	public String getConfigPath() {
		return configPath;
	}

	public static ExcelContext newInstance(String config){
		return new ExcelContext(config,null);
	}
	
	public static ExcelContext newInstance(List<ExcelDefinition> list){
		String writeXML = writeXML(list);
		return new ExcelContext(writeXML,null);
	}

	public static ExcelContext newInstanceAddFormatMap(String config,Map<String, Object> formatMap){
		return new ExcelContext(config,formatMap);
	}
	
	/**
	 * 单个sheet校验
	 * @param path
	 * @param type
	 *//*
	private void validate(String path,String type){
		FileInputStream io = null;
		try {
			io = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(path));
			CoreProperties cp = wb.getProperties().getCoreProperties();
			System.out.println("ckeys:"+cp.getKeywords());
			//暂时用模糊匹配， 用于满足多sheet导入场景，后续需调整精确匹配
			if(cp.getKeywords()==null || !cp.getKeywords().contains(type)){
				throw new ExcelException("导入的Excel业务类型不匹配，请确认导入数据是否为["+type+"]");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(io != null){
				try {
					io.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}*/

}
