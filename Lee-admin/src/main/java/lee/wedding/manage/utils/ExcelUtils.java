package lee.wedding.manage.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {

    /**
     * 导出数据到 Excel 文件
     *
     * @param dataList 要导出的数据列表
     * @param filePath 导出的文件路径
     * @param <T>      数据类型
     */
    public static <T> void exportToExcel(List<T> dataList, String filePath) {
        // 使用 EasyExcel 进行导出
        ExcelWriter excelWriter = EasyExcel.write(filePath, dataList.get(0).getClass()).build();

        // 创建一个工作表并写入数据
        WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();
        excelWriter.write(dataList, writeSheet);

        // 关闭流
        excelWriter.finish();
    }
    public static <T> void export(HttpServletResponse response, List<T> list, Class<T> clazz, String fileName, String sheetName) {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName);

            // 使用 EasyExcel 写入文件流
            ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream(), clazz);
            ExcelWriterSheetBuilder sheetBuilder = writerBuilder.sheet(sheetName);
            sheetBuilder.doWrite(list);

            // 关闭流
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            // 处理异常
            e.printStackTrace();
            // 或者抛出自定义异常
            // throw new ExcelExportException("导出Excel文件失败", e);
        }
    }
}