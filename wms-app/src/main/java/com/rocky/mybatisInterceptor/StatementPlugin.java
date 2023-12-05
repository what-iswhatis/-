package com.rocky.mybatisInterceptor;

import cn.hutool.core.collection.CollUtil;
import com.rocky.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;

import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class,Integer.class}
        )
})
// 使用Slf4j注解，以便使用Slf4j提供的日志功能
@Slf4j
public class StatementPlugin implements Interceptor {
    //需要监控的表
    private List<String> monitorTables = CollUtil.newArrayList("208_category","208_product");

    //需要监控的字段
    private List<String> monitorFields  = CollUtil.newArrayList("name","parentId");

    // 实现Interceptor接口的intercept方法，用于处理拦截逻辑
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 获取拦截的目标对象StatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        // 使用MyBatis提供的MetaObject工具类，简化对目标对象的操作
        // 使用SystemMetaObject工具类，通过传入目标对象statementHandler，创建一个MetaObject对象，以简化对目标对象的操作。
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        // 获取MappedStatement对象，包含了Mapper接口的信息

        /*metaObject.getValue("delegate.mappedStatement") :
         * 该方法返回一个MappedStatement对象，
         *是从StatementHandler对象的委托属性中提取MappedStatement对象，
         * 该对象包含有关映射语句的详细信息，包括关联的mapper接口方法。
         */

        MappedStatement mappedStatement =(MappedStatement) metaObject.getValue("delegate.mappedStatement");

        // 获取BoundSql对象，表示绑定了SQL语句的信息
        BoundSql boundSql = statementHandler.getBoundSql();

        // 获取SQL语句的参数对象
        Object parameterObject = boundSql.getParameterObject();

        // 获取SQL语句的参数映射信息
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        // 如果参数对象是BaseModel的实例
        if(parameterObject instanceof  BaseModel){
            // 获取SQL语句字符串
            String sql = boundSql.getSql();
            // 获取SQL语句的类型（INSERT、UPDATE等）
            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            // 如果是INSERT语句
            if(sqlCommandType.equals(SqlCommandType.INSERT)){
                //处理insert开始
                //增强, 动态添加 lastUpdateBy
                //解析sql 添加 字段
                Insert insert = (Insert) CCJSqlParserUtil.parse(sql);
                Stream<Column> lastUpdateBy1 = insert.getColumns().stream().filter(it -> it.getName(false).equals("lastUpdateBy"));

                /*
                lastUpdateBy1.findAny().isPresent() 用于检查是否存在名为 "lastUpdateBy" 的字段。
                如果存在，就说明已经进行过处理，无需再次处理，因此直接调用 invocation.proceed()
                继续执行原始的 SQL 语句，跳过当前拦截器的逻辑。
                这样可以避免重复添加相同的字段，确保在同一 SQL 语句中只对 "lastUpdateBy" 字段进行一次处理。
                * */
                if(lastUpdateBy1.findAny().isPresent()){
                    // 如果lastUpdateBy字段已存在，直接返回，不进行进一步的处理
                    return invocation.proceed();
                }

                insert.addColumns(new Column("lastUpdateBy"));
                //添加一个占位符
                ExpressionList itemsList = insert.getItemsList(ExpressionList.class);
                itemsList.getExpressions().add(new JdbcParameter());

                //添加映射关系
                List<ParameterMapping> lastUpdateByMapping = parameterMappings.stream().filter(it -> it.getProperty().equals("lastUpdateBy")).collect(Collectors.toList());
                // 如果映射关系列表中不存在 lastUpdateBy 的映射关系，则添加映射关系
                /*
                * lastUpdateByMapping.size() == 0 ：如果列表大小为0，表示在参数映射关系中没有找到“lastUpdateBy”的映射关系，那么就需要添加
                * */
                if(lastUpdateByMapping.size() == 0){
                    /*
                    * 构建一个 ParameterMapping 对象，
                    * 该对象包含了参数名称、参数类型、参数映射关系等信息。
                    * ParameterMapping.Builder: 创建一个新的参数映射对象
                    * mappedStatement.getConfiguration()：来获取全局的配置信息，然后传递给 ParameterMapping.Builder 的构造函数，以创建新的参数映射对象。
                    * property：“lastUpdateBy” 表示指定属性名
                    * String.class：表示指定类型为String
                    * */
                    ParameterMapping lastUpdateBy = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "lastUpdateBy", String.class).build();
                    //将新的参数映射关系添加给参数映射列表 parameterMappings 中
                    parameterMappings.add(lastUpdateBy);
                }

                // 修改原始的SQL语句
                metaObject.setValue("delegate.boundSql.sql",insert.toString());
                //处理insert结束
            }

            else if (sqlCommandType.equals(SqlCommandType.UPDATE)){
                //处理update开始
                //增强, 动态添加 lastUpdateBy字段
                //解析sql 添加 字段
                Update update = (Update) CCJSqlParserUtil.parse(sql);
                update.getUpdateSets().add(new UpdateSet(new Column("lastUpdateBy"),new JdbcParameter()));
                //添加映射关系
                ParameterMapping lastUpdateBy = new ParameterMapping.Builder(mappedStatement.getConfiguration(), "lastUpdateBy", String.class).build();
                parameterMappings.add(parameterMappings.size()-1,lastUpdateBy);
                // 修改原始的SQL语句
                metaObject.setValue("delegate.boundSql.sql",update.toString());
                //处理update结束
            }

        }
        // 继续执行原始的SQL语句
        return invocation.proceed();
    }
}
