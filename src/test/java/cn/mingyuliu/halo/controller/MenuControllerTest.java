package cn.mingyuliu.halo.controller;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.Menu;
import cn.mingyuliu.halo.common.enums.Target;
import cn.mingyuliu.halo.common.repository.MenuRepository;
import cn.mingyuliu.halo.test.BaseTest;
import cn.mingyuliu.halo.utils.JacksonSerializer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *     菜单ControllerTest
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/27
 */
public class MenuControllerTest extends BaseTest {

    @Resource
    private MenuRepository menuRepository;

    @Test
    @SuppressWarnings("unchecked")
    public void testAddMenus() {

        menuRepository.deleteAll();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Menu index = new Menu();
        index.setName("首页");
        index.setUrl("/");
        index.setOrderSeq((short) 1);
        index.setTarget(Target.BLANK);
        index.setActive(true);
        HttpEntity entity = new HttpEntity<>(JacksonSerializer.defaultSerializer().serialize(index), headers);
        JsonResult rst = testRestTemplate.postForObject("/api/menus/saveOrModify", entity, JsonResult.class);
        Assert.assertEquals(HttpStatus.OK, rst.getStatus());

        Menu link = new Menu();
        link.setName("文章");
        link.setUrl("/post");
        link.setOrderSeq((short) 1);
        link.setTarget(Target.BLANK);
        link.setActive(true);
        entity = new HttpEntity<>(JacksonSerializer.defaultSerializer().serialize(link), headers);
        rst = testRestTemplate.postForObject("/api/menus/saveOrModify", entity, JsonResult.class);
        Assert.assertEquals(HttpStatus.OK, rst.getStatus());

        Menu life = new Menu();
        life.setName("相册");
        life.setUrl("/album");
        life.setOrderSeq((short) 1);
        life.setTarget(Target.BLANK);
        life.setActive(true);
        entity = new HttpEntity<>(JacksonSerializer.defaultSerializer().serialize(life), headers);
        rst = testRestTemplate.postForObject("/api/menus/saveOrModify", entity, JsonResult.class);
        Assert.assertEquals(HttpStatus.OK, rst.getStatus());

        List<Menu> menus = menuRepository.findByParentIdIsNullAndActiveIsTrueOrderByOrderSeq();
        Assert.assertEquals(3, menus.size());

    }

}
