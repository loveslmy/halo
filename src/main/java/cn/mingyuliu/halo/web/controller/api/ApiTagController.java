package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.Tag;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     标签API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/tags")
public class ApiTagController {

    @Resource
    private TagService tagService;

    /**
     * 获取所有标签
     *
     * @return JsonResult
     */
    @GetMapping
    public JsonResult tags() {
        List<Tag> tags = tagService.findAllTags();
        if (null != tags && tags.size() > 0) {
            return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(), tags);
        } else {
            return new JsonResult<>(ResponseStatus.EMPTY, ResponseStatus.EMPTY.getMsg());
        }
    }

    /**
     * 获取单个标签的信息
     *
     * @param tagUrl tagUrl
     * @return JsonResult
     */
    @GetMapping(value = "/{tagUrl}")
    public JsonResult tags(@PathVariable("tagUrl") String tagUrl) {
        Tag tag = tagService.findByTagUrl(tagUrl);
        if (null != tag) {
            return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(), tag);
        } else {
            return new JsonResult<>(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getMsg());
        }
    }
}
