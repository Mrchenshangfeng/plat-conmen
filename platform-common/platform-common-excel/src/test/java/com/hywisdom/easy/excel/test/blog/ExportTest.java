package com.hywisdom.easy.excel.test.blog;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Splitter;
import com.hywisdom.easy.excel.ExcelContext;
import com.hywisdom.easy.excel.test.model.BookModel;
import com.hywisdom.easy.excel.test.model.StudentModel;
import org.apache.poi.ss.usermodel.Workbook;

import com.hywisdom.easy.excel.test.model.AuthorModel;

public class ExportTest {
	public static void main(String[] args) throws Exception {
//		//准备excel输出流
//		OutputStream ops = new FileOutputStream("D:/exportStudent.xlsx");
//		//创建excel上下文实例,它的构成需要配置文件的路径
//		ExcelContext context = ExcelContext.newInstance("excel-config.xml");
//		//获取POI创建结果
//		Workbook workbook = context.createExcel("student",getStudents());
//		workbook.write(ops);
//		ops.close();

		exportTemplate();
	}

	public static void exportTemplate() {
		//获取导出字段
		String fields = "code,name,repType,address,description,state,isDefault,createTime,createBy,updateTime,updateBy";
		Splitter splitter = Splitter.on(',').trimResults().omitEmptyStrings();
		List<String> cloumFields = splitter.splitToList(fields);
		ExcelContext context = ExcelContext.newInstanceAddFormatMap("repository-excel-config.xml", null);
		try {
			OutputStream ops = new FileOutputStream("D:/export-test.xlsx");
			Workbook workbook = context.createExcelTemplate("repository_export", null, cloumFields, null);
//            Sheet sheet1 = workbook.createSheet("new sheet");
			workbook.write(ops);
			ops.close();

		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	
	//获取模拟数据,数据库数据...
	public static List<StudentModel> getStudents(){
		int size = 10;
		List<StudentModel> students = new ArrayList<StudentModel>(size);
		for(int i=0;i<size;i++){
			StudentModel stu = new StudentModel();
			stu.setId(""+(i+1));
			stu.setName("张三"+i);
			stu.setAge(20+i);
			stu.setStudentNo("Stu_"+i);
			stu.setCreateTime(new Date());
			stu.setStatus(i%2==0?1:0);
			stu.setCreateUser("王五"+i);
			
			//创建复杂对象
			if(i % 2==0){
				BookModel book = new BookModel();
				book.setBookName("Thinking in java");
				AuthorModel author = new AuthorModel();
				author.setAuthorName("Bruce Eckel");
				book.setAuthor(author);
				stu.setBook(book);
			}
			
			students.add(stu);
		}
		return students;
	}
}
