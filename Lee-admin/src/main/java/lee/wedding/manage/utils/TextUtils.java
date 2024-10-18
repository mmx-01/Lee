package lee.wedding.manage.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
public class TextUtils {

    public final  static String ADDRESS = "http://192.168.1.148:28080/doc_ocr";

//    @Value("${ocr.address}")
//    private String OCR_ADDRESS;


    public static String uploadPdf(String filePath, String fileType, String OcrAddress) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(30000, TimeUnit.SECONDS) // 设置链接超时时间
                .writeTimeout(30000, TimeUnit.SECONDS) // 设置写入超时时间
                .readTimeout(30000, TimeUnit.SECONDS) // 设置读取超时时间
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        File pdfFile = new File(filePath);
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(fileType, pdfFile.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), pdfFile))
                // 添加文件大小到请求体中，可选项
                .build();
        Request request = new Request.Builder()
                .url(OcrAddress)
                .method("POST", body)
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        String jsonResult = null;
        try {
            Response response = client.newCall(request).execute();
//            BufferedSource source = response.body().source();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            jsonResult = gson.toJson(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String s = convertToJSON(jsonResult);
        System.out.println(s);
        String[] split = s.split(":");
        // 使用 Arrays.copyOfRange 方法获取从索引 4 开始到结束的子数组
        String[] newArray = Arrays.copyOfRange(split, 4, split.length);
        String output = Arrays.toString(newArray).replaceAll("\\[|\\]", ""); // 使用正则表达式去掉方括号
        String substring = output.substring(0, output.length() - 3);
        System.out.println(substring);
        return substring;
    }
    /**
     * 将包含Unicode转义序列的字符串转换为JSON格式的字符串
     * @param unicodeText 包含Unicode转义序列的字符串
     * @return JSON格式的字符串
     */
    public static String convertToJSON(String unicodeText) {
        String decodedText = unicodeToChar(unicodeText);
        // 替换掉结果中的反斜杠 \
        decodedText = decodedText.replace("\\", "");
        return "\"content\": \"" + decodedText + "\"";
    }

    /**
     * 解码Unicode转义序列为实际字符
     * @param unicode 包含Unicode转义序列的字符串
     * @return 解码后的字符串
     */
    private static String unicodeToChar(String unicode) {
        StringBuilder sb = new StringBuilder();
        Pattern pattern = Pattern.compile("\\\\u(\\p{XDigit}{4})");
        Matcher matcher = pattern.matcher(unicode);

        while (matcher.find()) {
            char ch = (char) Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(sb, Character.toString(ch));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    // 解析word文件
    public static String analysisWord(String filePath) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            String text = extractor.getText();
            fis.close();
            return text;
        } catch (IOException e) {
            assert fis != null;
            fis.close();
            throw new RuntimeException(e);
        }
    }

    // 数据治理正则
    public static String filterText(String originalText) {
        String pattern = "[  　‎\\u00A0\\u000D\\u000C\\u000B\\u0009\\u0008\\u200F\\u200D\\u2006\\uFEFF\\u2029\\u2028]";
//        String pattern2 = "\\n+";
        String pattern3 = " +";
        return originalText.replaceAll(pattern, "").replaceAll(pattern3, " ");
    }

    // 数据处理
    public static String StringBuildToDo(StringBuilder stringBuilder) {
        // 判断最后一位是否是逗号
        if (stringBuilder.length() > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ',') {
            // 删除最后一位逗号
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    // 解析txt文件为字符串
    public static String readTxt (String filePath) {
        StringBuilder txtBuilder = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(filePath);

            BufferedReader fileBufferReader = new BufferedReader(fileReader);
            String line;
            while ((line = fileBufferReader.readLine()) != null) {
                txtBuilder.append(line).append("\n");
            }
            return txtBuilder.toString();
        }catch (IOException e) {
            log.error("+++++++++++++++文件读取失败+++++++++++");
        }
        return null;
    }

    public static String textTypeAiChatUtil (MultipartFile file, String basePath,String OCRADDRESS) {
        String text = null;
        if (null !=  file) {
            try {
                String originalFileName = file.getOriginalFilename();
                String title = originalFileName.substring(0,originalFileName.lastIndexOf("."));
                String fileExtension = FileUtils.getFileExtension(originalFileName);
                String fileName = FileUtils.genNewFileName(title,basePath,fileExtension);
                String pathName = FileUtils.saveToLocal(file, basePath, fileName);
                try {
                    if ("docx".equals(fileExtension)) {
                        return analysisWord(pathName);
                    }
                    if ("pdf".equals(fileExtension)) {
                        return TextUtils.uploadPdf("E:\\API手册Lite-1.3.pdf", "pdf", OCRADDRESS);
                    }
                    if ("txt".equals(fileExtension)) {
                        return  TextUtils.readTxt(pathName);
                    }
                    if ("png".equals(fileExtension) || "jpg".equals(fileExtension) || "jpeg".equals(fileExtension)) {
                        return TextUtils.uploadPdf(pathName, "image", OCRADDRESS);
                    }
                } catch (IOException e) {
                    log.error("文件格式或解析文件失败{}",e.getMessage());
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
        return null;
    }

    // 处理html标签和html实体
    public static String htmlLabelAndEntity (String str) {
        String result = null;
        if (null != str) {
            result = str
                    .replaceAll("<[^>]+>", "")
                    .replaceAll("&nbsp;", " ")
                    .replaceAll("&ldquo;", "“")
                    .replaceAll("&rdquo;", "”");
        }
        return result;
    }
}
