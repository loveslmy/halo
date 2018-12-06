package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.Post;
import cn.mingyuliu.halo.common.repository.PostRepository;
import cn.mingyuliu.halo.service.IPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <pre>
 *     文章业务逻辑实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/12/06
 */
@Service
public class PostServiceImpl implements IPostService {

    @Resource
    private PostRepository postRepository;

    /**
     * (non-Javadoc)
     *
     * @see IPostService#saveOrModify(Post)
     */
    @Override
    public Post saveOrModify(Post post) {
        return postRepository.save(post);
    }


}
