package lee.wedding.common.utils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @ ClassName ReadFileZip
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/4 20:44
 **/
public class ReadFileZip {

    /**
     * @Author Lee MJ
     * @Description // 上传zip压缩包，将压缩包的所有文件读取出来，
     * 转成String类型，并且逐行读取，逐行写数据。使用ZipArchiveInputStream，支持压缩包中含有中文名称的文件
     * @Date 2024/9/4
     * @param file 上传文件参数
    */
    public static String readFile(MultipartFile file) {
        ZipArchiveInputStream zis = null;
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            InputStream inputStream = file.getInputStream();
            zis = new ZipArchiveInputStream(inputStream);
            ZipArchiveEntry ze;
            while ((ze = zis.getNextZipEntry()) != null) {
                // 处理每个文件中的条目
                if (!ze.isDirectory()) {
                    try {
                        reader = new BufferedReader(new InputStreamReader(zis));
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != zis) {
                try {
                    zis.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
