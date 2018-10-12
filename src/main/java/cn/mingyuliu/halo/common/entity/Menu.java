package cn.mingyuliu.halo.common.entity;

import cn.mingyuliu.halo.common.enums.MenuType;
import cn.mingyuliu.halo.common.enums.Target;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@Table(name = "halo_menu")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = -7726233157376388786L;

    @Column(columnDefinition = "BIGINT(20) NOT NULL DEFAULT '0' COMMENT '上级id'")
    private long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @Column(columnDefinition = "VARCHAR(16) NOT NULL DEFAULT '' COMMENT '菜单名称'")
    private String menuName;

    @NotBlank(message = "菜单url不能为空")
    @Column(columnDefinition = "VARCHAR(100) NOT NULL DEFAULT '' COMMENT 'url'", unique = true)
    private String menuUrl;

    @Column(columnDefinition = "SMALLINT(6) NOT NULL DEFAULT '0' COMMENT '序号'")
    private short orderSeq;

    @Column(columnDefinition = "VARCHAR(100) DEFAULT '' COMMENT '图标,可选'")
    private String menuIcon;

    @NotNull(message = "打开方式不能为空")
    @Column(columnDefinition = "BIT(1) NOT NULL DEFAULT b'0' COMMENT '打开方式'")
    private Target target = Target.BLANK;

    @NotNull(message = "menuType不能为空")
    @Column(columnDefinition = "BIT(1) NOT NULL DEFAULT b'0' COMMENT '菜单类型'")
    private MenuType menuType = MenuType.TOP_NAV;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT '' COMMENT '菜单描述'")
    private String menuDesc;

}
