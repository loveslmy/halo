package cn.mingyuliu.halo.config.spring;

import cn.mingyuliu.halo.config.sys.OptionHolder;
import cn.mingyuliu.halo.model.tag.ArticleTagDirective;
import cn.mingyuliu.halo.model.tag.CommonTagDirective;
import cn.mingyuliu.halo.service.IUserService;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * <pre>
 *     FreeMarker配置
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/02
 */
@Slf4j
@Configuration
public class HaloFreemarkerConfig {

    @Resource
    private OptionHolder optionHolder;

    @Resource
    private freemarker.template.Configuration configuration;

    @Resource
    private IUserService userService;

    @Resource
    private CommonTagDirective commonTagDirective;

    @Resource
    private ArticleTagDirective articleTagDirective;

    @PostConstruct
    public void setSharedVariable() {
        try {
            //自定义标签
            configuration.setSharedVariable("options", optionHolder.getOptions());
            configuration.setSharedVariable("commonTag", commonTagDirective);
            configuration.setSharedVariable("articleTag", articleTagDirective);
            configuration.setSharedVariable("user", userService.findOwnerUser());
        } catch (TemplateModelException e) {
            log.error("自定义标签加载失败：{}", e.getMessage());
        }
    }
}
