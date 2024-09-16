package com.metoo.monitor.core.manager;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.util.StringUtil;
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
import java.util.*;

@RequestMapping("monitor")
@RestController
public class LicenManagerController {

    @Autowired
    private AesEncryptUtils aesEncryptUtils;
    @Autowired
    private ILicenseService licenseService;

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
//        if(instance.getSystemSN() != null){
//            try {
//
//                instance.setUnit_id(UUID.randomUUID().toString());
//
//                String lincense = this.aesEncryptUtils.encrypt(JSONObject.toJSONString(instance));
//
//                instance.setLicense(lincense);
//
//                try {
//                    this.licenseService.save(instance);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return ResponseUtil.ok(lincense);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseUtil.error("申请码错误");
//            }
//        }
//        return null;


            try {



                String encryptCode = this.aesEncryptUtils.decrypt(instance.getSystemSN());

                Map<String, String> encryptApplicant = (Map<String, String>) JSONObject.parseObject(encryptCode, Map.class);

                String systemSN = encryptApplicant.get("code");

                String unit_id = "";
                if(StringUtil.isEmpty(encryptApplicant.get("unit_id"))){
                    unit_id = UUID.randomUUID().toString();
                }else{
                    unit_id = encryptApplicant.get("unit_id");
                }

                String city = encryptApplicant.get("city");

                String area = encryptApplicant.get("area");

                String unit = encryptApplicant.get("unit");

                instance.setSystemSN(systemSN);
                instance.setCity(city);
                instance.setUnit(unit);
                instance.setArea(area);
                instance.setUnit_id(unit_id);


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

    @PostMapping("/license/applicant")
    public Object generateApplicantCode(@RequestBody(required=false) License instance){
        if(instance.getApplicantCode() != null){
            try {

                instance.setUnit_id(UUID.randomUUID().toString());

                String encryptCode = this.aesEncryptUtils.decrypt(instance.getApplicantCode());

                Map<String, String> encryptApplicant = (Map<String, String>) JSONObject.parseObject(encryptCode, Map.class);

                String systemSN = encryptApplicant.get("code");

                String city = encryptApplicant.get("city");

                String area = encryptApplicant.get("area");

                String unit = encryptApplicant.get("unit");

                instance.setSystemSN(systemSN);
                instance.setCity(city);
                instance.setUnit(unit);
                instance.setArea(area);


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
