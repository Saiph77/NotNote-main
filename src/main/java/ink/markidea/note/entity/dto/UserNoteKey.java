package ink.markidea.note.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 用户笔记传输信息 用于缓存的信息传输
 */
@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(description = "笔记keyDTO")
public class UserNoteKey {

    @ApiModelProperty(notes = "文章所属用户名")
    private String username;

    @ApiModelProperty(notes = "文章所属笔记本")
    private String notebookName;

    @ApiModelProperty(notes = "文章标题")
    private String noteTitle;

}
