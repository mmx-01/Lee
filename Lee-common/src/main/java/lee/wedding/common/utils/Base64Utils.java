package lee.wedding.common.utils;

import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @ ClassName Base64Util
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/4 21:21
 **/
public class Base64Utils {

    /**
     * @Author Lee MJ
     * @Description // 使用Base64进行加密
     * @Date 2024/9/4
     * @param content 密文
    */
    @SneakyThrows
    public static String base64Encode(String content) {
        return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
    }
    /**
     * @Author Lee MJ
     * @Description // 使用Base64进行解密
     * @Date 2024/9/4
     * @param content
    */
    @SneakyThrows
    public static String base64Decode(String content) {
        return new String(Base64.getDecoder().decode(content), StandardCharsets.UTF_8);
    }
}
