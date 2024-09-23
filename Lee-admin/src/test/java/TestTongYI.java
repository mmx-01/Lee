import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import lee.wedding.manage.LeeAdminApplication;
import org.json.JSONException;
import org.junit.Test;
import java.util.Arrays;
import java.lang.System;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ ClassName TestTongYI
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/23 17:25
 **/
@SpringBootTest(classes = LeeAdminApplication.class)
@RunWith(SpringRunner.class)
public class TestTongYI {

    @Test
    public void test() throws NoApiKeyException, InputRequiredException, JSONException {
        Generation gen = new Generation();
        Message systemMsg = Message.builder()
                .role(Role.SYSTEM.getValue())
                .content("You are a helpful assistant.")
                .build();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content("介绍一下自己")
                .build();
        GenerationParam param = GenerationParam.builder()
                .model("qwen-max-latest")
                .apiKey("sk-f0fd0ce6fc904ab3848630035123ec50")
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

        System.out.println(content);

    }

}
