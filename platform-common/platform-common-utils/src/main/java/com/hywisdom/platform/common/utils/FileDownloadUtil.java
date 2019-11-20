package com.hywisdom.platform.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * <类的作用>
 *
 * @author yyh
 * @date 2019/9/16 16:17
 */
@Slf4j
public class FileDownloadUtil {

    /**
     * 下载系统中的文件
     * @param response
     * @param filePath  系统在文件中的路径，例如：excel/XX导入模板.xls
     */
    public static void templateDownload(HttpServletResponse response,String filePath){
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            Resource resource = new ClassPathResource(filePath);
            String filename = resource.getFilename();
            //File file = resource.getFile();
            //InputStream inputStream = new FileInputStream(file);
            //以流的形式下载文件（用上面两行代码会出现本地成功，发到服务器上有问题）
            inputStream = FileHelper.getResourcesFileInputStream(filePath);
            //强制下载不打开
            //response.setContentType("application/force-download");
            out = response.getOutputStream();
            //使用URLEncoder来防止文件名乱码或者读取错误
            response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(filename, "UTF-8"));
            int b = 0;
            byte[] buffer = new byte[1024];
            while (b != -1) {
                b = inputStream.read(buffer);
                if(b != -1) {
                    out.write(buffer, 0, b);
                }
            }
            out.flush();
        } catch (IOException e) {
            log.error("下载模板出错：{}",e);
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("关闭输入流出错：{}",e);
                }
            }
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("关闭输出流出错：{}",e);
                }
            }
        }
    }
}

