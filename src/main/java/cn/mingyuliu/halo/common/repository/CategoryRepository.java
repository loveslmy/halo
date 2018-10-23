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
 * @date : 2018/10/22
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据菜单类型和上级id查询所有子菜单
     *
     * @param parentId parentId
     * @return List<Menu>
     */
    List<Category> findByParentIdOrderByOrderSeq(long parentId);

}
