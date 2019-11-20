package com.hywisdom.platform.common.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * @program: hyzl-glassmes
 * @description: excel常用类
 * @author: xfl
 * @CreateTime: 2018年09月07日 11:40
 **/
@Slf4j
public class ExcelUtils {

    /**
     * @Method exportExcel
     * @Description:  导出excel
     * @param response
     * @param reMap	需包含fileName、sheetName、tempPath
     * @return：java.lang.Boolean
     * @exception
     * @Author: xfl
     * @Date: 2018/11/19
    **/
    public static Boolean exportExcelByTemp(HttpServletResponse response, Map<String,Object> reMap) {
        String fileName=reMap.get("fileName")==null ? "缺省的文件名":("".equals(reMap.get("fileName").toString())?"缺省的文件名":reMap.get("fileName").toString());
        String sheetName=reMap.get("sheetName")==null ? "缺省的sheet名":("".equals(reMap.get("sheetName").toString())?"缺省的sheet名":reMap.get("sheetName").toString());
        String tempPath=reMap.get("tempPath")==null ? "":reMap.get("tempPath").toString();
        if("".equals(tempPath)){
            return false;
        }
        ExcelUtils.exportExcelByTemp(fileName,sheetName,tempPath,reMap,response);
        return true;
    }

    /**
     * @Method exportExcelByTemp
     * @Description:  根据模板导出excel
     * @param fileName 文件名
     * @param sheetName sheet名
     * @param tempPath 模板路径
     * @param map 需要导出的数据
     * @param response	
     * @return：void
     * @exception   
     * @Author: xfl
     * @Date: 2018/9/7
    **/
    public static void exportExcelByTemp(String fileName, String sheetName, String tempPath,Map<String,Object> map,HttpServletResponse response){
        TemplateExportParams params = new TemplateExportParams(tempPath,sheetName);//
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        downLoadExcel(fileName,response,workbook);
        //本地测试用--------------------
//        File savefile = new File("D:/excel/");
//        if (!savefile.exists()) {
//            savefile.mkdirs();
//        }
//        try {
//            fileName+=System.currentTimeMillis()+".xls";
//            String path="D:/excel/"+fileName;
//            FileOutputStream fos = new FileOutputStream("D:/excel/"+fileName);
//            workbook.write(fos);
//            fos.close();
//        }catch (Exception e){}
        //------------------------------
    }

    /**
     * @Method exportExcel
     * @Description:  无模板导出excel
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param isCreateHeader
     * @param response
     * @return：void
     * @exception
     * @Author: xfl
     * @Date: 2019/3/9
    **/
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        exportParams.setStyle(ExcelStyleUtil.class);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }


    /**
     * @Method downLoadExcel
     * @Description:  excel下载
     * @param fileName	
     * @param response	
     * @param workbook	
     * @return：void
     * @exception   
     * @Author: xfl
     * @Date: 2018/9/7
    **/
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            //fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
//            response.reset();//清空输出流
            Properties pro = System.getProperties();
            //response.setCharacterEncoding(pro.getProperty("file.encoding"));
            //response.setCharacterEncoding("UTF-8");//编码
            response.setHeader("content-Type", "application/vnd.ms-excel");// 告诉浏览器用什么软件可以打开此文件"application/vnd.ms-excel"
            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8")+".xls");// 下载文件的默认名称
            workbook.write(response.getOutputStream());
            //workbook.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            log.error("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            log.error("excel文件不能为空");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

    /**
     * 复制文件
     *
     * @param s 源文件
     * @param t 复制到的新文件
     */

    public void fileChannelCopy(File s, File t) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(s), 1024);
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取excel模板，并复制到新文件中供写入和下载
     *
     * @return
     */
    public File createNewFile(String tempPath, String rPath) {
        // 读取模板，并赋值到新文件************************************************************
        // 文件模板路径
        String path = (tempPath);
        File file = new File(path);
        // 保存文件的路径
        String realPath = rPath;
        // 新的文件名
        String newFileName = System.currentTimeMillis() + ".xlsx";
        // 判断路径是否存在
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 写入到新的excel
        File newFile = new File(realPath, newFileName);
        try {
            newFile.createNewFile();
            // 复制模板到新文件
            fileChannelCopy(file, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 下载成功后删除
     *
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
