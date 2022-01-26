package top.fan2wan.resource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fan2wan.resource.dto.FileResourceDto;
import top.fan2wan.resource.service.IFileResourceService;

/**
 * @Author: fanT
 * @Date: 2022/1/26 13:35
 * @Description: controller for fileResource
 */
@RestController
@RequestMapping("fileResource")
public class FileResourceController {
    private final IFileResourceService fileResourceService;

    public FileResourceController(IFileResourceService fileResourceService) {
        this.fileResourceService = fileResourceService;
    }

    @GetMapping("/saveTest")
    public boolean saveTest() {

        return fileResourceService.saveFileResource(FileResourceDto.FileResourceDtoBuilder.aFileResourceDto()
                        .withPath("2021/10/12/2.doc")
                .build());
    }
}
