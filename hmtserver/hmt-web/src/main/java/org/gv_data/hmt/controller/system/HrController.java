package org.gv_data.hmt.controller.system;

import org.gv_data.hmt.model.User;
import org.gv_data.hmt.model.RespBean;
import org.gv_data.hmt.model.Role;
import org.gv_data.hmt.service.UserService;
import org.gv_data.hmt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhaoliang
 * @desc 健康监控系统
 * @时间 2019-10-24 8:09
 */
@RestController
@RequestMapping("/system/hr")
public class HrController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @GetMapping("/")
    public List<User> getAllHrs(String keywords) {
        return userService.getAllHrs(keywords);
    }

    @PutMapping(value = "/")
    public RespBean updateHr(@RequestBody User user) {
        if (userService.updateHr(user) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PutMapping("/role")
    public RespBean updateHrRole(Integer hrid, Integer[] rids) {
        if (userService.updateHrRole(hrid, rids)) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteHrById(@PathVariable Integer id) {
        if (userService.deleteHrById(id) == 1) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
