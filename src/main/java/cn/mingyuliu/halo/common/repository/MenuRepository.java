package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Menu;
import cn.mingyuliu.halo.common.enums.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 *     菜单持久层
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/06
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * 根据菜单类型和上级id查询所有子菜单
     *
     * @param menuType {@link MenuType}
     * @param parentId parentId
     * @return List<Menu>
     */
    List<Menu> findByMenuTypeAndParentIdOrderByOrderSeq(MenuType menuType, long parentId);


}
