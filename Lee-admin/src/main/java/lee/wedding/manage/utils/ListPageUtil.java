package lee.wedding.manage.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ListPageUtil {
    /**
     * list集合分页方法
     * @param list 需要分页的原始数据
     * @param pageNo 当前页码 默认1
     * @param pageSize 每页大小 默认10
     * @param <T> 列表中元素类型
     * @return 返回分页后的数据列表
     */
    public static <T> List<T> paginate(List<T> list, Integer pageNo, Integer pageSize) {
        if (null == list || pageNo < 1 || pageSize < 1) {
            log.error("===============无效参数，列表不能为空，页码和页数必须大于0");
            return null;
        }
        Integer totalItems = list.size(); // 列表中的总条数
        Integer fromIndex = (pageNo -1) * pageSize; // 当前页数据的起始索引
        Integer toIndex = Math.min(fromIndex + pageSize, totalItems); // 当前页的结束索引

        // 检查页码是否有效
        if (fromIndex >= totalItems || fromIndex < 0) {
            return new ArrayList<>(); // 超出页码范围，返回空集合
        }
        // 返回分页后的数据列表
        return new ArrayList<>(list.subList(fromIndex, toIndex));
    }
}
