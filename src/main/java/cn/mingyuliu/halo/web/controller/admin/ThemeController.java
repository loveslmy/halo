package cn.mingyuliu.halo.web.controller.admin;

import cn.mingyuliu.halo.config.sys.OptionHolder;
import cn.mingyuliu.halo.model.domain.OpLog;
import cn.mingyuliu.halo.model.dto.HaloConst;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.dto.LogsRecord;
import cn.mingyuliu.halo.model.enums.Option;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.LogsService;
import cn.mingyuliu.halo.utils.HaloUtils;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 *     后台主题管理控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/12/16
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/themes")
public class ThemeController extends BaseController {

    @Resource
    private OptionHolder optionHolder;


    @Resource
    private LogsService logsService;

    /**
     * 渲染主题设置页面
     *
     * @param model model
     * @return 模板路径admin/admin_theme
     */
    @GetMapping
    public String themes(Model model) {
        model.addAttribute("activeTheme", BaseController.THEME);
        if (null != HaloConst.THEMES) {
            model.addAttribute("themes", HaloConst.THEMES);
        }
        return "admin/admin_theme";
    }

    /**
     * 激活主题
     *
     * @param siteTheme 主题名称
     * @param request   request
     * @return JsonResult
     */
    @GetMapping(value = "/set")
    @ResponseBody
    @CacheEvict(value = "posts", allEntries = true, beforeInvocation = true)
    public JsonResult activeTheme(@PathParam("siteTheme") String siteTheme,
                                  HttpServletRequest request) {
        try {
            //保存主题设置项
            optionHolder.set(Option.THEME, siteTheme);
            //设置主题
            BaseController.THEME = siteTheme;
            log.info("已将主题改变为：{}", siteTheme);
            logsService.saveByLogs(
                    new OpLog(LogsRecord.CHANGE_THEME, "更换为" + siteTheme,
                            HaloUtils.getClientIP(request), new Date())
            );
            return new JsonResult<>(ResponseStatus.SUCCESS, "主题已设置为" + siteTheme);
        } catch (Exception e) {
            log.error("主题设置失败，当前主题为：{}", siteTheme);
            return new JsonResult<>(ResponseStatus.FAIL, "主题设置失败");
        }
    }

    /**
     * 跳转到主题设置
     *
     * @param theme theme名称
     */
    @GetMapping(value = "/options")
    public String setting(Model model, @RequestParam("theme") String theme) {
        model.addAttribute("themeDir", theme);
        return "themes/" + theme + "/module/options";
    }

    /**
     * 编辑主题
     *
     * @param model model
     * @return 模板路径admin/admin_theme-editor
     */
    @GetMapping(value = "/editor")
    public String editor(Model model) {
        List<String> tpls = HaloUtils.getTplName(BaseController.THEME);
        model.addAttribute("tpls", tpls);
        return "admin/admin_theme-editor";
    }

}
