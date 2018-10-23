package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Menu;
import cn.mingyuliu.halo.common.repository.MenuRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.IMenuService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     菜单API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/menus")
public class MenuController extends BaseController {

    @Resource
    private MenuRepository menuRepository;

    @Resource
    private IMenuService menuService;

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

    /**
     * 根据上级id查询所有子菜单
     *
     * @return {@link JsonResult<List<Menu>>}
     */
    @RequestMapping("/findByParentId")
    public JsonResult<List<Menu>> findByParentId() {
        try {
            return new JsonResult<>(HttpStatus.OK, menuRepository
                    .findByParentIdIsNullOrderByOrderSeq());
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

}
