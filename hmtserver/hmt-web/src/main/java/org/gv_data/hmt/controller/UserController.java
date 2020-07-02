package org.gv_data.hmt.controller;

import org.gv_data.hmt.config.FastDFSUtils;
import org.gv_data.hmt.model.User;
import org.gv_data.hmt.model.RespBean;
import org.gv_data.hmt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author zhaoliang
 * @desc 健康监控系统
 * @时间 2020-03-01 13:07
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Value("${fastdfs.nginx.host}")
    String nginxHost;

    @GetMapping("/hr/info")
    public User getCurrentHr(Authentication authentication) {
        return ((User) authentication.getPrincipal());
    }

    @PutMapping("/hr/info")
    public RespBean updateHr(@RequestBody User user, Authentication authentication) {
        if (userService.updateHr(user) == 1) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PutMapping("/hr/pass")
    public RespBean updateHrPasswd(@RequestBody Map<String, Object> info) {
        String oldpass = (String) info.get("oldpass");
        String pass = (String) info.get("pass");
        Integer hrid = (Integer) info.get("hrid");
        if (userService.updateHrPasswd(oldpass, pass, hrid)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @PostMapping("/hr/userface")
    public RespBean updateHrUserface(MultipartFile file, Integer id,Authentication authentication) {
        String fileId = FastDFSUtils.upload(file);
        String url = nginxHost + fileId;
        if (userService.updateUserface(url, id) == 1) {
            User user = (User) authentication.getPrincipal();
            user.setUserface(url);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), authentication.getAuthorities()));
            return RespBean.ok("更新成功!", url);
        }
        return RespBean.error("更新失败!");
    }

}