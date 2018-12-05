package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     标签持久层
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/12/04
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据标签名称查询
     *
     * @param name 标签名
     * @return Tag
     */
    Tag findTagByName(String name);
}
