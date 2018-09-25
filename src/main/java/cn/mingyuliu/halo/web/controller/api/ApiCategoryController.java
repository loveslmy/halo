package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.Category;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     文章分类API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/categories")
public class ApiCategoryController {

    @Resource
    private ICategoryService categoryService;

    /**
     * 获取所有分类
     *
     * @return JsonResult
     */
    @GetMapping
    public JsonResult<List<Category>> categories() {
        List<Category> categories = categoryService.findAllCategories();
        if (CollectionUtils.isNotEmpty(categories)) {
            new JsonResult<>(ResponseStatus.SUCCESS, categories);
        }
        return new JsonResult<>(ResponseStatus.EMPTY);
    }

    /**
     * 获取单个分类的信息
     *
     * @param cateUrl 分类路径
     * @return JsonResult
     */
    @GetMapping(value = "/{cateUrl}")
    public JsonResult<Category> categories(@PathVariable("cateUrl") String cateUrl) {
        Category category = categoryService.findByCateUrl(cateUrl);
        if (null != category) {
            new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(), category);
        }
        return new JsonResult<>(ResponseStatus.EMPTY);
    }

}
