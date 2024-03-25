package com.deinsoft.puntoventa.framework.security;

import com.deinsoft.puntoventa.business.repository.ActCajaTurnoRepository;
import com.deinsoft.puntoventa.business.repository.SegPermisoRepository;
import com.deinsoft.puntoventa.framework.security.repository.SecUserRepository;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
    @Order(1)
    @Configuration
    public static class WebSecurityRest extends WebSecurityConfigurerAdapter {
        @Autowired
	private LoginSuccessHandler successHandler;
        
	private final ObjectMapper objectMapper;
	
        @Autowired
        private SecUserRepository secUserRepository;
        
        @Autowired
        private ActCajaTurnoRepository actCajaTurnoRepository;
        
        @Autowired
	private JpaUserDetailsService userDetailsService;
        
        @Autowired
        private SegPermisoRepository segPermisoRepository;
        
	public WebSecurityRest(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	/*@Bean
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}*/
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/*@Bean
	public GrantProfileRepository gprepository() {
		return new GrantProfileRepository();
	}*/

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		/*
		 * 1. Se desactiva el uso de cookies
		 * 2. Se activa la configuración CORS con los valores por defecto
		 * 3. Se desactiva el filtro CSRF
		 * 4. Se indica que el login no requiere autenticación
		 * 5. Se indica que el resto de URLs esten securizadas
		 */
		httpSecurity
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        //.csrf().disable().authorizeRequests()
//			.cors().configurationSource(corsConfigurationSource()).and()
			.cors().and()
			.csrf().disable()
			//.antMatcher("/api/**")
                        .authorizeRequests()
				.antMatchers(HttpMethod.POST,"/login","/api/business/register-new-user").permitAll()
				.antMatchers(HttpMethod.GET,"/api/business/get-data-sunat").permitAll()
                                .antMatchers("/swagger-ui/**","/v3/**", "/resources/**"
                                        ,"/public/**", "/resources/**", "/resources/temp/**","/resources/public/**"
                                        ,"/api/business/seg-usuario/get-recover-password","/api/business/seg-usuario/recover-password").permitAll()
//                                .antMatchers(HttpMethod.GET,"/login").permitAll()
//                                .antMatchers("/ventas-backend/login").permitAll() 
			.anyRequest().authenticated()
//                        .and()
//                            .formLogin()
//                                .successHandler(successHandler)
//                                .loginPage("/login")
//                            .permitAll()
//                        .and()
//
//                        .logout().permitAll()
                        //.and().formLogin().permitAll()
                        .and().addFilter((Filter) new JWTAuthenticationFilter(authenticationManager(),
                                secUserRepository,actCajaTurnoRepository,segPermisoRepository))
					.addFilter(new JWTAuthorizationFilter(authenticationManager()))
                        ;
//					.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
	}
        @Bean
	public CorsFilter corsFilter() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://deinsoft-la.com/","http://66.29.149.124:8080/deinsoft-cloud/",
                        "http://localhost:4200/","http://localhost:57784/","http://127.0.0.1:5500/"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"Accept", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers","Content-Disposition"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
				"Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials","Content-Disposition"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
//	@Bean
//    CorsConfigurationSource corsConfigurationSource() {
//            CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/,https://deinsoft-cloud.herokuapp.com/"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        configuration.setAllowCredentials(true);
//        //the below three lines will add the relevant CORS response headers
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Se define la clase que recupera los usuarios y el algoritmo para procesar las passwords
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	private void loginSuccessHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException {

		response.setStatus(HttpStatus.OK.value());
//		objectMapper.writeValue(response.getWriter(), "Yayy you logged in!");
	}
	
	private void logoutSuccessHandler(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException {

		response.setStatus(HttpStatus.OK.value());
//		objectMapper.writeValue(response.getWriter(), "Bye!");
	}
    }
//    @Order(2)
//    @Configuration
//    public static class WebSecurityRestWeb extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//	private LoginSuccessHandler successHandler;
//	
//	@Autowired
//	private JpaUserDetailsService userDetailsService;
//	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		http.authorizeRequests().
//                        antMatchers("/ventas-backend/css/**", "/ventas-backend/js/**", "/ventas-backend/images/**", "/listar","/ventas-backend/login").permitAll()
//                        
//		/*.antMatchers("/ver/**").hasAnyRole("USER")*/
//		/*.antMatchers("/uploads/**").hasAnyRole("USER")*/
//		/*.antMatchers("/form/**").hasAnyRole("ADMIN")*/
//		/*.antMatchers("/eliminar/**").hasAnyRole("ADMIN")*/
//		/*.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
//		.anyRequest().authenticated()
//		.and()
//		    .formLogin()
//		        .successHandler(successHandler)
//		        .loginPage("/login")
//		    .permitAll()
//		.and()
//                
//		.logout().permitAll()
//		.and()
//		.exceptionHandling().accessDeniedPage("/error_403");
//
//	}
//
//	@Autowired
//	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
//	{
//		build.userDetailsService(userDetailsService)
//		.passwordEncoder(passwordEncoder);
//
//	}
//    }

}
