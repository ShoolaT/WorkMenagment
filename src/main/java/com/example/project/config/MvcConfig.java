package com.example.project.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig()
    {
        super();
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/post_new.html");
       registry.addViewController("/home.ftlh");


    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor());
    }
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**",
                        "/css/**",
                        "/resources/**",
                        "/js/**",
                        "/images/**",
                        "/font-awesome/**"

                )
                .addResourceLocations(
                       "classpath:/static/images/",
                        "classpath:/static/api/",
                        "classpath:/resources/"
                      );
    }
}