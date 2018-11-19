package cn.mingyuliu.halo.controller;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.repository.FileRepository;
import cn.mingyuliu.halo.test.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * <pre>
 *     菜单ControllerTest
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/09/27
 */
public class FileControllerTest extends BaseTest {

    @Resource
    private FileRepository fileRepository;

    @Test
    @SuppressWarnings("unchecked")
    public void testUploadImage() throws IOException {
        fileRepository.deleteAll();
        String filePath = ResourceUtils.getURL("classpath:image/desert.jpg").getPath();
        FileSystemResource fileResource = new FileSystemResource(filePath);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.set("file", fileResource);
        param.set("thumbnail", true);
        param.set("token", "ABC1234567890");
        HttpEntity entity = new HttpEntity<>(param);
        ResponseEntity<JsonResult> rst = testRestTemplate.exchange("/api/file/upload/image", HttpMethod.POST, entity,
                JsonResult.class);
        JsonResult jsonResult = rst.getBody();
        Assert.assertNotNull(jsonResult);
        LinkedHashMap file = (LinkedHashMap) jsonResult.getDatas();
        Assert.assertEquals(HttpStatus.OK, jsonResult.getStatus());
        rst = testRestTemplate.getForEntity("/api/file/getFile?id="+file.get("id"), JsonResult.class);
        jsonResult = rst.getBody();
        Assert.assertNotNull(jsonResult);
        Assert.assertEquals(HttpStatus.OK, jsonResult.getStatus());
        Assert.assertEquals(file.get("md5"), ((LinkedHashMap) jsonResult.getDatas()).get("md5"));
        System.out.println(jsonResult.getDatas().toString());
    }

}
