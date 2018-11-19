package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.Category;
import cn.mingyuliu.halo.common.entity.Menu;
import cn.mingyuliu.halo.common.repository.CategoryRepository;
import cn.mingyuliu.halo.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <pre>
 *     分类服务实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/03
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    /**
     * (non-Javadoc)
     *
     * @see ICategoryService#saveOrModify(Category)
     */
    @Override
    public Category saveOrModify(Category category) {
        return categoryRepository.save(category);
    }

}
