package cc.ryanc.halo.web.controller.admin;

import cc.ryanc.halo.model.domain.Logs;
import cc.ryanc.halo.model.dto.HaloConst;
import cc.ryanc.halo.model.dto.JsonResult;
import cc.ryanc.halo.model.dto.LogsRecord;
import cc.ryanc.halo.model.enums.BlogPropertiesEnum;
import cc.ryanc.halo.model.enums.ResultCodeEnum;
import cc.ryanc.halo.service.LogsService;
import cc.ryanc.halo.service.OptionsService;
import cc.ryanc.halo.utils.HaloUtils;
import cc.ryanc.halo.web.controller.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private OptionsService optionsService;

    @Autowired
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
            optionsService.saveOption(BlogPropertiesEnum.THEME.getProp(), siteTheme);
            //设置主题
            BaseController.THEME = siteTheme;
            log.info("已将主题改变为：{}", siteTheme);
            logsService.saveByLogs(
                    new Logs(LogsRecord.CHANGE_THEME, "更换为" + siteTheme,
                            HaloUtils.getClientIP(request), new Date())
            );
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "主题已设置为" + siteTheme);
        } catch (Exception e) {
            log.error("主题设置失败，当前主题为：{}", siteTheme);
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "主题设置失败");
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
