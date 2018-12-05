package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.TreeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * <pre>
 *     分类实体
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/10/22
 */
@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "category",indexes = {
        @Index(name = "name_index", columnList = "name"),
        @Index(name = "tree_seq_index", columnList = "treeSeq"),
        @Index(name = "create_date_index", columnList = "crtDate"),
        @Index(name = "update_date_index", columnList = "updDate")})
public class Category extends TreeEntity {

    private static final long serialVersionUID = 8383678847517271505L;

    @NotBlank(message = "分类名称不能为空")
    @Column(length = 32, columnDefinition = "VARCHAR(32) NOT NULL COMMENT '分类名称'", unique = true)
    private String name;

    @NotBlank(message = "分类路径不能为空")
    @Column(columnDefinition = "VARCHAR(128) NOT NULL COMMENT '分类路径'", unique = true)
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Category category = (Category) o;
        return this.id == category.getId();
    }

    @Override
    public int hashCode() {
        return (int)(this.id ^ (this.id >>> 32));
    }

}
