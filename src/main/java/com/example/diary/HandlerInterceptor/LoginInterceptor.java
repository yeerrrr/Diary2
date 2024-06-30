package com.example.diary.HandlerInterceptor;

import com.example.diary.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {
    /*
    * 在请求处理之前进行调用（Controller方法调用之前）
    */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Account account1 = (Account) request.getSession().getAttribute("account"); //获取请求中的信息
        if(account1 == null) {
            response.sendRedirect("/login");
            System.out.println("未通过");
            return false;
        }else{
            System.out.println("通过");
            return true;
        }
    }
}
