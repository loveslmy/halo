package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.PostStatus;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.PostService;
import cn.mingyuliu.halo.web.controller.core.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static cn.mingyuliu.halo.model.dto.HaloConst.POST_SORT;

/**
 * <pre>
 *     文章API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/posts")
public class ApiPostController extends BaseController {

    @Resource
    private PostService postService;

    /**
     * 获取文章列表 分页
     *
     * @param page 页码
     * @return JsonResult
     */
    @GetMapping(value = "/page/{page}")
    public JsonResult posts(@PathVariable(value = "page") Integer page) {
        int size = getPageSize();
        Pageable pageable = PageRequest.of(page - 1, size, POST_SORT);
        Page<Post> posts = postService.findPostByStatus(PostStatus.PUBLISHED, pageable);
        if (null == posts) {
            return new JsonResult<>(ResponseStatus.EMPTY, ResponseStatus.EMPTY.getMsg());
        }
        return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(), posts);
    }

    @GetMapping(value = "/hot")
    public JsonResult hotPosts() {
        List<Post> posts = postService.hotPosts();
        if (null != posts && posts.size() > 0) {
            return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(), posts);
        } else {
            return new JsonResult<>(ResponseStatus.EMPTY, ResponseStatus.EMPTY.getMsg());
        }
    }

    /**
     * 获取单个文章信息
     *
     * @param postId 文章编号
     * @return JsonResult
     */
    @GetMapping(value = "/{postId}")
    public JsonResult posts(@PathVariable(value = "postId") Long postId) {
        Optional<Post> postOpt = postService.findByPostId(postId);
        if (postOpt.isPresent()) {
            return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(),
                    postOpt.get());
        }
        return new JsonResult<>(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getMsg());
    }
}
