package cn.mingyuliu.halo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

/**
 * <pre>
 *     Halo Application
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
@Slf4j
@SpringBootApplication(excludeName = {"cn.mingyuliu.halo.controller.*"})
@EnableCaching
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        String serverPort = context.getEnvironment().getProperty("server.port");
        log.info("Halo started at http://127.0.0.1:" + serverPort);
    }

}
