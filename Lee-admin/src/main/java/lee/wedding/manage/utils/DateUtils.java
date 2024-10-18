package lee.wedding.manage.utils;
import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @ClassName DateUtils
 * @Description TODO
 * @Author Limj
 * @Date 2024/7/4 15:10
 **/
public class DateUtils {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间的时间戳（毫秒）
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 将时间戳转换为指定格式的字符串
     * @param timestamp 时间戳（毫秒）
     * @param pattern 时间格式
     * @return 格式化后的时间字符串
     */
    @NotNull
    public static String timestampToString(long timestamp, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(timestamp));
    }

    /**
     * 将时间戳转换为默认格式（yyyy-MM-dd HH:mm:ss）的字符串
     * @param timestamp 时间戳（毫秒）
     * @return 格式化后的时间字符串
     */
    @NotNull
    public static String timestampToString(long timestamp) {
        return timestampToString(timestamp, DEFAULT_PATTERN);
    }

    /**
     * 将字符串解析为 Date 对象
     * @param dateString 时间字符串
     * @param pattern 时间格式
     * @return 解析后的 Date 对象
     * @throws ParseException 解析异常
     */
    public static Date stringToDate(String dateString, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateString);
    }

    /**
     * 将字符串解析为 Date 对象（使用默认格式）
     * @param dateString 时间字符串
     * @return 解析后的 Date 对象
     * @throws ParseException 解析异常
     */
    public static Date stringToDate(String dateString) throws ParseException {
        return stringToDate(dateString, DEFAULT_PATTERN);
    }

    /**
     * 获取当前时间的默认格式（yyyy-MM-dd HH:mm:ss）的字符串
     * @return 当前时间的格式化字符串
     * @Author Lee MJ
     */
    @NotNull
    public static String getCurrentTimeString() {
        return timestampToString(getCurrentTimestamp(), DEFAULT_PATTERN);
    }
    /**
     * @Author Lee MJ
     * @Description //TODO
     * @Date 2024/7/4
     * 返回当前时间的时间戳
    */
    @NotNull
    public static String getNowCurrentTimeMillisString() {
       return System.currentTimeMillis() + "";
    }

}