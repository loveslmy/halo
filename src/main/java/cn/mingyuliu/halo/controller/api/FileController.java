package cn.mingyuliu.halo.controller.api;

import cn.mingyuliu.halo.common.dto.JsonResult;
import cn.mingyuliu.halo.common.entity.File;
import cn.mingyuliu.halo.common.enums.FileType;
import cn.mingyuliu.halo.common.repository.FileRepository;
import cn.mingyuliu.halo.controller.BaseController;
import cn.mingyuliu.halo.service.IFileService;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.Set;

import static cn.mingyuliu.halo.common.HaloConst.*;

/**
 * <pre>
 *     文件API
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since : 2018/10/22
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/file")
public class FileController extends BaseController {
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 800;
    private static final double ONE = 1.0;
    private static Set<String> SUPPORT_IMAGES = Sets.newHashSet("jpg","jpeg","tiff","raw","bmp","gif","png");

    @Resource
    private FileRepository fileRepository;

    @Resource
    private IFileService fileService;

    /**
     * 根据文件id获取文件
     *
     * @param id 文件id
     * @return {@link File}
     */
    @RequestMapping("/getFile")
    public JsonResult<File> getFile(@RequestParam("id") long id) {
        try {
            return new JsonResult<>(HttpStatus.OK, fileService.findFileById(id));
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 加载图片列表
     *
     * @param pageable 分页数据
     * @return {@link Page<File>}
     */
    @RequestMapping("/listImage")
    public JsonResult<Page<File>> listImage(@PageableDefault(sort = "updDate",
            direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            Page<File> pageFile = fileRepository.findBySuffixIn(SUPPORT_IMAGES, pageable);
            fileService.fillContent(pageFile.getContent());
            return new JsonResult<>(HttpStatus.OK, pageFile);
        } catch (Exception e) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_MSG);
        }
    }

    /**
     * 上传文件
     *
     * @param file      文件对象
     * @param thumbnail 是否裁剪
     * @param token     上传文件token
     * @return {@link JsonResult<File>}
     */
    @PostMapping(value = "/upload/image")
    public JsonResult<File> uploadImage(@RequestParam("file") MultipartFile file,
                                  @RequestParam("thumbnail") boolean thumbnail,
                                  @RequestParam("token") String token) {
        if (file.isEmpty()) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "文件不能为空!");
        }

        String originalFilename = StringUtils.defaultString(file.getOriginalFilename());
        String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf(DOT) + 1).toLowerCase();
        if (!SUPPORT_IMAGES.contains(fileSuffix)) {
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR,
                    MessageFormat.format("只支持以下图片格式:{0}", SUPPORT_IMAGES));
        }

        String nameWithOutSuffix = originalFilename.substring(0, originalFilename.lastIndexOf(DOT))
                .replaceAll(SPACE, UNDER_LINE).replaceAll(COMMA, StringUtils.EMPTY);
        String fileName = nameWithOutSuffix + DOT + fileSuffix;

        File newFile;
        try (InputStream in = file.getInputStream()) {
            byte[] contents;
            BufferedImage image = Thumbnails.of(in).scale(ONE).outputQuality(ONE).asBufferedImage();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            if (thumbnail && (image.getWidth() > WIDTH || image.getHeight() > HEIGHT)) {
                Thumbnails.of(image).size(WIDTH, HEIGHT).keepAspectRatio(Boolean.TRUE).outputFormat(fileSuffix).
                        toOutputStream(bos);
            } else {
                ImageIO.write(image, fileSuffix, bos);
            }

            contents = bos.toByteArray();

            String md5 = getMd5(contents);
            if (StringUtils.EMPTY.equals(md5)) {
                return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "不支持的文件!");
            }

            File dbFile = fileRepository.findByMd5(md5);
            if (dbFile != null) {
                return new JsonResult<>(HttpStatus.OK, dbFile);
            }

            newFile = buildFile(fileSuffix, fileName, contents, md5);
            newFile = fileService.saveOrModify(newFile);
        } catch (IOException e) {
            log.error("upload file occur exception：", e);
            return new JsonResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return new JsonResult<>(HttpStatus.OK, newFile);
    }

    private File buildFile(String fileSuffix, String fileName, byte[] contents, String md5) {
        File newFile;
        newFile = new File();
        newFile.setName(fileName);
        newFile.setFileType(FileType.TEXT);
        newFile.setMd5(md5);
        newFile.setSuffix(fileSuffix);
        newFile.setContent("data:image/" + fileSuffix + ";base64," + BASE64_ENCODER.encodeToString(contents));
        newFile.setSize(newFile.getContent().getBytes().length);
        return newFile;
    }

    private String getMd5(byte[] contents) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(contents);
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error("no such algorithm MD5!");
        }
        return StringUtils.EMPTY;
    }

}
