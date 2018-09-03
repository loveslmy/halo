package cn.mingyuliu.halo.web.interceptor;

import cn.mingyuliu.halo.model.dto.HaloConst;
import cn.mingyuliu.halo.model.enums.OptionEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *     博客初始化拦截器
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/1/28
 */
@Component
public class InstallInterceptor implements HandlerInterceptor {

    private boolean install = false;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
/*        if (!install) {
            if (StringUtils.equals(Boolean.TRUE.toString(),
                    HaloConst.OPTIONS.get(OptionEnum.IS_INSTALL.getValue()))) {
                install = true;
                return true;
            }
        }
        response.sendRedirect("/install");*/
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
