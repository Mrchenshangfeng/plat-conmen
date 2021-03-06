package com.hywisdom.easy.excel.test.blog;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.hywisdom.easy.excel.ExcelContext;
import com.hywisdom.easy.excel.result.ExcelImportResult;
import com.hywisdom.easy.excel.test.model.StudentModel;

public class ImportTest {
	public static void main(String[] args) throws Exception {
		//准备excel文件流
		InputStream excelStream = new FileInputStream("C:/Users/Administrator/Desktop/stu.xlsx");
		//创建excel上下文实例,它的构成需要配置文件的路径
		ExcelContext context = ExcelContext.newInstance("excel-config.xml");
		//按照xml配置中id为student的配置形式读取excel文件,并转换成StudentModel
		//这里的第二个参数是值,标题是第几行开始,之前也说了标题之前的数据并不是规则的数据
		ExcelImportResult result = context.readExcel("student", excelStream);
		//打印导入结果,查看标题之前不规则的数据
		List<List<Object>> header = result.getHeader();
		System.out.println(header.get(0));
		System.out.println(header.get(1));
		//查看学生集合导入结果
		List<StudentModel> students = result.getListBean();
		for(StudentModel stu:students){
			System.out.println(stu);
		}
	}
}
