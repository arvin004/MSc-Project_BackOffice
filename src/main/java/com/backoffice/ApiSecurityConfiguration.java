package com.backoffice;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import com.backoffice.entity.Client;
import com.backoffice.repository.ClientRepository;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;
	 
	 protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/api/**")
				.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
							throws IOException, ServletException {
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
					}
				})
				.and()
				.authorizeRequests()
					.antMatchers("/api/client/login")
						.permitAll()
					.anyRequest()
						.authenticated()
						.and()
						.rememberMe()
							.key("backOfficeKey")
							.rememberMeServices(rememberMeServices());;
			
			http
			  .headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
		}
	 
	 @Bean
	public RememberMeServices rememberMeServices() {
		// Key must be equal to rememberMe().key()
		TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("backOfficeKey", userDetailsService);
		rememberMeServices.setTokenValiditySeconds(31536000); // 1 year
		rememberMeServices.setCookieName("remember_me_backOfficeKey");
		rememberMeServices.setAlwaysRemember(true);
		return rememberMeServices;
	}
	 
	 @Bean
	public UserDetailsService createUserDetailsService() {
	    return new UserDetailsService() {
	    	@Override
	    	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    		Client client = clientRepository.findByUsername(username);
	    		if (client == null) {
	    			throw new UsernameNotFoundException("Account does not exist with this email");
	    		}
	    		
	    		List<GrantedAuthority> auth = AuthorityUtils.createAuthorityList(client.getUsername().toString());
	    		
	    		return new BackOfficeUser(client.getId(), username, client.getPassword(), true, auth);
	    	}
	    };
	}
}