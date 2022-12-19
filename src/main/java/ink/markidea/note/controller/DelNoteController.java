package ink.markidea.note.controller;

import ink.markidea.note.entity.resp.ServerResponse;
import ink.markidea.note.entity.vo.DeletedNoteVo;
import ink.markidea.note.service.INoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delnote")
@Api(tags = "删除文章相关接口",description = "操作回收表中的文章")
public class DelNoteController {

    @Autowired
    private INoteService noteService;

    @ApiOperation(value = "获取回收站中的文章列表",notes = "")
    @GetMapping("")
    public ServerResponse<List<DeletedNoteVo>> getDelNotes(){
        return noteService.listDelNotes();
    }

    @ApiOperation(value = "清空回收站",notes = "")
    @DeleteMapping("")
    public ServerResponse clearAllDelNotes(){
        return noteService.clearAllDelNotes();
    }

    @ApiOperation(value = "删除回收站中的单个文章",notes = "传参时需要判断是恢复文章还是彻底删除在恢复文章或彻底删除文章时都需要将数据从回收站中删除")
    @DeleteMapping("/{id}")
    //传参时需要判断是恢复文章还是彻底删除在恢复文章或彻底删除文章时都需要将数据从回收站中删除
    public ServerResponse clearDelNote(@PathVariable Integer id, Boolean recover){
        if (recover != null && recover){
            return noteService.recoverNote(id);
        }else{
            return noteService.clearDelNote(id);
        }
    }

}
