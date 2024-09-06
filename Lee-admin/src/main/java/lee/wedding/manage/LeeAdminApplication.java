package lee.wedding.manage;

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
@ComponentScan(basePackages = "lee.wedding.*")
//@MapperScan("lee.wedding.*")
public class LeeAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeeAdminApplication.class, args);
    }
}
