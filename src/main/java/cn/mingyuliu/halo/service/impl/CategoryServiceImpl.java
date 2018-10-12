package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.Category;
import cn.mingyuliu.halo.common.repository.CategoryRepository;
import cn.mingyuliu.halo.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static cn.mingyuliu.halo.common.dto.HaloConst.DEFAULT_CATEGORY_URL;

/**
 * <pre>
 *     分类服务实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private CategoryRepository categoryRepository;

    /**
     * (non-Javadoc)
     *
     * @see ICategoryService#createDefaultCategory()
     */
    @Override
    public Category createDefaultCategory() {
        Category category = categoryRepository.findCategoryByCateUrl(DEFAULT_CATEGORY_URL);
        if (category == null) {
            category = new Category();
            category.setCateName("默认分类");
            category.setCateUrl("all");
            category.setCateDesc("默认分类");
            return categoryRepository.save(category);
        }
        return category;
    }

    /**
     * 保存/修改分类目录
     *
     * @param category 分类目录
     * @return Category
     */
    @Override
    public Category saveByCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * 根据编号移除分类目录
     *
     * @param cateId 分类目录编号
     * @return Category
     */
    @Override
    public Category removeByCateId(Long cateId) {
        Optional<Category> categoryOpt = this.findByCateId(cateId);
        if (!categoryOpt.isPresent()) {
            return null;
        }
        Category category = categoryOpt.get();
        categoryRepository.delete(category);
        return category;
    }

    /**
     * 查询所有分类目录
     *
     * @return List
     */
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * 根据编号查询分类目录
     *
     * @param cateId 分类编号
     * @return Category
     */
    @Override
    public Optional<Category> findByCateId(Long cateId) {
        return categoryRepository.findById(cateId);
    }

    /**
     * 根据分类目录路径查询，用于验证是否已经存在该路径
     *
     * @param cateUrl cateUrl
     * @return Category
     */
    @Override
    public Category findByCateUrl(String cateUrl) {
        return categoryRepository.findCategoryByCateUrl(cateUrl);
    }

    /**
     * 将分类字符串集合转化为Category泛型集合
     *
     * @param strings strings
     * @return List
     */
    @Override
    public List<Category> strListToCateList(List<String> strings) {
        if (null == strings) {
            return null;
        }
        List<Category> categories = new ArrayList<>();
        Optional<Category> category;
        for (String str : strings) {
            category = findByCateId(Long.parseLong(str));
            categories.add(category.get());
        }
        return categories;
    }
}
