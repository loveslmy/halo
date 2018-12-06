package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Post;
import cn.mingyuliu.halo.common.repository.PostRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <pre>
 *     文章API
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/12/06
 */
@RestController
@RequestMapping(value = "/api/post")
public class PostController extends BaseController {

    @Resource
    private PostRepository postRepository;

    @Resource
    private IPostService postService;

    /**
     * 加载文章
     */
    @RequestMapping("/load")
    public JsonResult<Post> load(Long postId) {
        try {
            Optional<Post> postOpt = postRepository.findById(postId);
            if (!postOpt.isPresent()) {
                return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "您要访问的文章已删除!");
            }
            return new JsonResult<>(HttpStatus.OK, postOpt.get());
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    /**
     * 新增/修改文章
     *
     * @param post {@link Post}
     * @return {@link JsonResult<Post>}
     */
    @PostMapping("/saveOrModify")
    public JsonResult<Post> saveOrModify(@RequestBody Post post) {
        try {
            return new JsonResult<>(HttpStatus.OK, postService.saveOrModify(post));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
