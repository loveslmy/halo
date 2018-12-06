package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.common.entity.Menu;
import cn.mingyuliu.halo.common.repository.MenuRepository;
import cn.mingyuliu.halo.service.IMenuService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

import static cn.mingyuliu.halo.common.HaloConst.DOT;

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
        Optional<Menu> parentOpt = menuRepository.findById(menu.getParentId());
        if (!parentOpt.isPresent()) {
            throw new IllegalArgumentException("not found parent menu with " + menu.toString() + "!");
        }

        Menu parent = parentOpt.get();
        parent.setLeaf(false);
        menuRepository.save(parent);
        menu.setLeaf(CollectionUtils.isEmpty(menuRepository
                .findByParentIdAndActiveIsTrueOrderByOrderSeq(menu.getId())));
        menu.setTreeSeq(parent.getTreeSeq() + parent.getId() + DOT);
        menu = menuRepository.save(menu);
        return menu;
    }


}
