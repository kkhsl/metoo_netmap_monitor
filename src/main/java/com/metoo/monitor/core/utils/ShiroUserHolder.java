package com.metoo.monitor.core.utils;

import com.metoo.monitor.core.entity.User;
import com.metoo.monitor.core.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

@Component
public class ShiroUserHolder {

    public static User currentUser(){
        if (SecurityUtils.getSubject() != null){
            Subject subject = SecurityUtils.getSubject();
            if(subject.getPrincipal() != null && subject.isAuthenticated()){
                String userName = SecurityUtils.getSubject().getPrincipal().toString();
                IUserService userService = (IUserService) ApplicationContextUtils.getBean("userServiceImpl");
                User user = userService.findByUserName(userName);
                if(user != null){
                    return user;
                }
            }
        }
        return null;
    }

}
