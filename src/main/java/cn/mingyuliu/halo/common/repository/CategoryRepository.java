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
     * 根据parentId查询直接子分类
     *
     * @return List<Menu>
     */
    List<Category> findByParentIdAndActiveIsTrueOrderByOrderSeq(Long parentId);

    /**
     * 根据parentId查询所有子分类
     *
     * @return List<Category>
     */
    List<Category> findByTreeSeqLikeAndActiveIsTrueOrderByOrderSeq(String treeSeq);

    /**
     * 查寻root分类列表
     *
     * @return List<Category>
     */
    List<Category> findByParentIdIsNullAndActiveIsTrueOrderByOrderSeq();


}
