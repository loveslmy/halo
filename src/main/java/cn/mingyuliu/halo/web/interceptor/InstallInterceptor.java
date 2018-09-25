package cn.mingyuliu.halo.web.interceptor;

import cn.mingyuliu.halo.config.sys.OptionHolder;
import cn.mingyuliu.halo.model.enums.Option;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *     博客安装拦截器
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
@Component
public class InstallInterceptor implements HandlerInterceptor {

    @Resource
    private OptionHolder optionHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (optionHolder.getBoolean(Option.IS_INSTALL)) {
            return true;
        }
        response.sendRedirect("/install");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o, Exception e) {

    }

}
