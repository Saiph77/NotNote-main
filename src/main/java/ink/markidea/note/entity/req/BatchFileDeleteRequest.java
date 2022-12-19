package ink.markidea.note.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 批量删除请求
 */
@Getter
@Setter
@ApiModel(description = "批量删除文件请求")
public class BatchFileDeleteRequest {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "文件名集合",required = false)
    private List<String> fileNames;

}
