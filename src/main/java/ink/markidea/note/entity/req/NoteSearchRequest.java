package ink.markidea.note.entity.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "根据关键字搜索笔记")
public class NoteSearchRequest {
    @ApiModelProperty(value = "关键字")
    String keyWord;

    @ApiModelProperty(value = "指定搜索范围 可为空")
    List<String> searchNotebookList;
}
