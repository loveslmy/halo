package cn.mingyuliu.halo.web.controller.front;

import cn.mingyuliu.halo.config.sys.OptionHolder;
import cn.mingyuliu.halo.model.domain.Comment;
import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.domain.Tag;
import cn.mingyuliu.halo.model.dto.HaloConst;
import cn.mingyuliu.halo.model.enums.CommentStatusEnum;
import cn.mingyuliu.halo.model.enums.Option;
import cn.mingyuliu.halo.model.enums.PostStatus;
import cn.mingyuliu.halo.service.CommentService;
import cn.mingyuliu.halo.service.PostService;
import cn.mingyuliu.halo.utils.CommentUtil;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.mingyuliu.halo.model.dto.HaloConst.POST_SORT;

/**
 * <pre>
 *     前台文章归档控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/4/26
 */
@Slf4j
@Controller
@RequestMapping(value = "/archives")
public class FrontArchiveController extends BaseController {

    @Resource
    private OptionHolder optionHolder;

    @Resource
    private PostService postService;

    @Resource
    private CommentService commentService;

    /**
     * 文章归档
     *
     * @param model model
     * @return 模板路径
     */
    @GetMapping
    public String archives(Model model) {
        return this.archives(model, 1);
    }

    /**
     * 文章归档分页
     *
     * @param model model
     * @param page  page 当前页码
     * @return 模板路径/themes/{theme}/archives
     */
    @GetMapping(value = "page/{page}")
    public String archives(Model model,
                           @PathVariable(value = "page") Integer page) {

        //所有文章数据，分页，material主题适用
        Pageable pageable = PageRequest.of(page - 1, 5, POST_SORT);
        Page<Post> posts = postService.findPostByStatus(PostStatus.PUBLISHED, pageable);
        if (null == posts) {
            return this.renderNotFound();
        }
        model.addAttribute("is_archives", true);
        model.addAttribute("posts", posts);
        return this.render("archives");
    }

    /**
     * 文章归档，根据年月
     *
     * @param model model
     * @param year  year 年份
     * @param month month 月份
     * @return 模板路径/themes/{theme}/archives
     */
    @GetMapping(value = "{year}/{month}")
    public String archives(Model model,
                           @PathVariable(value = "year") String year,
                           @PathVariable(value = "month") String month) {
        Page<Post> posts = postService.findPostByYearAndMonth(year, month, null);
        if (null == posts) {
            return this.renderNotFound();
        }
        model.addAttribute("is_archives", true);
        model.addAttribute("posts", posts);
        return this.render("archives");
    }

    /**
     * 渲染文章详情
     *
     * @param postUrl 文章路径名
     * @param model   model
     * @return 模板路径/themes/{theme}/post
     */
    @GetMapping(value = "{postUrl}")
    public String getPost(@PathVariable String postUrl, Model model) {
        Post post = postService.findByPostUrl(postUrl);
        if (null == post || PostStatus.PUBLISHED != post.getPostStatus()) {
            return this.renderNotFound();
        }
        //获得当前文章的发布日期
        Date postDate = post.getPostDate();
        //查询当前文章日期之前的所有文章
        List<Post> beforePosts = postService.findByPostDateBefore(postDate);
        //查询当前文章日期之后的所有文章
        List<Post> afterPosts = postService.findByPostDateAfter(postDate);

        if (null != beforePosts && beforePosts.size() > 0) {
            model.addAttribute("beforePost", beforePosts.get(beforePosts.size() - 1));
        }
        if (null != afterPosts && afterPosts.size() > 0) {
            model.addAttribute("afterPost", afterPosts.get(afterPosts.size() - 1));
        }
        List<Comment> comments;
        if (!optionHolder.getBoolean(Option.NEW_COMMENT_NEED_CHECK)) {
            comments = commentService.findCommentsByPostAndCommentStatus(post, CommentStatusEnum.PUBLISHED.getCode());
        } else {
            comments = commentService.findCommentsByPostAndCommentStatusNot(post, CommentStatusEnum.RECYCLE.getCode());
        }
        //获取文章的标签用作keywords
        List<Tag> tags = post.getTags();
        List<String> tagWords = new ArrayList<>();
        if (tags != null) {
            for (Tag tag : tags) {
                tagWords.add(tag.getTagName());
            }
        }
        model.addAttribute("is_post", true);
        model.addAttribute("post", post);
        model.addAttribute("comments", CommentUtil.getComments(comments));
        model.addAttribute("commentsCount", comments.size());
        model.addAttribute("tagWords", HaloConst.DOT_JOINER.join(tagWords));
        postService.updatePostView(post);
        return this.render("post");
    }
}
