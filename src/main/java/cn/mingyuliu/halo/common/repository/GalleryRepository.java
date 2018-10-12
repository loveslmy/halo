package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     图库持久层
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/2/26
 */
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
}
