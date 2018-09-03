package cn.mingyuliu.halo.web.controller.front;

import cn.mingyuliu.halo.model.domain.Comment;
import cn.mingyuliu.halo.model.domain.Gallery;
import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.dto.HaloConst;
import cn.mingyuliu.halo.model.enums.OptionEnum;
import cn.mingyuliu.halo.model.enums.CommentStatusEnum;
import cn.mingyuliu.halo.service.CommentService;
import cn.mingyuliu.halo.service.GalleryService;
import cn.mingyuliu.halo.service.PostService;
import cn.mingyuliu.halo.utils.CommentUtil;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     前台内置页面，自定义页面控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/4/26
 */
@Controller
public class FrontPageController extends BaseController {

    @Resource
    private GalleryService galleryService;

    @Resource
    private PostService postService;

    @Resource
    private CommentService commentService;

    /**
     * 跳转到图库页面
     *
     * @return 模板路径/themes/{theme}/gallery
     */
    @GetMapping(value = "/gallery")
    public String gallery(Model model) {
        List<Gallery> galleries = galleryService.findAllGalleries();
        model.addAttribute("galleries", galleries);
        return this.render("gallery");
    }

    /**
     * 友情链接
     *
     * @return 模板路径/themes/{theme}/links
     */
    @GetMapping(value = "/links")
    public String links() {
        return this.render("links");
    }

    /**
     * 渲染自定义页面
     *
     * @param postUrl 页面路径
     * @param model   model
     * @return 模板路径/themes/{theme}/post
     */
    @GetMapping(value = "/p/{postUrl}")
    public String getPage(@PathVariable(value = "postUrl") String postUrl, Model model) {
        Post post = postService.findByPostUrl(postUrl);
        if (null == post) {
            return this.renderNotFound();
        }
        List<Comment> comments;
        if (!optionHolder.getBoolean(OptionEnum.NEW_COMMENT_NEED_CHECK)) {
            comments = commentService.findCommentsByPostAndCommentStatus(post, CommentStatusEnum.PUBLISHED.getCode());
        } else {
            comments = commentService.findCommentsByPostAndCommentStatusNot(post, CommentStatusEnum.RECYCLE.getCode());
        }
        model.addAttribute("is_page", true);
        model.addAttribute("post", post);
        model.addAttribute("comments", CommentUtil.getComments(comments));
        model.addAttribute("commentsCount", comments.size());
        postService.updatePostView(post);
        return this.render("page");
    }
}
