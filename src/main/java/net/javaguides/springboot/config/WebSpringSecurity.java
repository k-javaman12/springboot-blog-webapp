package net.javaguides.springboot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.javaguides.springboot.util.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSpringSecurity {

    private UserDetailsService userDetailsService;

    public WebSpringSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(new AntPathRequestMatcher("/resources/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/register/**")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/guest/**"))
                                // Give guest access to guest and GUEST now
                                .hasAnyRole("ADMIN", "GUEST")
                                .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/post/**")).permitAll()
                                // The pattern "/{postId:[\\d]+}" ensures that only numeric values (like /1, /2, etc.) match and are permitted without authentication.
                                .requestMatchers(new AntPathRequestMatcher("/{postId:[\\d]+}")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/page/search", "GET")).permitAll()
                                .requestMatchers(new AntPathRequestMatcher("/posts/category/**")).permitAll()
                                .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(myAuthenticationSuccessHandler())
                        .loginProcessingUrl("/login")
                        .permitAll()
                ).logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                );
        return http.build();
    }

    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, IOException, ServletException {
                String role = SecurityUtils.getRole();
                if ("ROLE_GUEST".equals(role)) {
                    getRedirectStrategy().sendRedirect(request, response, "/guest/posts");
                } else if ("ROLE_ADMIN".equals(role)) { // You can extend this for other roles as needed
                    getRedirectStrategy().sendRedirect(request, response, "/admin/posts");
                } else {
                    super.onAuthenticationSuccess(request, response, authentication);
                }
            }
        };
    }

}
