package ink.markidea.note.entity.req;

import ink.markidea.note.entity.vo.ArticleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "批量删除文章请求")
public class BatchDelArticleReq {

    @ApiModelProperty(value = "文章集合")
    private List<ArticleVo> articleList;
}
