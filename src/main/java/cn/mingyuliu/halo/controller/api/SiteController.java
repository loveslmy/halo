package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Site;
import cn.mingyuliu.halo.common.repository.SiteRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.IFileService;
import cn.mingyuliu.halo.service.ISiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     站点API
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/11/15
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/site")
public class SiteController extends BaseController {

    @Resource
    private SiteRepository siteRepository;

    @Resource
    private IFileService fileService;

    @Resource
    private ISiteService siteService;

    /**
     * 加载站点
     * @return {@link Page<Site>}
     */
    @RequestMapping("/listSite")
    public JsonResult<List<Site>> listSite() {
        try {
            return new JsonResult<>(HttpStatus.OK, siteRepository.findAll());
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 新增/修改站点
     *
     * @param site {@link Site}
     * @return {@link JsonResult<Site>}
     */
    @PostMapping("/saveOrModify")
    public JsonResult<Site> saveOrModify(@RequestBody Site site) {
        try {
            return new JsonResult<>(HttpStatus.OK, siteService.saveOrModify(site));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


}
