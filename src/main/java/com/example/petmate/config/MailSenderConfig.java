package com.example.petmate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

	@Value("${spring.mail.host}")
	private String getHost;

	@Value("${spring.mail.port}")
	private String getPort;

	@Value("${spring.mail.username}")
	private String getUsername;

	@Value("${spring.mail.password}")
	private String getPassword;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost(getHost);
		javaMailSender.setPort(Integer.parseInt(getPort));

		javaMailSender.setUsername(getUsername);
		javaMailSender.setPassword(getPassword);

		Properties props = javaMailSender.getJavaMailProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.required", "true");

		return javaMailSender;
	}
}
