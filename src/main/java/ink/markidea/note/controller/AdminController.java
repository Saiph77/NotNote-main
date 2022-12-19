package ink.markidea.note.controller;

import ink.markidea.note.entity.dto.WebsiteConfigDto;
import ink.markidea.note.entity.req.RemoteRepoRequest;
import ink.markidea.note.entity.req.WebsiteConfigReq;
import ink.markidea.note.entity.resp.ServerResponse;
import ink.markidea.note.entity.vo.UserVo;
import ink.markidea.note.service.IAdminService;
import ink.markidea.note.service.IFileService;
import ink.markidea.note.service.INoteService;
import ink.markidea.note.service.IUserService;
import ink.markidea.note.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/api/admin")
@Api(tags = "管理员接口",description = "管理网站相关接口")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "获取网站配置信息",notes = "")
    @GetMapping("websiteConfig")
    public ServerResponse<WebsiteConfigDto> pullWebsiteConfig(){
        return ServerResponse.buildSuccessResponse(adminService.getWebsiteConfig());
    }

    @ApiOperation(value = "更新网站配置信息",notes = "")
    @PostMapping("updateWebsiteConfig")
    public ServerResponse updateWebsiteConfig(@RequestBody WebsiteConfigReq req) {
        return adminService.updateWebSiteConfig(req) ? ServerResponse.buildSuccessResponse() : ServerResponse.buildErrorResponse("更新失败");
    }

    @ApiOperation(value = "展示所有用户",notes = "")
    @GetMapping("listUser")
    public ServerResponse<Page<UserVo>> listUser(Integer page, Integer size) {
        return ServerResponse.buildSuccessResponse(userService.getUserList(page, size));
    }
}
