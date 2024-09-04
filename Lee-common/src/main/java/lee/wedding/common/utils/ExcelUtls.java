package lee.wedding.common.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ ClassName ExcelUtls
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/4 21:29
 **/
public class ExcelUtls {

    /**
     * @Author Lee MJ
     * @Description 导出数据成Excel
     * @Date 2024/9/4
     * @param list 要导出的数据
     * @param <T>  数据类型
     * @param clazz 实体Class
     * @param fileName 文件名
     * @param response 设置请求头
     * @param sheetName 工作簿名
     * @return null
    */
    public static <T> void exportExcel(HttpServletResponse response, List<T> list, String fileName, String sheetName, Class<T> clazz) {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + encodedFileName);
            // 使用EasyExcel 写入文件流
            ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream(), clazz);
            writerBuilder.sheet(sheetName).doWrite(list);
            // 关闭流
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.getOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
