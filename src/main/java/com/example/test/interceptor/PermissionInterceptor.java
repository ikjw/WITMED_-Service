package com.example.test.interceptor;

import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * 登录检查与权限控制
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {
    envConfig config;
    public PermissionInterceptor(envConfig config){
        this.config = config;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof  HandlerMethod){
            HandlerMethod method = ((HandlerMethod) handler);
            response.setCharacterEncoding("UTF-8");
            Class<?> clazz = method.getBeanType();
            Class<?>[] classes = clazz.getInterfaces();
            if(Arrays.asList(classes).contains(IPermission.class)){
                HttpSession session = request.getSession();
                String username = (String) session.getAttribute("UID");
                Integer role = (Integer) session.getAttribute("type");
                if(username == null || role == null){
                    PrintWriter writer = response.getWriter();
                    RespResult<?> result = new RespResult<>(BaseRespResultCode.LOGIN_TIMEOUT,"",config.getEnv(),"");
                    ObjectMapper objectMapper = new ObjectMapper();
                    writer.println(objectMapper.writeValueAsString(result));
                    return false;
                }
                IPermission permission = (IPermission) clazz.getConstructor().newInstance();
                if(permission.hasPermission(username, role, request.getRequestURI())){
                    return true;
                }else{
                    PrintWriter writer = response.getWriter();
                    RespResult<?> result = new RespResult<>(BaseRespResultCode.PERMISSION_DENIED,"",config.getEnv(),"");
                    ObjectMapper objectMapper = new ObjectMapper();
                    writer.println(objectMapper.writeValueAsString(result));
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
