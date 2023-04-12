package com.mrqinzh.framework.mybatis;

import com.mrqinzh.commons.mapper.BlogTypeAlias;
import com.mrqinzh.framework.mybatis.id.AutoIdGenerator;
import com.mrqinzh.framework.mybatis.id.IdGeneratorPlugin;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.mrqinzh")
public class MybatisConfiguration {

    // 实体主键字段
    private final static String ID_FIELD = "id";


    @Bean
    public IdGeneratorPlugin idGeneratorPlugin() {
        return new IdGeneratorPlugin();
    }

    @Bean
    public AutoIdGenerator autoIdGenerator() {
        return new AutoIdGenerator();
    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, IdGeneratorPlugin idGeneratorPlugin) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
////        bean.setTypeAliases(BlogTypeAlias.class);
//        bean.setMapperLocations();
//        bean.setTypeAliasesPackage("com.mrqinzh.common.entity");
//        bean.setPlugins(idGeneratorPlugin);
//
//        return bean.getObject();
//    }




}
