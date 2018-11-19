package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * <pre>
 *     文章标签
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/02
 */
@Getter
@Setter
@Entity
@Table(name = "tag")
public class Tag extends BaseEntity {

    private static final long serialVersionUID = -7501342327884372194L;

    @NotBlank(message = "标签名称不能为空")
    @Column(length = 32, columnDefinition = "VARCHAR(32) NOT NULL COMMENT '标签名称'", unique = true)
    private String tagName;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '标签路径'", unique = true)
    private String tagUrl;

    @ManyToMany(mappedBy = "tags", targetEntity = Post.class)
    private List<Post> posts;

}
