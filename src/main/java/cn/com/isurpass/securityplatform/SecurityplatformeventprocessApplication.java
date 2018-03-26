package cn.com.isurpass.securityplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "cn.com.isurpass.securityplatform.dao")
@EntityScan(basePackages = "cn.com.isurpass.securityplatform.domain")
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan
public class SecurityplatformeventprocessApplication {
	
	@SuppressWarnings("unused")
	@Autowired
	private SpringUtil springutil;  //ensure that SpringUtil will be initialize first ;
	
	public static void main(String[] args) 
	{
		SpringApplication.run(SecurityplatformeventprocessApplication.class, args);
	}
}
