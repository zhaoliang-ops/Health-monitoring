package org.gv_data.hmt.utils;

import org.gv_data.hmt.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author zhaoliang
 * @desc 健康监控系统
 * @时间 2019-10-24 8:11
 */
public class UserUtils {
    public static User getCurrentHr() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
