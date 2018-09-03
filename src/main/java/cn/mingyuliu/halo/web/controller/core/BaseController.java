package cn.mingyuliu.halo.web.controller.core;

import cn.mingyuliu.halo.config.OptionHolder;
import cn.mingyuliu.halo.model.enums.OptionEnum;

import javax.annotation.Resource;

import static cn.mingyuliu.halo.model.dto.HaloConst.DEFAULT_PAGE_SIZE;

/**
 * <pre>
 *     Controller抽象类
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2017/12/15
 */
public abstract class BaseController {

    @Resource
    protected OptionHolder optionHolder;

    /**
     * 定义默认主题
     */
    public static String THEME = "anatole";

    /**
     * 根据主题名称渲染页面
     *
     * @param pageName pageName
     * @return 返回拼接好的模板路径
     */
    public String render(String pageName) {
        return "themes/" + THEME + "/" + pageName;
    }

    /**
     * 渲染404页面
     *
     * @return redirect:/404
     */
    public String renderNotFound() {
        return "redirect:/404";
    }

    /**
     * 获取每页显示数据条数
     * @return 数据条数
     */
    protected int getPageSize() {
        // 尝试加载设置选项，用于设置显示条数
        int size = optionHolder.getByte(OptionEnum.INDEX_POST_COUNT);

        // 默认显示10条
        if (size <= 0) {
            size = DEFAULT_PAGE_SIZE;
        }
        return size;
    }

}
