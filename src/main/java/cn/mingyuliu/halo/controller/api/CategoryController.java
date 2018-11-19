package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Category;
import cn.mingyuliu.halo.common.repository.CategoryRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
            if (null != parentId) {
                return new JsonResult<>(HttpStatus.OK, categoryRepository
                        .findByParentIdAndActiveIsTrueOrderByOrderSeq(parentId));
            }
            return new JsonResult<>(HttpStatus.OK, categoryRepository
                    .findByParentIdIsNullAndActiveIsTrueOrderByOrderSeq());
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
