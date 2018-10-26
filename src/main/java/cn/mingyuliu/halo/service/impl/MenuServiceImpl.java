package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.Menu;
import cn.mingyuliu.halo.common.repository.MenuRepository;
import cn.mingyuliu.halo.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <pre>
 *     菜单业务逻辑实现类
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/27
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    private MenuRepository menuRepository;

    /**
     * (non-Javadoc)
     *
     * @see IMenuService#saveOrModify(Menu)
     */
    @Override
    public Menu saveOrModify(Menu menu) {
        return menuRepository.save(menu);
    }

    /**
     * (non-Javadoc)
     *
     * @see IMenuService#removeById(long)
     */
    @Override
    public void removeById(long menuId) {
        menuRepository.deleteById(menuId);
    }

}
