package ink.markidea.note.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(description = "远程仓库地址请求")
public class RemoteRepoRequest {

    @ApiModelProperty(value = "远程仓库地址")
    private String remoteRepoUrl;
}
