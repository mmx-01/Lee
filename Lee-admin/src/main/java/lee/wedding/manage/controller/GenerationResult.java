package lee.wedding.manage.controller;

import lombok.Data;

/**
 * @ ClassName GenerationResult
 * @ Description TODO
 * @ Author Limj
 * @ Date 2024/9/23 20:53
 **/
@Data
public class GenerationResult {
    private String requestId;
    private GenerationUsage usage;
    private String output;

}
