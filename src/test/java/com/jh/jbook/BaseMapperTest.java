package com.jh.jbook;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

public class BaseMapperTest {

    @Autowired
    private DataSource dataSource;

    private static SqlSessionFactory sqlSessionFactory;


    public SqlSession getSqlSession(){
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:/mapping/*.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bean.setDataSource(dataSource);
        try {
            sqlSessionFactory = bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sqlSessionFactory.openSession();
    }

}
