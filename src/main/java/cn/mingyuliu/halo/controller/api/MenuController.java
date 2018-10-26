package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Menu;
import cn.mingyuliu.halo.common.repository.MenuRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.IMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     菜单API
 * </pre>
 *
 * @author : RYAN0UP
 * @since : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/menus")
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
    public JsonResult<List<Menu>> listMenu() {
        try {
            return new JsonResult<>(HttpStatus.OK, menuRepository
                    .findAll());
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
            if (null != parentId) {
                return new JsonResult<>(HttpStatus.OK, menuRepository
                        .findByParentIdOrderByOrderSeq(parentId));
            }

            return new JsonResult<>(HttpStatus.OK, menuRepository
                    .findByParentIdIsNullOrderByOrderSeq());
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 新增/修改菜单
     *
     * @param menu {@link Menu}
     * @return {@link JsonResult<Menu>}
     */
    @PostMapping("/saveOrModify")
    public JsonResult<Menu> saveOrModify(@RequestBody Menu menu) {
        try {
            return new JsonResult<>(HttpStatus.OK, menuService.saveOrModify(menu));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

}
