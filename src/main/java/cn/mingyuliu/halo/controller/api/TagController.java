package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Tag;
import cn.mingyuliu.halo.common.repository.TagRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.ITagService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     标签API
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/12/04
 */
@RestController
@RequestMapping(value = "/api/tag")
public class TagController extends BaseController {

    @Resource
    private TagRepository tagRepository;

    @Resource
    private ITagService tagService;

    /**
     * 查询标签列表
     *
     * @return {@link JsonResult<List<Tag>>}
     */
    @RequestMapping("/listTag")
    public JsonResult<List<Tag>> listTag() {
        try {
            return new JsonResult<>(HttpStatus.OK, tagRepository.findAll());
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 新增/修改标签
     *
     * @param tag {@link Tag}
     * @return {@link JsonResult<Tag>}
     */
    @PostMapping("/saveOrModify")
    public JsonResult<Tag> saveOrModify(@RequestBody Tag tag) {
        try {
            return new JsonResult<>(HttpStatus.OK, tagService.saveOrModify(tag));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
