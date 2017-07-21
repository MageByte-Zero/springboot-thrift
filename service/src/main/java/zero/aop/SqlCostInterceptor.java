package zero.aop;


import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

/**
 * Created by sari.huang on 2017/6/29.
 * AOP方式实现截获SQL语句,输出实际运行SQL及运行时间
 * 暂时只对query,update型SQL截获
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
})
public class SqlCostInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(SqlCostInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        long startTime = System.currentTimeMillis();
        StatementHandler statementHandler = (StatementHandler) target;
        try {
            return invocation.proceed();
        } finally {
            BoundSql boundSql = statementHandler.getBoundSql();
//      获得完整内容的sql语句
            String realRunnedSql = getRealRunningSQL(boundSql.getSql(), boundSql.getParameterObject(), boundSql.getParameterMappings());
            long endTime = System.currentTimeMillis();
            long costTime = endTime - startTime;
            logger.info("============>SQL：[{}, 执行耗时: {} ms]==========", realRunnedSql, costTime);
        }
    }

    public String getRealRunningSQL(String sql, Object parameterObject, List<ParameterMapping> parameterMappingList) {

        if (StringUtils.isEmpty(sql)) {
            return "No Sql Running";
        }

        sql = formatSQL(sql);

        if (parameterObject == null || parameterMappingList == null || parameterMappingList.size() == 0) {
            return sql;
        }

        String sqlWithoutReplacePlaceholder = sql;
        try {
            if (parameterMappingList != null) {
                Class<?> parameterObjectClass = parameterObject.getClass();
                if (isStrictMap(parameterObjectClass)) {
                    DefaultSqlSession.StrictMap<Collection<?>> strictMap = (DefaultSqlSession.StrictMap<Collection<?>>) parameterObject;

                    if (isList(strictMap.get("list").getClass())) {
                        sql = handleListParameter(sql, strictMap.get("list"));
                    }
                } else if (isMap(parameterObjectClass)) {
                    Map<?, ?> paramMap = (Map<?, ?>) parameterObject;
                    sql = handleMapParameter(sql, paramMap, parameterMappingList);
                } else {
                    sql = handleCommonParameter(sql, parameterMappingList, parameterObjectClass, parameterObject);
                }
            }
        } catch (Exception e) {
            logger.info("----------RealRunningSql Got Error-------------");
            logger.info("Error is :" + e);
            logger.info("------------------------------------------------");
            return sqlWithoutReplacePlaceholder;
        }
        return sql;
    }

    private String formatSQL(String sql) {
        return sql.replaceAll("[\\s\n ]+", " ");
    }

    private String handleListParameter(String sql, Collection<?> col) {
        if (col != null && col.size() != 0) {
            for (Object obj : col) {
                String value = null;
                Class<?> objClass = obj.getClass();
                if (isPrimitiveOrPrimitiveWrapper(objClass)) {
                    value = obj.toString();
                } else if (objClass.isAssignableFrom(String.class)) {
                    value = "\"" + obj.toString() + "\"";
                }
                sql = sql.replaceFirst("\\?", value);
            }
        }
        return sql;
    }

    private String handleMapParameter(String sql, Map<?, ?> paramMap, List<ParameterMapping> parameterMappingList) {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            Object propertyName = parameterMapping.getProperty();
            Object propertyValue = paramMap.get(propertyName);
            if (propertyValue != null) {
                if (propertyValue.getClass().isAssignableFrom(String.class)) {
                    propertyValue = "\"" + propertyValue + "\"";
                }
                sql = sql.replaceFirst("\\?", propertyValue.toString());
            }
        }
        return sql;
    }

    private String handleCommonParameter(String sql, List<ParameterMapping> parameterMappingList, Class<?> parameterObjectClass,
                                         Object parameterObject) throws Exception {
        for (ParameterMapping parameterMapping : parameterMappingList) {
            String propertyValue = null;
            if (isPrimitiveOrPrimitiveWrapper(parameterObjectClass)) {
                propertyValue = parameterObject.toString();
            } else {
                String propertyName = parameterMapping.getProperty();
                Map<?, ?> maps;
                if (Map.class.isAssignableFrom(parameterObject.getClass())) {
                    maps = (Map<?, ?>) parameterObject;
                    propertyValue = String.valueOf(maps.get(propertyName));

                    if (parameterMapping.getJavaType().isAssignableFrom(String.class)) {
                        propertyValue = "\"" + propertyValue + "\"";
                    }
                } else {
                    Field[] fields = parameterObject.getClass().getDeclaredFields();
                    Map<String, Object> objMap = new HashMap<>();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        objMap.put(field.getName(), field.get(parameterObject));
                    }
                    propertyValue = String.valueOf(objMap.get(propertyName));

                    if (parameterMapping.getJavaType().isAssignableFrom(String.class)) {
                        propertyValue = "\"" + propertyValue + "\"";
                    }
                }

            }
            sql = sql.replaceFirst("\\?", propertyValue);
        }
        return sql;

    }


    private boolean isPrimitiveOrPrimitiveWrapper(Class<?> parameterObjectClass) {
        String classType = parameterObjectClass.getName();
        boolean isPrimitiveOrPrimitiveWrapper = classType.contains("Byte") || classType.contains("Short") ||
                classType.contains("Integer") || classType.contains("Long") ||
                classType.contains("Double") || classType.contains("Float") ||
                classType.contains("Character") || classType.contains("Boolean") || classType.contains("String");
        return isPrimitiveOrPrimitiveWrapper;

    }

    private boolean isStrictMap(Class<?> parameterObjectClass) {
        return parameterObjectClass.isAssignableFrom(DefaultSqlSession.StrictMap.class);
    }


    private boolean isList(Class<?> clazz) {
        Class<?>[] interfaceClasses = clazz.getInterfaces();
        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(List.class)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMap(Class<?> parameterObjectClass) {
        Class<?>[] interfaceClasses = parameterObjectClass.getInterfaces();
        for (Class<?> interfaceClass : interfaceClasses) {
            if (interfaceClass.isAssignableFrom(Map.class)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
