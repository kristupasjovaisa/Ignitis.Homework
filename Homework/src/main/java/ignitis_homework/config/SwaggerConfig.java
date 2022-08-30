package ignitis_homework.config;

import ignitis_homework.common.OpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(OpenApi.class))
                .build();
    }

    private static ApiInfo getInfo() {
        return new ApiInfo(
                "Ignitis homework RESTful API Documentation",
                "This is the Ignitis homework documentation",
                "1.0.0",
                null,
                new Contact("Kristupas Jovaiša", null, "kristupas.jovaisa03@gmail.com"),
                null,
                null,
                Collections.emptyList()
        );
    }
}
