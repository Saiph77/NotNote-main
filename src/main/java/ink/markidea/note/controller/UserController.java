package ink.markidea.note.controller;

import ink.markidea.note.entity.dto.EditorConfigDto;
import ink.markidea.note.entity.req.EditorConfigReq;
import ink.markidea.note.entity.req.RemoteRepoRequest;
import ink.markidea.note.entity.req.UserRequest;
import ink.markidea.note.entity.resp.ServerResponse;
import ink.markidea.note.entity.vo.UserVo;
import ink.markidea.note.service.IAdminService;
import ink.markidea.note.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户相关接口",description = "操作用户")
public class UserController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "登陆",notes = "")
    @PostMapping("login")
    public ServerResponse<UserVo> login(@RequestBody UserRequest request){
        return userService.login(request.getUsername(), request.getPassword());
    }

    @ApiOperation(value = "验证过滤器",notes = "")
    @PostMapping("validate")
    public ServerResponse validate(){
        // Validated by interceptor
        return ServerResponse.buildSuccessResponse();
    }

    @ApiOperation(value = "注册",notes = "")
    @PostMapping("register")
    public ServerResponse<UserVo> register(@RequestBody UserRequest request){
        return userService.register(request.getUsername(), request.getPassword());
    }

    @ApiOperation(value = "更改密码",notes = "")
    @PostMapping("/changePass")
    public ServerResponse changePassword(@RequestBody UserRequest request){
        return userService.changePassword(request.getPassword(), request.getNewPassword());
    }

    @ApiOperation(value = "退出登陆",notes = "")
    @PostMapping("/logout")
    public ServerResponse logout(){
        return userService.logout();
    }

    @ApiOperation(value = "设置远程仓库地址",notes = "")
    @PutMapping("remote")
    public ServerResponse setRemoteRepoUrl(@RequestBody RemoteRepoRequest request){
        return adminService.setRemoteRepoUrl(request.getRemoteRepoUrl());
    }

    @ApiOperation(value = "获取远程仓库地址",notes = "")
    @GetMapping("remote")
    public ServerResponse getRemoteRepoUrl(){
        return adminService.getRemoteRepoUrl();
    }

    @ApiOperation(value = "生成sshKey",notes = "")
    @PostMapping("sshkey")
    public ServerResponse<String> genSshKeys(){
        return adminService.generateSshKey();
    }

    @ApiOperation(value = "停止push至远程仓库",notes = "")
    @DeleteMapping("push")
    public ServerResponse stopPushToRemote(){
        return adminService.stopPushToRemoteRepo();
    }

    @ApiOperation(value = "检查push状态（成功/失败）",notes = "")
    @GetMapping("push")
    public ServerResponse checkPushStatus(){
        return adminService.checkPushTaskStatus();
    }

    @ApiOperation(value = "push至远程仓库",notes = "")
    @PostMapping("push")
    public ServerResponse startPushToRemote(){
        return adminService.startPushToRemoteRepo();
    }

    @ApiOperation(value = "拉取远程仓库",notes = "")
    @PostMapping("pull")
    public ServerResponse pullFromRemote(){
        return adminService.pullFromRemote();
    }

    @ApiOperation(value = "获取编辑器配置",notes = "")
    @GetMapping("editorConfig")
    public ServerResponse<EditorConfigDto> getEditorConfig() {
        return ServerResponse.buildSuccessResponse(userService.getEditorConfig());
    }

    @ApiOperation(value = "更改编辑器配置",notes = "")
    @PostMapping("updateEditorConfig")
    public ServerResponse<EditorConfigDto> updateEditorConfig(@RequestBody EditorConfigReq req) {
        return ServerResponse.buildSuccessResponse(userService.updateEditorConfig(req));
    }
}
