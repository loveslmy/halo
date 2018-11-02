package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 *     分类Repository
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/10/22
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 查寻根菜单列表
     *
     * @return List<Menu>
     */
    List<Category> findByParentIdIsNullAndActiveIsTrueOrderByOrderSeq();

    /**
     * 根据parentId查询所有子菜单
     *
     * @return List<Menu>
     */
    List<Category> findByParentIdAndActiveIsTrueOrderByOrderSeq(Long parentId);

}
