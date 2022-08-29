package ignitis_homework.config;

import ignitis_homework.common.OpenApi;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

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
                "Ignitis homework RestFull Api Documentation",
                "This is simple documentation using swagger and springFox",
                "0.0.1",
                "Ignitis homework term URL",
                new Contact("Kristupas Jovaiša", "www.ignitishomework.com", "kristupas.jovaisa03@gmail.com"),
                "Ignitis homework licence",
                "Licence URL",
                Collections.emptyList()
        );
    }
}
