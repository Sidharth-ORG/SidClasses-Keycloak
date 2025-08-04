package com.learn.SidClasses.Configs;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info=@Info(
                title="Class of Sid",
                description = "This Product is designed by Nikku & Team",
                version = "1.0v",
                contact = @Contact(
                        name ="team sid class",
                        email = "sidharthbhuyan02@gmail.com",
                        url = "https://sidclass.com"
                ),
                license = @License(
                        url = "https://sidclass.com",
                        name="Apache License 2.0"
                )
        )
)
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class ProjectConfig {
    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }
}
