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
 * @date : 2018/09/06
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {

    /**
     * 查找菜单根节点
     *
     * @return List<Menu>
     */
    List<Menu> findByParentIdIsNullOrderByOrderSeq();


}
