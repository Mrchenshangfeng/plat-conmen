package com.hywisdom.easy.excel.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public  class ExcelConfig<T> {
	/** 临时文件路径，由配置读取 */
	public static String tempPath = System.getProperty("java.io.tmpdir");;
	/** Excel输出路径 */
	private String outPath = null;
	
	/** Excel模板路径 */
	private String templatePath = null;
	
	private Map<String,List<T>> dataMap = new HashMap<String, List<T>>();
	
	
	public ExcelConfig() {
		super();
	}
	
	public ExcelConfig(Map<String, List<T>> dataMap) {
		super();
		this.dataMap = dataMap;
	}

	public ExcelConfig(String templatePath) {
		super();
		this.templatePath = templatePath;
	}
	
	public ExcelConfig(String templatePath, Map<String, List<T>> dataMap) {
		super();
		this.templatePath = templatePath;
		this.dataMap = dataMap;
	}

	public ExcelConfig(String templatePath, String outPath) {
		super();
		this.outPath = outPath;
		this.templatePath = templatePath;
	}

	public ExcelConfig(String templatePath, String outPath, Map<String, List<T>> dataMap) {
		super();
		this.outPath = outPath;
		this.templatePath = templatePath;
		this.dataMap = dataMap;
	}

	public String getOutPath() {
		return outPath==null?getTempOutPath():outPath;
	}

	public void setOutPath(String outPath) {
		this.outPath = outPath;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	
	public void pushDataMap(String key, List<T> value){
		this.dataMap.put(key, value);
	}
	
	public Map<String, List<T>> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, List<T>> dataMap) {
		this.dataMap = dataMap;
	}

	public static String getTempOutPath(){
		return tempPath+UUID.randomUUID().toString()+".xlsx";
	}
}
