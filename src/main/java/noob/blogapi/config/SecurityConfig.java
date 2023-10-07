package noob.blogapi.config;

import lombok.RequiredArgsConstructor;
import noob.blogapi.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailService;
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return  http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                   auth.requestMatchers(HttpMethod.GET,"/api/**").permitAll();
                   auth.requestMatchers("/api/auth/**").permitAll();
                   auth.anyRequest().authenticated();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailService);
        return new ProviderManager(authProvider);
    }



    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails abdo = User.builder()
                .username("abdo")
                .password(passwordEncoder().encode("abdo"))
                .roles("ADMIN")
                .build();
        UserDetails ahmed = User.builder()
                .username("ahmed")
                .password(passwordEncoder().encode("ahmed"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(abdo, ahmed);
    }*/
}
