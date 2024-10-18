package lee.wedding.manage.utils;


import cn.hutool.log.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class FileZipCreator {


    @Value("${kangaroohy.minio.local-file-path}")
    private String basePath;

    private static final ObjectMapper objectMapper = new ObjectMapper();

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
                    String fileName = type +"_"+ list.get(list.size()-1) + ".jsonl"; // 任务类型+tab类型

                    String outFilePath = "/home/dataexa/application/data/" + fileName;

                    //               ZipEntry zipEntry = new ZipEntry(fileName);
                    //               zipOutputStream.putNextEntry(zipEntry);
                    //            zipOutputStream.write(jsonLines.getBytes());
                    //               zipOutputStream.closeEntry();
                    //              zipOutputStream.close();
                    //             String zipFileName = format.format(new Date()) + "_" + list.get(list.size()-1) + ".zip";
                    //              fileNameList.add(zipFileName);
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

}
