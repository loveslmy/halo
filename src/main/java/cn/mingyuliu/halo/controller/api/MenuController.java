package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Menu;
import cn.mingyuliu.halo.common.entity.base.TreeEntity;
import cn.mingyuliu.halo.common.repository.MenuRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.IMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static cn.mingyuliu.halo.common.HaloConst.DOT;

/**
 * <pre>
 *     菜单API
 * </pre>
 *
 * @author : mingyuliu@126.com
 * @since : 2018/12/02
 */
@RestController
@RequestMapping(value = "/api/menu")
public class MenuController extends BaseController {

    @Resource
    private MenuRepository menuRepository;

    @Resource
    private IMenuService menuService;

    /**
     * 查询菜单列表
     *
     * @return {@link JsonResult<List<Menu>>}
     */
    @RequestMapping("/listMenu")
    public JsonResult<List<Menu>> listMenu(@RequestParam(required = false) Long parentId) {
        try {
            if (null == parentId) {
                return new JsonResult<>(HttpStatus.OK, menuRepository
                        .findAll());
            }

            return new JsonResult<>(HttpStatus.OK, menuRepository
                    .findByTreeSeqLikeAndActiveIsTrueOrderByOrderSeq(parentId + DOT + "%"));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 根据上级id查询所有子菜单
     *
     * @param parentId parentId
     * @return {@link JsonResult<List<Menu>>}
     */
    @RequestMapping("/findByParentId")
    public JsonResult<List<Menu>> findByParentId(@RequestParam(required = false) Long parentId) {
        try {
            List<Menu> menus = menuRepository.findByParentIdAndActiveIsTrueOrderByOrderSeq(parentId);
            for (Menu menu : menus) {
                if (!menu.isLeaf()) {
                    menu.setChildren(menuRepository.findByParentIdAndActiveIsTrueOrderByOrderSeq(menu.getId()));
                }
            }
            return new JsonResult<>(HttpStatus.OK, menus);
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 新增/修改菜单
     *
     * @param menu {@link TreeEntity}
     * @return {@link JsonResult<Menu>}
     */
    @PostMapping("/saveOrModify")
    public JsonResult<Menu> saveOrModify(@RequestBody Menu menu) {
        try {
            return new JsonResult<>(HttpStatus.OK, menuService.saveOrModify(menu));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
