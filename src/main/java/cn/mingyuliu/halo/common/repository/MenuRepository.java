package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 *     菜单持久层
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/06
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * 根据parentId查询所有子菜单
     *
     * @return List<Menu>
     */
    List<Menu> findByParentIdAndActiveIsTrueOrderByOrderSeq(Long parentId);

    /**
     * 根据parentId查询所有子菜单
     *
     * @return List<Menu>
     */
    List<Menu> findByTreeSeqLikeAndActiveIsTrueOrderByOrderSeq(String treeSeq);

}
