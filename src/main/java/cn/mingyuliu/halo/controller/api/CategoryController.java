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
 * @date : 2018/10/22
 */
@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController extends BaseController {

    @Resource
    private CategoryRepository categoryRepository;

    @Resource
    private ICategoryService categoryService;

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

    /**
     * 根据上级id查询所有子菜单
     *
     * @param parentId parentId
     * @return {@link JsonResult<List<Category>>}
     */
    @RequestMapping("/findByParentId")
    public JsonResult<List<Category>> findByParentId(@RequestParam long parentId) {
        try {
            return new JsonResult<>(HttpStatus.OK, categoryRepository
                    .findByParentIdOrderByOrderSeq(parentId));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }




}
