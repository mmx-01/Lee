package lee.wedding.common.utils;

//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @ ClassName PageUtil
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/4 20:58
 **/
public class PageUtils {

    /**
     * @Author Lee MJ
     * @Description // 手动分页
     * @Date 2024/9/4
     * @param list 要做分页的数据
     * @param pageEntity 分页数据 页码和总条数
     * @param <T> 数据类型
     * @return 返回分页结果
    */
//    public static <T> Page<T> getPage(List<T> list, Page<T> pageEntity){
//        long pageNo = pageEntity.getCurrent();
//        long pageSize = pageEntity.getSize();
//        long start = (pageNo - 1) * pageSize;
//        long end = Math.min(start + pageSize, list.size());
//        if (start > list.size()) {
//            start = list.size();
//        }
//        List<T> resultList = list.subList((int) start, (int) end);
//        Page<T> page = new Page<>(pageNo, pageSize, list.size());
//        pageEntity.setRecords(resultList);
//        return page;
//    }
}
