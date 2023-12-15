package com.example.project.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
//        registry.addViewController("/home.html");


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