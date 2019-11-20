package com.hywisdom.easy.excel.parsing;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.hywisdom.easy.excel.result.ExcelImportResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLProperties.CoreProperties;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hywisdom.easy.excel.ExcelDefinitionReader;
import com.hywisdom.easy.excel.config.ExcelDefinition;
import com.hywisdom.easy.excel.config.FieldValue;
import com.hywisdom.easy.excel.exception.ExcelException;
import com.hywisdom.easy.util.ReflectUtil;
/**
 * Excel导入实现类
 * @author wangxz
 *
 */
public class ExcelImport extends AbstractExcelResolver{
	
	public ExcelImport(ExcelDefinitionReader definitionReader) {
		super(definitionReader);
	}
	
	/**
	 * 读取Excel信息
	 * @param id 注册的ID
	 * @param titleIndex 标题索引
	 * @param workbook Excel workbook
	 * @param sheetIndex Sheet索引位置
	 * @param multivalidate 是否逐条校验，默认单行出错立即抛出ExcelException，为true时为批量校验,可通过ExcelImportResult.hasErrors,和getErrors获取具体错误信息
	 * @return
	 * @throws Exception
	 */
	public ExcelImportResult readExcel(String id,Workbook workbook,boolean multivalidate) throws Exception {
		//从注册信息中获取Bean信息
		ExcelDefinition excelDefinition = definitionReader.getRegistry().get(id);
		if(excelDefinition==null){
			throw new ExcelException("没有找到 ["+id+"] 的配置信息");
		}
		if(excelDefinition.getType()!=null){
			this.checkExcelFlag(workbook, excelDefinition.getType());
		}
		return doReadExcel(excelDefinition,workbook,multivalidate);
	}
	
	/**
	 * 读取Excel信息
	 * @param id 注册的ID
	 * @param titleIndex 标题索引
	 * @param excelStream Excel文件流
	 * @param sheetIndex Sheet索引位置
	 * @param multivalidate 是否逐条校验，默认单行出错立即抛出ExcelException，为true时为批量校验,可通过ExcelImportResult.hasErrors,和getErrors获取具体错误信息
	 * @return
	 * @throws Exception
	 */
	public ExcelImportResult readExcel(String id,InputStream excelStream,boolean multivalidate) throws Exception {
		return readExcel(id,WorkbookFactory.create(excelStream),multivalidate);
	}
	
	protected ExcelImportResult doReadExcel(ExcelDefinition excelDefinition,Workbook workbook,boolean multivalidate) throws Exception {
		ExcelImportResult result = new ExcelImportResult();
		//读取sheet,sheetIndex参数优先级大于ExcelDefinition配置sheetIndex
		Sheet sheet = workbook.getSheetAt(excelDefinition.getSheetIndex());
		//标题之前的数据处理
		List<List<Object>> header = readHeader(excelDefinition, sheet,excelDefinition.getTitleIndex());
		result.setHeader(header);
		//获取标题
		List<String> titles = readTitle(excelDefinition,sheet,excelDefinition.getTitleIndex());
		//获取Bean
		List<Object> listBean = readRows(result.getErrors(),excelDefinition,titles, sheet,excelDefinition.getTitleIndex(),multivalidate);
		result.setListBean(listBean);
		return result;
	}
	
	/**
	 * 解析标题之前的内容,如果ExcelDefinition中titleIndex 不是0
	 * @param excelDefinition
	 * @param sheet
	 * @return
	 */
	protected List<List<Object>> readHeader(ExcelDefinition excelDefinition,Sheet sheet,int titleIndex){
		List<List<Object>> header = null;
		if(titleIndex!=0){
			header = new ArrayList<List<Object>>(titleIndex);
			for(int i=0;i<titleIndex;i++){
				Row row = sheet.getRow(i);
				short cellNum = row.getLastCellNum();
				List<Object> item = new ArrayList<Object>(cellNum);
				for(int j=0;j<cellNum;j++){
					Cell cell = row.getCell(j);
					Object value = getCellValue(cell);
					item.add(value);
				}
				header.add(item);
			}
		}
		return header;
	}
	
	/**
	 * 读取多行
	 * @param result
	 * @param excelDefinition
	 * @param titles
	 * @param sheet
	 * @param titleIndex
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> readRows(List<ExcelError> errors,ExcelDefinition excelDefinition, List<String> titles, Sheet sheet,int titleIndex,boolean multivalidate)throws Exception {
		int rowNum = sheet.getLastRowNum();
		//读取数据的总共次数
		int totalNum = rowNum - titleIndex;
		int startRow =  -titleIndex;
		List<T> listBean = new ArrayList<T>(totalNum);
		for (int i = titleIndex+1; i <= rowNum; i++) {
			Row row = sheet.getRow(i);
			Object bean = readRow(errors,excelDefinition,row,titles,startRow+i+1,multivalidate);
			listBean.add((T) bean);
		}
		return listBean;
	}
	
	/**
	 * 读取1行
	 * @param excelDefinition
	 * @param row
	 * @param titles
	 * @param rowNum 第几行
	 * @return
	 * @throws Exception
	 */
	protected Object readRow(List<ExcelError> errors,ExcelDefinition excelDefinition, Row row, List<String> titles,int rowNum,boolean multivalidate) throws Exception {
		//创建注册时配置的bean类型
		Object bean = ReflectUtil.newInstance(excelDefinition.getClazz());
		for(FieldValue fieldValue:excelDefinition.getFieldValues()){
			String title = fieldValue.getTitle();
			for (int j = 0; j < titles.size(); j++) {
				if(title.equals(titles.get(j))){
					try{
						Cell cell = row.getCell(j);
						//获取Excel原生value值
						Object value = getCellValue(cell);
						//校验
						String errMessage = validate(fieldValue, value, rowNum);
						//格式错误集中返回
						if(StringUtils.isNoneBlank(errMessage)){
							errors.add(new ExcelError(rowNum,errMessage));
						}
						if(value != null){
							if(value instanceof String){
								//去除前后空格
								value = value.toString().trim();
							}
							value = super.convert(bean,value, fieldValue, Type.IMPORT,rowNum);
							ReflectUtil.setProperty(bean, fieldValue.getName(), value);
						}
						break;
					}catch(ExcelException e){
						//应用multivalidate
						if(multivalidate){
							errors.add(new ExcelError(rowNum,e.getMessage()));
							continue;
						}else{
							throw e;
						}
					}
				}
			}
		}
		return bean;
	}

	protected List<String> readTitle(ExcelDefinition excelDefinition, Sheet sheet,int titleIndex) {
		// 获取Excel标题数据
		Row hssfRowTitle = sheet.getRow(titleIndex);
		int cellNum = hssfRowTitle.getLastCellNum();
		List<String> titles = new ArrayList<String>(cellNum);
		// 获取标题数据
		for (int i = 0; i < cellNum; i++) {
			Cell cell = hssfRowTitle.getCell(i);
			Object value = getCellValue(cell);
			if(value==null){
				throw new ExcelException("id 为:["+excelDefinition.getId()+"]的标题不能为[ null ]");
			}
			titles.add(value.toString());
		}
		return titles;
	}
	
	/**
	 * 数据有效性校验
	 * @param fieldValue
	 * @param value
	 * @param rowNum
	 */
	private String validate(FieldValue fieldValue,Object value,int rowNum){
		if(value == null || StringUtils.isBlank(value.toString())){
			//空校验
			if(!fieldValue.isNull()){
				String err = getErrorMsg(fieldValue, "不能为空", rowNum);
//				throw new ExcelException(err);
				return err;
			}
		}else{
			String val = value.toString().trim();
			//字符最大长度校验
			if(null != fieldValue.getMaxLength() && fieldValue.getMaxLength() < val.length()){
				return getErrorMsg(fieldValue, "超过最大长度"+fieldValue.getMaxLength(), rowNum);
			}
			//正则校验
			String regex = fieldValue.getRegex();
			if(StringUtils.isNotBlank(regex)){
				if(!val.matches(regex)){
					String errMsg = fieldValue.getRegexErrMsg()==null?"格式错误":fieldValue.getRegexErrMsg();
					String err = getErrorMsg(fieldValue, errMsg, rowNum);
//					throw new ExcelException(err);
					return err;
				}
			}
		}
		return null;
	}
	
	/**
	 * 检查Excel标记
	 * @param workbook
	 * @param type
	 */
	private void checkExcelFlag(Workbook workbook, String type){
		String keyword = null;
		if(workbook instanceof XSSFWorkbook){
			System.out.println("is XSSFWorkbook");
			CoreProperties cp = ((XSSFWorkbook)workbook).getProperties().getCoreProperties();
			keyword = cp.getKeywords();
		}else if(workbook instanceof HSSFWorkbook){
			keyword = ((HSSFWorkbook)workbook).getSummaryInformation().getKeywords();
			System.out.println("is HSSFWorkbook");
		}else if(workbook instanceof SXSSFWorkbook){
			keyword = ((SXSSFWorkbook)workbook).getXSSFWorkbook().getProperties().getCoreProperties().getKeywords();
			System.out.println("is SXSSFWorkbook");
		}
		if(keyword == null){
			throw new ExcelException("导入的Excel业务类型不匹配，请确认导入数据是否为["+type+"]");
		}else if(!keyword.contains(type)){
			throw new ExcelException("导入的Excel业务类型不匹配，请确认导入数据是否为["+type+"]");
		}
	}
	
}
