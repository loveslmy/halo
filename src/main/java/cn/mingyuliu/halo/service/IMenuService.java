package cn.mingyuliu.halo.service;

import cn.mingyuliu.halo.common.entity.Menu;

/**
 * <pre>
 *     菜单业务逻辑接口
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/06
 */
public interface IMenuService {

    /**
     * 新增/修改菜单
     *
     * @param menu menu
     * @return Menu
     */
    Menu saveOrModify(Menu menu);

    /**
     * 删除菜单
     *
     * @param id 菜单id
     */
    void removeById(long id);

}
