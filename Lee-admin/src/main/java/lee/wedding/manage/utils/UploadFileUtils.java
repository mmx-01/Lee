package lee.wedding.manage.utils;


import cn.hutool.core.util.IdUtil;
import com.hjj.datasupport.result.DataExaResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @ ClassName UploadFileUtils
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/7/12 13:56
 **/

public class UploadFileUtils {


    private static final Logger log = LoggerFactory.getLogger(UploadFileUtils.class);

    /**
     * @Author Lee MJ
     * @Description //TODO
     * @Date 2024/7/15
     * fileLocalPath 上传地址
     * mulFile 文件流
     * 返回DataExaResult，通知消息，是否上传成功,返回文件名
    */

    public static LeeWebbingResult uploadPhotos(String fileLocalPath, MultipartFile mulFile) {
//        String fileLocalPath = basePath;
        log.info("进入图片上传方法========================");
        String fileName = null;
        try {
            if (mulFile == null) {
                throw new IOException("上传的图片不能为空!");
            }
            String path = fileLocalPath +"/";
            File file = new File(path);
            //如果文件夹不存在
            if (!file.exists()) {
                //创建文件夹
                file.mkdir();
            }
            String myFileName = mulFile.getOriginalFilename();
            // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
            if (!myFileName.trim().equals("")) {
                // 重命名上传后的文件名
                fileName = IdUtil.getSnowflake(0, 0).nextId() + ".jpg";
                //+ fileImg.getOriginalFilename();
                // 定义上传路径
                String fpath = path + fileName;
                File localFile = new File(fpath);
                mulFile.transferTo(localFile);
//                log.info("文件权限已添加成功！");
            }
        }catch (IOException e) {
            e.printStackTrace();
            log.error("上传图片失败!删除已上传成功的图片!错误信息:" + e.getMessage());
            return LeeWebbingResult.error();
        }
        return LeeWebbingResult.success(fileName);
    }
}
