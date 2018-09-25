package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <pre>
 *     页面API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/pages")
public class ApiPageController {

    @Resource
    private PostService postService;

    /**
     * 获取单个页面
     *
     * @param postId postId
     * @return JsonResult
     */
    @GetMapping(value = "/{postId}")
    public JsonResult pages(@PathVariable(value = "postId") Long postId) {
        Optional<Post> postOpt = postService.findByPostId(postId);
        if (postOpt.isPresent()) {
            return new JsonResult<>(ResponseStatus.SUCCESS,
                    ResponseStatus.SUCCESS.getMsg(), postOpt.get());
        }
        return new JsonResult<>(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getMsg());
    }
}
