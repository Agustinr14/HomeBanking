package com.example.homebanking.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//Web authorization se encarga de determinar quien tiene acceso a las diferentes parte de la pagina
@EnableWebSecurity
@Configuration
public class WebAuthorization{

    @Bean //Asocia con spring         Esto ya existe en spring
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http.authorizeRequests()
                .antMatchers("/web/index.html","/web/css/**","/web/img/**","/web/js/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/login","/api/clients").permitAll()
//-------------- Aca se le otorga permiso a ADMIN a secciones criticas de la aplicacion.
                .antMatchers(HttpMethod.POST,"/admin/loans").hasAuthority("ADMIN")
//-------------- Con hasAnyAuthority ademas permitimos tanto al client como al admin a los GET.
                .antMatchers("/**").hasAnyAuthority("CLIENT", "ADMIN");


        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout");

        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.exceptionHandling().authenticationEntryPoint( (req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED) );
        http.formLogin().successHandler((req, res, exc) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        return http.build();
    }
    private void clearAuthenticationAttributes(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }

}
