package cn.mingyuliu.halo.config.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.*;

/**
 * <pre>
 *     拦截器，资源路径配置
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/1/2
 */
@Slf4j
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cn.mingyuliu.halo.controller")
@PropertySource(value = "classpath:application.yaml", ignoreResourceNotFound = true, encoding = "UTF-8")
public class HaloWebConfig implements WebMvcConfigurer {


    /**
     * 注册拦截器
     *
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * 配置静态资源路径
     *
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/themes/");
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/upload/");
        registry.addResourceHandler("/favicon.ico")
                .addResourceLocations("classpath:/static/images/favicon.ico");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }

}
