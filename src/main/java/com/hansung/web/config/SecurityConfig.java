package com.hansung.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hansung.web.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserService userService;
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		  auth.authenticationProvider(authenticationProvider(userService));
	}

	@Override
	public void configure(WebSecurity web) { // main/resources/static 하위 폴더 정적 리소스 접근 설정
		web.ignoring().antMatchers("/css/**", "/js/**", "/images/**", "/adminlte/**", "/toastui/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/","/user/signup", "/webjars/**").permitAll()
				.antMatchers("/csrf/*").hasAnyAuthority("ROLE_ADMIN","ROLE_MANAGER")
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/user/signin")
           		.loginProcessingUrl("/user/signinProcess")
				.failureUrl("/user/signin?error")
				//.failureHandler(FailureHandler())
				.defaultSuccessUrl("/user/signinSuccess")
				.permitAll()
			.and()
				.logout()
				.logoutSuccessUrl("/")
				.deleteCookies("JSESSIONID")
			.and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied") //권한없는 URL 접속시 403에러처리
			.and()
				.sessionManagement()
				.maximumSessions(1) //같은 아이디로 1명만 로그인
	        	.maxSessionsPreventsLogin(true) //false :신규 로그인 허용, 기존 사용자는 세션 아웃  true: 이미 로그인한 세션이있으면 로그인 불가 
	        	.expiredUrl("/user/signin"); //세션 아웃되면 이동할 url
	}
	
}
