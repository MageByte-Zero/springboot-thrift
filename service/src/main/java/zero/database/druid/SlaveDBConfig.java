package zero.database.druid;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *
 * 使用该方式配置数据源与mybatis相关配置更加灵活
 * Created by jianqing.li on 2017/7/5.
 */
@Configuration
@MapperScan(basePackages = {"com.zero.mapper.slave"},
        sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveDBConfig extends AbstractConfig {

    @Value("${mybatis.slave.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis.slave.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis.slave.config-location}")
    private String configLocation;


    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "druid.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 数据库所用的sessionfactory
     *
     * @param dataSource
     * @return SqlSessionFactory mysql会话工厂
     * @throws Exception
     */
    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(
            @Qualifier("slaveDataSource") DataSource dataSource)
            throws Exception {
        return getSqlSessionFactory(dataSource, typeAliasesPackage, mapperLocations, configLocation);
    }


}
