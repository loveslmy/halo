package cn.mingyuliu.halo.config.spring;

import cn.mingyuliu.halo.common.enums.AllProfiles;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <pre>
 *     不是uat判断条件
 * </pre>
 *
 * @author : liumy2009@126.com
 * @since  : 2018/12/010
 */
public class NotTestCondition implements Condition {

    /**
     * (non-Javadoc)
     *
     * @see Condition#matches(ConditionContext, AnnotatedTypeMetadata)
     */
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return !ArrayUtils.contains(context.getEnvironment().getActiveProfiles(), AllProfiles.TEST);
    }

}
