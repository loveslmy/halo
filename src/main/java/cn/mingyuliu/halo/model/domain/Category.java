package cn.mingyuliu.halo.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     文章分类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/08/31
 */
@Getter
@Setter
@Entity
@Table(name = "halo_category")
public class Category extends BaseEntity {

    private static final long serialVersionUID = 8383678847517271505L;

    @NotBlank(message = "分类名称不能为空")
    @Column(length = 32, columnDefinition = "VARCHAR(32) NOT NULL COMMENT '分类名称'", unique = true)
    private String cateName;

    @NotBlank(message = "分类路径不能为空")
    @Column(columnDefinition = "VARCHAR(255) NOT NULL COMMENT '分类路径'", unique = true)
    private String cateUrl;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '分类描述'")
    private String cateDesc;

    @ManyToMany(mappedBy = "categories", targetEntity = Post.class)
    @JsonIgnore
    private List<Post> posts;

}
