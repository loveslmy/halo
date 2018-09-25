package cn.mingyuliu.halo.web.controller.install;

import cn.mingyuliu.halo.exception.RepetInstallException;
import cn.mingyuliu.halo.model.dto.InstallDto;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.Option;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.IInstallService;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * <pre>
 *     博客初始化控制器
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
@Slf4j
@Controller
@RequestMapping(value = "/install")
public class InstallController extends BaseController {

    @Resource
    private IInstallService installService;

    /**
     * 渲染安装页面
     *
     * @param model model
     * @return 模板路径
     */
    @GetMapping
    public String install(Model model) {
        try {
            if (optionHolder.getBoolean(Option.IS_INSTALL)) {
                model.addAttribute("installed", true);
            } else {
                model.addAttribute("installed", false);
            }
        } catch (Exception e) {
            log.error("install occur exception", e);
            return "common/error/500";
        }
        return "common/install";
    }

    /**
     * 执行安装
     *
     * @param installDto {@link InstallDto}
     * @param request    {@link HttpServletRequest}
     * @return true：安装成功，false：安装失败
     */
    @PostMapping(value = "/do")
    @ResponseBody
    public JsonResult doInstall(@Valid @ModelAttribute InstallDto installDto, HttpServletRequest request) {
        try {
            installService.install(installDto,request);
        } catch (RepetInstallException e) {
            return new JsonResult<>(ResponseStatus.FAIL, "已经安装成功，请不要重复执行安装操作!");
        } catch (Exception e) {
            log.error("doInstall occur exception", e);
            return new JsonResult<>(ResponseStatus.FAIL, "安装失败,请再次尝试安装!");
        }
        return new JsonResult<>(ResponseStatus.SUCCESS, "安装成功!");
    }

}
