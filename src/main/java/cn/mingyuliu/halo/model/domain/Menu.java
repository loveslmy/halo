package cn.mingyuliu.halo.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

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

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单路径
     */
    private String menuUrl;

    /**
     * 排序编号
     */
    private int menuSort;

    /**
     * 图标，可选，部分主题可显示
     */
    private String menuIcon;

    /**
     * 打开方式
     */
    private String menuTarget;

}
