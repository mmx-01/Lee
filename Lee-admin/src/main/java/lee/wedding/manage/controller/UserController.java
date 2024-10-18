package lee.wedding.manage.controller;


import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import lee.wedding.manage.utils.LeeWebbingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * @ ClassName UserController
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/5 14:18
 **/
@RestController
@RequestMapping("/admin/user")
public class UserController {
    
    
    @Value("${aliyun.apiKey}")
    private String apiKey;

    @Value("${aliyun.model}")
    private String model;

    @GetMapping("/chatAi")
    public String chatAi(@RequestParam("text") String text) throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        com.alibaba.dashscope.common.Message systemMsg = com.alibaba.dashscope.common.Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();
        com.alibaba.dashscope.common.Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(text)
                .build();
        GenerationParam param = GenerationParam.builder()
                .model(model)
                .apiKey(apiKey)
                .messages(Arrays.asList(systemMsg, userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        GenerationResult call = gen.call(param);

        String json = JsonUtils.toJson(call);

        JSONObject jsonObject = new JSONObject(json);
        String content = jsonObject
                .getJSONObject("output")
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

//        System.out.println(content);
        return content;
    }
}
