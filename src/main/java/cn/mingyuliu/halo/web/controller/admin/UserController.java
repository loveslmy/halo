package cn.mingyuliu.halo.web.controller.admin;

import cn.mingyuliu.halo.model.domain.User;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.IUserService;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * <pre>
 *     后台用户管理控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/12/24
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/profile")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private Configuration configuration;

    /**
     * 获取用户信息并跳转
     *
     * @return 模板路径admin/admin_profile
     */
    @GetMapping
    public String profile() {
        return "admin/admin_profile";
    }

    /**
     * 处理修改用户资料的请求
     *
     * @param user    user
     * @param session session
     * @return JsonResult
     */
    @PostMapping(value = "save")
    @ResponseBody
    public JsonResult saveProfile(@Valid @ModelAttribute User user, BindingResult result, HttpSession session) {
        try {
            if (result.hasErrors()) {
                for (ObjectError error : result.getAllErrors()) {
                    return new JsonResult<>(ResponseStatus.FAIL, error.getDefaultMessage());
                }
            }
            userService.saveByUser(user);
            configuration.setSharedVariable("user", userService.findOwnerUser());
            session.invalidate();
        } catch (Exception e) {
            log.error("修改用户资料失败：{}", e.getMessage());
            return new JsonResult<>(ResponseStatus.FAIL, "修改失败！");
        }
        return new JsonResult<>(ResponseStatus.SUCCESS, "修改成功！");
    }

    /**
     * 处理修改密码的请求
     *
     * @param beforePass 旧密码
     * @param newPass    新密码
     * @param userId     用户编号
     * @param session    session
     * @return JsonResult
     */
    @PostMapping(value = "changePass")
    @ResponseBody
    public JsonResult changePass(@ModelAttribute("beforePass") String beforePass,
                                 @ModelAttribute("newPass") String newPass,
                                 @ModelAttribute("userId") Long userId,
                                 HttpSession session) {
        try {
            User user = userService.findByUserIdAndUserPass(userId, DigestUtils.md5DigestAsHex(beforePass.getBytes()));
            if (null != user) {
                user.setUserPass(DigestUtils.md5DigestAsHex(newPass.getBytes()));
                userService.saveByUser(user);
                session.invalidate();
            } else {
                return new JsonResult<>(ResponseStatus.FAIL, "原密码错误！");
            }
        } catch (Exception e) {
            log.error("修改密码失败：{}", e.getMessage());
            return new JsonResult<>(ResponseStatus.FAIL, "密码修改失败！");
        }
        return new JsonResult<>(ResponseStatus.SUCCESS, "修改密码成功！");
    }

}
