package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.Option;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 *     系统设置API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/7/19
 */
@RestController
@RequestMapping(value = "/api/options")
public class ApiOptionController extends BaseController {

    /**
     * 获取所有设置选项
     *
     * @return JsonResult
     */
    @GetMapping
    public JsonResult options() {
        return new JsonResult<>(ResponseStatus.SUCCESS,
                ResponseStatus.SUCCESS.getMsg(), optionHolder.getOptions());
    }

    /**
     * 获取单个设置项
     *
     * @param optionName 设置选项名称
     * @return JsonResult
     */
    @GetMapping(value = "/{optionName}")
    public JsonResult option(@PathVariable(value = "optionName") String optionName) {
        return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(),
                optionHolder.get(Option.valueOf(optionName)));
    }
}
