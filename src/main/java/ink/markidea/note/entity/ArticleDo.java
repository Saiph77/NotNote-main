package ink.markidea.note.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Entity
@Table(name = "article")
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(description = "文章实体类")
public class ArticleDo {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "notebook",nullable = false)
    private String notebookName;

    @Column(name = "article_title",nullable = true)
    @Deprecated
    private String articleTitle = "";

    @Column(name = "note_title",nullable = false)
    private String noteTitle;

    @Column(nullable = false)
    private String username;

}
