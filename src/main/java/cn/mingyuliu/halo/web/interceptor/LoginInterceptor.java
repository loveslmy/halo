package cn.mingyuliu.halo.web.interceptor;

import cn.mingyuliu.halo.model.dto.HaloConst;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *     后台登录控制器
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object obj = request.getSession().getAttribute(HaloConst.USER_SESSION_KEY);
        // 如果user不为空则放行
        if (null != obj) {
            return true;
        }
        //否则拦截并跳转到登录
        response.sendRedirect("/admin/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

}
