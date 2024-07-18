package uol.compass.gabrielyoshino.ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Permissão para cadastrar, logar, resetar senha e resetar a senha liberada para todos
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset-password").permitAll()
                        // Permissão para criar, atualizar, ativar e desativar produtos somente para Admin
                        .requestMatchers(HttpMethod.POST, "/api/produtos/criar").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/produtos/atualizar/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/produtos/ativar/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/produtos/desativar/**").hasRole("ADMIN")
                        // Permissão para excluir e atualizar vendas liberada somente para Admin
                        .requestMatchers(HttpMethod.DELETE, "/api/excluir/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/atualizar/**").hasRole("ADMIN")
                        // Tentei restringir o acesso do H2 somente aos Admin mas não consegui
                        // Então deixei aberto para todos, mas por algum motivo, ele não deixa acessar mesmo com as credenciais corretas
                        .requestMatchers("/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
