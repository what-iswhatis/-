package com.rocky.mybatisInterceptor;

import com.gmw.threadlocal.LocalUserUtil;
import com.rocky.model.BaseModel;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;



/**
 * Copyright (c) 2023, 61813631@qq.com All rights reserved.
 *
 * @author Spades--K
 */
//拦截器放入到IOC里才生效
@Component
@Intercepts({
        @Signature(
                //type：拦截的类型 （接口）
                //method：拦截的方法    method = ParameterHandler里的方法：这里我们选择setParameters
                //args：拦截的方法的参数
                type = ParameterHandler.class,
                method = "setParameters",
                args = {PreparedStatement.class}
        )
})
public class ParmeterPlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
        Object parameter = parameterHandler.getParameterObject();
        if (parameter instanceof BaseModel){
            BaseModel model = (BaseModel) parameter;
            model.setLastUpdateBy(LocalUserUtil.get().getNickName());
        }
        return invocation.proceed();
    }
}
