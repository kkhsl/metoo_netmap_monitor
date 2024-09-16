package com.metoo.monitor.core.manager.netmap;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.metoo.monitor.core.dto.LicenseDto;
import com.metoo.monitor.core.entity.License;
import com.metoo.monitor.core.service.ILicenseService;
import com.metoo.monitor.core.utils.AesEncryptUtils;
import com.metoo.monitor.core.utils.ResponseUtil;
import com.metoo.monitor.core.utils.aes.Aes;
import com.metoo.monitor.core.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author HKK
 * @version 1.0
 * @date 2024-07-02 15:47
 */
@RequestMapping("monitor/netmap")
@RestController
public class NetmapManagerController {

    @Autowired
    private AesEncryptUtils aesEncryptUtils;
    @Autowired
    private Aes aes;
    @Autowired
    private ILicenseService licenseService;


    @PostMapping("/getLicense")
    public Object getLicense(@RequestBody(required=false) NetMapLicense instance){
        if(instance.getSystemSN() != null){
            try {

                instance.setUnit_id(UUID.randomUUID().toString());

                String lincense = this.aesEncryptUtils.encrypt(JSONObject.toJSONString(instance));

                instance.setLicense(lincense);

                return ResponseUtil.ok(lincense);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.error("申请码错误");
            }
        }
        return null;
    }


    @PostMapping("/list")
    public Object list(@RequestBody(required=false) LicenseDto dto){
        if(dto == null){
            dto = new LicenseDto();
        }
        Page<License> page = this.licenseService.selectObjConditionQuery(dto);
        if(page.getResult().size() > 0){
            return ResponseUtil.ok(new PageInfo<License>(page));
        }
        return  ResponseUtil.ok();
    }

    @PostMapping("/license")
    public Object license(@RequestBody(required=false) License instance){
        if(instance.getSystemSN() != null){
            try {
                String lincense = this.aesEncryptUtils.encrypt(JSONObject.toJSONString(instance));

                instance.setLicense(lincense);

                try {
                    this.licenseService.save(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ResponseUtil.ok(lincense);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.error("申请码错误");
            }
        }
        return null;
    }

    @RequestMapping("/system")
    public Object systemInfo(HttpServletRequest request){
        try {
            HttpSession session = request.getSession();
            System.out.println("session：" + session.getId());
            Cookie[] cookies  = request.getCookies();
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("JSESSIONID")){
                    System.out.println("cookieName：" + cookie.getName() + " cookieValue："+cookie.getValue());
                }
            }
            return "123";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/delete")
    public Object delete(String ids){
        if(ids != null && !ids.equals("")){
            for (String id : ids.split(",")){
                Map params = new HashMap();
                params.put("id", Long.parseLong(id));
                List<License> licenses = this.licenseService.selectObjByMap(params);
                if(licenses.size() > 0){
                    License license = licenses.get(0);
                    try {
                        this.licenseService.delete(Long.parseLong(id));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return ResponseUtil.badArgument("删除失败");
                    }
                }else{
                    return ResponseUtil.badArgument();
                }
            }

            return ResponseUtil.ok();
        }
        return ResponseUtil.badArgument();
    }

}
