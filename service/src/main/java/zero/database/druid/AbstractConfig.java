package zero.database.druid;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * Created by jianqing.li on 2017/7/6.
 */
public abstract class AbstractConfig {

    protected SqlSessionFactory getSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource, String typeAliasesPackage,
                                                     String mapperLocations, String configLocation) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sessionFactory.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));
        return sessionFactory.getObject();
    }
}
