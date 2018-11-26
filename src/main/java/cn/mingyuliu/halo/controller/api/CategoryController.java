package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.CategoryDto;
import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Category;
import cn.mingyuliu.halo.common.repository.CategoryRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 *     分类API
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/10/22
 */
@RestController
@RequestMapping(value = "/api/category")
public class CategoryController extends BaseController {

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private ICategoryService categoryService;

    /**
     * 查询分类列表
     *
     * @return {@link JsonResult<List<Category>>}
     */
    @RequestMapping("/listCategory")
    public JsonResult<List<Category>> listCategory() {
        try {
            return new JsonResult<>(HttpStatus.OK, categoryRepository
                    .findAll());
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 根据上级id查询所有子分类
     *
     * @param parentId parentId
     * @return {@link JsonResult<List<Category>>}
     */
    @RequestMapping("/findByParentId")
    public JsonResult<List<Category>> findByParentId(@RequestParam Long parentId) {
        try {
            List<Category> categories;

            if (null != parentId) {
                categories = categoryRepository
                        .findByParentIdAndActiveIsTrueOrderByOrderSeq(parentId);
            } else {
                categories = categoryRepository
                        .findByParentIdIsNullAndActiveIsTrueOrderByOrderSeq();
            }

            if (CollectionUtils.isEmpty(categories)) {
                return new JsonResult<>(HttpStatus.OK, Collections.emptyList());
            }

            return new JsonResult<>(HttpStatus.OK, categories);
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 新增/修改分类
     *
     * @param menu {@link Category}
     * @return {@link JsonResult<Category>}
     */
    @PostMapping("/saveOrModify")
    public JsonResult<Category> saveOrModify(@RequestBody Category menu) {
        try {
            return new JsonResult<>(HttpStatus.OK, categoryService.saveOrModify(menu));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

}
