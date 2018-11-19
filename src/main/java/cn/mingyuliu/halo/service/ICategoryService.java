package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.Category;

/**
 * <pre>
 *     分类服务接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/10/22
 */
public interface ICategoryService {

    /**
     * 新增/修改分类
     *
     * @param category {@link Category}
     * @return {@link Category} 分类
     */
    Category saveOrModify(Category category);

}
