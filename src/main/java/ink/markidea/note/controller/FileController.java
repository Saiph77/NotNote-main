package ink.markidea.note.controller;

import ink.markidea.note.entity.req.BatchFileDeleteRequest;
import ink.markidea.note.entity.resp.ServerResponse;
import ink.markidea.note.entity.resp.VditorFileUploadResponse;
import ink.markidea.note.entity.vo.UserFileVo;
import ink.markidea.note.service.IFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/file")
@RestController
@Api(tags = "文件相关接口",description = "操作文件")
public class FileController {
    @Autowired
    private IFileService fileService;

    @ApiOperation(value = "上传文件",notes = "")
    @PostMapping("")
    public ServerResponse<String> upload(MultipartFile file){
        String filePath = fileService.upload(file);
        if (filePath == null){
            return ServerResponse.buildErrorResponse("upload file failed");
        }
        return ServerResponse.buildSuccessResponse(filePath);
    }


    /**
     * vditor上传文件定制接口
     * @param request
     * @return
     */
    @ApiOperation(value = "vditor上传文件",notes = "")
    @PostMapping("vditor")
    public VditorFileUploadResponse upload(StandardMultipartHttpServletRequest request){
        //从请求中获取文件上传
        //需要返回上传成功的集合以及上传失败的集合
        List<MultipartFile> files = request.getMultiFileMap().get("file[]");
        Map<String, String> succFileMap = new HashMap<>();
        List<String> errorFileList = new ArrayList<>();
        files.forEach(file -> {
            try{
                succFileMap.put(file.getOriginalFilename(), fileService.upload(file));
            }catch (Exception e){
                errorFileList.add(file.getOriginalFilename());
            }
        });
        //创建定制vditor定制响应对像
        VditorFileUploadResponse response = new VditorFileUploadResponse();
        response.setData(new HashMap<>());
        response.getData().put("errFiles", errorFileList);
        response.getData().put("succMap", succFileMap);
        return response;
    }

    /**
     * 获取用户所有文件列表
     * @param pageIndex 第几页
     * @param pageSize 一页多少文件
     * @return
     */
    @ApiOperation(value = "获取用户的文件列表",notes = "")
    @GetMapping("")
    public ServerResponse<UserFileVo> listUserFiles(int pageIndex, int pageSize){
        UserFileVo userFileVo = fileService.listUserFiles(pageIndex, pageSize);
        return ServerResponse.buildSuccessResponse(userFileVo);
    }

    /**
     * 批量删除接口
     */
    @ApiOperation(value = "批量删除文件",notes = "")
    @PostMapping("delete/batch")
    public ServerResponse<Void> batchDelete(@RequestBody BatchFileDeleteRequest request){
        fileService.batchDelete(request.getFileNames());
        return ServerResponse.buildSuccessResponse();
    }

}
