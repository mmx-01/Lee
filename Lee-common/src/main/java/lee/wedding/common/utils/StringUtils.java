package lee.wedding.common.utils;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
/**
 * @ ClassName StringUtils
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/4 22:05
 **/
public class StringUtils {

    // 判空
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    // 判非空
    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    // 去除空白字符
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    // 字符串连接
    public static String join(String delimiter, String... elements) {
        return Arrays.stream(elements)
                .filter(StringUtils::isNotNullOrEmpty)
                .collect(Collectors.joining(delimiter));
    }

    // 判断是否为数字
    public static boolean isNumeric(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return str.chars().allMatch(Character::isDigit);
    }
}
