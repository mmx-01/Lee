package lee.wedding.manage.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ImageComprehendUtil
 * @Description: 类描述
 * @author: sunjiuxiang
 * @date: 2024/8/5 11:26
 */
@Slf4j
public class ImageComprehendUtil {

    /**
     * http://ip:port/worker_generate_stream
     * @param baseUrl
     * @param imageContent
     * @param isUrl
     * @return
     */
    public static String imageComprehend(String baseUrl,String imageContent,String prompt,Boolean isUrl){
        Map map = new HashMap();
        map.put("model","llava-v1.6-vicuna-13b");
        map.put("prompt",prompt);
        map.put("temperature",0.2);
        map.put("top_p",0.7);
        map.put("max_new_tokens'",1536);
        map.put("stop","</s>");
        if (isUrl){
            map.put("image-file",imageContent);
        }else {
            //img_b64_str = base64.b64encode(buffered.getvalue()).decode()
            List list = new ArrayList<>();
            list.add(imageContent);
            map.put("images",list);
        }
        String body = JSON.toJSONString(map);
        String result = HttpUtil.post(baseUrl, body,60000);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map resultMap = objectMapper.readValue(result.substring(result.lastIndexOf("{"),result.lastIndexOf("}")+1), Map.class);
            String text = (String) resultMap.get("text");
            result = text.substring(text.lastIndexOf("ASSISTANT:")+10,text.length());
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return "";
    }
}
