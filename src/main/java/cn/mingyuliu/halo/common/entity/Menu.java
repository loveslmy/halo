package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.enums.Target;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

/**
 * <pre>
 *     菜单
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/06
 */
@Getter
@Setter
@Entity
@Table(name = "menu")
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

    /**
     * 子菜单
     */
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "parentId", targetEntity = Menu.class,
            fetch = FetchType.EAGER)
    private Set<Menu> children;

}
