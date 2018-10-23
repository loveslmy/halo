package cn.mingyuliu.halo.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * <pre>
 *     分类实体
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/10/22
 */
@Getter
@Setter
@Entity
@Table(name = "category")
public class Category extends TreeEntity {

    private static final long serialVersionUID = 8383678847517271505L;

    @NotBlank(message = "分类名称不能为空")
    @Column(length = 32, columnDefinition = "VARCHAR(32) NOT NULL COMMENT '分类名称'", unique = true)
    private String name;

    @NotBlank(message = "分类路径不能为空")
    @Column(columnDefinition = "VARCHAR(128) NOT NULL COMMENT '分类路径'", unique = true)
    private String url;

    @Column(columnDefinition = "TINYINT(4) NOT NULL COMMENT '分类类型'")
    private byte type;

    /**
     * 子分类
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentId", fetch = FetchType.EAGER)
    private Set<Category> children;

}
