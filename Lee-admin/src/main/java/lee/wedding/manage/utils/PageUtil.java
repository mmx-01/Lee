package lee.wedding.manage.utils;

import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 * 分页工具
 *
 * @author czx
 */
public class PageUtil {

	/**
	 * 手动分页
	 * @param list 全部数据列表
	 * @param pageEntity 分页数据
	 * @param <T> 数据类型
	 * @return 分页结果
	 */
	public static <T> Page<T> paginate(List<T> list, Page<T> pageEntity) {
		long pageNo = pageEntity.getCurrent();
		long pageSize = pageEntity.getSize();
		long start = (pageNo - 1) * pageSize;
		long end = Math.min(start + pageSize, list.size());
		if (start > list.size()) {
			start = list.size();
		}
		List<T> resultList = list.subList((int)start, (int)end);
		Page<T> page = new Page<>(pageNo, pageSize, list.size());
		page.setRecords(resultList);
		return page;
	}

}
