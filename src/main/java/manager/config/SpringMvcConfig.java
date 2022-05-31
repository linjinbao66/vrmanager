package manager.config;

import manager.util.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/login.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }
    private static final List<String> EXCLUDE_PATH =
            Arrays.asList(
                    "/layui/css/modules/layer/default/*.png",
                    "/*.html",
                    "/img/*.png",
                    "/js/*.js",
                    "/layui/*.js",
                    "/css/*.css",
                    "/layui/css/*.css",
                    "/layui/font/*",
                    "/layui/css/modules/laydate/default/*.css",
                    "/layui/css/modules/layer/default/*.css",
                    "/layui/css/modules/*.css",
                    "/*.ico",
                    "/system/login",
                    "/system/users",
                    "/admin/login",
                    "/doc.html",
                    "/"
            );

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePathPatterns = new ArrayList<>();
        excludePathPatterns.add("/swagger-ui.html");
        excludePathPatterns.add("/swagger-resources/**");
        excludePathPatterns.add("/error");
        excludePathPatterns.add("/webjars/**");

        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(EXCLUDE_PATH).excludePathPatterns(excludePathPatterns);
    }
}