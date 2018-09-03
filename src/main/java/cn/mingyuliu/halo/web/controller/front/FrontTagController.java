package cn.mingyuliu.halo.web.controller.front;

import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.domain.Tag;
import cn.mingyuliu.halo.service.PostService;
import cn.mingyuliu.halo.service.TagService;
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

import static cn.mingyuliu.halo.model.dto.HaloConst.POST_SORT;

/**
 * <pre>
 *     前台标签控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/4/26
 */
@Controller
@RequestMapping(value = "/tags")
public class FrontTagController extends BaseController {

    @Resource
    private TagService tagService;

    @Resource
    private PostService postService;

    /**
     * 标签
     *
     * @return 模板路径/themes/{theme}/tags
     */
    @GetMapping
    public String tags() {
        return this.render("tags");
    }

    /**
     * 根据标签路径查询所有文章
     *
     * @param tagUrl 标签路径
     * @param model  model
     * @return String
     */
    @GetMapping(value = "{tagUrl}")
    public String tags(Model model,
                       @PathVariable("tagUrl") String tagUrl) {
        return this.tags(model, tagUrl, 1);
    }

    /**
     * 根据标签路径查询所有文章 分页
     *
     * @param model  model
     * @param tagUrl 标签路径
     * @param page   页码
     * @return String
     */
    @GetMapping(value = "{tagUrl}/page/{page}")
    public String tags(Model model,
                       @PathVariable("tagUrl") String tagUrl,
                       @PathVariable("page") int page) {
        Tag tag = tagService.findByTagUrl(tagUrl);
        if (null == tag) {
            return this.renderNotFound();
        }
        int pageSize = getPageSize();
        Pageable pageable = PageRequest.of(page - 1, pageSize, POST_SORT);
        Page<Post> posts = postService.findPostsByTags(tag, pageable);
        model.addAttribute("is_tags", true);
        model.addAttribute("posts", posts);
        model.addAttribute("tag", tag);
        return this.render("tag");
    }

}
