package lee.wedding.manage.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.json.JSONUtil;
import cn.hutool.http.HttpRequest;
//还需要引入下面两个domain类

@RestController
public class ChatController {
//    @PostMapping("/chat")//文档要求使用post请求
//    public ChatResponse chat(String q){
//        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";//这里看官方文档
//        String ApiKey = "sk-f0fd0ce6fc904ab3848630035123ec50";//这里换成你自己的ApiKey
//
//        ChatRequest chatRequest = new ChatRequest(q);
//        String json = JSONUtil.toJsonStr(chatRequest);
//        //System.out.println(json);//正式发送给api前,查看请求的主要数据情况
//        String result = HttpRequest.post(url)
//                .header("Authorization","Bearer "+ ApiKey)
//                .header("Content-Type","application/json")
//                .body(json)
//                .execute().body();
//        System.out.println(result);
//        return JSONUtil.toBean(result, ChatResponse.class);
//    }

    public ChatResponse chat(String q){
        String url = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";//这里看官方文档
        String ApiKey = "sk-f0fd0ce6fc904ab3848630035123ec50";//这里换成你自己的ApiKey

        ChatRequest chatRequest = new ChatRequest(q);
        String json = JSONUtil.toJsonStr(chatRequest);
        //System.out.println(json);//正式发送给api前,查看请求的主要数据情况
        String result = HttpRequest.post(url)
                .header("Authorization","Bearer "+ ApiKey)
                .header("Content-Type","application/json")
                .body(json)
                .execute().body();
        System.out.println(result);
        return JSONUtil.toBean(result, ChatResponse.class);
    }

    public static void main(String[] args) {
        ChatController controller = new ChatController();
        ChatResponse ask = controller.chat("who is you");
        System.out.println(ask);
    }
}
