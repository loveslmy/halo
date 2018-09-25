package cn.mingyuliu.halo.web.controller.front;

import cn.mingyuliu.halo.model.domain.Comment;
import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.CommentStatusEnum;
import cn.mingyuliu.halo.model.enums.Option;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.CommentService;
import cn.mingyuliu.halo.service.IUserService;
import cn.mingyuliu.halo.service.MailService;
import cn.mingyuliu.halo.service.PostService;
import cn.mingyuliu.halo.utils.CommentUtil;
import cn.mingyuliu.halo.utils.HaloUtils;
import cn.mingyuliu.halo.utils.OwoUtil;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     前台评论控制器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/4/26
 */
@Slf4j
@Controller
public class FrontCommentController extends BaseController {

    @Resource
    private CommentService commentService;

    @Resource
    private PostService postService;

    @Resource
    private IUserService userService;

    @Resource
    private MailService mailService;

    /**
     * 获取文章的评论
     *
     * @param postId postId 文章编号
     * @return List
     */
    @GetMapping(value = "/getComment/{postId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<Comment> getComment(@PathVariable Long postId) {
        Optional<Post> post = postService.findByPostId(postId);
        Sort sort = new Sort(Sort.Direction.DESC, "commentDate");
        Pageable pageable = PageRequest.of(0, 999, sort);
        List<Comment> comments = commentService.findCommentsByPostAndCommentStatus(post.get(),
                pageable, CommentStatusEnum.PUBLISHED.getCode()).getContent();
        return CommentUtil.getComments(comments);
    }

    /**
     * 加载评论
     *
     * @param page 页码
     * @param post 当前文章
     * @return List
     */
    @GetMapping(value = "/loadComment")
    @ResponseBody
    public List<Comment> loadComment(@RequestParam(value = "page") Integer page,
                                     @RequestParam(value = "post") Post post) {
        Sort sort = new Sort(Sort.Direction.DESC, "commentDate");
        Pageable pageable = PageRequest.of(page - 1, 10, sort);
        List<Comment> comments = commentService.findCommentsByPostAndCommentStatus(post, pageable,
                CommentStatusEnum.PUBLISHED.getCode()).getContent();
        return comments;
    }

    /**
     * 提交新评论
     *
     * @param comment comment实体
     * @param post    post实体
     * @param request request
     * @return JsonResult
     */
    @PostMapping(value = "/newComment")
    @ResponseBody
    public JsonResult newComment(@Valid @ModelAttribute("comment") Comment comment,
                                 BindingResult result,
                                 @ModelAttribute("post") Post post,
                                 HttpServletRequest request) {
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                return new JsonResult<>(ResponseStatus.FAIL, error.getDefaultMessage());
            }
        }
        try {
            Comment lastComment = null;
            post = postService.findByPostId(post.getId()).get();
            comment.setCommentAuthorEmail(StringEscapeUtils.escapeHtml4(comment.getCommentAuthorEmail()).toLowerCase());
            comment.setPost(post);
            comment.setCommentDate(new Date());
            comment.setCommentAuthorIp(HaloUtils.getClientIP(request));
            comment.setIsAdmin(0);
            comment.setCommentAuthor(StringEscapeUtils.escapeHtml4(comment.getCommentAuthor()));
            if (comment.getCommentParent() > 0) {
                lastComment = commentService.findCommentById(comment.getCommentParent()).get();
                String lastContent = "<a href='#comment-id-" + lastComment.getId() + "'>@"
                        + lastComment.getCommentAuthor() + "</a>";
                comment.setCommentContent(lastContent
                        + StringUtils.substringAfter(OwoUtil.markToImg(StringEscapeUtils
                                .escapeHtml4(comment.getCommentContent())),
                        ":"));
            } else {
                //将评论内容的字符专为安全字符
                comment.setCommentContent(OwoUtil.markToImg(StringEscapeUtils.escapeHtml4(comment.getCommentContent())));
            }
            if (StringUtils.isNotEmpty(comment.getCommentAuthorUrl())) {
                comment.setCommentAuthorUrl(HaloUtils.normalize(comment.getCommentAuthorUrl()));
            }
            commentService.saveByComment(comment);
            if (comment.getCommentParent() > 0) {
                new EmailToParent(comment, lastComment, post).start();
                new EmailToAdmin(comment, post).start();
            } else {
                new EmailToAdmin(comment, post).start();
            }
            if (optionHolder.getBoolean(Option.NEW_COMMENT_NEED_CHECK)) {
                return new JsonResult<>(ResponseStatus.SUCCESS, "你的评论已经提交，待博主审核之后可显示。");
            } else {
                return new JsonResult<>(ResponseStatus.SUCCESS, "你的评论已经提交，刷新后即可显示。");
            }
        } catch (Exception e) {
            return new JsonResult<>(ResponseStatus.FAIL, "评论失败！");
        }
    }

    /**
     * 发送邮件给博主
     */
    class EmailToAdmin extends Thread {
        private Comment comment;
        private Post post;

        private EmailToAdmin(Comment comment, Post post) {
            this.comment = comment;
            this.post = post;
        }

        @Override
        public void run() {
            /*if (StringUtils.equals(HaloConst.OPTIONS.get(Option.SMTP_EMAIL_ENABLE.getProp()),
                    TrueFalseEnum.TRUE.getDesc())
                    && StringUtils.equals(HaloConst.OPTIONS.get(Option.NEW_COMMENT_NOTICE.getProp()),
                    TrueFalseEnum.TRUE.getDesc())) {
                try {
                    //发送邮件到博主
                    Map<String, Object> map = new HashMap<>();
                    map.put("author", IUserService.findOwnerUser().getUserDisplayName());
                    map.put("pageName", post.getPostTitle());
                    if (StringUtils.equals(post.getPostType(), PostTypeEnum.POST.getDesc())) {
                        map.put("pageUrl", HaloConst.OPTIONS.get(Option.BLOG_URL.getProp())
                                + "/archives/" + post.getPostUrl() + "#comment-id-" + comment.getCommentId());
                    } else {
                        map.put("pageUrl", HaloConst.OPTIONS.get(Option.BLOG_URL.getProp())
                                + "/p/" + post.getPostUrl() + "#comment-id-" + comment.getCommentId());
                    }
                    map.put("visitor", comment.getCommentAuthor());
                    map.put("commentContent", comment.getCommentContent());
                    mailService.sendTemplateMail(IUserService.findOwnerUser().getUserEmail(), "有新的评论",
                            map, "common/mail_template/mail_admin.ftl");
                } catch (Exception e) {
                    log.error("邮件服务器未配置：{}", e.getMessage());
                }
            }*/
        }
    }

    /**
     * 发送邮件给被评论方
     */
    class EmailToParent extends Thread {
        private Comment comment;
        private Comment lastComment;
        private Post post;

        private EmailToParent(Comment comment, Comment lastComment, Post post) {
            this.comment = comment;
            this.lastComment = lastComment;
            this.post = post;
        }

        @Override
        public void run() {
            //发送通知给对方
            /*if (StringUtils.equals(HaloConst.OPTIONS.get(Option.SMTP_EMAIL_ENABLE.getProp()),
                    TrueFalseEnum.TRUE.getDesc())
                    && StringUtils.equals(HaloConst.OPTIONS.get(Option.NEW_COMMENT_NOTICE.getProp()),
                    TrueFalseEnum.TRUE.getDesc())) {
                if (HaloUtils.isEmail(lastComment.getCommentAuthorEmail())) {
                    *//*Map<String, Object> map = new HashMap<>();
                    map.put("blogTitle", HaloConst.OPTIONS.get(Option.BLOG_TITLE.getProp()));
                    map.put("commentAuthor", lastComment.getCommentAuthor());
                    map.put("pageName", lastComment.getPost().getPostTitle());
                    if (StringUtils.equals(post.getPostType(), PostTypeEnum.POST.getDesc())) {
                        map.put("pageUrl", HaloConst.OPTIONS.get(Option.BLOG_URL.getProp())
                                + "/archives/" + post.getPostUrl() + "#comment-id-" + comment.getCommentId());
                    } else {
                        map.put("pageUrl", HaloConst.OPTIONS.get(Option.BLOG_URL.getProp())
                                + "/p/" + post.getPostUrl() + "#comment-id-" + comment.getCommentId());
                    }
                    map.put("commentContent", lastComment.getCommentContent());
                    map.put("replyAuthor", comment.getCommentAuthor());
                    map.put("replyContent", comment.getCommentContent());
                    map.put("blogUrl", HaloConst.OPTIONS.get(Option.BLOG_URL.getProp()));
                    mailService.sendTemplateMail(
                            lastComment.getCommentAuthorEmail(), "您在"
                                    + HaloConst.OPTIONS.get(Option.BLOG_TITLE.getProp()) + "的评论有了新回复",
                            map, "common/mail_template/mail_reply.ftl");*//*
                }
            }*/
        }
    }

}

