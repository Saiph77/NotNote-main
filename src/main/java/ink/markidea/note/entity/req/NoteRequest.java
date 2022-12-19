package ink.markidea.note.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ApiModel(description = "笔记相关请求")
public class NoteRequest {

    @ApiModelProperty(value = "笔记标题")
    private String noteTitle;

    @ApiModelProperty(value = "所属笔记本名字")
    private String notebookName;

    @ApiModelProperty(value = "笔记内容")
    private String content;

    @ApiModelProperty(value = "笔记版本")
    private String versionRef;

    @ApiModelProperty(value = "源笔记本 用于复制或移动文章至其他笔记本")
    private String srcNotebook;

    @ApiModelProperty(value = "源笔记标题 用于复制或移动文章至其他笔记本")
    private String srcTitle;

    @ApiModelProperty(value = "是否移动笔记至其他笔记本")
    private Boolean move;

    @ApiModelProperty(value = "临时保存草稿 被记录版本控制")
    private boolean tmpSave = false;

}
