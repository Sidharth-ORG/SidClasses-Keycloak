package com.learn.SidClasses.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.SidClasses.Configs.CustomAcessDeniedHandler;
import com.learn.SidClasses.Configs.CustomAuthenticationEntryPoint;
import com.learn.SidClasses.Customs_Constants.AppConstants;
import com.learn.SidClasses.Customs_Constants.CustomMesssage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) //this enables method level security
public class SecurityConfig {
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private CustomAcessDeniedHandler customAcessDeniedHandler;
    private CustomConverter customConverter;

    public SecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, CustomAcessDeniedHandler customAcessDeniedHandler, CustomConverter customConverter) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAcessDeniedHandler = customAcessDeniedHandler;
        this.customConverter = customConverter;
    }

    @Bean
    public SecurityFilterChain securityfilterchain(HttpSecurity https)throws Exception{

        https.authorizeHttpRequests(auth->
                    auth.requestMatchers("/doc.html","/v3/api-docs/**","swagger-ui/**","/swagger-resource/**","/all").permitAll()
                        .requestMatchers(HttpMethod.GET,AppConstants.Default_base_api).hasAnyRole(AppConstants.Default_Staff)
                        .requestMatchers(HttpMethod.POST,AppConstants.Default_base_api).hasRole(AppConstants.Default_Admin)
                        .requestMatchers(HttpMethod.PUT,AppConstants.Default_base_api).hasRole(AppConstants.Default_Admin)
                        .requestMatchers(HttpMethod.DELETE,AppConstants.Default_base_api).hasRole(AppConstants.Default_Admin)
                        .anyRequest().authenticated()

        );

    //csrf
        https.csrf(AbstractHttpConfigurer::disable);
    //cors
        https.cors(cors->{
            CorsConfiguration config=new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:5174/","http://localhost:8082/"));
            config.addAllowedMethod("*");
            config.addAllowedHeader("*");
            config.setAllowCredentials(true);
            config.setMaxAge(300L);
            UrlBasedCorsConfigurationSource urlbasedcorsconfsrc=new UrlBasedCorsConfigurationSource();
            urlbasedcorsconfsrc.registerCorsConfiguration("/**",config); //(api endpoint,rules)
            cors.configurationSource(urlbasedcorsconfsrc);
        });

    //for exception handaling
        https.exceptionHandling(e->
                e.authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAcessDeniedHandler));

    //for oAuth2
        https.oauth2ResourceServer(resourceserverconfigurer->resourceserverconfigurer.jwt(
                jwtconfigurer->jwtconfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())
        ));

       return https.build();
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtAuthenticationConverter jauthconverter=new JwtAuthenticationConverter();
        jauthconverter.setJwtGrantedAuthoritiesConverter(customConverter);
        return jauthconverter;
    }






}
