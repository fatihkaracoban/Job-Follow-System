package com.fatihkaracoban.job_follow_system.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fatihkaracoban.job_follow_system.entities.Kullanici;
import com.fatihkaracoban.job_follow_system.repository.IKullaniciRepository;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/login").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());
        return http.build();
    }

    @Bean
    public CommandLineRunner initData(IKullaniciRepository repo, PasswordEncoder enc) {
        return args -> {
            if (repo.findByKullaniciAdi("admin").isEmpty()) {
                repo.save(new Kullanici(null, "admin", enc.encode("123456"), "ROLE_ADMIN"));
            }
        };
    }
}
