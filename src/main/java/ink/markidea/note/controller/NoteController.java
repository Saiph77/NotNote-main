package ink.markidea.note.controller;

import ink.markidea.note.entity.req.NoteRequest;
import ink.markidea.note.entity.req.NoteSearchRequest;
import ink.markidea.note.entity.resp.ServerResponse;
import ink.markidea.note.entity.vo.NoteVersionVo;
import ink.markidea.note.entity.vo.NoteVo;
import ink.markidea.note.entity.vo.NotebookVo;
import ink.markidea.note.service.INoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/note")
@Api(tags = "笔记相关接口",description = "展示笔记")
public class NoteController {

    @Autowired
    private INoteService noteService;

    /**
     * 保存笔记
     * @param notebookName
     * @param noteTitle
     * @param request
     * @return
     */
    @ApiOperation(value = "保存笔记",notes = "")
    @PostMapping("/{notebookName}/{noteTitle}")
    public ServerResponse saveNote(@PathVariable String notebookName,
                                   @PathVariable String noteTitle,
                                   @RequestBody NoteRequest request){

        //获取版本信息
        if (StringUtils.isNotBlank(request.getVersionRef())){
            return noteService.resetAndGet(notebookName, noteTitle, request.getVersionRef());
        }
        //临时保存 暂不推至git仓库
        if (request.isTmpSave()) {
            noteService.tmpSaveNote(noteTitle, notebookName, request.getContent());
            return ServerResponse.buildSuccessResponse();
        }
        return noteService.saveNote(noteTitle, notebookName, request.getContent());
    }

    @ApiOperation(value = "通过关键字查找笔记",notes = "")
    @PostMapping("/search")
    public ServerResponse<List<NoteVo>> searchNote(@RequestBody NoteSearchRequest request){
        if (StringUtils.isBlank(request.getKeyWord())){
            throw new IllegalArgumentException();
        }
        return noteService.search(request.getKeyWord(), request.getSearchNotebookList());

    }


    @ApiOperation(value = "获取所有的笔记本和笔记信息",notes = "")
    @GetMapping("")
    public ServerResponse<List<NotebookVo>> getNotebooks(){
        List<String>  notebookNames = noteService.listNotebooks().getData();
        List<NotebookVo> notebookVoList = new ArrayList<>(notebookNames.size());
        for (String notebookName : notebookNames){
            NotebookVo notebookVo = new NotebookVo().setNotebookName(notebookName).setNoteList(new ArrayList<>());
            notebookVo.setNoteList(noteService.listNotes(notebookName).getData());
            notebookVoList.add(notebookVo);
        }
        return ServerResponse.buildSuccessResponse(notebookVoList);
    }

    /**
     * 通过笔记本名获取笔记
     * @param notebookName
     * @return
     */
    @ApiOperation(value = "展示笔记本中的笔记",notes = "需要传入笔记本名")
    @GetMapping("/{notebookName}")
    public ServerResponse<List<NoteVo>> getNotes(@PathVariable String notebookName){
        return noteService.listNotes(notebookName);
    }


    /**
     * get content of note
     */
    @ApiOperation(value = "获取笔记内容",notes = "需要传入笔记本名和笔记名")
    @GetMapping("/{notebookName}/{noteTitle}")
    public ServerResponse<String> getNote(@PathVariable String notebookName, @PathVariable String noteTitle){
        return noteService.getNote(notebookName, noteTitle);
    }

    @ApiOperation(value = "获取笔记历史版本",notes = "需要传入笔记本名和笔记名")
    @GetMapping("/{notebookName}/{noteTitle}/history")
    public ServerResponse<List<NoteVersionVo>> getNoteHistory(@PathVariable String notebookName, @PathVariable String noteTitle){
        return noteService.getNoteHistory(notebookName, noteTitle);
    }

    /**
     * 创建或重命名笔记
     */
    @ApiOperation(value = "创建或重命命笔记",notes = "")
    @PutMapping("/{notebookName}")
    public ServerResponse createNotebook(@PathVariable String notebookName, @RequestBody NoteRequest request){
        if (Boolean.TRUE.equals(request.getMove())) {
            noteService.renameNotebook(request.getSrcNotebook(), notebookName);
            return ServerResponse.buildSuccessResponse(noteService.listNotes(notebookName).getData());
        }
        return noteService.createNotebook(notebookName);
    }


    /**
     * 复制或移动文章至其他笔记本
     */
    @ApiOperation(value = "复制或移动文章至其他笔记本",notes = "需要传入目标笔记本和目标标题")
    @PutMapping("/{targetNotebook}/{targetNoteTitle}")
    public ServerResponse copyOrMoveNote(@PathVariable String targetNotebook,
                                         @PathVariable String targetNoteTitle,
                                         @RequestBody NoteRequest request){
        String srcNotebook = request.getSrcNotebook();
        String srcTitle = request.getSrcTitle();
        if (request.getMove() != null && request.getMove()){
            return noteService.moveNote(srcNotebook, srcTitle, targetNotebook, targetNoteTitle);
        }else {
            return noteService.copyNote(srcNotebook, targetNotebook, targetNoteTitle);
        }
    }


    /**
     * 删除笔记本
     * @param notebookName
     * @return
     */
    @ApiOperation(value = "删除笔记本",notes = "")
    @DeleteMapping("/{notebookName}")
    public ServerResponse delNotebook(@PathVariable String notebookName){
        return noteService.deleteNotebook(notebookName);
    }

    /**
     * 删除笔记
     */
    @ApiOperation(value = "删除笔记",notes = "")
    @DeleteMapping("/{notebookName}/{noteTitle}")
    public ServerResponse delNote(@PathVariable String notebookName,
                                  @PathVariable String noteTitle,
                                  Boolean delDraft){
        if (Boolean.TRUE.equals(delDraft)) {
            noteService.delTmpSavedNote(noteTitle, notebookName);
            return ServerResponse.buildSuccessResponse();
        }
        return noteService.deleteNote(notebookName, noteTitle);
    }

}
