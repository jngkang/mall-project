package com.mall.threadlocal;

import com.mall.model.CurrentUser;

/**
 * 使用ThreadLocal临时保存Token中的数据，达成随处可用效果
 *
 * @author JngKang
 * @date 2022-07-01 11:35
 */
public class CurrentThreadLocal {

    private static ThreadLocal<CurrentUser> threadLocal = new ThreadLocal<>();

    public static void set(CurrentUser currentUser) {
        threadLocal.set(currentUser);
    }

    public static CurrentUser get() {
        CurrentUser currentUser = threadLocal.get();
        if (currentUser == null) {
            currentUser = new CurrentUser(0L, "default", "default");
        }
        return currentUser;
    }

}
