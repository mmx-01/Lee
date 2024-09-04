package lee.wedding.common.utils;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
/**
 * @ ClassName DateUtil
 * @ Author Limj
 * @ Date 2024/9/4 21:59
 * @Description 使用说明
 * @Description 获取当前日期和时间：
 * LocalDate today = DateUtils.nowDate();
 * LocalDateTime now = DateUtils.nowDateTime();
 * @Description 格式化日期和时间：
 * String formattedDate = DateUtils.formatDate(today);
 * String formattedDateTime = DateUtils.formatDateTime(now);
 * @Description 解析日期和时间：
 * LocalDate parsedDate = DateUtils.parseDate("2024-09-04");
 * LocalDateTime parsedDateTime = DateUtils.parseDateTime("2024-09-04 13:58:00");
 * @Description 计算日期之间的差异：
 * long daysBetween = DateUtils.daysBetween(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
 * @Description 将 java.util.Date 与 java.time 类型互转：
 * Date utilDate = DateUtils.toUtilDate(today);
 * LocalDate localDate = DateUtils.toLocalDate(utilDate);
 **/
public class DateUtils {

    // 定义常用的日期时间格式
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 获取当前日期
    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    // 获取当前时间
    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

    // 获取当前时间戳（秒）
    public static long nowTimestamp() {
        return Instant.now().getEpochSecond();
    }

    // 获取当前时间戳（毫秒）
    public static long nowTimestampMillis() {
        return Instant.now().toEpochMilli();
    }

    // 日期转换为字符串
    public static String formatDate(LocalDate date) {
        return date.format(DEFAULT_DATE_FORMATTER);
    }

    // 日期时间转换为字符串
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DEFAULT_DATETIME_FORMATTER);
    }

    // 字符串转换为日期
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DEFAULT_DATE_FORMATTER);
    }

    // 字符串转换为日期时间
    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DEFAULT_DATETIME_FORMATTER);
    }

    // 获取两个日期之间的天数差
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    // 获取两个日期时间之间的秒数差
    public static long secondsBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.SECONDS.between(start, end);
    }

    // 获取本周的开始日期（周一）
    public static LocalDate startOfWeek() {
        return LocalDate.now().with(DayOfWeek.MONDAY);
    }

    // 获取本月的开始日期
    public static LocalDate startOfMonth() {
        return LocalDate.now().withDayOfMonth(1);
    }

    // 获取本年的开始日期
    public static LocalDate startOfYear() {
        return LocalDate.now().withDayOfYear(1);
    }

    // 日期加天数
    public static LocalDate addDays(LocalDate date, long days) {
        return date.plusDays(days);
    }

    // 日期减天数
    public static LocalDate subtractDays(LocalDate date, long days) {
        return date.minusDays(days);
    }

    // 日期时间加秒数
    public static LocalDateTime addSeconds(LocalDateTime dateTime, long seconds) {
        return dateTime.plusSeconds(seconds);
    }

    // 日期时间减秒数
    public static LocalDateTime subtractSeconds(LocalDateTime dateTime, long seconds) {
        return dateTime.minusSeconds(seconds);
    }

    // 获取当前日期和时间的字符串（指定格式）
    public static String formatNow(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
        return LocalDateTime.now().format(formatter);
    }

    // 将日期转换为 java.util.Date
    public static Date toUtilDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // 将 java.util.Date 转换为 LocalDate
    public static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    // 将日期时间转换为 java.util.Date
    public static Date toUtilDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 将 java.util.Date 转换为 LocalDateTime
    public static LocalDateTime toLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    // 获取当前时间的 Unix 时间戳
    public static long getUnixTimestamp() {
        return Instant.now().getEpochSecond();
    }

    // 获取当前时间的 Unix 时间戳（毫秒）
    public static long getUnixTimestampMillis() {
        return Instant.now().toEpochMilli();
    }
}
