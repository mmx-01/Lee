package lee.wedding.manage.utils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReadFileJsonText {


    public static String readZipFile (MultipartFile files) {
        ZipArchiveInputStream zipInputStream = null;
        ArrayList<String> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            InputStream inputStream =  files.getInputStream();
            zipInputStream = new ZipArchiveInputStream(inputStream);

            ZipArchiveEntry entry;
            while ((entry = zipInputStream.getNextZipEntry()) != null) {
                // 处理每个文件中的条目
                if (!entry.isDirectory()) {
                    try {
                        reader = new BufferedReader(new InputStreamReader(zipInputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            list.add(sb.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (zipInputStream != null) {
                try {
                    zipInputStream.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
