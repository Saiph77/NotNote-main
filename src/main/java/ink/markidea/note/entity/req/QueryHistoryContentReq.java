package ink.markidea.note.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "查询笔记历史内容")
public class QueryHistoryContentReq {

    @ApiModelProperty(value = "笔记本")
    private String notebookName;

    @ApiModelProperty(value = "标题")
    private String noteTitle;

    @ApiModelProperty(value = "版本信息")
    private String versionRef;
}
