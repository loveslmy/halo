package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <pre>
 *     评论API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/comments")
public class ApiCommentController {

    @Resource
    private CommentService commentService;
}
