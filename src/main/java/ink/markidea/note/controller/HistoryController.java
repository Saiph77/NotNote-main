package ink.markidea.note.controller;

import ink.markidea.note.entity.req.QueryHistoryContentReq;
import ink.markidea.note.entity.resp.ServerResponse;
import ink.markidea.note.service.INoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history")
@Api(tags = "获取历史文章内容",description = "")
public class HistoryController {

    @Autowired
    private INoteService noteService;

    @ApiOperation(value = "获取历史版本内容",notes = "需要传入笔记本名，笔记名和版本号")
    @PostMapping("/queryHistoryContent")
    public ServerResponse<String> queryHistoryContent(@RequestBody QueryHistoryContentReq req) {
        return noteService.getNoteHistoryContent(req.getNotebookName(), req.getNoteTitle(), req.getVersionRef());
    }
}
