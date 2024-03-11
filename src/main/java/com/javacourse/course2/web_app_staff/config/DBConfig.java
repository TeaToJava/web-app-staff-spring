package com.javacourse.course2.web_app_staff.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.javacourse.course2.web_app_staff.repository", entityManagerFactoryRef = "sessionFactory")
@PropertySource("classpath:startup.properties")
@ComponentScan("com.javacourse.course2.web_app_staff")
public class DBConfig {

	@Value("${dataSourceClassName}")
	private String dataSourceClassName;
	@Value("${dataSource.user}")
	private String user;
	@Value("${dataSource.password}")
	private String password;
	@Value("${dataSource.databaseName}")
	private String databaseName;
	@Value("${dataSource.url}")
	private String url;
	@Value("${dataSource.portNumber}")
	private String portNumber;
	@Value("${dataSource.serverName}")
	private String serverName;

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public DataSource dataSource() {
		Properties props = new Properties();
		props.setProperty("dataSourceClassName", dataSourceClassName);
		props.setProperty("dataSource.user", user);
		props.setProperty("dataSource.password", password);
		props.setProperty("dataSource.databaseName", databaseName);
		HikariConfig hikariConfig = new HikariConfig(props);
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public Properties hibernateProperties() {
		Properties hibernateProp = new Properties();
		hibernateProp.put("hibernate.format_sql", true);
		hibernateProp.put("hibernate.use_sql_comments", true);
		hibernateProp.put("hibernate.show_sql", true);
		hibernateProp.put("hibernate.max_fetch_depth", 3);
		hibernateProp.put("hibernate.jdbc.batch_size", 10);
		hibernateProp.put("hibernate.jdbc.fetch_size", 50);
		return hibernateProp;
	}

	@Bean
	public SessionFactory sessionFactory() throws IOException {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setPackagesToScan("com.javacourse.course2.web_app_staff.model");
		factoryBean.setDataSource(dataSource());
		factoryBean.setHibernateProperties(hibernateProperties());
		factoryBean.afterPropertiesSet();
		return factoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws IOException {
		return new JpaTransactionManager(sessionFactory());
	}

	@Bean
	public SpringLiquibase springLiquibase(DataSource dataSource) {
		SpringLiquibase springLiquibase = new SpringLiquibase();
		springLiquibase.setDataSource(dataSource());
		springLiquibase.setDropFirst(false);
		springLiquibase.setChangeLog("classpath:db/changelog/db.changelog-master.yaml");
		return springLiquibase;
	}

}