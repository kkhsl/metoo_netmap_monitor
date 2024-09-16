package com.metoo.monitor.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.metoo.monitor.core.vo.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyAccessControlFilter extends AccessControlFilter {


    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal() != null){
            if(subject.isAuthenticated()){
                return true;
            }else{
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().print(JSONObject.toJSONString(new Result(401,"Log in")));
                return false;
            }
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSONObject.toJSONString(new Result(401,"Log in")));
        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return false;
    }
}

