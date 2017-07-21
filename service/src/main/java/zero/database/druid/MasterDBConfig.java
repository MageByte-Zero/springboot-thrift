package zero.database.druid;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 *
 * 使用该方式配置数据源与mybatis相关配置更加灵活,。
 * 多数据源配置的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean」
 * Created by jianqing.li on 2017/7/5.
 */
@Configuration
@MapperScan(basePackages = {"com.zero.mapper.master"},
        sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDBConfig extends AbstractConfig {

    @Value("${mybatis.master.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis.master.mapper-locations}")
    private String mapperLocations;

    @Value("${mybatis.master.config-location}")
    private String configLocation;


    @Bean(name = "masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "druid.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 数据库所用的sessionfactory
     *
     * @param dataSource
     * @return SqlSessionFactory mysql会话工厂
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(
            @Qualifier("masterDataSource") DataSource dataSource)
            throws Exception {
        return getSqlSessionFactory(dataSource, typeAliasesPackage, mapperLocations, configLocation);
    }

}
