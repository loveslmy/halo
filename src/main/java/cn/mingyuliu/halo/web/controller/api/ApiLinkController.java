package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.Link;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.LinkService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     友情链接API
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
@RestController
@RequestMapping(value = "/api/links")
public class ApiLinkController {

    @Resource
    private LinkService linkService;

    /**
     * 获取所有友情链接
     *
     * @return JsonResult
     */
    @GetMapping
    public JsonResult<List<Link>> links() {
        List<Link> links = linkService.findAllLinks();
        if (CollectionUtils.isNotEmpty(links)) {
            new JsonResult<>(ResponseStatus.SUCCESS, links);
        }
        return new JsonResult<>(ResponseStatus.EMPTY);
    }

}
