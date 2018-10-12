package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     分类服务接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
public interface ICategoryService {

    /**
     * 创建默认分类
     * @return {@link Category}
     */
    Category createDefaultCategory();

    /**
     * 新增/修改分类目录
     *
     * @param category 分类目录
     * @return 如果插入成功，返回分类目录对象
     */
    Category saveByCategory(Category category);

    /**
     * 根据编号删除分类目录
     *
     * @param cateId 分类目录编号
     * @return category
     */
    Category removeByCateId(Long cateId);

    /**
     * 获取所有分类目录
     *
     * @return 返回List集合
     */
    List<Category> findAllCategories();

    /**
     * 根据编号查询单个分类
     *
     * @param cateId 分类编号
     * @return 返回category实体
     */
    Optional<Category> findByCateId(Long cateId);

    /**
     * 根据分类目录路径查询，用于验证是否已经存在该路径
     *
     * @param cateUrl cateUrl
     * @return category
     */
    Category findByCateUrl(String cateUrl);

    /**
     * 将分类字符串集合转化为Category泛型集合
     *
     * @param strings strings
     * @return List
     */
    List<Category> strListToCateList(List<String> strings);
}
