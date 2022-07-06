package com.mall.threadlocal;

import com.mall.PageXInfo;

/**
 * 使用ThreadLocal临时保存Token中的数据，达成随处可用效果
 *
 * @author JngKang
 * @date 2022-07-01 11:35
 */
public class PageXThreadLocal {

    private static ThreadLocal<PageXInfo> threadLocal = new ThreadLocal<>();

    public static void set(PageXInfo pageXInfo) {
        threadLocal.set(pageXInfo);
    }

    public static PageXInfo get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
