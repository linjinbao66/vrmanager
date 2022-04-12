package manager.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义token拦截器
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {

        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("utf-8");

        if (request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String token = request.getHeader("admin-token");

        boolean verify = TokenUtil.verify(token);
        if (verify){
            String sn = TokenUtil.getUserName(token);
            Integer roleId = TokenUtil.getRoleid(token);
            request.setAttribute("sn",sn);//存放的req中
            request.setAttribute("roleId",roleId);//存放的req中
            return true;
        }
        log.info("拦截器拦截, {}",request.getRequestURL());
        response.sendRedirect("/login.html");
        return false;
    }
}