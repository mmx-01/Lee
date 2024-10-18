package lee.wedding.manage.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaiduUploadFileUtil {

//    private static final String X_BCE_REQUEST_ID = "X_Bce_Request_ID";
//
//    private static final String SIGN_TIME = "Sign_Time";
//
//    private static final String X_BCE_REQUEST_ACCESS_ADDRESS = "X_Bce_Request_Access_Address";
//
//    private static final String ACCESS_KEY = "Access_Key";
//
//    private static final String TOKEN = "Token";


    /**
     *
     * @param AK Access Key 鉴权参数之一
     * @param SK Secret Key 鉴权参数之一
     * @param file 文件参数
     * @param url 项目地址 ip+端口
     * @param BDPath 接口地址
     * @param organizationId 存储卷Id
     * @param projectId 项目Id
     * @param batchId 批次号（可随意填写参数，批次号相同的文件会上传到同一个文件夹下，文件的父级文件夹）
     * @return String 返回结果：volumeId：储存id，path：文件路径
     */
    public static String BDUploadFile(String AK,
                                    String SK,
                                    File file,
                                    String url,
                                    String BDPath,
                                    String organizationId,
                                    String projectId,
                                    String batchId
                                      ) {
        String requestId = UUID.randomUUID().toString();
        String signTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String token = SecureUtil.sha256(SK + requestId + signTime);
        String resultBody = HttpUtil.createPost(url + BDPath)
                .header("X-Bce-Request-ID", requestId)
                .header("Sign-Time", signTime)
                .header("Access-Key", AK)
                .header("secretKey", SK)
                .header("Token", token)
                .form("organizationId", organizationId)
                .form("projectId", projectId)
                .form("batchId", batchId)
                .form("file", file)
                .execute()
                .body();
        return resultBody;
    }

}
