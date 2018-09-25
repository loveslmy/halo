package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.User;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 *     用户API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/user")
public class ApiUserController {

    @Resource
    private IUserService userService;

    /**
     * 获取博主信息
     *
     * @return JsonResult
     */
    @GetMapping
    public JsonResult user() {
        User user = userService.findOwnerUser();
        return new JsonResult<>(ResponseStatus.SUCCESS, user);
    }

}
