package lee.wedding.manage.utils;


import lombok.Data;

/**
 * @ ClassName LeeWebbingResult
 * @ Description 统一返回工具类
 * @ Author Limj
 * @ Date 2024/9/4 21:48
 **/
@Data
public class LeeWebbingResult<T> {

    // 状态码
    private Integer code;

    // 返回描述信息
    private String message;

    // 返回的数据
    private T data;

    // 无参构造
    public LeeWebbingResult() {}

    // 成功
    public static<T> LeeWebbingResult success(T data) {
        LeeWebbingResult<T> result = new LeeWebbingResult();
        if (null != data) {
            result.setData(data);
            result.setCode(200);
            result.setMessage("success");
        }else {
            result.setData(null);
            result.setCode(201);
            result.setMessage("success!Data is Null");
        }
        return result;
    }
    public static<T> LeeWebbingResult error() {
        LeeWebbingResult<T> result = new LeeWebbingResult();
        result.setCode(500);
        result.setMessage("error");
        return result;
    }
}
