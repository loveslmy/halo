package cn.mingyuliu.halo.common.repository;

import cn.mingyuliu.halo.common.entity.Post;
import cn.mingyuliu.halo.common.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <pre>
 *     文章持久层
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/12/04
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByPostStatus(PostStatus postStatus , Pageable page);

}
