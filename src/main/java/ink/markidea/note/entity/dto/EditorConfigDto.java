package ink.markidea.note.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(value = "编辑器配置")
public class EditorConfigDto {

    /**
     * 用户自定义样式路径
     */
    @ApiModelProperty(value = "用户自定义样式路径")
    private String customStylePath;

    /**
     * 使用自定义css
     */
    @ApiModelProperty(value = "使用自定义css")
    private boolean enableCustomStyle = false;

    /**
     * 开启计数器
     */
    @ApiModelProperty(value = "开启计数器")
    private boolean enableCounter = false;

    /**
     * 默认打开大纲
     */
    @ApiModelProperty(value = "默认打开大纲")
    private boolean enableOutline = false;

    /**
     * 编辑模式
     * 支持sv, ir, wysiwyg
     */
    @ApiModelProperty(value = "编辑模式 支持sv, ir, wysiwyg")
    private String editMode = "ir";

    /**
     * 启用代码高亮
     */
    @ApiModelProperty(value = "启用代码高亮")
    private boolean enableHighLight = true;


    /**
     * 代码块样式
     */
    @ApiModelProperty(value = "代码块样式")
    private String codeStyle = "native";

    /**
     * 启用代码行号
     */
    @ApiModelProperty(value = "启用代码行号")
    private boolean enableLineNumber = false;

    /**
     * 大纲位置
     */
    @ApiModelProperty(value = "大纲位置")
    private String outlinePosition = "left";

}
