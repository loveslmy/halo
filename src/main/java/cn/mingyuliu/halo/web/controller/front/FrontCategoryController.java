package cn.mingyuliu.halo.web.controller.front;

import cn.mingyuliu.halo.model.domain.Category;
import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.service.ICategoryService;
import cn.mingyuliu.halo.service.PostService;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

import static cn.mingyuliu.halo.model.dto.HaloConst.POST_SORT;

/**
 * <pre>
 *     前台文章分类控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/4/26
 */
@Controller
@RequestMapping(value = "categories")
public class FrontCategoryController extends BaseController {

    @Resource
    private ICategoryService categoryService;

    @Resource
    private PostService postService;

    /**
     * 分类列表页面
     *
     * @param model model
     * @return String
     */
    @GetMapping
    public String categories(Model model) {
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return this.render("categories");
    }

    /**
     * 根据分类路径查询文章
     *
     * @param model   model
     * @param cateUrl cateUrl
     * @return string
     */
    @GetMapping(value = "{cateUrl}")
    public String categories(Model model,
                             @PathVariable("cateUrl") String cateUrl) {
        return this.categories(model, cateUrl, 1);
    }

    /**
     * 根据分类目录查询所有文章 分页
     *
     * @param model   model
     * @param cateUrl 分类目录路径
     * @param page    页码
     * @return String
     */
    @GetMapping("{cateUrl}/page/{page}")
    public String categories(Model model,
                             @PathVariable("cateUrl") String cateUrl,
                             @PathVariable("page") Integer page) {
        Category category = categoryService.findByCateUrl(cateUrl);
        if (null == category) {
            return this.renderNotFound();
        }
        int pageSize = getPageSize();
        Pageable pageable = PageRequest.of(page - 1, pageSize, POST_SORT);
        Page<Post> posts = postService.findPostByCategories(category, pageable);
        model.addAttribute("is_categories",true);
        model.addAttribute("posts", posts);
        model.addAttribute("category", category);
        return this.render("category");
    }

}
