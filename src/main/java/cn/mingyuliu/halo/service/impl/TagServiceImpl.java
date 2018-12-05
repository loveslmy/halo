package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.Tag;
import cn.mingyuliu.halo.common.repository.TagRepository;
import cn.mingyuliu.halo.service.ITagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <pre>
 *     标签业务逻辑实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since  : 2018/12/4
 */
@Service
public class TagServiceImpl implements ITagService {

    @Resource
    private TagRepository tagRepository;

    /**
     * (non-Javadoc)
     *
     * @see ITagService#saveOrModify(Tag)
     */
    @Override
    public Tag saveOrModify(Tag tag) {
        return tagRepository.save(tag);
    }

}
