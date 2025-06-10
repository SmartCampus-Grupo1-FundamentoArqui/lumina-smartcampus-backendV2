package upc.edu.pe.smartcampusbackend.auth.config.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**").permitAll()  // Permitir el acceso a Swagger
                                .anyRequest().authenticated()  // Requiere autenticación para otras rutas
                )
                .formLogin(Customizer.withDefaults());  // Desactiva la página de inicio de sesión predeterminada

        return http.build();
    }
}