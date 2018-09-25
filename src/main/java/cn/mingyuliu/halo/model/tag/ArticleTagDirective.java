package cn.mingyuliu.halo.model.tag;

import cn.mingyuliu.halo.model.enums.PostStatus;
import cn.mingyuliu.halo.service.PostService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * <pre>
 *     FreeMarker自定义标签
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/4/26
 */
@Component
public class ArticleTagDirective implements TemplateDirectiveModel {

    private static final String METHOD_KEY = "method";

    @Resource
    private PostService postService;

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
        if (map.containsKey(METHOD_KEY)) {
            String method = map.get(METHOD_KEY).toString();
            switch (method) {
                case "postsCount":
                    environment.setVariable("postsCount", builder.build().wrap(postService
                            .findPostByStatus(PostStatus.PUBLISHED).size()));
                    break;
                case "archives":
                    environment.setVariable("archives", builder.build().wrap(postService
                            .findPostGroupByYearAndMonth()));
                    break;
                case "archivesLess":
                    environment.setVariable("archivesLess", builder.build().wrap(postService
                            .findPostGroupByYear()));
                    break;
                case "hotPosts":
                    environment.setVariable("hotPosts", builder.build().wrap(postService
                            .hotPosts()));
                    break;
                default:
                    break;
            }
        }
        templateDirectiveBody.render(environment.getOut());
    }
}
