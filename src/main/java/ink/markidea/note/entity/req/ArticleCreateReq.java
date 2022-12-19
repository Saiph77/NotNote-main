package ink.markidea.note.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "创建文章请求")
public class ArticleCreateReq {

    @ApiModelProperty(value = "笔记本")
    private String notebookName;

    @ApiModelProperty(value = "笔记标题")
    private String noteTitle;
}
