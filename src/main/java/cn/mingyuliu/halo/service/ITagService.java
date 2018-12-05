package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.Tag;

/**
 * <pre>
 *     标签业务逻辑接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since :  2018/12/4
 */
public interface ITagService {

    /**
     * 新增/修改菜单
     *
     * @param tag {@link Tag}
     * @return Tag
     */
    Tag saveOrModify(Tag tag);

}
