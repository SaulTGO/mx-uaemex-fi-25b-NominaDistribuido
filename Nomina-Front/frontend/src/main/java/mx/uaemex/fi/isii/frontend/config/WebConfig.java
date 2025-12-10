package mx.uaemex.fi.isii.frontend.config;

import mx.uaemex.fi.isii.frontend.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/login", "/css/**", "/js/**",
                        "/create-cookie",
                        "/logout",
                        "/login",
                        "/");
    }
}
