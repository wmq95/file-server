package top.fan2wan.fileserver.convert.controller;

import org.springframework.web.bind.annotation.RestController;
import top.fan2wan.fileserver.convert.service.IConvertService;

/**
 * @Author: fanT
 * @Date: 2021/10/22 13:20
 * @Description: controller for convert
 */
@RestController
public class ConvertController {
    private final IConvertService convertService;

    public ConvertController(IConvertService convertService) {
        this.convertService = convertService;
    }
}
