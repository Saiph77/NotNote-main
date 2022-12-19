package ink.markidea.note.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 网站配置信息
 */
@Getter
@Setter
@ApiModel(description = "网站配置信息dto")
public class WebsiteConfigDto {

    private String websiteTitle = "NotNote";

    /**
     * 注册 策略控制
     * 0 不允许注册
     * 1 完全允许注册
     * 2 预留 审批注册
     */
    private Integer registerStrategy = 0;

    /**
     * 最大上传文件大小
     * 单位是MB
     */
    private String maxUploadFileSize = "10MB";


    /**
     * token过期时间
     * 单位是小时
     */
    private int tokenExpireTimeInHour = 2;

}
