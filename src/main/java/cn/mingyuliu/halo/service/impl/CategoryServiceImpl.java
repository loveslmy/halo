package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.Category;
import cn.mingyuliu.halo.common.repository.CategoryRepository;
import cn.mingyuliu.halo.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

import static cn.mingyuliu.halo.common.HaloConst.DOT;


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
        Optional<Category> parentOpt = categoryRepository.findById(category.getParentId());
        if (!parentOpt.isPresent()) {
            throw new IllegalArgumentException("not found parent category with " + category.toString() + "!");
        }

        Category parent = parentOpt.get();
        parent.setLeaf(false);
        categoryRepository.save(parent);

        category.setLeaf(CollectionUtils.isEmpty(categoryRepository
                .findByParentIdAndActiveIsTrueOrderByOrderSeq(category.getId())));
        category.setTreeSeq(parent.getTreeSeq() + parent.getId() + DOT);
        category = categoryRepository.save(category);
        return category;
    }

}
