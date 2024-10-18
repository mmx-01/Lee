package lee.wedding.manage.utils;

import lombok.Getter;

@Getter
public enum PromptWordText {
//    public final static Integer SERVICEID = 1;

    // 打标签
    SERVICE_ONE(1,100),
    // 关联主题
    SERVICE_TWO(2,1000);

    public final Integer SERVICEID;
    public final Integer BIZID;

    // 构造函数
    PromptWordText(int service_id, int biz_id) {
        this.SERVICEID= service_id;
        this.BIZID= biz_id;
    }
}
