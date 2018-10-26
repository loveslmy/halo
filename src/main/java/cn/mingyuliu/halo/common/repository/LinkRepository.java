package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     友情链接持久层
 * </pre>
 *
 * @author : RYAN0UP
 * @since : 2017/11/14
 */
public interface LinkRepository extends JpaRepository<Link, Long> {
}
