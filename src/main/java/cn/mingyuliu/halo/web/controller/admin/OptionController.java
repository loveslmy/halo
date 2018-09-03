package cn.mingyuliu.halo.web.controller.admin;

import cn.mingyuliu.halo.model.dto.HaloConst;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResultCodeEnum;
import cn.mingyuliu.halo.service.IOptionService;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <pre>
 *     后台设置选项控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/12/13
 */
@Slf4j
@Controller
@RequestMapping("/admin/option")
public class OptionController extends BaseController {

    @Resource
    private Configuration configuration;

    /**
     * 请求跳转到option页面并完成渲染
     *
     * @return 模板路径admin/admin_option
     */
    @GetMapping
    public String options() {
        return "admin/admin_option";
    }

    /**
     * 保存设置选项
     *
     * @param options options
     * @return JsonResult
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public JsonResult saveOptions(@RequestParam Map<String, String> options) {
        try {
            // 刷新options
            optionHolder.saveOptions(options);
            optionHolder.loadOptions();
            configuration.setSharedVariable("options", optionHolder.getOptions());
            log.info("所保存的设置选项列表：" + options);
            return new JsonResult(ResultCodeEnum.SUCCESS.getCode(), "操作成功！");
        } catch (Exception e) {
            log.error("保存设置选项失败：{}", e.getMessage(), e);
            return new JsonResult(ResultCodeEnum.FAIL.getCode(), "操作失败" + e.getMessage());
        }
    }
}
