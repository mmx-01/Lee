package lee.wedding.manage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ ClassName LeeController
 * @ Description
 * @ Author Limj
 * @ Date 2024/9/4 18:15
 **/
@RestController
@RequestMapping("/test/admin")
public class LeeController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
