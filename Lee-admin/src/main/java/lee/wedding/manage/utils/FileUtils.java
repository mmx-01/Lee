package lee.wedding.manage.utils;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName: FileUtils
 * @Description: 文件工具类
 * @author: sunjiuxiang
 * @date: 2024/4/15 18:27
 */
@Slf4j
public class FileUtils {

    public static byte[] read(String filePath) {
        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
            byte[] data = inputStream2ByteArray(in);
            return data;
        }catch (IOException e){
            e.printStackTrace();
            log.error("read file error::" +filePath+e);
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;

    }
    private static byte[] inputStream2ByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static void StringToTxtFile(String text, String path) {
        // 字符串内容
        FileWriter writer = null;
        // 创建新的txt文件
        File file = new File(path);

        try {
            writer = new FileWriter(file);
            // 将字符串写入txt文件
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 保存并关闭文件
            try {
                if (null != writer) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

//    public static Path toPath(String fileName) {
//        try {
//            URL fileUrl = FileUtils.class.getResource(fileName);
//            return Paths.get(fileUrl.toURI());
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static String saveToLocal(MultipartFile file, String path, String newName) {
        if (file.isEmpty()) {
            log.info("save to local,file is empty");
            throw new RuntimeException("A_FILE_NOT_EXIST");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String fileExt = getFileExtension(fileName);
        log.info("save to local,original name:{},new name:{}", fileName, newName);
//        String pathName = path + newName + "." + fileExt;
        String pathName = path + newName;
        try {
            // 将文件保存到目标路径
            file.transferTo(new File(pathName));
        } catch (IOException e) {
            log.error("save to local error", e);
        }
        return pathName;
    }

    public static byte[] readBytes(String localPath) {
        try {
            return org.apache.commons.io.FileUtils.readFileToByteArray(new File(localPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            // 文件名中没有后缀或者后缀位于文件名的末尾
            return "";
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }

    public static String getContentType(File file) {
        if (file.getName().endsWith(".txt")) {
            return "text/plain; charset=utf-8";
        }
        if (file.getName().endsWith(".html")) {
            return "text/html; charset=UTF-8";
        }

        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        return mimetypesFileTypeMap.getContentType(file);
    }

    public static String genNewFileName(String title, String basePath, String fileExtension) {
        String timeStr = LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");
        String txtName = title + "_" + timeStr + "."+fileExtension;
        File folder = new File(basePath+"/" + timeStr);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        return txtName;
    }

    // 新的文件夹上传，拼接window上的文件夹路径
    public static String getNewFileName(String title, String basePath, String fileExtension, String path) {
        String timeStr = LocalDateTimeUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss");
        String txtName = title + "_" + timeStr + "."+fileExtension;
        File folder = new File(basePath+ "/" + path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return path+"/"+ txtName;
    }

    public static Integer getFileTypeByExtension(String fileExtension){
        Integer fileType = 0;
        switch (fileExtension){
            case "txt":
                fileType = 1;
                break;
            case "jpg":
            case "png":
            case "jpeg":
            case "webp":
                fileType =  2;
                break;
            case "pdf":
                fileType = 3;
                break;
            case "mp4":
            case "avi":
            case "mov":
            case "mkv":
            case "flv":
            case "wmv":
            case "webm":
            case "m4v":
            case "3gp":
            case "3g2":
                fileType = 4;
            break;
            case "docx":
                fileType = 5;
            break;
            case "html":
            case "htm":
                fileType = 6;
            break;
            case "json":
                fileType = 7;
            break;
        }
        return fileType;
    }


    /**
     * 提取文件内容
     * @param file
     * @return
     * @throws IOException
     */
    public static String extractContent(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        if (contentType != null) {
            switch (contentType) {
                case "application/pdf":
                    return extractPdfContent(file.getInputStream());
                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                    return extractWordContent(file.getInputStream());
                case "text/plain":
                    return new String(file.getBytes(), StandardCharsets.UTF_8);
                default:
                    throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }
        } else {
            throw new IllegalArgumentException("File content type is null");
        }
    }

    private static String extractPdfContent(InputStream inputStream) throws IOException {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    private static String extractWordContent(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            return extractor.getText();
        }
    }

    public static String downloadFileByUrl (String url) {
        StringBuilder fileTextBuilder = new StringBuilder();
        try(InputStream is = new URL(url).openStream();
            XWPFDocument document = new XWPFDocument(is)) {
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                fileTextBuilder.append(paragraph.getText()).append(System.lineSeparator());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return fileTextBuilder.toString();
    }
}
