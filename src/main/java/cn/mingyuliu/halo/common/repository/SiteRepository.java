package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * <pre>
 *     站点链接持久层
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since :  2018-11-15
 */
public interface SiteRepository extends JpaRepository<Site, Long> {

    /**
     * 根据分类id获取站点
     *
     * @return 站点列表
     */
    Page<Site> findByCategoryId(long categoryId, Pageable pageable);

}
