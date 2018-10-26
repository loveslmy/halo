package cn.mingyuliu.halo.init;

import cn.mingyuliu.halo.common.enums.Option;
import cn.mingyuliu.halo.config.sys.OptionHolder;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;

/**
 * <pre>
 *     初始化数据执行器
 * </pre>
 *
 * @author : liumy2009@126.com
 * date : 2018/10/25
 */
@Component
@Slf4j
public class InitDataExecutor implements ApplicationContextAware {

    @Value("${application.init.data.script}")
    private String initDataSQLFile;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        OptionHolder optionHolder = applicationContext.getBean("optionHolder", OptionHolder.class);
        if (!optionHolder.getBoolean(Option.DATA_IS_INIT)) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.setSqlScriptEncoding(Charsets.UTF_8.name());
            resourceDatabasePopulator.setCommentPrefix("--");
            resourceDatabasePopulator.setContinueOnError(false);
            resourceDatabasePopulator.setIgnoreFailedDrops(true);
            ResourceLoader resourceLoader = new PathMatchingResourcePatternResolver();
            Resource resource = resourceLoader.getResource(ResourceUtils.CLASSPATH_URL_PREFIX + initDataSQLFile);

            if (resource.exists()) {
                resourceDatabasePopulator.setScripts(resource);
                log.info("Executing SQL scripts: " + ObjectUtils.nullSafeToString(resource));
                DataSource dataSource = retrieveDataSource(applicationContext);
                if (dataSource == null) {
                    throw new IllegalStateException(String.format("Failed to executeProcess SQL scripts for " +
                                    "test context %s: " + "supply at least saveFund DataSource or PlatformTransactionManager."
                            , applicationContext));
                }
                DatabasePopulatorUtils.execute(resourceDatabasePopulator, dataSource);
            } else {
                String msg = String.format("Could not detect default SQL script for test: " +
                        "%s does not exist. Either declare statements or scripts via @Sql or make the " +
                        "default SQL script available.", resource);
                log.error(msg);
                throw new IllegalStateException(msg);
            }
            optionHolder.set(Option.DATA_IS_INIT, "true");
        }
    }


    private DataSource retrieveDataSource(ApplicationContext applicationContext) {
        try {
            return applicationContext.getBean("dataSource", DataSource.class);
        } catch (BeansException ex) {
            if (log.isDebugEnabled()) {
                log.info("Caught exception while retrieving DataSource for test context " + applicationContext, ex);
            }
            return null;
        }
    }


}
