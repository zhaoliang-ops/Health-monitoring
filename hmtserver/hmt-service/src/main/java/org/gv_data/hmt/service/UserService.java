package org.gv_data.hmt.service;

import org.gv_data.hmt.mapper.UserMapper;
import org.gv_data.hmt.mapper.UserRoleMapper;
import org.gv_data.hmt.model.User;
import org.gv_data.hmt.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhaoliang
 * @desc 健康监控系统
 * @时间 2019-09-20 8:21
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        user.setRoles(userMapper.getHrRolesById(user.getId()));
        return user;
    }

    public List<User> getAllHrs(String keywords) {
        return userMapper.getAllHrs(UserUtils.getCurrentHr().getId(),keywords);
    }

    public Integer updateHr(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        userRoleMapper.deleteByHrid(hrid);
        return userRoleMapper.addRole(hrid, rids) == rids.length;
    }

    public Integer deleteHrById(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public List<User> getAllHrsExceptCurrentHr() {
        return userMapper.getAllHrsExceptCurrentHr(UserUtils.getCurrentHr().getId());
    }

    public Integer updateHyById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    public boolean updateHrPasswd(String oldpass, String pass, Integer hrid) {
        User user = userMapper.selectByPrimaryKey(hrid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldpass, user.getPassword())) {
            String encodePass = encoder.encode(pass);
            Integer result = userMapper.updatePasswd(hrid, encodePass);
            if (result == 1) {
                return true;
            }
        }
        return false;
    }

    public Integer updateUserface(String url, Integer id) {
        return userMapper.updateUserface(url, id);
    }
}
