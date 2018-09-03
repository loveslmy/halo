package cn.mingyuliu.halo.repository;

import cn.mingyuliu.halo.model.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     菜单持久层
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/1/24
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
