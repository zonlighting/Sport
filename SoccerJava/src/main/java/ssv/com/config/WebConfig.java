package ssv.com.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.ibatis.session.SqlSessionFactory;
import org.modelmapper.ModelMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@MapperScan("ssv.com")
@Configuration
public class WebConfig {

	@Bean
	public DriverManagerDataSource dataSource() throws IOException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		Properties properties = new Properties();
		InputStream user_props = this.getClass().getResourceAsStream("/application.properties");
		properties.load(user_props);
		dataSource.setDriverClassName(properties.getProperty("spring.datasource.driver-class-name"));
		dataSource.setUrl(properties.getProperty("spring.datasource.url"));
		dataSource.setUsername(properties.getProperty("spring.datasource.username"));
		dataSource.setPassword(properties.getProperty("spring.datasource.password"));
		return dataSource;
	}

	@Bean
	public SqlSessionFactory masterSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		Resource configLocation = new ClassPathResource("mybatis-config.xml");
		factoryBean.setDataSource(dataSource());
		factoryBean.setConfigLocation(configLocation);
		return factoryBean.getObject();
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList("http://localhost:8080","http://localhost:8081","http://192.168.10.174:8080"));
		config.addAllowedMethod(HttpMethod.DELETE);
		config.addAllowedMethod(HttpMethod.POST);
		config.addAllowedMethod(HttpMethod.GET);

		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("nguyencongtoan14051997@gmail.com");
		mailSender.setPassword("toansech123");
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	@Bean
	public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowUrlEncodedSlash(true);
	    return firewall;
	}
}
