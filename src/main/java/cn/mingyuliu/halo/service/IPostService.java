package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.Post;

/**
 * <pre>
 *     文章/页面业务逻辑接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/12/06
 */
public interface IPostService {

    /**
     * 新增/修改文章
     *
     * @param post {@link Post}
     * @return Post
     */
    Post saveOrModify(Post post);

}
