package cn.mingyuliu.halo.web.controller.api;

import cn.mingyuliu.halo.model.domain.Gallery;
import cn.mingyuliu.halo.model.dto.JsonResult;
import cn.mingyuliu.halo.model.enums.ResponseStatus;
import cn.mingyuliu.halo.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <pre>
 *     图库API
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/galleries")
public class ApiGalleryController {

    @Resource
    private GalleryService galleryService;

    /**
     * 获取所有图片
     *
     * @return JsonResult
     */
    @GetMapping
    public JsonResult galleries() {
        List<Gallery> galleries = galleryService.findAllGalleries();
        if (null != galleries && galleries.size() > 0) {
            return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(), galleries);
        } else {
            return new JsonResult<>(ResponseStatus.EMPTY, ResponseStatus.EMPTY.getMsg());
        }
    }

    /**
     * 获取单张图片的信息
     *
     * @param id id
     * @return JsonResult
     */
    @GetMapping(value = "/{id}")
    public JsonResult galleries(@PathVariable("id") Long id) {
        Optional<Gallery> gallery = galleryService.findByGalleryId(id);
        if (gallery.isPresent()) {
            return new JsonResult<>(ResponseStatus.SUCCESS, ResponseStatus.SUCCESS.getMsg(), gallery.get());
        } else {
            return new JsonResult<>(ResponseStatus.NOT_FOUND, ResponseStatus.NOT_FOUND.getMsg());
        }
    }
}
