package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.Post;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatusEnum;
import cn.mingyuliu.halo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
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
            return new JsonResult(ResponseStatusEnum.SUCCESS.getCode(),
                    ResponseStatusEnum.SUCCESS.getMsg(), postOpt.get());
        }
        return new JsonResult(ResponseStatusEnum.NOTFOUND.getCode(), ResponseStatusEnum.NOTFOUND.getMsg());
    }
}
