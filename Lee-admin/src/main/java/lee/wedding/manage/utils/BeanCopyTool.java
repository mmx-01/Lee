package lee.wedding.manage.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: BeanCopyTool
 * @Description: 类描述
 * @author: sunjiuxiang
 * @date: 2024/4/22 11:25
 */
@Slf4j
public class BeanCopyTool {
    public static <T> T convertCopyBean(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception ex) {
            log.error("转换错误",ex);
        }
        return null;
    }

    public static <T, E> List<T> convertCopyList(Collection<E> oldList, Class<T> clazz) {
        List<T> newList = new ArrayList<>();
        if (oldList != null && !oldList.isEmpty()) {
            try {
                for (Object item : oldList) {
                    T newObj = clazz.newInstance();
                    BeanUtils.copyProperties(item, newObj);
                    newList.add(newObj);
                }
            } catch (Exception ex) {
                log.error("转换错误",ex);
            }
        }
        return newList;
    }

}
