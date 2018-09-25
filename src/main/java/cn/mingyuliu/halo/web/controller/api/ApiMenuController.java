package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.Menu;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ApiMenuController {

    @Resource
    private MenuService menuService;

    /**
     * 获取所有菜单
     *
     * @return JsonResult
     */
    @GetMapping
    public JsonResult menus() {
        List<Menu> menus = menuService.findAllMenus();
        if (null != menus && menus.size() > 0) {
            return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(), menus);
        } else {
            return new JsonResult<>(ResponseStatus.EMPTY, ResponseStatus.EMPTY.getMsg());
        }
    }
}
