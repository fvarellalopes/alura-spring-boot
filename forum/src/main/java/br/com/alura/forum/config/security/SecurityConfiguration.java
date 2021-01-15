package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoService autenticacaoService;
	
	// controle de acesso de usuarios
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	// permissões de urls
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//
				.antMatchers(HttpMethod.GET, "/topicos").permitAll()//
				.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()//
				.antMatchers(HttpMethod.POST, "/auth").permitAll()//
				.anyRequest().authenticated()//
				.and().csrf().disable()//
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
				;
	}

	// config de recursos estaticos
	@Override
	public void configure(WebSecurity web) throws Exception {
	}

}
