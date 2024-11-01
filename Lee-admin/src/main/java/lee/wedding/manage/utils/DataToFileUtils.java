package lee.wedding.manage.utils;

import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @ ClassName FileZipUtils
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/11/1 17:35
 **/
@Slf4j
public class DataToFileUtils {
    /**
     * @Author Lee MJ
     * @Description // 将数据写入到文件中，可操作上传
     * @Date 2024/11/1
     * @param lists 要打包的数据
    */

    public static HashMap<String, Object> generateZip(List<List<String>> lists,
                                                      String type,
                                                      Long taskId,
                                                      String AK,
                                                      String SK,
                                                      String url,
                                                      String BDPath,
                                                      String organizationId,
                                                      String projectId
    ) {
        HashMap<String, Object> resultFilePathName = new HashMap<>();
        ArrayList<File> fileList = new ArrayList<>();
        ArrayList<String> fileNameList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            for (int i = 0; i < lists.size(); i++) {
                List<String> list = lists.get(i);
                if (list.size() > 1) {
                    //   ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    // ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
                    // ObjectWriter writer = objectMapper.writer();

                    String jsonLines = list.stream().limit(list.size() - 1)
                            .reduce("", (acc, line) -> acc + line + System.lineSeparator());
                    // 任务类型+tab类型  拼接文件名
                    String fileName = type +"_"+ list.get(list.size()-1) + ".jsonl";

                    // 保存文件路径
                    String outFilePath = "/home/dataexa/application/data/" + fileName;

                    File outFile = new File(outFilePath);

                    File parentFile = outFile.getParentFile();
                    if (!parentFile.exists()) {
                        if (!parentFile.mkdirs()) {
                            log.error("File Mkdir Error");
                        }
                    }
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(outFile);
                        fileOutputStream.write(jsonLines.getBytes());
                    }catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (null != fileOutputStream) {
                            fileOutputStream.close();
                        }
                    }
                    fileNameList.add(fileName);
                    fileList.add(outFile);
                }
            }
        }catch (IOException e) {
            log.error("Error occurred: " + e.getMessage());
        }
        String result = null;
        for (File file : fileList) {
            result = BaiduUploadFileUtil.BDUploadFile(AK, SK, file, url, BDPath, organizationId, projectId, type);
        }
        resultFilePathName.put("fileNameList",fileNameList);
        resultFilePathName.put("result", result);
        resultFilePathName.put("taskId", taskId);
        return resultFilePathName;
    }

    // http请求调第三方接口
    public String http (String URL, HashMap<String, Object> dataHashMap) {
        return HttpUtil.post(URL, new Gson().toJson(dataHashMap));
    }
}
