package ink.markidea.note.entity.vo;

import ink.markidea.note.entity.ArticleDo;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@ApiModel(description = "文章视图")
public class ArticleVo  {

    private Integer articleId;

    private String notebookName;

    private String noteTitle;

    private String content;
}
