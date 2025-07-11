package it.uniroma3.siw.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

	@Autowired
	private DataSource dataSource;

	//Questo metodo definisce le query SQL per ottenere username e password
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.authoritiesByUsernameQuery("SELECT username, ruolo as role FROM credenziali WHERE username = ?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username = ?");
	}

	//Questo metodo definisce il componente "passwordEncoder", usato per criptare e decriptare la password nella sorgente dati.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	//Questo metodo contiene le impostazioni della configurazione di autenticatzione e autorizzazione
	@Bean
	protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.cors(cors -> cors.disable())
		.csrf(csrf -> csrf.disable()) // solo per debug
		.authorizeHttpRequests(requests -> requests
				.requestMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/**.css", "/css/**", "/js/**", "/images/**", "/favicon.ico", "/webjars/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				//.requestMatchers("/user/**").hasAuthority("DEFAULT")
				.anyRequest().authenticated()
				)
		.formLogin(login -> login
				.loginPage("/login")
				.permitAll()
				.defaultSuccessUrl("/", true) // redirect solo se non c'era pagina precedente
				.failureUrl("/login?error=true")
				)
		.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.clearAuthentication(true)
				.permitAll()
				);

		return httpSecurity.build();
	}
}