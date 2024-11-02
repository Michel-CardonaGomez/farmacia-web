package com.farmacia_web.farmacia_web.configuration;

import com.farmacia_web.farmacia_web.services.EmpleadoDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    EmpleadoDetailsService empleadoDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(empleadoDetailsService);
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/registro", "/login", "/facturas").permitAll() // Permitir acceso a las páginas de registro y login
                .requestMatchers("/css/**", "/js/**", "/imagenes/**").permitAll() // Permitir acceso a los recursos estáticos
                .requestMatchers("/admin").hasAnyRole("ADMINISTRADOR")
                .requestMatchers("/**").hasAnyRole("ADMINISTRADOR", "EMPLEADO")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("contrasena")
                .defaultSuccessUrl("/", true) // Redirigir tras login exitoso
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout") // Cerrar sesión
                .logoutSuccessUrl("/login?logout") // Redirigir a la página de inicio después de cerrar sesión
                .invalidateHttpSession(true) // Invalidar la sesión
                .deleteCookies("JSESSIONID") // Eliminar las cookies
                .permitAll();

        return http.build();
    }

}
