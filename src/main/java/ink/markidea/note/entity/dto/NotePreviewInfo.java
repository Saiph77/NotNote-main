package ink.markidea.note.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@ApiModel(description = "文章预览dto")
public class NotePreviewInfo {

    @ApiModelProperty(notes = "笔记预览信息")
    String previewContent;

    /**
     * 是否为公开笔记
     */
    Integer articleId;

}
