package lee.wedding.manage.utils;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @ ClassName DocHandler
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/7/9 15:58
 * 解析word文件 保留工具类
 **/


public class DocxHandler {
    public static void main(String[] args) {
        StringBuilder content = new StringBuilder();
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream("D:\\otherFile\\test.docx"));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    // 获取文本及其样式信息
                    String text = run.getText(0);
                    // 这里可以获取更多的样式信息，如字体、颜色、大小等
                    content.append(text);
                }
                content.append("\n"); // 可根据需要添加段落分隔符
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        return content.toString();

//        try {
//            // 1. 读取现有的 docx 文件
//            FileInputStream fis = new FileInputStream("D:\\otherFile\\test.docx");
//            XWPFDocument doc = new XWPFDocument(fis);
//
//            // 2. 处理文档内容，例如读取段落、表格等
//            for (XWPFParagraph paragraph : doc.getParagraphs()) {
//                System.out.println(paragraph.getText());
//            }
//            for (XWPFTable table : doc.getTables()) {
//                for (XWPFTableRow row : table.getRows()) {
//                    for (XWPFTableCell cell : row.getTableCells()) {
//                        System.out.println(cell.getText());
//                    }
//                }
//            }
//
//            // 3. 修改文档内容或者创建新文档
//            XWPFParagraph newParagraph = doc.createParagraph();
//            XWPFRun run = newParagraph.createRun();
//            run.setText("This is a new paragraph added using Apache POI.");
//
//            // 4. 保存修改后的文档
//            FileOutputStream fos = new FileOutputStream("path/to/save/modified/file.docx");
//            doc.write(fos);
//
//            // 5. 关闭流
//            fos.close();
//            fis.close();
//            doc.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//
//        }
    }
    public String readDocxFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            XWPFDocument document = new XWPFDocument(new FileInputStream(filePath));
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    // 获取文本及其样式信息
                    String text = run.getText(0);
                    // 这里可以获取更多的样式信息，如字体、颜色、大小等
                    content.append(text);
                }
                content.append("\n"); // 可根据需要添加段落分隔符
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}