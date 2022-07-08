package com.mall.intercepter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mall.model.AbstractQuery;
import com.mall.model.PageXInfo;
import com.mall.threadlocal.PageXThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

@Slf4j
public class MapperInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        //当前拦截的方法
        Method method = invocation.getMethod();
        //当前拦截方法的参数
        Object[] arguments = invocation.getArguments();
        //获取第一个参数，因为我们Mapper对应的方法只有一个参数
        Object arg0 = arguments[0];
        // 判断是否需要分页，如果是分页，则将数据存储到ThreadLocal中，见下面代码
        boolean isPage = false;
        //判断是否是Query对象
        if (arg0 instanceof AbstractQuery) {
            //强转 AbstractQuery， 目的是获取参数，是否要分页
            AbstractQuery query = (AbstractQuery) arg0;
            Integer pageIndex = query.getPageNum(); //参数，第几页。
            Integer pageSize = query.getPageSize();// 参数，每页多少条。

            if (ObjectUtil.isNotEmpty(pageIndex) && ObjectUtil.isNotEmpty(pageSize)) {
                // 启用分页，查询第pageIndex页，每页pageSize条
                PageHelper.startPage(pageIndex, pageSize);
                isPage = true;
            }
        }
        Object value = invocation.proceed();
        // 将分页数据存储到TheadLocal中
        if (isPage) {
            Page page = (Page) value;
            PageXThreadLocal.set(new PageXInfo(page.getPages(), page.getTotal()));
        }
        log.info("method:{},args:{},value:{}", method.getName(), JSONUtil.toJsonStr(arguments), value);
        return value;
    }
}