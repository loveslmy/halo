package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.entity.base.TreeEntity;
import cn.mingyuliu.halo.common.enums.Target;
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
 *     菜单
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/06
 */
@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "menu", indexes = {
        @Index(name = "name_index", columnList = "name"),
        @Index(name = "tree_seq_index", columnList = "treeSeq"),
        @Index(name = "create_date_index", columnList = "crtDate"),
        @Index(name = "update_date_index", columnList = "updDate")})
public class Menu extends TreeEntity {

    private static final long serialVersionUID = -7726233157376388786L;

    @NotBlank(message = "菜单名称不能为空")
    @Column(columnDefinition = "VARCHAR(16) NOT NULL DEFAULT '' COMMENT '菜单名称'")
    private String name;

    @NotBlank(message = "菜单url不能为空")
    @Column(columnDefinition = "VARCHAR(128) NOT NULL DEFAULT '' COMMENT 'url'", unique = true)
    private String url;

    @Column(columnDefinition = "BIT(1) NOT NULL DEFAULT b'0' COMMENT '打开方式'")
    private Target target;

}
