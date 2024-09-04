package lee.wedding.common.utils;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @ ClassName CollectionUtils
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/4 22:05
 **/
public class CollectionUtils {
    // 判空
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    // 判非空
    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    /**
     * @Author Lee MJ
     * @Description 使用说明：假设你有一个 List<String>，想要按字母顺序对字符串进行排序，可以这样做：
     * List<String> words = Arrays.asList("banana", "apple", "cherry");
     * List<String> sortedWords = sort(words, Comparator.naturalOrder());
     * // sortedWords 结果是 ["apple", "banana", "cherry"]
    */
    public static <T> List<T> sort(List<T> list, Comparator<? super T> comparator) {
        return list.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * @Author Lee MJ
     * @Description 使用说明：假设你有一个 List<Integer>，想要过滤出所有大于 10 的数字，可以这样做：
     * List<Integer> numbers = Arrays.asList(5, 15, 8, 22);
     * List<Integer> filteredNumbers = filter(numbers, n -> n > 10);
     * filteredNumbers 结果是 [15, 22]
    */
    public static <T> List<T> filter(List<T> list, java.util.function.Predicate<? super T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}
