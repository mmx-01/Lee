package lee.wedding.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ ClassName LeeAdminApplication
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/4 18:11
 **/
@SpringBootApplication
@EnableAsync // 开启异步事务
//@ComponentScan(basePackages = {})
//@MapperScan(basePackages = {"lee.wedding.api.*"})
public class LeeAdminApplication {
    public static void main(String[] args) {
//        System.setProperty("java.net.preferIPv4Stack", "true");
        SpringApplication.run(LeeAdminApplication.class, args);
    }
}
