package manager.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    private static final String AUTH_HEADER_NAME = "token";

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("虚拟现实后台管理文档")
                        .termsOfServiceUrl("")
                        .version("1.0")
                        .build())
                .groupName("1.0版本")
                .select()
                .apis(RequestHandlerSelectors.basePackage("tk.amrom.vrmanager.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes());

        return docket;
    }

    private List<SecurityScheme> securitySchemes() {
        return Arrays.asList(new ApiKey(AUTH_HEADER_NAME, "auth", In.HEADER.name()));
    }

}